package dao;

import classes.Provider;
import connection.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProviderDAO {
    private DBConnection dbConnection;
    private Connection conn;
    
    public ProviderDAO() {
        this.dbConnection = new DBConnection();
        this.conn = this.dbConnection.getConnection();
    }
    
    public void insert(Provider provider) {
        String insertPerson = "INSERT INTO person(name, cpf, tel, address, email) VALUES " + "(?, ? , ? , ?, ?)";
        String insertProvider = "INSERT INTO provider(product, cnpj, companyName) VALUES " + "(?, ? , ?)";
        try {
            PreparedStatement stmt = this.conn.prepareStatement(insertPerson);
            stmt.setString(1, provider.getName());
            stmt.setString(2, provider.getCpf());
            stmt.setString(3, provider.getTel());
            stmt.setString(4, provider.getAddress());
            stmt.setString(5, provider.getEmail());
            stmt.execute();
            PreparedStatement stmt2 = this.conn.prepareStatement(insertProvider);
            stmt.setString(1, provider.getProduct());
            stmt.setString(2, provider.getCnpj());
            stmt.setString(3, provider.getCompanyName());
            stmt2.execute();
        } catch (SQLException error) {
            System.out.println("Erro ao inserir um fornecedor: " + error.getMessage());
        }
    }
}
