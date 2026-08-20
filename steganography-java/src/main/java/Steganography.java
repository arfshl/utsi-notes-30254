import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.imageio.ImageIO;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;
import java.util.concurrent.Callable;

@Command(name = "steganography", mixinStandardHelpOptions = true,
        description = "Simple and minimalist steganography tool written in Java")
public class Steganography implements Callable<Integer> {

    @Option(names = {"--encode"}, description = "Encode message into image")
    private boolean encode;

    @Option(names = {"--decode"}, description = "Decode message from image")
    private boolean decode;

    @Option(names = {"-i", "--image"}, required = true, description = "Image input path")
    private File imageFile;

    @Option(names = {"-o", "--output"}, description = "Image output path (only for encode mode)")
    private File outputFile;

    @Option(names = {"-m", "--message"}, description = "Message file path (only for encode mode)")
    private File messageFile;

    @Option(names = {"-p", "--password"}, description = "Optional password for encryption/decryption")
    private String password;

    public static void main(String[] args) {
        int exitCode = new CommandLine(new Steganography()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public Integer call() {
        if (!encode && !decode) {
            System.err.println("ERR: You must specify either --encode or --decode");
            return 1;
        }
        if (encode && decode) {
            System.err.println("ERR: Options --encode and --decode are mutually exclusive");
            return 1;
        }

        String pass = (password != null && !password.trim().isEmpty()) ? password.trim() : null;

        try {
            if (encode) {
                handleEncode(pass);
            } else {
                handleDecode(pass);
            }
        } catch (Exception e) {
            System.err.println("ERR: " + e.getMessage());
            return 1;
        }
        return 0;
    }

    private void handleEncode(String pass) throws Exception {
        if (messageFile == null) {
            System.err.println("ERR: Parameter --message <file.txt> is required for --encode mode");
            return;
        }
        if (!messageFile.exists()) {
            System.err.println("ERR: File message '" + messageFile.getPath() + "' not found!");
            return;
        }

        byte[] rawBytes = Files.readAllBytes(messageFile.toPath());
        if (rawBytes.length == 0) {
            System.out.println("WARN: Message file is empty. Proceeding to encode an empty message.");
        }

        byte[] payload;
        if (pass != null) {
            System.out.println("INFO: Encrypting message...");
            payload = encryptData(rawBytes, pass);
        } else {
            System.out.println("INFO: No password provided. Encoding in plain stego mode...");
            payload = rawBytes;
        }

        File destination = outputFile;
        if (destination == null) {
            String name = imageFile.getName();
            int dotIndex = name.lastIndexOf('.');
            String baseName = (dotIndex == -1) ? name : name.substring(0, dotIndex);
            destination = new File(imageFile.getParent(), baseName + "_stego.png");
        }

        System.out.println("INFO: Encoding message into '" + imageFile.getPath() + "'...");
        encodeImage(imageFile, payload, destination);
        System.out.println("INFO: Success! Image saved as '" + destination.getPath() + "'");
    }

    private void handleDecode(String pass) throws Exception {
        System.out.println("INFO: Reading from '" + imageFile.getPath() + "'...");
        byte[] payload = decodeImage(imageFile);

        byte[] finalBytes;
        if (pass != null) {
            System.out.println("INFO: Password provided. Decrypting...");
            finalBytes = decryptData(payload, pass);
        } else {
            System.out.println("INFO: No password provided. Reading plain payload...");
            finalBytes = payload;
        }

        String decryptedText = new String(finalBytes, StandardCharsets.UTF_8);
        System.out.println("\n+++ Hidden Message +++");
        System.out.println(decryptedText);
        System.out.println("+++ End of Message +++\n");
    }

    // --- Steganography Core Logic ---

    private void encodeImage(File inputImg, byte[] payload, File outputImg) throws Exception {
        BufferedImage img = ImageIO.read(inputImg);
        if (img == null) {
            throw new IllegalArgumentException("'" + inputImg.getPath() + "' is not a valid image file!");
        }

        int width = img.getWidth();
        int height = img.getHeight();

        // Standard 4-byte Big-Endian Length Header
        byte[] headerBytes = ByteBuffer.allocate(4).putInt(payload.length).array();
        byte[] fullPayload = new byte[headerBytes.length + payload.length];
        System.arraycopy(headerBytes, 0, fullPayload, 0, headerBytes.length);
        System.arraycopy(payload, 0, fullPayload, headerBytes.length, payload.length);

        long totalBits = (long) fullPayload.length * 8;
        long capacityBits = (long) width * height * 3;

        if (totalBits > capacityBits) {
            throw new IllegalArgumentException("Message is too large! Needs " + totalBits +
                    " bits, but image capacity is only " + capacityBits + " bits.");
        }

        int bitIdx = 0;
        outerLoop:
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = img.getRGB(x, y);
                int r = (rgb >> 16) & 0xFF;
                int g = (rgb >> 8) & 0xFF;
                int b = rgb & 0xFF;

                int[] colors = {r, g, b};
                for (int c = 0; c < 3; c++) {
                    if (bitIdx < totalBits) {
                        int bytePos = bitIdx / 8;
                        int bitOffset = 7 - (bitIdx % 8);
                        int bitVal = (fullPayload[bytePos] >> bitOffset) & 1;

                        colors[c] = (colors[c] & ~1) | bitVal;
                        bitIdx++;
                    }
                }

                int newRgb = (colors[0] << 16) | (colors[1] << 8) | colors[2];
                img.setRGB(x, y, newRgb);

                if (bitIdx >= totalBits) {
                    break outerLoop;
                }
            }
        }

        ImageIO.write(img, "png", outputImg);
    }

