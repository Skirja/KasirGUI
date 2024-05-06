/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Kasir;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.time.*;
import java.util.*;
import java.time.format.*;
import java.math.*;
import org.jdesktop.swingx.prompt.PromptSupport;

import Auth.Login;
import static Main.KasirGUI.getConnection;

/**
 *
 * @author Irza
 */
public class KasirHome extends JFrame {
    
    private JLabel[] label;
    private JTextField[] txtField;
    private JButton[] button;
    private JComboBox<String> boxPembayaran;
    private JTable barangTabel;
    private String namaAuth;
    
    // Super class JLabel
    class lbl extends JLabel {
        private lbl(String text, int x, int y, int width, int height) {
            setFont(new Font("Arial", Font.BOLD, 12));
            setText(text);
            setBounds(x, y, width, height);
        }
    }
    
    // Super class JTextField
    class txt extends JTextField {
        private txt(int x, int y, int width, int height) {
            setFont(new Font("Arial", Font.BOLD, 12));
            setText("");
            setBounds(x, y, width, height);
        }
    }
    
    // Super class JButton
    class btn extends JButton {
        private btn(String text, int x, int y, int width, int height) {
            setFont(new Font("Arial", Font.BOLD, 12));
            setText(text);
            setBounds(x, y, width, height);
        }
    }
    
