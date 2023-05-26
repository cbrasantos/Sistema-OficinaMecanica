package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {
    
    public Connection getConnection() {
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/lps_oficina?serverTimeZone=UTC", // linha de conexão
                "root", // usuario
                "" // senha
            );
            Statement stmt = conn.createStatement();
            return conn;
        } catch(SQLException error) {
            System.out.println("Erro de conexão com o banco de dados: " + error.getMessage());
            return null;
        }                
    }
}
