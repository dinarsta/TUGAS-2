import java.text.DecimalFormat;
import java.util.Scanner;

public class Main {
    // Kapasitas awal menu (akan di-resize jika diperlukan)
    static Menu[] daftarMenu = new Menu[20];
    static int menuCount = 0;

    // Untuk pemesanan: simpan daftar menggunakan array
    static Menu[] orderedMenus = new Menu[50];
    static int[] orderedQty = new int[50];
    static int orderCount = 0;

    static final double TAX_RATE = 0.10; // Tarif pajak 10%
    static final int SERVICE_FEE = 20000; // Biaya layanan tetap

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        seedMenus();
        mainMenu();
    }

    // Inisialisasi menu awal
    
    static void seedMenus() {
        addMenuInternal(new Menu("Ayam Geprek", 15000, "Makanan"));
        addMenuInternal(new Menu("Mie Goreng", 12000, "Makanan"));
        addMenuInternal(new Menu("Nasi Goreng", 15000, "Makanan"));
        addMenuInternal(new Menu("Sate Ayam", 18000, "Makanan"));

        addMenuInternal(new Menu("Es Teh", 5000, "Minuman"));
        addMenuInternal(new Menu("Es Jeruk", 7000, "Minuman"));
        addMenuInternal(new Menu("Kopi", 10000, "Minuman"));
        addMenuInternal(new Menu("Lemon Tea", 8000, "Minuman"));
    }

 
    // Tambah menu ke array internal
    
    static void addMenuInternal(Menu m) {
        if (menuCount >= daftarMenu.length) resizeMenuArray();
        daftarMenu[menuCount++] = m;
    }


    // Perbesar array menu jika penuh
  
    static void resizeMenuArray() {
        Menu[] tmp = new Menu[daftarMenu.length * 2];
        System.arraycopy(daftarMenu, 0, tmp, 0, daftarMenu.length);
        daftarMenu = tmp;
    }

   
    // Perbesar array pemesanan jika diperlukan
  
    static void resizeOrderArraysIfNeeded() {
        if (orderCount >= orderedMenus.length - 1) {
            Menu[] tmpM = new Menu[orderedMenus.length * 2];
            int[] tmpQ = new int[orderedQty.length * 2];
            System.arraycopy(orderedMenus, 0, tmpM, 0, orderedMenus.length);
            System.arraycopy(orderedQty, 0, tmpQ, 0, orderedQty.length);
            orderedMenus = tmpM;
            orderedQty = tmpQ;
        }
    }

  
    // Menu utama
   
    static void mainMenu() {
        while (true) {
            System.out.println("\n=== APLIKASI RESTORAN ===");
            System.out.println("1. Pemesanan (Customer)");
            System.out.println("2. Manajemen Menu (Owner)");
            System.out.println("3. Keluar");
            System.out.print("Pilih: ");
            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1": menuPelanggan(); break;
                case "2": manajemenMenu(); break;
                case "3":
                    System.out.println("Terima kasih. Program selesai.");
                    return;
                default:
                    System.out.println("Pilihan tidak valid. Coba lagi.");
            }
        }
    }

    // Alur pelanggan

    static void menuPelanggan() {
        orderCount = 0; // reset pesanan sebelumnya
        System.out.println("\n-- Menu Pemesanan --");
        tampilMenuBergrup();

        while (true) {
            System.out.print("\nMasukkan nama menu (ketik 'selesai' untuk selesai): ");
            String nama = sc.nextLine().trim();
            if (nama.equalsIgnoreCase("selesai")) break;

            Menu found = findMenuByName(nama);
            if (found == null) {
                System.out.println("Menu tidak ditemukan. Silakan masukkan nama menu yang benar.");
                continue;
            }

            int qty = 0;
            while (true) {
                System.out.print("Jumlah: ");
                String qtyStr = sc.nextLine().trim();
                try {
                    qty = Integer.parseInt(qtyStr);
                    if (qty <= 0) {
                        System.out.println("Jumlah harus lebih besar dari 0.");
                        continue;
                    }
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Input tidak valid. Masukkan angka.");
                }
            }

            // Tambahkan ke pesanan
            boolean appended = false;
            for (int i = 0; i < orderCount; i++) {
                if (orderedMenus[i].getNama().equalsIgnoreCase(found.getNama())) {
                    orderedQty[i] += qty;
                    appended = true;
                    break;
                }
            }
            if (!appended) {
                resizeOrderArraysIfNeeded();
                orderedMenus[orderCount] = found;
                orderedQty[orderCount] = qty;
                orderCount++;
            }
            System.out.println(qty + " x " + found.getNama() + " ditambahkan ke pesanan.");
        }

        if (orderCount == 0) {
            System.out.println("Tidak ada pesanan. Kembali ke menu utama.");
            return;
        }

        prosesPemesanan();
    }

   
    // Cari menu berdasarkan nama
  
    static Menu findMenuByName(String nama) {
        for (int i = 0; i < menuCount; i++) {
            if (daftarMenu[i].getNama().equalsIgnoreCase(nama)) return daftarMenu[i];
        }
        return null;
    }

  
    // Proses dan konfirmasi pesanan
 
    static void prosesPemesanan() {
        System.out.println("\n-- Konfirmasi Pesanan --");
        for (int i = 0; i < orderCount; i++) {
            System.out.printf("%d. %s x%d -> Rp %d\n",
                    i + 1, orderedMenus[i].getNama(), orderedQty[i], orderedMenus[i].getHarga());
        }

        Receipt receipt = hitungTotalDanDiskon();
        cetakStruk(receipt);
    }

    // Logika harga, pajak, dan diskon
   
    static class Receipt {
        int subtotal;
        int drinkDiscount;
        double discount10Percent;
        double tax;
        int service;
        double totalFinal;
    }

    static Receipt hitungTotalDanDiskon() {
        Receipt r = new Receipt();
        r.service = SERVICE_FEE;

        int subtotal = 0;
        for (int i = 0; i < orderCount; i++) {
            subtotal += orderedMenus[i].getHarga() * orderedQty[i];
        }
        r.subtotal = subtotal;

        int drinkDiscount = 0;
        if (subtotal > 50000) {
            for (int i = 0; i < orderCount; i++) {
                if (orderedMenus[i].getKategori().equalsIgnoreCase("Minuman")) {
                    int qty = orderedQty[i];
                    int freeItems = qty / 2;
                    drinkDiscount += freeItems * orderedMenus[i].getHarga();
                }
            }
        }
        r.drinkDiscount = drinkDiscount;

        double afterDrinkPromo = subtotal - drinkDiscount;
        double discount10 = 0;
        if (afterDrinkPromo > 100000) discount10 = 0.10 * afterDrinkPromo;
        r.discount10Percent = discount10;

        double afterDiscounts = afterDrinkPromo - discount10;
        r.tax = TAX_RATE * afterDiscounts;
        r.totalFinal = afterDiscounts + r.tax + r.service;
        return r;
    }

  
    // Cetak struk pembayaran
   
    static void cetakStruk(Receipt r) {
        DecimalFormat df = new DecimalFormat("#,###");
        System.out.println("\n========== STRUK PEMBAYARAN ==========");
        for (int i = 0; i < orderCount; i++) {
            int lineTotal = orderedMenus[i].getHarga() * orderedQty[i];
            System.out.printf("%-20s x%-3d : Rp %s\n",
                    orderedMenus[i].getNama(), orderedQty[i], df.format(lineTotal));
        }
        System.out.println("--------------------------------------");
        System.out.printf("%-25s: Rp %s\n", "Subtotal", df.format(r.subtotal));
        if (r.drinkDiscount > 0)
            System.out.printf("%-25s: -Rp %s\n", "Promo Beli 1 Gratis 1 (Minuman)", df.format(r.drinkDiscount));
        if (r.discount10Percent > 0)
            System.out.printf("%-25s: -Rp %s\n", "Diskon 10%", df.format(Math.round(r.discount10Percent)));

        double amountAfterDiscounts = r.subtotal - r.drinkDiscount - r.discount10Percent;
        System.out.printf("%-25s: Rp %s\n", "Jumlah setelah diskon", df.format(Math.round(amountAfterDiscounts)));
        System.out.printf("%-25s: Rp %s\n", "Pajak (10%)", df.format(Math.round(r.tax)));
        System.out.printf("%-25s: Rp %s\n", "Biaya Pelayanan", df.format(r.service));
        System.out.println("--------------------------------------");
        System.out.printf("%-25s: Rp %s\n", "TOTAL AKHIR", df.format(Math.round(r.totalFinal)));
        System.out.println("======================================");
        System.out.println("Terima kasih telah memesan!");
    }

 
    // Manajemen menu untuk owner

    static void manajemenMenu() {
        while (true) {
            System.out.println("\n-- Manajemen Menu --");
            System.out.println("1. Lihat Daftar Menu");
            System.out.println("2. Tambah Menu");
            System.out.println("3. Ubah Harga Menu");
            System.out.println("4. Hapus Menu");
            System.out.println("5. Kembali");
            System.out.print("Pilih: ");
            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1": tampilMenuBergrup(); break;
                case "2": tambahMenu(); break;
                case "3": ubahHargaMenu(); break;
                case "4": hapusMenu(); break;
                case "5": return;
                default: System.out.println("Pilihan tidak valid.");
            }
        }
    }

    // Tampilkan menu per kategori
    
    static void tampilMenuBergrup() {
        System.out.println("\n--- MAKANAN ---");
        for (int i = 0; i < menuCount; i++) {
            if (daftarMenu[i].getKategori().equalsIgnoreCase("Makanan"))
                System.out.printf("%d. %s - Rp %d\n", i + 1, daftarMenu[i].getNama(), daftarMenu[i].getHarga());
        }

        System.out.println("\n--- MINUMAN ---");
        for (int i = 0; i < menuCount; i++) {
            if (daftarMenu[i].getKategori().equalsIgnoreCase("Minuman"))
                System.out.printf("%d. %s - Rp %d\n", i + 1, daftarMenu[i].getNama(), daftarMenu[i].getHarga());
        }
    }

  
    // Tambah menu baru
 
    static void tambahMenu() {
        System.out.print("Berapa menu baru yang ingin ditambahkan? ");
        int n = readPositiveInt();
        for (int i = 0; i < n; i++) {
            System.out.println("Menu ke-" + (i + 1));
            System.out.print("Nama menu: ");
            String nama = sc.nextLine().trim();

            int harga;
            while (true) {
                System.out.print("Harga (Rp): ");
                String hs = sc.nextLine().trim();
                try {
                    harga = Integer.parseInt(hs);
                    if (harga <= 0) { System.out.println("Harga harus positif."); continue; }
                    break;
                } catch (NumberFormatException e) { System.out.println("Input tidak valid. Masukkan angka."); }
            }

            String kategori;
            while (true) {
                System.out.print("Kategori (Makanan/Minuman): ");
                kategori = sc.nextLine().trim();
                if (kategori.equalsIgnoreCase("Makanan") || kategori.equalsIgnoreCase("Minuman")) {
                    kategori = kategori.equalsIgnoreCase("Makanan") ? "Makanan" : "Minuman";
                    break;
                } else { System.out.println("Kategori harus 'Makanan' atau 'Minuman'."); }
            }

            addMenuInternal(new Menu(nama, harga, kategori));
            System.out.println("Menu '" + nama + "' berhasil ditambahkan.");
        }
    }

  
    // Ubah harga menu
   
    static void ubahHargaMenu() {
        tampilMenuDenganNomor();
        if (menuCount == 0) { System.out.println("Tidak ada menu."); return; }

        int nomor;
        while (true) {
            System.out.print("Masukkan nomor menu yang ingin diubah: ");
            String s = sc.nextLine().trim();
            try {
                nomor = Integer.parseInt(s);
                if (nomor < 1 || nomor > menuCount) { System.out.println("Nomor tidak valid."); continue; }
                break;
            } catch (NumberFormatException e) { System.out.println("Input tidak valid. Masukkan angka."); }
        }

        Menu m = daftarMenu[nomor - 1];
        System.out.println("Menu dipilih: " + m.getNama() + " - Rp " + m.getHarga());
        System.out.print("Harga baru (Rp): ");
        int newPrice = readPositiveInt();
        System.out.print("Konfirmasi perubahan? (Ya/Tidak): ");
        String c = sc.nextLine().trim();
        if (c.equalsIgnoreCase("Ya")) { m.setHarga(newPrice); System.out.println("Harga berhasil diubah."); }
        else { System.out.println("Perubahan dibatalkan."); }
    }

   
    // Hapus menu
 
    static void hapusMenu() {
        tampilMenuDenganNomor();
        if (menuCount == 0) { System.out.println("Tidak ada menu."); return; }

        int nomor;
        while (true) {
            System.out.print("Masukkan nomor menu yang ingin dihapus: ");
            String s = sc.nextLine().trim();
            try {
                nomor = Integer.parseInt(s);
                if (nomor < 1 || nomor > menuCount) { System.out.println("Nomor tidak valid."); continue; }
                break;
            } catch (NumberFormatException e) { System.out.println("Input tidak valid. Masukkan angka."); }
        }

        Menu m = daftarMenu[nomor - 1];
        System.out.println("Menu dipilih: " + m.getNama() + " - Rp " + m.getHarga());
        System.out.print("Konfirmasi penghapusan? (Ya/Tidak): ");
        String c = sc.nextLine().trim();
        if (c.equalsIgnoreCase("Ya")) {
            for (int i = nomor - 1; i < menuCount - 1; i++) daftarMenu[i] = daftarMenu[i + 1];
            daftarMenu[menuCount - 1] = null;
            menuCount--;
            System.out.println("Menu berhasil dihapus.");
        } else { System.out.println("Penghapusan dibatalkan."); }
    }

   
    // Tampilkan menu dengan nomor
   
    static void tampilMenuDenganNomor() {
        System.out.println("\nDaftar Menu:");
        for (int i = 0; i < menuCount; i++) {
            System.out.printf("%d. %s (%s) - Rp %d\n",
                    i + 1, daftarMenu[i].getNama(), daftarMenu[i].getKategori(), daftarMenu[i].getHarga());
        }
    }

  
    // Baca input integer positif
  
    static int readPositiveInt() {
        while (true) {
            String s = sc.nextLine().trim();
            try {
                int v = Integer.parseInt(s);
                if (v <= 0) { System.out.print("Harap masukkan angka positif: "); continue; }
                return v;
            } catch (NumberFormatException e) { System.out.print("Input tidak valid. Masukkan angka: "); }
        }
    }
}
