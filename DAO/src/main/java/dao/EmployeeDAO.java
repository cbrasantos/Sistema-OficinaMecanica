package dao;

import classes.Employee;
import connection.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EmployeeDAO {
    private DBConnection dbConnection;
    private Connection conn;
    
    public EmployeeDAO() {
        this.dbConnection = new DBConnection();
        this.conn = this.dbConnection.getConnection();
    }
    
    public void insert(Employee employee) {
        String insertPerson = "INSERT INTO person(name, cpf, tel, address, email) VALUES " + "(?, ? , ? , ?, ?)";
        String insertEmployee = "INSERT INTO employee(position, salary, workHours, contractDate) VALUES " + "(?, ? , ?, ?)";
        try {
            PreparedStatement stmt = this.conn.prepareStatement(insertPerson);
            stmt.setString(1, employee.getName());
            stmt.setString(2, employee.getCpf());
            stmt.setString(3, employee.getTel());
            stmt.setString(4, employee.getAddress());
            stmt.setString(5, employee.getEmail());
            stmt.execute();
            PreparedStatement stmt2 = this.conn.prepareStatement(insertEmployee);
            stmt.setString(1, employee.getPosition());
            stmt.setString(2, employee.getSalary());
            stmt.setFloat(3, employee.getWorkHours());
            stmt.setString(4, employee.getContractDate());
            stmt2.execute();
        } catch (SQLException error) {
            System.out.println("Erro ao inserir um funcionário: " + error.getMessage());
        }
    }
}
