import java.util.Scanner;

class Menu {
    String nama;
    int harga;
    String kategori; // makanan / minuman

    Menu(String nama, int harga, String kategori) {
        this.nama = nama;
        this.harga = harga;
        this.kategori = kategori;
    }
}

public class Main {

    static Scanner input = new Scanner(System.in);
    static Menu[] menuList = new Menu[100];
    static int menuCount = 0;

    static String[] pesananNama = new String[100];
    static int[] pesananJumlah = new int[100];
    static int[] pesananHarga = new int[100];
    static int pesananCount = 0;

    public static void main(String[] args) {
        isiMenuAwal();
        menuUtama();
    }

    // -------------------------------
    // MENU AWAL
    // -------------------------------
    public static void menuUtama() {
        while (true) {
            System.out.println("\n=== MENU UTAMA ===");
            System.out.println("1. Pemesanan");
            System.out.println("2. Manajemen Menu");
            System.out.println("3. Keluar");
            System.out.print("Pilih: ");

            String pilih = input.nextLine();

            switch (pilih) {
                case "1":
                    pemesanan();
                    break;
                case "2":
                    manajemenMenu();
                    break;
                case "3":
                    System.out.println("Terima kasih!");
                    return;
                default:
                    System.out.println("Input salah!");
            }
        }
    }

    // -------------------------------
    // ISI MENU AWAL
    // -------------------------------
    public static void isiMenuAwal() {
        tambahMenu("Nasi Goreng", 20000, "makanan");
        tambahMenu("Ayam Bakar", 25000, "makanan");
        tambahMenu("Mie Goreng", 18000, "makanan");
        tambahMenu("Sate Ayam", 22000, "makanan");

        tambahMenu("Es Teh", 5000, "minuman");
        tambahMenu("Jus Mangga", 12000, "minuman");
        tambahMenu("Kopi Hitam", 8000, "minuman");
        tambahMenu("Air Mineral", 4000, "minuman");
    }

    // -------------------------------
    // FUNGSI TAMBAH MENU
    // -------------------------------
    public static void tambahMenu(String nama, int harga, String kategori) {
        menuList[menuCount] = new Menu(nama, harga, kategori);
        menuCount++;
    }

    // -------------------------------
    // MENAMPILKAN MENU
    // -------------------------------
    public static void tampilMenu(String kategori) {
        System.out.println("\n=== MENU " + kategori.toUpperCase() + " ===");

        for (int i = 0; i < menuCount; i++) {
            if (menuList[i].kategori.equals(kategori)) {
                System.out.println((i + 1) + ". " + menuList[i].nama + " - Rp" + menuList[i].harga);
            }
        }
    }

    // -------------------------------
    // PEMESANAN
    // -------------------------------
    public static void pemesanan() {
        pesananCount = 0;

        while (true) {
            System.out.println("\nPesan apa?");
            System.out.println("1. Makanan");
            System.out.println("2. Minuman");
            System.out.println("Ketik 'selesai' untuk selesai.");
            System.out.print("Pilih: ");

            String pilih = input.nextLine();

            if (pilih.equalsIgnoreCase("selesai")) {
                break;
            }

            switch (pilih) {
                case "1":
                    tampilMenu("makanan");
                    pilihMenuUntukDipesan();
                    break;
                case "2":
                    tampilMenu("minuman");
                    pilihMenuUntukDipesan();
                    break;
                default:
                    System.out.println("Input tidak valid!");
            }

            tampilPesananSementara();
        }

        hitungTotal();
    }

    // -------------------------------
    // PILIH MENU
    // -------------------------------
    public static void pilihMenuUntukDipesan() {
        while (true) {
            System.out.print("Masukkan nomor menu: ");
            String x = input.nextLine();

            int nomor;

            try {
                nomor = Integer.parseInt(x);
            } catch (Exception e) {
                System.out.println("Input harus angka!");
                continue;
            }

            if (nomor < 1 || nomor > menuCount) {
                System.out.println("Nomor menu tidak ada!");
                continue;
            }

            Menu m = menuList[nomor - 1];

            System.out.print("Jumlah: ");
            int jumlah = Integer.parseInt(input.nextLine());

            pesananNama[pesananCount] = m.nama;
            pesananJumlah[pesananCount] = jumlah;
            pesananHarga[pesananCount] = m.harga;
            pesananCount++;

            break;
        }
    }

    // -------------------------------
    // TAMPIL PESANAN SEMENTARA
    // -------------------------------
    public static void tampilPesananSementara() {
        System.out.println("\n=== PESANAN SEMENTARA ===");

        int total = 0;

        for (int i = 0; i < pesananCount; i++) {
            int subtotal = pesananJumlah[i] * pesananHarga[i];
            total += subtotal;

            System.out.println((i + 1) + ". " + pesananNama[i] + " x" + pesananJumlah[i] + " = Rp" + subtotal);
        }

        System.out.println("Total sementara: Rp" + total);
    }

