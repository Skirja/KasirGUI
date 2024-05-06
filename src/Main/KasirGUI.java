/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Main;

import java.sql.*;
import javax.swing.JOptionPane;

import Auth.Login;

/**
 *
 * @author Irza
 */
public class KasirGUI {
    // Informasi koneksi ke database SQL Server
    private static final String URL = "jdbc:sqlserver://localhost\\LAPTOP-KK8IE6CU:1433;databaseName=db_programKasir;encrypt=true;trustServerCertificate=true";
    private static final String USERNAME = "sa";
    private static final String PASSWORD = "irza1103";

    // Metode untuk mendapatkan koneksi ke SQL Server
    public static Connection getConnection() throws SQLException {
        Connection connection = null;
        try {
            // Mengatur kelas driver JDBC yang akan digunakan
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            // Membuat koneksi
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            // System.out.println("Koneksi berhasil!");
        } catch (ClassNotFoundException | SQLException e) {
            // System.out.println("Koneksi gagal: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Koneksi gagal: " + e.getMessage());
            System.exit(0);
        }
        return connection;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        // Menggunakan koneksi untuk membuat objek Login
        try {
            Connection connection = getConnection();
            Login login = new Login();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
