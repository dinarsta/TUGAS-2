import java.util.Scanner;

public class Main {

    static Menu[] daftarMenu = new Menu[100];
    static int jumlahMenu = 0;

    static String[] pesananNama = new String[100];
    static int[] pesananJumlah = new int[100];
    static double[] pesananHarga = new double[100];
    static int jumlahPesanan = 0;

    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {

        tambahMenuDefault();

        while (true) {
            System.out.println("\n=== APLIKASI RESTORAN ===");
            System.out.println("1. Menu Pelanggan (Pemesanan)");
            System.out.println("2. Menu Pengelolaan Restoran");
            System.out.println("3. Keluar");
            System.out.print("Pilih menu: ");

            int pilih = bacaAngka();

            switch (pilih) {
                case 1: menuPelanggan(); break;
                case 2: menuPengelolaan(); break;
                case 3:
                    System.out.println("Terima kasih!");
                    return;
                default:
                    System.out.println("Pilihan tidak valid!");
            }
        }
    }

    // =====================================
    // MENU DEFAULT
    // =====================================
    public static void tambahMenuDefault() {
        daftarMenu[jumlahMenu++] = new Menu("Nasi Goreng", 25000, "makanan");
        daftarMenu[jumlahMenu++] = new Menu("Ayam Bakar", 30000, "makanan");
        daftarMenu[jumlahMenu++] = new Menu("Sate Ayam", 28000, "makanan");
        daftarMenu[jumlahMenu++] = new Menu("Mie Goreng", 20000, "makanan");

        daftarMenu[jumlahMenu++] = new Menu("Es Teh", 8000, "minuman");
        daftarMenu[jumlahMenu++] = new Menu("Jus Jeruk", 12000, "minuman");
        daftarMenu[jumlahMenu++] = new Menu("Kopi Hitam", 10000, "minuman");
        daftarMenu[jumlahMenu++] = new Menu("Air Mineral", 5000, "minuman");
    }

    // =====================================
    // MENU PELANGGAN
    // =====================================
    public static void menuPelanggan() {
        jumlahPesanan = 0;

        while (true) {
            System.out.println("\n=== MENU PELANGGAN ===");
            tampilkanMenu();

            System.out.print("Masukkan nomor menu (0 = selesai): ");
            int nomor = bacaAngka();

            if (nomor == 0) {
                cetakStruk();
                return;
            }

            if (nomor < 1 || nomor > jumlahMenu) {
                System.out.println("Nomor menu tidak valid!");
                continue;
            }

            System.out.print("Jumlah: ");
            int jumlah = bacaAngka();

            pesananNama[jumlahPesanan] = daftarMenu[nomor - 1].nama;
            pesananJumlah[jumlahPesanan] = jumlah;
            pesananHarga[jumlahPesanan] = daftarMenu[nomor - 1].harga;
            jumlahPesanan++;

            System.out.println("✔ Pesanan ditambahkan!");
        }
    }

    // =====================================
    // MENU PENGELOLAAN
    // =====================================
    public static void menuPengelolaan() {
        while (true) {
            System.out.println("\n=== MENU PENGELOLAAN ===");
            System.out.println("1. Tambah Menu Baru");
            System.out.println("2. Ubah Harga Menu");
            System.out.println("3. Hapus Menu");
            System.out.println("4. Kembali");
            System.out.print("Pilih: ");

            int pilih = bacaAngka();

            switch (pilih) {
                case 1: tambahMenuBaru(); break;
                case 2: ubahHargaMenu(); break;
                case 3: hapusMenu(); break;
                case 4: return;
                default:
                    System.out.println("Pilihan salah!");
            }
        }
    }

    // =====================================
    // TAMPILKAN MENU
    // =====================================
    public static void tampilkanMenu() {
        System.out.println("\n--- DAFTAR MENU ---");
        for (int i = 0; i < jumlahMenu; i++) {
            System.out.println((i + 1) + ". " + daftarMenu[i].nama +
                               " (Rp " + daftarMenu[i].harga + ") [" +
                                daftarMenu[i].kategori + "]");
        }
    }

    // =====================================
    // TAMBAH MENU BARU
    // =====================================
    public static void tambahMenuBaru() {
        System.out.print("Nama menu baru: ");
        String nama = input.nextLine();

        System.out.print("Harga: ");
        double harga = bacaDouble();

        String kategori;
        while (true) {
            System.out.print("Kategori (makanan/minuman): ");
            kategori = input.nextLine();
            if (kategori.equalsIgnoreCase("makanan")
                    || kategori.equalsIgnoreCase("minuman")) break;
            System.out.println("Kategori salah!");
        }

        daftarMenu[jumlahMenu++] = new Menu(nama, harga, kategori);
        System.out.println("✔ Menu berhasil ditambahkan!");
    }

    // =====================================
    // UBAH HARGA MENU
    // =====================================
    public static void ubahHargaMenu() {
        tampilkanMenu();

        System.out.print("Masukkan nomor menu yg ingin diubah: ");
        int nomor = bacaAngka();

        if (nomor < 1 || nomor > jumlahMenu) {
            System.out.println("Nomor tidak valid!");
            return;
        }

        System.out.print("Harga baru: ");
        double hargaBaru = bacaDouble();

        daftarMenu[nomor - 1].harga = hargaBaru;
        System.out.println("✔ Harga berhasil diubah!");
    }

    // =====================================
    // HAPUS MENU
    // =====================================
    public static void hapusMenu() {
        tampilkanMenu();

        System.out.print("Masukkan nomor menu yg ingin dihapus: ");
        int nomor = bacaAngka();

        if (nomor < 1 || nomor > jumlahMenu) {
            System.out.println("Nomor tidak valid!");
            return;
        }

        for (int i = nomor - 1; i < jumlahMenu - 1; i++) {
            daftarMenu[i] = daftarMenu[i + 1];
        }

        jumlahMenu--;
        System.out.println("✔ Menu berhasil dihapus!");
    }

    // =====================================
    // CETAK STRUK
    // =====================================
    public static void cetakStruk() {
        System.out.println("\n=== STRUK PEMBAYARAN ===");

        double subtotal = 0;

        for (int i = 0; i < jumlahPesanan; i++) {
            double totalItem = pesananHarga[i] * pesananJumlah[i];
            subtotal += totalItem;

            System.out.println("- " + pesananNama[i] +
                    " x " + pesananJumlah[i] + " = Rp " + totalItem);
        }

        double pajak = subtotal * 0.10;
        double service = 20000;
        double total = subtotal + pajak + service;

        System.out.println("\nSubtotal: Rp " + subtotal);
        System.out.println("Pajak (10%): Rp " + pajak);
        System.out.println("Service: Rp " + service);
        System.out.println("\nTOTAL BAYAR: Rp " + total);
    }

    // =====================================
    // FUNGSI BACA INPUT AMAN
    // =====================================
    public static int bacaAngka() {
        while (!input.hasNextInt()) {
            System.out.print("Masukkan angka yang benar: ");
            input.next();
        }
        int angka = input.nextInt();
        input.nextLine();
        return angka;
    }

    public static double bacaDouble() {
        while (!input.hasNextDouble()) {
            System.out.print("Masukkan angka yang benar: ");
            input.next();
        }
        double angka = input.nextDouble();
        input.nextLine();
        return angka;
    }
}
