/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Manager;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

import Auth.Login;
import static Main.KasirGUI.getConnection;

/**
 *
 * @author Irza
 */
public class ManagerHome extends JFrame{
    
    private JLabel[] label;
    private JTextField[] txtField;
    private JButton[] button;
    private JTable barangTabel;
    private String namaAuth;
    
    // Super class JLabel
    class lbl extends JLabel{
        private lbl(String text, int x, int y, int width, int heigth){
            setFont(new Font("Arial", Font.BOLD, 12));
            setText(text);
            setBounds(x, y, width, heigth);
        }
    }
    
    // Super class JTextField
    class txt extends JTextField{
        private txt(int x, int y, int width, int heigth){
            setFont(new Font("Arial", Font.BOLD, 12));
            setText("");
            setBounds(x, y, width, heigth);
        }
    }
    
    // Super class JButton
    class btn extends JButton{
        private btn(String text, int x, int y, int width, int heigth){
            setFont(new Font("Arial", Font.BOLD, 12));
            setText(text);
            setBounds(x, y, width, heigth);
        }
    }
    
    public ManagerHome(String namaAuth){
        this.namaAuth = namaAuth;
        ImageIcon ikon = new ImageIcon(getClass().getResource("/Manager/GambarM.img/manager.png"));
        setTitle("Manager Home");
        setLayout(null);
        setSize(1000, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        this.setIconImage(ikon.getImage());
        
        // Deklarasi JLabel
        label = new JLabel[6];
        
        // Deklarasi JTextField
        txtField = new JTextField[7];
        
        // Deklarasi JButton
        button = new JButton[4];
        
         // Membuat menu bar
        JMenuBar menuBar = new JMenuBar();

        // Membuat Manage Barang
        ImageIcon icon_Manager= new ImageIcon(getClass().getResource("/Manager/GambarM.img/inventory.png"));
        // Menu "Manage Barang"
        JMenu manageMenu = new JMenu("");
        manageMenu.setIcon(icon_Manager); // Menetapkan ikon pada JMenu

        JMenuItem managebarangItem = new JMenuItem("Manage Barang");
        manageMenu.add(managebarangItem);
        menuBar.add(manageMenu);
        
        // Membuat Laporan
        ImageIcon icon_Laporan= new ImageIcon(getClass().getResource("/Manager/GambarM.img/Laporan.png"));
        // Menu "Laporan"
        JMenu laporanMenu = new JMenu("");
        laporanMenu.setIcon(icon_Laporan); // Menetapkan ikon pada JMenu

        JMenuItem laporanItem = new JMenuItem("Laporan Penjualan");
        laporanMenu.add(laporanItem);
        menuBar.add(laporanMenu);
        
        laporanItem.addActionListener(e -> {
            // Tampilkan tabel laporan penjualan
            LaporanPenjualanTabel.startLaporanPenjualanTabel(namaAuth);
            dispose();
        });
        
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
        model.addColumn("Stok Barang");
        model.addColumn("Harga Beli");
        model.addColumn("Harga Jual");

        barangTabel = new JTable(model);
        JScrollPane spane = new JScrollPane(barangTabel);
        spane.setBounds(10, 28, 480, 452); // Tambahkan batas pada JScrollPane
        add(spane);

        // Mengisi tabel dari database
        populateTableFromDatabase();

        // Menampilkan frame setelah komponen-komponen ditambahkan
        setVisible(true);
        
        // ID Barang
        label[0] = new lbl("ID Barang :", 500, 10, 100, 12);
        add(label[0]);
        
        txtField[0] = new txt(500, 28, 230, 22);
        txtField[0].setEditable(false);
        add(txtField[0]);
        
        // Nama Barang
        label[1] = new lbl("Nama Barang :", 740, 10, 100, 12);
        add(label[1]);
        
        txtField[1] = new txt(740, 28, 230, 22);
        add(txtField[1]);
        
        // Stok Barang
        label[2] = new lbl("Stok Barang :", 500, 56, 100, 12);
        add(label[2]);
        
        txtField[2] = new txt(500, 74, 230, 22);
        txtField[2].addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!(Character.isDigit(c) || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE)) {
                    e.consume();
                }
            }
        });
        add(txtField[2]);
        
        // Harga Beli Barang
        label[3] = new lbl("Harga Beli :", 740, 56, 100, 12);
        add(label[3]);
        
        txtField[3] = new txt(740, 74, 230, 22);
        txtField[3].addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!(Character.isDigit(c) || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE)) {
                    e.consume();
                }
            }
        });
        add(txtField[3]);
        
        // Harga Jual Barang
        label[4] = new lbl("Harga Jual :", 500, 102, 100, 12);
        add(label[4]);
        
        txtField[4] = new txt(500, 120, 230, 22);
        txtField[4].addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!(Character.isDigit(c) || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE)) {
                    e.consume();
                }
            }
        });
        add(txtField[4]);
        
        // Alert
        txtField[5] = new txt(740, 120, 230, 22);
        txtField[5].setFont(new Font(txtField[5].getFont().getName(), Font.ITALIC, txtField[5].getFont().getSize()));
        txtField[5].setForeground(Color.RED);
        txtField[5].setBorder(null);
        txtField[5].setEditable(false);
        add(txtField[5]);
        
        // Welcomeing
        label[5] = new lbl("Selamat Datang,", 10, 10, 90, 12 );
        label[5].setFont(new Font(label[5].getFont().getName(), Font.ITALIC, label[5].getFont().getSize()));
        add(label[5]);
        
        txtField[6] = new txt(100, 10, 100, 12);
        txtField[6].setText(namaAuth);
        txtField[6].setBorder(null);
        txtField[6].setEditable(false);
        add(txtField[6]);
                
        // Button Tambah Barang
        button[0] = new btn("Tambah Barang", 500, 162, 230, 40);
        ImageIcon icon1 = new ImageIcon(getClass().getResource("/Kasir/GambarK.img/plus.png")); // Mendapatkan ikon dari path yang tepat
        button[0].setIcon(icon1); // Menambahkan ikon ke JButton
        add(button[0]);
        
        button[0].addActionListener(e -> {
            String idBarang = txtField[0].getText(); // Ambil ID_Barang dari input
            String namaBarang = txtField[1].getText();
            int stokBarang = Integer.parseInt(txtField[2].getText());
            double hargaBeli = Double.parseDouble(txtField[3].getText());
            double hargaJual = Double.parseDouble(txtField[4].getText());

            try (Connection connection = getConnection()) {
                String sql = "INSERT INTO tb_barang (ID_Barang, Nama_Barang, Stok_Barang, Harga_Beli, Harga_Jual) VALUES (?, ?, ?, ?, ?)";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, idBarang); // Masukkan ID_Barang ke pernyataan
                    statement.setString(2, namaBarang);
                    statement.setInt(3, stokBarang);
                    statement.setDouble(4, hargaBeli);
                    statement.setDouble(5, hargaJual);
                    statement.executeUpdate();

                    JOptionPane.showMessageDialog(null, "Barang berhasil ditambahkan!");
                    
                    for(int i = 0; i < 5; i++){
                        txtField[i].setText("");
                    }
                    populateTableFromDatabase(); // Memperbarui tampilan tabel
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Gagal menambahkan barang!");
            }
        });

        // Button Hapus Barang
        button[1] = new btn("Hapus Barang", 740, 162, 230, 40);
        ImageIcon icon2 = new ImageIcon(getClass().getResource("/Kasir/GambarK.img/remove.png")); // Mendapatkan ikon dari path yang tepat
        button[1].setIcon(icon2); // Menambahkan ikon ke JButton
        add(button[1]);

        button[1].addActionListener(e -> {
            int selectedRow = barangTabel.getSelectedRow();

            if (selectedRow != -1) {
                // Dapatkan ID barang dari baris yang dipilih
                String idBarang = (String) barangTabel.getValueAt(selectedRow, 0);

                // Hapus barang dari database berdasarkan ID barang
                try (Connection connection = getConnection()) {
                    String sql = "DELETE FROM tb_barang WHERE ID_Barang = ?";
                    try (PreparedStatement statement = connection.prepareStatement(sql)) {
                        statement.setString(1, idBarang);
                        statement.executeUpdate();

                        JOptionPane.showMessageDialog(null, "Barang berhasil dihapus!");
                        populateTableFromDatabase(); // Perbarui tampilan tabel
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Gagal menghapus barang!");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Pilih baris tabel terlebih dahulu!");
            }
        });

        
        // Button Ubah Data Barang
        button[2] = new btn("Ubah Data Barang", 570, 222, 330, 40);
        ImageIcon icon3 = new ImageIcon(getClass().getResource("/Kasir/GambarK.img/change.png")); // Mendapatkan ikon dari path yang tepat
        button[2].setIcon(icon3); // Menambahkan ikon ke JButton
        add(button[2]);

        button[2].addActionListener(e -> {
            int selectedRow = barangTabel.getSelectedRow();

            if (selectedRow != -1) {
                String id = (String) barangTabel.getValueAt(selectedRow, 0);
                String nama = (String) barangTabel.getValueAt(selectedRow, 1);
                String stok = String.valueOf(barangTabel.getValueAt(selectedRow, 2));
                String hargaBeli = String.valueOf(barangTabel.getValueAt(selectedRow, 3));
                String hargaJual = String.valueOf(barangTabel.getValueAt(selectedRow, 4));

                txtField[0].setText(id);
                txtField[1].setText(nama);
                txtField[2].setText(stok);
                txtField[3].setText(hargaBeli);
                txtField[4].setText(hargaJual);
            } else {
                JOptionPane.showMessageDialog(null, "Pilih baris tabel terlebih dahulu!");
            }
        });

        
        // Button Konfirmasi Ubah Data Barang
        button[3] = new btn("Konfirmasi Ubah Data Barang", 570, 272, 330, 40);
        ImageIcon icon4 = new ImageIcon(getClass().getResource("/Kasir/GambarK.img/refresh.png")); // Mendapatkan ikon dari path yang tepat
        button[3].setIcon(icon4); // Menambahkan ikon ke JButton
        add(button[3]);

        button[3].addActionListener(e -> {
            int selectedRow = barangTabel.getSelectedRow();

            if (selectedRow != -1) {
                // Dapatkan ID barang dari baris yang dipilih
                String idBarang = (String) barangTabel.getValueAt(selectedRow, 0);
                String namaBarang = txtField[1].getText();
                int stokBarang = Integer.parseInt(txtField[2].getText());
                double hargaBeli = Double.parseDouble(txtField[3].getText());
                double hargaJual = Double.parseDouble(txtField[4].getText());

                try (Connection connection = getConnection()) {
                    String sql = "UPDATE tb_barang SET Nama_Barang=?, Stok_Barang=?, Harga_Beli=?, Harga_Jual=? WHERE ID_Barang=?";
                    try (PreparedStatement statement = connection.prepareStatement(sql)) {
                        statement.setString(1, namaBarang);
                        statement.setInt(2, stokBarang);
                        statement.setDouble(3, hargaBeli);
                        statement.setDouble(4, hargaJual);
                        statement.setString(5, idBarang);
                        statement.executeUpdate();

                        JOptionPane.showMessageDialog(null, "Data barang berhasil diperbarui!");
                        for (int i = 0; i < txtField.length; i++){
                        txtField[i].setText("");
                        }
                        populateTableFromDatabase(); // Perbarui tampilan tabel
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Gagal memperbarui data barang!");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Pilih baris tabel terlebih dahulu!");
            }
        });

        setVisible(true);
    }
    
    private void populateTableFromDatabase() {
        try (Connection connection = getConnection()) {
            String query = "SELECT * FROM tb_barang";
            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery(query);

                DefaultTableModel model = (DefaultTableModel) barangTabel.getModel();

                // Membersihkan tabel sebelum memuat data baru
                model.setRowCount(0);

                // Mengisi tabel dengan data dari database
                while (resultSet.next()) {
                    String id = resultSet.getString("ID_Barang");
                    String nama = resultSet.getString("Nama_Barang");
                    int stok = resultSet.getInt("Stok_Barang");
                    double hargaBeli = resultSet.getDouble("Harga_Beli");
                    double hargaJual = resultSet.getDouble("Harga_Jual");

                    model.addRow(new Object[]{id, nama, stok, hargaBeli, hargaJual});
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Gagal mengambil data dari database!");
        }
    }

    public void callUpdateProcedure() {
        try (Connection connection = getConnection()) {
            String sql = "{call update_manager_home}";
            try (CallableStatement statement = connection.prepareCall(sql)) {
                statement.execute();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Gagal memanggil stored procedure!");
        }
    }

    public static void startManagerHome(String namaAuth) {
        // Memulai aplikasi ManagerHome
        SwingUtilities.invokeLater(() -> {
            new ManagerHome(namaAuth);
        });
    }
}