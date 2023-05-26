package model;

import java.util.Scanner;

public class Client extends Person {
    private int id_client;
    private String vehicles;
    
    public Client() {
        super();
        this.vehicles = "";
    }

    @Override
    public void fillPerson() {
        Scanner scan = new Scanner(System.in);
        System.out.println("******* Preencha o Cliente *******");
        System.out.print("Informe o nome do cliente: ");
        super.setName(scan.nextLine());
        System.out.print("Informe o CPF do cliente: ");
        super.setCpf(scan.nextLine());
        System.out.print("Informe o telefone/whatsapp do cliente: ");
        super.setTel(scan.nextLine());
        System.out.print("Informe o e-mail do cliente: ");
        super.setEmail(scan.nextLine());
        System.out.print("Informe o endereço completo do cliente: ");
        super.setAddress(scan.nextLine());
        System.out.print("Informe o(s) veículo(s) do cliente: ");
        this.setVehicles(scan.nextLine());
    }
    
    @Override
    public void printPerson() {
        System.out.println("******* Dados do Cliente *******");
        System.out.print("Nome: " + super.getName());
        System.out.print("CPF: " + super.getCpf());
        System.out.print("Telefone/Whatsapp: " + super.getTel());
        System.out.print("E-mail: " + super.getEmail());
        System.out.print("Endereço: " + super.getAddress());
        System.out.print("Veículos: " + this.getVehicles());
        System.out.println("*******************************");
    }
    
    @Override
    public String printPersonToString() {
        String out = "";
        out = "******* Dados do Cliente *******\n"
                + "Nome: " + super.getName() + "\n"
                + "CPF: " + super.getCpf() + "\n"
                + "Telefone/Whatsapp: " + super.getTel() + "\n"
                + "E-mail: " + super.getEmail() + "\n"
                + "Endereço: " + super.getAddress() + "\n"
                + "Veículos: " + this.getVehicles() + "\n"
                + "***********************************\n";
        return out;
    }
    
    public void copyPerson(Client other) {
        super.setName(other.getName());
        super.setCpf(other.getCpf());
        super.setTel(other.getTel());
        super.setEmail(other.getEmail());
        super.setAddress(other.getAddress());
        this.setVehicles(other.getVehicles());        
    }

    /**
     * @return the vehicles
     */
    public String getVehicles() {
        return vehicles;
    }

    /**
     * @param vehicles the vehicles to set
     */
    public void setVehicles(String vehicles) {
        this.vehicles = vehicles;
    }

   
}