    public KasirHome(String namaAuth) {
        this.namaAuth = namaAuth;
        setTitle("Kasir App");
        setLayout(null);
        setSize(1000, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Deklarasi JLabel
        label = new JLabel[6];
        
        // Deklarasi JTextField
        txtField = new JTextField[7];
        
        // Deklarasi JButton
        button = new JButton[5];
        
        // Deklarasi JComboBox
        boxPembayaran = new JComboBox();
        
        // Membuat menu bar
        JMenuBar menuBar = new JMenuBar();

        // Menu "Keranjang"
        JMenu keranjangMenu = new JMenu("Keranjang");
        menuBar.add(keranjangMenu);

        // Menu "Keluar"
        JMenu keluarMenu = new JMenu("Keluar");
        JMenuItem logoutItem = new JMenuItem("Logout");
        keluarMenu.add(logoutItem);
        menuBar.add(keluarMenu);
        
        setJMenuBar(menuBar);

        setLocationRelativeTo(null);
        setVisible(true);
        
        logoutItem.addActionListener(e -> {
            // Tampilkan kembali halaman login
            new Login();
            dispose(); // Tutup jendela ManagerHome
        });
        
         // Tabel
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID Barang");
        model.addColumn("Nama Barang");
        model.addColumn("Harga Satuan");
        model.addColumn("Jumlah");
        model.addColumn("Sub Total Harga");
        barangTabel = new JTable(model);
        JScrollPane spane = new JScrollPane(barangTabel);
        spane.setBounds(10, 28, 480, 434);
        add(spane);

        // Memuat data transaksi sementara saat pertama kali tampilan kasir dimuat
        loadTransaksiTemp();
        
        // ID Barang
        label[0] = new lbl("ID Barang :", 500, 10, 100, 12);
        add(label[0]);
        
        txtField[0] = new txt(500, 28, 230, 22);
        txtField[0].addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!(Character.isDigit(c) || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE)) {
                    e.consume();
                }
            }
        });
        PromptSupport.setPrompt("Masukkan id barang", txtField[0]); 
        add(txtField[0]);
        
        // Nama Barang
        label[1] = new lbl("Nama Barang", 740, 10, 100, 12);
        add(label[1]);
        
        txtField[1] = new txt(740, 28, 230, 22);
        PromptSupport.setPrompt("Masukkan nama barang", txtField[1]); 
        add(txtField[1]);
        
        // Jumlah Barang
        label[2] = new lbl("Jumlah Barang :", 500, 56, 100, 12);
        add(label[2]);
        
        txtField[2] = new txt(500, 74, 230, 22);
        PromptSupport.setPrompt("Masukkan jumlah barang", txtField[2]); 
        add(txtField[2]);

        // Welcomeing
        label[3] = new lbl("Selamat Datang,", 10, 10, 90, 12 );
        label[3].setFont(new Font(label[3].getFont().getName(), Font.ITALIC, label[3].getFont().getSize()));
        add(label[3]);
        
        txtField[3] = new txt(100, 10, 100, 12);
        txtField[3].setText(namaAuth);
        txtField[3].setBorder(null);
        txtField[3].setEditable(false);
        add(txtField[3]);
        
        // Tanggal
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy", Locale.ENGLISH);
        String formattedDate = today.format(formatter);
        
        txtField[4] = new txt(352, 10, 135, 12);
        txtField[4].setHorizontalAlignment(JTextField.RIGHT);
        txtField[4].setBorder(null);
        txtField[4].setEditable(false);
        txtField[4].setText(formattedDate);
        add(txtField[4]);
        
        // Total Harga
        label[4] = new lbl("Total Harga :", 10, 468, 67, 13);
        label[4].setFont(new Font(label[4].getFont().getName(), Font.ITALIC, label[4].getFont().getSize()));
        add(label[4]);
        
        txtField[5] = new txt(81, 468, 230, 12);
        txtField[5].setBorder(null);
        txtField[5].setEditable(false);
        add(txtField[5]);
        
        // Pembayaran
        label[5] = new lbl("Metode Pembayaran :", 500, 222, 150, 12);
        add(label[5]);
        
        boxPembayaran.setFont(new Font("Arial", Font.BOLD, 12));
        boxPembayaran.addItem("Pilih Pembayaran...");
        boxPembayaran.addItem("Cash");
        boxPembayaran.addItem("Cashless");
        boxPembayaran.setBounds(500, 240, 150, 22);
        add(boxPembayaran);
        
        txtField[6] = new txt(500, 268, 150, 22);
        PromptSupport.setPrompt("Masukkan jumlah uang", txtField[6]); 
        add(txtField[6]);
        
        button[4] = new btn("Hitung", 680, 240, 290, 50);
        button[4].addActionListener(e -> {
            try {
                // Mendapatkan nilai dari txtField[5] (total harga) dan txtField[6] (jumlah bayar)
                BigDecimal totalHarga = new BigDecimal(txtField[5].getText());
                BigDecimal jumlahBayar = new BigDecimal(txtField[6].getText());

                // Mengurangi jumlah bayar dari total harga
                BigDecimal kembalian = jumlahBayar.subtract(totalHarga);

                // Menampilkan hasil kembalian pada txtField[6]
                txtField[6].setText(kembalian.toString());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Masukkan jumlah bayar yang valid.");
            }
        });

        add(button[4]);
  
        // Button Cek Barang
        button[0] = new btn("Cek Barang", 570, 112, 330, 40);
        add(button[0]);
        
        button[0].addActionListener(e -> {
            String idBarang = txtField[0].getText().trim();
            String namaBarang = txtField[1].getText().trim();

            if(idBarang.isEmpty() && namaBarang.isEmpty()){
                JOptionPane.showMessageDialog(null, "Mohon isi ID atau Nama Barang!");
            } else {
                // Panggil method untuk mencari barang berdasarkan ID atau nama barang
                // dan tampilkan hasilnya di txtField[1] jika pencarian berdasarkan ID
                // atau di txtField[0] jika pencarian berdasarkan nama
                if (!idBarang.isEmpty()) {
                    String namaBarangResult = cariBarangDariDatabaseById(idBarang);
                    txtField[1].setText(namaBarangResult);
                } else {
                    String idBarangResult = cariBarangDariDatabaseByNama(namaBarang);
                    txtField[0].setText(idBarangResult);
                }
            }
        });
        
        // Button Tambah Barang
        button[1] = new btn("Tambah Barang", 500, 162, 230, 40);
        add(button[1]);
        
        button[1].addActionListener(e -> {
            // Mendapatkan nilai dari text field
            String idBarang = txtField[0].getText().trim();
            int jumlah = Integer.parseInt(txtField[2].getText().trim());

            try {
                Connection conn = getConnection(); // Mendapatkan koneksi ke database

                // Query untuk mengambil stok barang dari tb_barang
                PreparedStatement stokStatement = conn.prepareStatement("SELECT Stok_Barang FROM tb_barang WHERE ID_Barang = ?");
                stokStatement.setString(1, idBarang);
                ResultSet stokResult = stokStatement.executeQuery();
                if (stokResult.next()) {
                    int stokBarang = stokResult.getInt("Stok_Barang");
                    if (jumlah <= stokBarang) {
                        // Jika jumlah barang yang diminta tidak melebihi stok yang tersedia
                        // Lakukan penambahan barang ke tb_transaksi_temp menggunakan stored procedure
                        CallableStatement addBarangProcedure = conn.prepareCall("{call tambah_barang_procedure(?, ?)}");
                        addBarangProcedure.setString(1, idBarang);
                        addBarangProcedure.setInt(2, jumlah);
                        addBarangProcedure.execute();

                        // Refresh tabel transaksi temp
                        loadTransaksiTemp();

                        // Menghitung ulang total harga
                        hitungTotalTransaksiTemp();
                        
                        for(int i = 0; i < 3; i++){
                            txtField[i].setText("");
                        }

                        JOptionPane.showMessageDialog(null, "Barang telah ditambahkan ke transaksi.");
                    } else {
                        // Jika jumlah barang yang diminta melebihi stok yang tersedia
                        JOptionPane.showMessageDialog(null, "Jumlah barang yang diminta melebihi stok yang tersedia.");
                    }
                }

                conn.close(); // Tutup koneksi
            } catch (Exception ex) {
                System.out.println(ex);
            }
        });

        // Button Hapus Barang
        button[2] = new btn("Hapus Barang", 740, 162, 230, 40);
        
        button[2].addActionListener(e -> {
            int selectedRow = barangTabel.getSelectedRow();
            if (selectedRow != -1) {
                String idBarang = (String) barangTabel.getValueAt(selectedRow, 0); // Ambil ID barang dari baris yang dipilih

                try {
                    Connection conn = getConnection();

                    // Panggil stored procedure untuk menghapus barang dari tb_transaksi_temp
                    CallableStatement deleteStatement = conn.prepareCall("{call delete_barang_and_update_stock(?)}");
                    deleteStatement.setString(1, idBarang);
                    deleteStatement.execute();

                    // Refresh tampilan
                    loadTransaksiTemp();
                    
                    JOptionPane.showMessageDialog(null, "Barang berhasil dihapus.");
                    
                    hitungTotalTransaksiTemp();

                    conn.close();
                } catch (Exception ex) {
                    System.out.println(ex);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Pilih barang yang ingin dihapus.");
            }
        });
        add(button[2]);
        
        // Button Print Resi
        button[3] = new btn("Print Resi", 570, 310, 330, 40);
        add(button[3]);
        
        setVisible(true);
    }
    
    // Method untuk mencari barang dari database berdasarkan ID barang
    private String cariBarangDariDatabaseById(String idBarang) {
        String namaBarang = ""; // Simpan nama barang hasil pencarian
        try {
            Connection conn = getConnection(); // Mendapatkan koneksi ke database
            PreparedStatement ps = conn.prepareStatement("SELECT Nama_Barang FROM tb_Barang WHERE ID_Barang = ?");
            ps.setString(1, idBarang);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                namaBarang = rs.getString("Nama_Barang"); // Ambil nama barang dari hasil query
            } else {
                JOptionPane.showMessageDialog(null, "Barang dengan ID tersebut tidak ditemukan!");
            }
            conn.close(); // Tutup koneksi
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return namaBarang;
    }

    // Method untuk mencari barang dari database berdasarkan nama barang
    private String cariBarangDariDatabaseByNama(String namaBarang) {
        String idBarang = ""; // Simpan ID barang hasil pencarian
        try {
            Connection conn = getConnection(); // Mendapatkan koneksi ke database
            PreparedStatement ps = conn.prepareStatement("SELECT ID_Barang FROM tb_Barang WHERE Nama_Barang = ?");
            ps.setString(1, namaBarang);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                idBarang = rs.getString("ID_Barang"); // Ambil ID barang dari hasil query
            } else {
                JOptionPane.showMessageDialog(null, "Barang dengan nama tersebut tidak ditemukan!");
            }
            conn.close(); // Tutup koneksi
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return idBarang;
    }
    
    private void loadTransaksiTemp() {
        try {
            Connection conn = getConnection(); // Mendapatkan koneksi ke database
            PreparedStatement ps = conn.prepareStatement("SELECT ID_Barang, Nama_Barang, Harga_Satuan, Jumlah, Subtotal_Harga FROM tb_transaksi_temp");
            ResultSet rs = ps.executeQuery();
            
            // Bersihkan model tabel sebelum menambahkan data baru
            DefaultTableModel model = (DefaultTableModel) barangTabel.getModel();
            model.setRowCount(0);
            
            // Tambahkan data dari hasil kueri ke tabel
            while (rs.next()) {
                Object[] row = new Object[5];
                row[0] = rs.getString("ID_Barang");
                row[1] = rs.getString("Nama_Barang");
                row[2] = rs.getBigDecimal("Harga_Satuan");
                row[3] = rs.getInt("Jumlah");
                row[4] = rs.getBigDecimal("Subtotal_Harga");
                model.addRow(row);
            }
            
            conn.close(); // Tutup koneksi
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
    
    private void hitungTotalTransaksiTemp() {
        try {
            Connection conn = getConnection(); // Mendapatkan koneksi ke database
            PreparedStatement ps = conn.prepareStatement("SELECT SUM(Subtotal_Harga) AS Total FROM tb_transaksi_temp");
            ResultSet rs = ps.executeQuery();

            // Ambil nilai total
            if (rs.next()) {
                BigDecimal total = rs.getBigDecimal("Total");
                if (total != null) {
                    txtField[5].setText(total.toString());
                } else {
                    txtField[5].setText("");
                }
            }

            conn.close(); // Tutup koneksi
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
    
    public static void startKasirHome(String namaAuth) {
        // Memulai aplikasi KasirHome
        SwingUtilities.invokeLater(() -> {
            new KasirHome(namaAuth);
        });
    }
}