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

            int pilih = input.nextInt();
            input.nextLine();

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

    // ==================================================
    // DEFAULT MENU (4 makanan, 4 minuman)
    // ==================================================
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

    // ==================================================
    // MENU PELANGGAN
    // ==================================================
    public static void menuPelanggan() {
        jumlahPesanan = 0;

        while (true) {
            System.out.println("\n=== MENU PELANGGAN ===");
            tampilkanMenu();

            System.out.print("Masukkan nama menu (atau 'selesai'): ");
            String nama = input.nextLine();

            if (nama.equalsIgnoreCase("selesai")) {
                cetakStruk();
                return;
            }

            int idx = cariMenu(nama);
            if (idx == -1) {
                System.out.println("Menu tidak ditemukan, coba lagi!");
                continue;
            }

            System.out.print("Jumlah: ");
            int jumlah = input.nextInt();
            input.nextLine();

            pesananNama[jumlahPesanan] = daftarMenu[idx].nama;
            pesananJumlah[jumlahPesanan] = jumlah;
            pesananHarga[jumlahPesanan] = daftarMenu[idx].harga;
            jumlahPesanan++;

            System.out.println("Pesanan ditambahkan!");
        }
    }

    // ==================================================
    // MENU PENGELOLAAN
    // ==================================================
    public static void menuPengelolaan() {
        while (true) {
            System.out.println("\n=== MENU PENGELOLAAN ===");
            System.out.println("1. Tambah Menu Baru");
            System.out.println("2. Ubah Harga Menu");
            System.out.println("3. Hapus Menu");
            System.out.println("4. Kembali");
            System.out.print("Pilih: ");

            int pilih = input.nextInt();
            input.nextLine();

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

    // ==================================================
    // FUNGSI MENAMPILKAN MENU
    // ==================================================
    public static void tampilkanMenu() {
        System.out.println("\n--- MAKANAN ---");
        for (int i = 0; i < jumlahMenu; i++) {
            if (daftarMenu[i].kategori.equals("makanan")) {
                System.out.println((i+1) + ". " + daftarMenu[i].nama + " (Rp " + daftarMenu[i].harga + ")");
            }
        }

        System.out.println("\n--- MINUMAN ---");
        for (int i = 0; i < jumlahMenu; i++) {
            if (daftarMenu[i].kategori.equals("minuman")) {
                System.out.println((i+1) + ". " + daftarMenu[i].nama + " (Rp " + daftarMenu[i].harga + ")");
            }
        }
    }

    public static int cariMenu(String nama) {
        for (int i = 0; i < jumlahMenu; i++) {
            if (daftarMenu[i].nama.equalsIgnoreCase(nama)) {
                return i;
            }
        }
        return -1;
    }

    // ==================================================
    // TAMBAH MENU BARU
    // ==================================================
    public static void tambahMenuBaru() {
        System.out.print("Nama menu baru: ");
        String nama = input.nextLine();

        System.out.print("Harga: ");
        double harga = input.nextDouble();
        input.nextLine();

        String kategori = "";
        while (true) {
            System.out.print("Kategori (makanan/minuman): ");
            kategori = input.nextLine();
            if (kategori.equalsIgnoreCase("makanan") || kategori.equalsIgnoreCase("minuman"))
                break;
            System.out.println("Kategori invalid!");
        }

        daftarMenu[jumlahMenu++] = new Menu(nama, harga, kategori);
        System.out.println("Menu berhasil ditambahkan!");
    }

    // ==================================================
    // UBAH HARGA MENU
    // ==================================================
    public static void ubahHargaMenu() {
        tampilkanMenu();
        System.out.print("Masukkan nomor menu yang ingin diubah: ");

        int nomor = input.nextInt();
        input.nextLine();

        if (nomor < 1 || nomor > jumlahMenu) {
            System.out.println("Nomor tidak valid!");
            return;
        }

        int idx = nomor - 1;

        System.out.print("Yakin ubah harga? (Ya/Tidak): ");
        String konfirmasi = input.nextLine();

        if (!konfirmasi.equalsIgnoreCase("Ya")) {
            System.out.println("Dibatalkan.");
            return;
        }

        System.out.print("Harga baru: ");
        daftarMenu[idx].harga = input.nextDouble();
        input.nextLine();

        System.out.println("Harga berhasil diperbarui!");
    }

    // ==================================================
    // HAPUS MENU
    // ==================================================
    public static void hapusMenu() {
        tampilkanMenu();
        System.out.print("Masukkan nomor menu yang ingin dihapus: ");

        int nomor = input.nextInt();
        input.nextLine();

        if (nomor < 1 || nomor > jumlahMenu) {
            System.out.println("Nomor tidak valid!");
            return;
        }

        System.out.print("Yakin hapus? (Ya/Tidak): ");
        String konfirmasi = input.nextLine();

        if (!konfirmasi.equalsIgnoreCase("Ya")) {
            System.out.println("Penghapusan dibatalkan.");
            return;
        }

        int idx = nomor - 1;
        for (int i = idx; i < jumlahMenu - 1; i++) {
            daftarMenu[i] = daftarMenu[i + 1];
        }
        jumlahMenu--;

        System.out.println("Menu berhasil dihapus!");
    }

    // ==================================================
    // CETAK STRUK
    // ==================================================
    public static void cetakStruk() {
        System.out.println("\n=== STRUK PEMBAYARAN ===");

        double subtotal = 0;
        boolean promoBOGO = false;

        for (int i = 0; i < jumlahPesanan; i++) {
            double totalItem = pesananHarga[i] * pesananJumlah[i];
            subtotal += totalItem;

            System.out.println(pesananNama[i] + " x " + pesananJumlah[i] + " = Rp " + totalItem);
        }

        // Promo BOGO minuman
        if (subtotal > 50000) {
            promoBOGO = true;
            System.out.println("\nPromo: Beli 1 Gratis 1 Minuman (BOGO)");
        }

        double diskon = 0;
        if (subtotal > 100000) {
            diskon = subtotal * 0.10;
        }

        double pajak = subtotal * 0.10;
        double service = 20000;
        double total = subtotal + pajak + service - diskon;

        System.out.println("\nSubtotal: Rp " + subtotal);
        System.out.println("Pajak 10%: Rp " + pajak);
        System.out.println("Service: Rp " + service);
        System.out.println("Diskon: Rp " + diskon);

        if (promoBOGO) {
            System.out.println("Promo BOGO diterapkan: Gratis 1 minuman");
        }

        System.out.println("\nTOTAL BAYAR: Rp " + total);
    }
}
