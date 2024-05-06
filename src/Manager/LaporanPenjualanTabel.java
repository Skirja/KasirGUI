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
public class LaporanPenjualanTabel extends JFrame{
    
    private JLabel[] label;
    private JTextField[] txtField;
    private JButton[] button;
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
    
    public LaporanPenjualanTabel(String namaAuth){
        this.namaAuth = namaAuth;
        setTitle("Laporan Penjualan");
        setLayout(null);
        setSize(1000, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Deklarasi JLabel
        label = new JLabel[3];
        
        // Deklarasi JTextField
        txtField = new JTextField[3];
        
        // Deklarasi JButton
        button = new JButton[3];
        
        // Membuat menu bar
        JMenuBar menuBar = new JMenuBar();

        // Menu "Manage Barang"
        JMenu manageMenu = new JMenu("Manage");
        JMenuItem managebarangItem = new JMenuItem("Manage Barang");
        manageMenu.add(managebarangItem);
        menuBar.add(manageMenu);
        
        managebarangItem.addActionListener(e -> {
            // Kembali ke ManagerHome
            ManagerHome.startManagerHome(namaAuth);
            dispose();
        });
        
        // Menu "Laporan"
        JMenu laporanMenu = new JMenu("Laporan");
        JMenuItem penjualanItem = new JMenuItem("Laporan Penjualan");
        laporanMenu.add(penjualanItem);
        menuBar.add(laporanMenu);
        
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
        JTable barangTabel = new JTable();
        JScrollPane spane;
        
        // Tabel Head
        String kolom[] = {"ID Transaksi","ID Kasir","Tanggal Transaksi","Keuntungan"};
        
        // Tabel Isi
        String isi[][] = {
            {"T001","K002","12-04-2024","50000"},
            {"T002","K003","19-04-2024","97345"},
        };
        
        barangTabel =  new JTable(isi,kolom);
        spane = new JScrollPane(barangTabel);
        spane.setBounds(10,10, 480, 452);
        add(spane);
        
        setVisible(true);
        
        // Cari Tanggal
        label[0] = new lbl("Cari Tanggal :", 500, 10, 100, 12);
        add(label[0]);
        
        txtField[0] = new txt(500, 28, 230, 22);
        add(txtField[0]);
        
        //Cari ID Kasir
        label[1] = new lbl("Cari ID Kasir :", 740, 10, 100, 12);
        add(label[1]);
        
        txtField[1] = new txt(740, 28, 230, 22);
        add(txtField[1]);
        
        // Total Keuntungan
        label[2] = new lbl("Total Keuntungan :", 10, 468, 110, 13);
        label[2].setFont(new Font(label[2].getFont().getName(), Font.ITALIC, label[2].getFont().getSize()));
        add(label[2]);
        
        txtField[2] = new txt(122, 468, 230, 12);
        txtField[2].setBorder(null);
        txtField[2].setEditable(false);
        add(txtField[2]);
        
        // Button Cari
        button[0] = new btn("Cari", 570, 70, 330, 40);
        add(button[0]);
        
        // Button Lihat Detail Barang
        button[1] = new btn("Lihat Detail Barang", 500, 120, 230, 40);
        add(button[1]);
        
        // Button Print Detail Barang
        button[2] = new btn("Print Detail Barang", 740, 120, 230, 40);
        add(button[2]);
        
    }
    
    public static void startLaporanPenjualanTabel(String namaAuth) {
        // Memulai aplikasi ManagerHome
        SwingUtilities.invokeLater(() -> {
            new LaporanPenjualanTabel(namaAuth);
        });
    }
}