    private byte[] decodeImage(File inputImg) throws Exception {
        BufferedImage img = ImageIO.read(inputImg);
        if (img == null) {
            throw new IllegalArgumentException("'" + inputImg.getPath() + "' is not a valid image file!");
        }

        int width = img.getWidth();
        int height = img.getHeight();

        // 1. Read Header (First 32 bits / 4 bytes)
        int dataLength = 0;
        int bitsRead = 0;

        for (int y = 0; y < height && bitsRead < 32; y++) {
            for (int x = 0; x < width && bitsRead < 32; x++) {
                int rgb = img.getRGB(x, y);
                int[] colors = {(rgb >> 16) & 0xFF, (rgb >> 8) & 0xFF, rgb & 0xFF};

                for (int c = 0; c < 3 && bitsRead < 32; c++) {
                    dataLength = (dataLength << 1) | (colors[c] & 1);
                    bitsRead++;
                }
            }
        }

        long totalPayloadBits = (long) dataLength * 8;
        long capacityBits = (long) width * height * 3;

        if (32 + totalPayloadBits > capacityBits || dataLength < 0) {
            throw new IllegalArgumentException("Invalid header, wrong password, or corrupted data.");
        }

        // 2. Read Payload
        byte[] payload = new byte[dataLength];
        int payloadBitIdx = 0;
        int currentBit = 0;

        for (int y = 0; y < height && payloadBitIdx < totalPayloadBits; y++) {
            for (int x = 0; x < width && payloadBitIdx < totalPayloadBits; x++) {
                int rgb = img.getRGB(x, y);
                int[] colors = {(rgb >> 16) & 0xFF, (rgb >> 8) & 0xFF, rgb & 0xFF};

                for (int c = 0; c < 3 && payloadBitIdx < totalPayloadBits; c++) {
                    // Skip the first 32 bits (header)
                    if (currentBit >= 32) {
                        int bitVal = colors[c] & 1;
                        int bytePos = payloadBitIdx / 8;
                        payload[bytePos] = (byte) ((payload[bytePos] << 1) | bitVal);
                        payloadBitIdx++;
                    }
                    currentBit++;
                }
            }
        }

        return payload;
    }

    // --- Fernet-compatible Encryption / Decryption ---

