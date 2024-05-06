/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Auth;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import org.jdesktop.swingx.prompt.PromptSupport;

import Manager.ManagerHome;
import Kasir.KasirHome;
import static Main.KasirGUI.getConnection;

/**
 *
 * @author Irza
 */
public class Login extends JFrame{
    private JLabel[] label;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> hakaksesBox;
    private JButton loginButton;
    private Connection connection;
    private String namaAuth;
    ImageIcon ikon = new ImageIcon(getClass().getResource("/Kasir/GambarK.img/smartphone.png"));
    
    
    // Super class JLabel
    class lbl extends JLabel{
        private lbl(String text, int x, int y, int width, int heigth){
            setFont(new Font("Arial", Font.BOLD, 12));
            setText(text);
            setBounds(x, y, width, heigth);
        }
    }
    
    // Super class JTextField
    class user extends JTextField{
        private user(int x, int y, int width, int heigth){
            setFont(new Font("Arial", Font.BOLD, 12));
            setText("");
            setBounds(x, y, width, heigth);
        }
    }
    
    // Super class JPasswordField
    class pass extends JPasswordField{
        private pass(int x, int y, int width, int heigth){
            setFont(new Font("Arial", Font.BOLD, 12));
            setText("");
            setBounds(x, y, width, heigth);
        }
    }
    
    public Login(){
        setTitle("Login");
        getContentPane().setLayout(null);
        setSize(300, 220);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Deklarasi label
        label = new JLabel[3];
        
        // Hak Akses
        label[0] = new lbl("Hak Akses :", 10, 10, 100, 12);
        add(label[0]);
        
        hakaksesBox = new JComboBox();
        hakaksesBox.setFont(new Font("Arial", Font.BOLD, 12));
        hakaksesBox.addItem("Pilih Role...");
        hakaksesBox.addItem("Manager");
        hakaksesBox.addItem("Kasir");
        hakaksesBox.setBounds(10, 28, 100, 22);
        add(hakaksesBox);
        
        // Username
        label[1] = new lbl("Username :", 10, 56, 100, 12);
//        label[1] = new lbl("", 10, 74, 24, 16);
//        label[1].setIcon(new ImageIcon(getClass().getResource("/gambar/user.png")));
        add(label[1]);
        
        usernameField = new user(10, 74, 270, 22);
//        usernameField = new user(26, 74, 270, 22);
        add(usernameField);
        PromptSupport.setPrompt("Masukkan Username", usernameField); 
        
        // Password
        label[2] = new lbl("Password :", 10, 102, 100, 12);
        add(label[2]);
        
        passwordField = new pass(10, 120, 270, 22);
        add(passwordField);
        PromptSupport.setPrompt("Masukkan Password", passwordField);
        
        // Button Login
        loginButton = new JButton("Login"); // Inisialisasi JButton dengan teks "Login"
        ImageIcon icon = new ImageIcon(getClass().getResource("/Auth/Gambar.img/ikey.png")); // Mendapatkan ikon dari path yang tepat
        loginButton.setIcon(icon); // Menambahkan ikon ke JButton
        this.getContentPane().add(loginButton); // Menambahkan JButton ke dalam konten utama frame        

        //loginButton.setIcon(new ImageIcon(this.getClass().getResource("src/Auth/Gambar/ikey.png")));
        loginButton.setFont(new Font("Arial", Font.BOLD, 8));
        loginButton.setText("Login");
        loginButton.setBounds(10, 148, 80, 25);
        add(loginButton);
        
        loginButton.addActionListener(e -> {
            // Tempatkan logika autentikasi di sini
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            String hakAkses = (String) hakaksesBox.getSelectedItem(); // Ambil pilihan hak akses dari combobox
            
            if (hakAkses.equals("Pilih Role...")) {
                JOptionPane.showMessageDialog(null, "Hak Akses belum dipilih!");
                return; // Keluar dari method karena hak akses belum dipilih
            }

            try (Connection connection = getConnection()) {
                String query = "SELECT Role, Nama_Auth, ID_Auth FROM tb_Auth WHERE Username = ? AND Password = ?";
                try (PreparedStatement ps = connection.prepareStatement(query)) {
                    ps.setString(1, username);
                    ps.setString(2, password);
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        String roleFromDB = rs.getString("Role");
                        namaAuth = rs.getString("Nama_Auth");
                        getNamaAuthFromDatabase(username);
                        if (hakAkses.equals("Manager") && roleFromDB.equals("Manager")) {
                            JOptionPane.showMessageDialog(null, " Login sebagai Manager!\nSelamat Datang, " + namaAuth);
                            ManagerHome.startManagerHome(namaAuth);
                            dispose();
                        } else if (hakAkses.equals("Kasir") && roleFromDB.equals("Kasir")) {
                            JOptionPane.showMessageDialog(null, " Login sebagai Kasir!\nSelamat Datang, " + namaAuth);
                            int idAuth = rs.getInt("ID_Auth");
                            KasirHome.startKasirHome(namaAuth);
                            dispose();
                        } else {
                            JOptionPane.showMessageDialog(null, "Anda tidak memiliki akses!");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Username atau password salah!");
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Gagal melakukan autentikasi!");
            }
        });

        setVisible(true);
    }
    
    private void getNamaAuthFromDatabase(String username) {
        try (Connection connection = getConnection()) {
            String query = "SELECT Nama_Auth FROM tb_Auth WHERE Username = ?";
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setString(1, username);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    namaAuth = rs.getString("Nama_Auth");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Gagal mengambil Nama_Auth dari database!");
        }
    }
}