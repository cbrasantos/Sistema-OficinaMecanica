package model.dao;

import model.Employee;
import connection.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Person;

public class EmployeeDAO {
    private DBConnection dbConnection;
    private Connection conn;
    
    public EmployeeDAO() {
        this.dbConnection = new DBConnection();
        this.conn = this.dbConnection.getConnection();
    }
    
    public void insert(String[] p) {
        String idPerson = null;
        String insertPerson = "INSERT INTO person(name, cpf, tel, address, email) VALUES " + "(?, ? , ? , ?, ?)";
        try {
            PreparedStatement stmt = this.conn.prepareStatement(insertPerson, PreparedStatement.RETURN_GENERATED_KEYS);
            stmt.setString(1, p[0]);
            stmt.setString(2, p[1]);
            stmt.setString(3, p[2]);
            stmt.setString(4, p[3]);
            stmt.setString(5, p[4]);
            stmt.execute();
            
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()){
                idPerson = rs.getString(1);
            }
            insertEmployee(idPerson);
        } catch (SQLException error) {
            System.out.println("Erro ao inserir um funcionário: " + error.getMessage());
        }
    }
    
    public void insertEmployee(String id) {
        String insertEmployee = "INSERT INTO employee(id_person) VALUES " + "(?)";
        try {
            PreparedStatement stmt = this.conn.prepareStatement(insertEmployee);
            stmt.setString(1, id);
            stmt.execute();
        } catch (SQLException error) {
            System.out.println("Erro ao inserir um funcionário: " + error.getMessage());
        }     
    }
    
    public Person getEmployee(int id) {
        Person response = new Person();
        try {
            String sql = "SELECT * FROM person INNER JOIN employee ON person.id_person = employee.id_person"
                    + " WHERE person.id_person=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            response.setName(rs.getString("person.name"));
            response.setCpf(rs.getString("person.cpf"));
            response.setTel(rs.getString("person.tel"));
            response.setAddress(rs.getString("person.address"));
            response.setEmail(rs.getString("person.email"));
                               
            return response;
        } catch (SQLException e) {
            System.out.println(e);
            return response;
        }
    }
    
    public Person getEmployeeByCpf(String cpf) {
        Person response = new Person();
        try {
            String sql = "SELECT * FROM person INNER JOIN employee ON person.id_person = employee.id_person"
                    + " WHERE person.cpf=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, cpf);
                        
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){   
                response.setName(rs.getString("person.name"));
                response.setCpf(rs.getString("person.cpf"));
                response.setTel(rs.getString("person.tel"));
                response.setAddress(rs.getString("person.address"));
                response.setEmail(rs.getString("person.email"));
            }

            return response;
        } catch (SQLException e) {
            System.out.println(e);
            return response;
        }
    }
    
    public ArrayList<Person> getEmployees() {
        ArrayList<Person> responseDAO = new ArrayList();
        try {
            String sql = "SELECT name, cpf, tel FROM person INNER JOIN employee ON person.id_person = employee.id_person"
                    + " WHERE person.id_person=employee.id_person";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            Person temp;
            while (rs.next()) {
                temp = new Person();
                temp.setName(rs.getString("person.name"));
                temp.setCpf(rs.getString("person.cpf"));
                temp.setTel(rs.getString("person.tel"));
                               
                responseDAO.add(temp);
            }
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
        return responseDAO;
    }

    public void updateEmployee(String[] p) {
        System.out.println(p[0]);
        System.out.println(p[1]);
        System.out.println(p[2]);
        System.out.println(p[3]);
        System.out.println(p[4]);
        try {
            String sql = "UPDATE person set name=?, cpf=?, tel=?, address=?, email=? WHERE cpf=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            
            stmt.setString(1, p[0]);
            stmt.setString(2, p[1]);
            stmt.setString(3, p[2]);
            stmt.setString(4, p[3]);
            stmt.setString(5, p[4]);
            stmt.setString(6, p[1]);

            stmt.execute();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
    
    public void deleteEmployee(String cpf) {
        try {
            String sql = "DELETE FROM person WHERE cpf=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            
            stmt.setString(1, cpf);
            stmt.execute();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
}