    private byte[] encryptData(byte[] dataBytes, String password) throws Exception {
        byte[] salt = new byte[16];
        new SecureRandom().nextBytes(salt);

        byte[] key = deriveFernetKey(password, salt);
        byte[] ciphertext = fernetEncrypt(dataBytes, key);

        byte[] combined = new byte[salt.length + ciphertext.length];
        System.arraycopy(salt, 0, combined, 0, salt.length);
        System.arraycopy(ciphertext, 0, combined, salt.length, ciphertext.length);
        return combined;
    }

    private byte[] decryptData(byte[] combinedBytes, String password) throws Exception {
        if (combinedBytes.length < 17) {
            throw new IllegalArgumentException("Payload is too short to contain valid salt and ciphertext.");
        }

        byte[] salt = Arrays.copyOfRange(combinedBytes, 0, 16);
        byte[] ciphertext = Arrays.copyOfRange(combinedBytes, 16, combinedBytes.length);

        byte[] key = deriveFernetKey(password, salt);
        return fernetDecrypt(ciphertext, key);
    }

    private byte[] deriveFernetKey(String password, byte[] salt) throws Exception {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 600000, 256);
        SecretKey tmp = factory.generateSecret(spec);
        return tmp.getEncoded();
    }

    private byte[] fernetEncrypt(byte[] data, byte[] key) throws Exception {
        byte[] signingKey = Arrays.copyOfRange(key, 0, 16);
        byte[] encryptionKey = Arrays.copyOfRange(key, 16, 32);

        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);

        long timestamp = System.currentTimeMillis() / 1000L;
        byte[] timestampBytes = ByteBuffer.allocate(8).putLong(timestamp).array();

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(encryptionKey, "AES"), new IvParameterSpec(iv));
        byte[] ciphertext = cipher.doFinal(data);

        // Version (0x80) + Timestamp + IV + Ciphertext
        byte[] basicParts = new byte[1 + 8 + 16 + ciphertext.length];
        basicParts[0] = (byte) 0x80;
        System.arraycopy(timestampBytes, 0, basicParts, 1, 8);
        System.arraycopy(iv, 0, basicParts, 9, 16);
        System.arraycopy(ciphertext, 0, basicParts, 25, ciphertext.length);

        Mac hmac = Mac.getInstance("HmacSHA256");
        hmac.init(new SecretKeySpec(signingKey, "HmacSHA256"));
        byte[] hmacBytes = hmac.doFinal(basicParts);

        byte[] finalFernet = new byte[basicParts.length + 32];
        System.arraycopy(basicParts, 0, finalFernet, 0, basicParts.length);
        System.arraycopy(hmacBytes, 0, finalFernet, basicParts.length, 32);

        return Base64.getUrlEncoder().withoutPadding().encode(finalFernet);
    }

    private byte[] fernetDecrypt(byte[] fernetToken, byte[] key) throws Exception {
        byte[] decoded = Base64.getUrlDecoder().decode(fernetToken);

        byte[] signingKey = Arrays.copyOfRange(key, 0, 16);
        byte[] encryptionKey = Arrays.copyOfRange(key, 16, 32);

        byte[] hmacReceived = Arrays.copyOfRange(decoded, decoded.length - 32, decoded.length);
        byte[] basicParts = Arrays.copyOfRange(decoded, 0, decoded.length - 32);

        Mac hmac = Mac.getInstance("HmacSHA256");
        hmac.init(new SecretKeySpec(signingKey, "HmacSHA256"));
        byte[] hmacCalculated = hmac.doFinal(basicParts);

        if (!Arrays.equals(hmacReceived, hmacCalculated)) {
            throw new IllegalArgumentException("Password is wrong or file is corrupted. Decryption failed.");
        }

        byte[] iv = Arrays.copyOfRange(basicParts, 9, 25);
        byte[] ciphertext = Arrays.copyOfRange(basicParts, 25, basicParts.length);

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(encryptionKey, "AES"), new IvParameterSpec(iv));
        return cipher.doFinal(ciphertext);
    }
}