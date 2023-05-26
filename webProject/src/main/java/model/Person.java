package model;

import java.util.Scanner;

public class Person {
    private String name;
    private String cpf;
    private String tel;
    private String email;
    private String address;
    
    public Person() {
        this.name = "";
        this.cpf = "";
        this.tel = "";
        this.email = "";
        this.address = "";  
    }
    
    public void fillPerson() {
        Scanner scan = new Scanner(System.in);
        System.out.println("******* Preencha a Pessoa *******");
        System.out.print("Informe o nome da pessoa: ");
        this.setName(scan.nextLine());
        System.out.print("Informe o CPF da pessoa: ");
        this.setCpf(scan.nextLine());
        System.out.print("Informe o telefone/whatsapp da pessoa: ");
        this.setTel(scan.nextLine());
        System.out.print("Informe o e-mail da pessoa: ");
        this.setEmail(scan.nextLine());
        System.out.print("Informe o endereço completo da pessoa: ");
        this.setAddress(scan.nextLine());
    }
    
    public void printPerson() {
        System.out.println("******* Dados da Pessoa *******");
        System.out.print("Nome: " + this.getName());
        System.out.print("CPF: " + this.getCpf());
        System.out.print("Telefone/Whatsapp: " + this.getTel());
        System.out.print("E-mail: " + this.getEmail());
        System.out.print("Endereço: " + this.getAddress());
        System.out.println("*******************************");
    }
    
    public String printPersonToString() {
        String out = "";
        out = "******* Dados da Pessoa *******\n"
                + "Nome: " + this.getName() + "\n"
                + "CPF: " + this.getCpf() + "\n"
                + "Telefone/Whatsapp: " + this.getTel() + "\n"
                + "E-mail: " + this.getEmail() + "\n"
                + "Endereço: " + this.getAddress() + "\n"
                + "***********************************\n";
        return out;
    }
    
    public void copyPerson(Person other) {
        this.setName(other.getName());
        this.setCpf(other.getCpf());
        this.setTel(other.getTel());
        this.setEmail(other.getEmail());
        this.setAddress(other.getAddress());       
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the cpf
     */
    public String getCpf() {
        return cpf;
    }

    /**
     * @param cpf the cpf to set
     */
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    /**
     * @return the tel
     */
    public String getTel() {
        return tel;
    }

    /**
     * @param tel the tel to set
     */
    public void setTel(String tel) {
        this.tel = tel;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }
    
}