    // -------------------------------
    // HITUNG TOTAL DAN CETAK STRUK
    // -------------------------------
    public static void hitungTotal() {
        int totalPesanan = 0;
        boolean adaMinuman = false;

        System.out.println("\n=== STRUK PEMBAYARAN ===");

        for (int i = 0; i < pesananCount; i++) {
            int subtotal = pesananJumlah[i] * pesananHarga[i];
            totalPesanan += subtotal;

            for (Menu m : menuList) {
                if (m != null && m.nama.equals(pesananNama[i]) && m.kategori.equals("minuman")) {
                    adaMinuman = true;
                }
            }

            System.out.println(pesananNama[i] + " x" + pesananJumlah[i] + " = Rp" + subtotal);
        }

        System.out.println("----------------------------------");
        System.out.println("Total Pesanan: Rp" + totalPesanan);

        // Pajak
        int pajak = (int) (totalPesanan * 0.10);
        System.out.println("Pajak 10%: Rp" + pajak);

        // Service fee
        int service = 20000;
        System.out.println("Biaya Pelayanan: Rp" + service);

        // Diskon
        int diskon = 0;
        if (totalPesanan > 100000) {
            diskon = (int) (totalPesanan * 0.10);
            System.out.println("Diskon 10%: -Rp" + diskon);
        }

        // Promo B1G1 minuman
        int promo = 0;
        if (totalPesanan > 50000 && adaMinuman) {
            promo = 1; // hanya info saja
            System.out.println("Promo: Beli 1 Gratis 1 Minuman");
        }

        int totalAkhir = totalPesanan + pajak + service - diskon;
        System.out.println("----------------------------------");
        System.out.println("TOTAL BAYAR: Rp" + totalAkhir);
    }

    // -------------------------------
    // MANAJEMEN MENU
    // -------------------------------
    public static void manajemenMenu() {
        while (true) {
            System.out.println("\n=== MANAJEMEN MENU ===");
            System.out.println("1. Tambah Menu");
            System.out.println("2. Ubah Harga Menu");
            System.out.println("3. Hapus Menu");
            System.out.println("4. Kembali");
            System.out.print("Pilih: ");

            switch (input.nextLine()) {
                case "1":
                    tambahMenuBaru();
                    break;
                case "2":
                    ubahHargaMenu();
                    break;
                case "3":
                    hapusMenu();
                    break;
                case "4":
                    return;
                default:
                    System.out.println("Input tidak valid!");
            }
        }
    }

    // -------------------------------
    // TAMBAH MENU BARU
    // -------------------------------
    public static void tambahMenuBaru() {
        System.out.print("Nama menu baru: ");
        String nama = input.nextLine();

        System.out.print("Harga: ");
        int harga = Integer.parseInt(input.nextLine());

        System.out.print("Kategori (makanan/minuman): ");
        String kategori = input.nextLine();

        tambahMenu(nama, harga, kategori);
        System.out.println("Menu berhasil ditambahkan!");
    }

    // -------------------------------
    // UBAH HARGA MENU
    // -------------------------------
    public static void ubahHargaMenu() {
        tampilSemuaMenu();

        System.out.print("Pilih nomor menu untuk diubah: ");
        int nomor = Integer.parseInt(input.nextLine());

        System.out.print("Yakin ubah? (Ya/Tidak): ");
        if (input.nextLine().equalsIgnoreCase("Ya")) {
            System.out.print("Harga baru: ");
            int harga = Integer.parseInt(input.nextLine());

            menuList[nomor - 1].harga = harga;
            System.out.println("Harga berhasil diubah!");
        } else {
            System.out.println("Dibatalkan.");
        }
    }

    // -------------------------------
    // HAPUS MENU
    // -------------------------------
    public static void hapusMenu() {
        tampilSemuaMenu();

        System.out.print("Pilih nomor menu untuk hapus: ");
        int nomor = Integer.parseInt(input.nextLine());

        System.out.print("Yakin hapus? (Ya/Tidak): ");
        if (input.nextLine().equalsIgnoreCase("Ya")) {
            for (int i = nomor - 1; i < menuCount - 1; i++) {
                menuList[i] = menuList[i + 1];
            }
            menuCount--;
            System.out.println("Menu berhasil dihapus!");
        } else {
            System.out.println("Dibatalkan.");
        }
    }

    // -------------------------------
    // TAMPIL SEMUA MENU
    // -------------------------------
    public static void tampilSemuaMenu() {
        System.out.println("\n=== DAFTAR MENU ===");
        for (int i = 0; i < menuCount; i++) {
            System.out.println((i + 1) + ". " + menuList[i].nama + " | Rp" + menuList[i].harga + " | " + menuList[i].kategori);
        }
    }
}
