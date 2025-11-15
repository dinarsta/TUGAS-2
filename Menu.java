public class Menu {
    private String nama;
    private int harga; // in Rupiah, integer
    private String kategori; // "Makanan" or "Minuman"

    public Menu(String nama, int harga, String kategori) {
        this.nama = nama;
        this.harga = harga;
        this.kategori = kategori;
    }

    public String getNama() {
        return nama;
    }

    public int getHarga() {
        return harga;
    }

    public String getKategori() {
        return kategori;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }

    @Override
    public String toString() {
        return nama + " - Rp " + harga;
    }
}
