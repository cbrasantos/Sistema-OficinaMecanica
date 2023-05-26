package controller;

import java.util.ArrayList;
import model.Person;
import model.dao.EmployeeDAO;
import view.EmployeeForm;

public class EmployeeController {
    
   private final EmployeeDAO employeeDAO;
    
    public EmployeeController() {
        employeeDAO = new EmployeeDAO();
    }
    
    public void showView() {
        EmployeeForm employeeView = new EmployeeForm(this);
        employeeView.setVisible(true);
        employeeView.setLocationRelativeTo(null);
    }
    
    public void createEmployee(String[] person) {
        employeeDAO.insert(person);
    }
    
    public ArrayList<String []> getEmployees() {
        ArrayList<String[]> allEmployees = new ArrayList();
        ArrayList<Person> responseDAO = employeeDAO.getEmployees();
        
        for(int i = 0; i < responseDAO.size(); i++) {
            String employee[] = new String[3];
            employee[0] = responseDAO.get(i).getName();
            employee[1] = responseDAO.get(i).getCpf();
            employee[2] = responseDAO.get(i).getTel();
            allEmployees.add(employee);
        }
        
        return allEmployees;
    }
    
    public void getEmployee(int id) {
        employeeDAO.getEmployee(id);
    }
    
    public String[] getEmployeeByCpf(String cpf) {
        Person responseDAO = employeeDAO.getEmployeeByCpf(cpf);
        
        String[] employee = {responseDAO.getName(), responseDAO.getCpf(), responseDAO.getTel(), responseDAO.getEmail(), responseDAO.getAddress()};
        
        return employee;
    }
    
    public void updateEmployee(String[] person) {
        employeeDAO.updateEmployee(person);
    }
    
    public void deleteEmployee(String cpf) {
        employeeDAO.deleteEmployee(cpf);
    }
}
