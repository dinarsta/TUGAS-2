import java.util.Scanner;

class Menu {
    String nama;
    double harga;
    String kategori;

    Menu(String nama, double harga, String kategori) {
        this.nama = nama;
        this.harga = harga;
        this.kategori = kategori;
    }
}

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

            int pilih = inputAngka();

            switch (pilih) {
                case 1 -> menuPelanggan();
                case 2 -> menuPengelolaan();
                case 3 -> {
                    System.out.println("Terima kasih!");
                    return;
                }
                default -> System.out.println("Pilihan tidak valid!");
            }
        }
    }

    // ============================================
    // DEFAULT MENU
    // ============================================
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

    // ============================================
    // INPUT ANGKA AMAN
    // ============================================
    public static int inputAngka() {
        while (true) {
            try {
                return Integer.parseInt(input.nextLine());
            } catch (Exception e) {
                System.out.print("Input harus angka! Ulangi: ");
            }
        }
    }

    // ============================================
    // MENU PELANGGAN
    // ============================================
    public static void menuPelanggan() {
        jumlahPesanan = 0;

        while (true) {
            System.out.println("\n=== MENU PELANGGAN ===");
            tampilkanMenuDenganNomor();

            System.out.print("Pilih nomor menu (0 = selesai): ");
            int nomor = inputAngka();

            if (nomor == 0) {
                cetakStruk();
                return;
            }

            if (nomor < 1 || nomor > jumlahMenu) {
                System.out.println("Nomor tidak valid!");
                continue;
            }

            System.out.print("Jumlah: ");
            int jml = inputAngka();

            pesananNama[jumlahPesanan] = daftarMenu[nomor - 1].nama;
            pesananJumlah[jumlahPesanan] = jml;
            pesananHarga[jumlahPesanan] = daftarMenu[nomor - 1].harga;
            jumlahPesanan++;

            System.out.println("Pesanan ditambahkan!");
        }
    }

    // ============================================
    // MENU PENGELOLAAN
    // ============================================
    public static void menuPengelolaan() {
        while (true) {
            System.out.println("\n=== MENU PENGELOLAAN ===");
            System.out.println("1. Tambah Menu Baru");
            System.out.println("2. Ubah Harga Menu");
            System.out.println("3. Hapus Menu");
            System.out.println("4. Kembali");
            System.out.print("Pilih: ");

            int pilih = inputAngka();

            switch (pilih) {
                case 1 -> tambahMenuBaru();
                case 2 -> ubahHargaMenu();
                case 3 -> hapusMenu();
                case 4 -> { return; }
                default -> System.out.println("Pilihan salah!");
            }
        }
    }

    // ============================================
    // TAMPILKAN MENU
    // ============================================
    public static void tampilkanMenuDenganNomor() {
        System.out.println("\n--- MAKANAN ---");
        for (int i = 0; i < jumlahMenu; i++) {
            if (daftarMenu[i].kategori.equals("makanan")) {
                System.out.println((i+1) + ". " + daftarMenu[i].nama + " - Rp " + daftarMenu[i].harga);
            }
        }

        System.out.println("\n--- MINUMAN ---");
        for (int i = 0; i < jumlahMenu; i++) {
            if (daftarMenu[i].kategori.equals("minuman")) {
                System.out.println((i+1) + ". " + daftarMenu[i].nama + " - Rp " + daftarMenu[i].harga);
            }
        }
    }

    // ============================================
    // TAMBAH MENU BARU
    // ============================================
    public static void tambahMenuBaru() {
        System.out.print("Nama menu: ");
        String nama = input.nextLine();

        System.out.print("Harga: ");
        double harga = Double.parseDouble(input.nextLine());

        String kategori;
        while (true) {
            System.out.print("Kategori (makanan/minuman): ");
            kategori = input.nextLine().toLowerCase();
            if (kategori.equals("makanan") || kategori.equals("minuman")) break;
            System.out.println("Kategori tidak valid!");
        }

        daftarMenu[jumlahMenu++] = new Menu(nama, harga, kategori);
        System.out.println("Menu ditambahkan!");
    }

    // ============================================
    // UBAH HARGA
    // ============================================
    public static void ubahHargaMenu() {
        tampilkanMenuDenganNomor();
        System.out.print("Pilih nomor menu: ");
        int nomor = inputAngka();

        if (nomor < 1 || nomor > jumlahMenu) {
            System.out.println("Nomor tidak valid!");
            return;
        }

        System.out.print("Yakin ubah harga? (Ya/Tidak): ");
        if (!input.nextLine().equalsIgnoreCase("Ya")) {
            System.out.println("Dibatalkan.");
            return;
        }

        System.out.print("Harga baru: ");
        double hargaBaru = Double.parseDouble(input.nextLine());
        daftarMenu[nomor-1].harga = hargaBaru;

        System.out.println("Harga berhasil diperbarui!");
    }

    // ============================================
    // HAPUS MENU
    // ============================================
    public static void hapusMenu() {
        tampilkanMenuDenganNomor();
        System.out.print("Pilih nomor menu: ");
        int nomor = inputAngka();

        if (nomor < 1 || nomor > jumlahMenu) {
            System.out.println("Nomor tidak valid!");
            return;
        }

        System.out.print("Yakin hapus? (Ya/Tidak): ");
        if (!input.nextLine().equalsIgnoreCase("Ya")) {
            System.out.println("Dibatalkan.");
            return;
        }

        for (int i = nomor - 1; i < jumlahMenu - 1; i++) {
            daftarMenu[i] = daftarMenu[i + 1];
        }
        jumlahMenu--;

        System.out.println("Menu berhasil dihapus!");
    }

    // ============================================
    // CETAK STRUK
    // ============================================
    public static void cetakStruk() {
        System.out.println("\n=== STRUK PEMBAYARAN ===");

        double subtotal = 0;
        boolean promoBOGO = false;

        for (int i = 0; i < jumlahPesanan; i++) {
            double totalItem = pesananHarga[i] * pesananJumlah[i];
            subtotal += totalItem;
            System.out.println(pesananNama[i] + " x " + pesananJumlah[i] +
                    " = Rp " + totalItem);
        }

        // Promo BOGO
        if (subtotal > 50_000) {
            promoBOGO = true;
            System.out.println("\nPromo: Beli 1 Gratis 1 Minuman (BOGO)");
        }

        double diskon = (subtotal > 100_000) ? subtotal * 0.10 : 0;
        double pajak = subtotal * 0.10;
        double service = 20_000;

        double total = subtotal + pajak + service - diskon;

        System.out.println("\nSubtotal: Rp " + subtotal);
        System.out.println("Pajak (10%): Rp " + pajak);
        System.out.println("Biaya Service: Rp " + service);
        System.out.println("Diskon: Rp " + diskon);

        if (promoBOGO) {
            System.out.println("Promo BOGO diterapkan.");
        }

        System.out.println("\nTOTAL BAYAR: Rp " + total);
    }
}
