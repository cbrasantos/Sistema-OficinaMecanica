package dao;

import classes.Person;
import connection.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ClientDAO {
    private DBConnection dbConnection;
    private Connection conn;
    
    public ClientDAO() {
        this.dbConnection = new DBConnection();
        this.conn = this.dbConnection.getConnection();
    }
    
    public void insert(Person person) {
        String idPerson = null;
        String insertPerson = "INSERT INTO person(name, cpf, tel, address, email) VALUES " + "(?, ? , ? , ?, ?)";
        try {
            PreparedStatement stmt = this.conn.prepareStatement(insertPerson, PreparedStatement.RETURN_GENERATED_KEYS);
            stmt.setString(1, person.getName());
            stmt.setString(2, person.getCpf());
            stmt.setString(3, person.getTel());
            stmt.setString(4, person.getAddress());
            stmt.setString(5, person.getEmail());
            stmt.execute();
            
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()){
                idPerson = rs.getString(1);
            }
            insertClient(idPerson);
        } catch (SQLException error) {
            System.out.println("Erro ao inserir um cliente: " + error.getMessage());
        }
    }
    
    public void insertClient(String id) {
        String insertClient = "INSERT INTO client(id_person) VALUES " + "(?)";
        try {
            PreparedStatement stmt = this.conn.prepareStatement(insertClient);
            stmt.setString(1, id);
            stmt.execute();
        } catch (SQLException error) {
            System.out.println("Erro ao inserir um cliente: " + error.getMessage());
        }     
    }
    
    public Person getClient(int id) {
        Person response = new Person();
        try {
            String sql = "SELECT * FROM person INNER JOIN client ON person.id_person = client.id_person"
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
    
    public Person getClientByCpf(String cpf) {
        Person response = new Person();
        try {
            String sql = "SELECT * FROM person INNER JOIN client ON person.id_person = client.id_person"
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
    
    public ArrayList<Person> getClients() {
        ArrayList<Person> response = new ArrayList<>();
        try {
            String sql = "SELECT name, cpf, tel FROM person INNER JOIN client ON person.id_person = client.id_person"
                    + " WHERE person.id_person=client.id_person";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            Person temp;
            while (rs.next()) {
                temp = new Person();
                temp.setName(rs.getString("person.name"));
                temp.setCpf(rs.getString("person.cpf"));
                temp.setTel(rs.getString("person.tel"));
                               
                response.add(temp);
            }
            return response;
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
    }

    public void updateClient(Person p) {
        try {
            String sql = "UPDATE person set name=?, cpf=?, tel=?, address=?, email=? WHERE cpf=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            
            stmt.setString(1, p.getName());
            stmt.setString(2, p.getCpf());
            stmt.setString(3, p.getTel());
            stmt.setString(4, p.getAddress());
            stmt.setString(5, p.getEmail());
            stmt.setString(6, p.getCpf());

            stmt.execute();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
    
    public void deleteClient(String cpf) {
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
