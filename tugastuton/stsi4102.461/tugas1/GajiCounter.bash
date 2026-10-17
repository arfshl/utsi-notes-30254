#!/bin/bash

# Menentukan Gaji Pokok
while true; do
    read -p "MASUKKAN GOLONGAN (A/B/C): " golongan
    # Ubah output ke uppercase
    golongan=${golongan^^}

    case "$golongan" in
        A) gaji=5000000; break ;;
        B) gaji=6500000; break ;;
        C) gaji=9500000; break ;;
        *) echo "GOLONGAN TIDAK VALID (A/B/C)!" ;;
    esac
done

# Menentukan Persentase Lembur
while true; do
    read -p "MASUKKAN JAM LEMBUR: " jam
    case "$jam" in
        0) persen=0; break ;;
        1) persen=30; break ;;
        2) persen=32; break ;;
        3) persen=34; break ;;
        4) persen=36; break ;;
        [5-9]|[1-9][0-9]*) persen=38; break ;;
        *) echo "JAM LEMBUR TIDAK DALAM FORMAT YANG VALID!" ;;
    esac
done

# Hitung Lembur dan Total Penghasilan
lembur=$((gaji * persen / 100))
total=$((gaji + lembur))

# Output
echo "Gaji Pokok : Rp $gaji"
echo "Bonus Lembur ($persen%): Rp $lembur"
echo "Total Penghasilan: Rp $total"