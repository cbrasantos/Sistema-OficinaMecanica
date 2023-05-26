package controller;

import java.util.ArrayList;
import model.Person;
import model.dao.ClientDAO;
import view.ClientForm;

public class ClientController {
    
    private final ClientDAO clientDAO;
    
    public ClientController() {
        clientDAO = new ClientDAO();
    }
    
    public void showView() {
        ClientForm clientView = new ClientForm(this);
        clientView.setVisible(true);
        clientView.setLocationRelativeTo(null);
    }
    
    public void createClient(String[] person) {
        clientDAO.insert(person);
    }
    
    public ArrayList<String []> getClients() {
        ArrayList<String[]> allClients = new ArrayList();
        ArrayList<Person> responseDAO = clientDAO.getClients();
        
        for(int i = 0; i < responseDAO.size(); i++) {
            String client[] = new String[3];
            client[0] = responseDAO.get(i).getName();
            client[1] = responseDAO.get(i).getCpf();
            client[2] = responseDAO.get(i).getTel();
            allClients.add(client);
        }
        
        return allClients;
    }
    
    public void getClient(int id) {
        clientDAO.getClient(id);
    }
    
    public String[] getClientByCpf(String cpf) {
        Person responseDAO = clientDAO.getClientByCpf(cpf);
        
        String[] client = {responseDAO.getName(), responseDAO.getCpf(), responseDAO.getTel(), responseDAO.getEmail(), responseDAO.getAddress()};
        
        return client;
    }
    
    public void updateClient(String[] person) {
        clientDAO.updateClient(person);
    }
    
    public void deleteClient(String cpf) {
        clientDAO.deleteClient(cpf);
    }
}
