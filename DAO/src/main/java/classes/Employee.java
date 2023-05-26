package classes;

import java.util.Scanner;

public class Employee extends Person {
    private int id_employee;
    private String position;
    private int workHours;
    private String salary;
    private String contractDate;  
    
    public Employee() {
        super(); 
        this.position = "";
        this.workHours = 0;
        this.salary = "";
        this.contractDate = "";
    }
    
    @Override
    public void fillPerson() {
        Scanner scan = new Scanner(System.in);
        System.out.println("******* Preencha o Funcionário *******");
        System.out.print("Informe o nome do funcionário: ");
        super.setName(scan.nextLine());
        System.out.print("Informe o CPF do funcionário: ");
        super.setCpf(scan.nextLine());
        System.out.print("Informe o telefone/whatsapp do funcionário: ");
        super.setTel(scan.nextLine());
        System.out.print("Informe o e-mail do funcionário: ");
        super.setEmail(scan.nextLine());
        System.out.print("Informe o endereço completo do funcionário: ");
        super.setAddress(scan.nextLine());
        System.out.print("Informe o cargo do funcionário: ");
        this.setPosition(scan.nextLine());
        System.out.print("Informe a quantidade de horas trabalhadas pelo funcionário: ");
        this.setWorkHours(scan.nextInt());
        System.out.print("Informe o salário fixo do funcionário: ");
        this.setSalary(scan.nextLine());
        System.out.print("Informe a data de contratação do funcionário: ");
        this.setContractDate(scan.nextLine());
       
    }
    
    @Override
    public void printPerson() {
        System.out.println("******* Dados do Funcionário *******");
        System.out.print("Nome: " + super.getName());
        System.out.print("CPF: " + super.getCpf());
        System.out.print("Telefone/Whatsapp: " + super.getTel());
        System.out.print("E-mail: " + super.getEmail());
        System.out.print("Endereço: " + super.getAddress());
        System.out.print("Cargo: " + this.getPosition());
        System.out.print("Horas Trabalhadas: " + this.getWorkHours());
        System.out.print("Salário Fixo: " + this.getSalary());
        System.out.print("Data de Contratação: " + this.getContractDate());
        System.out.println("*******************************");
    }
    
    @Override
    public String printPersonToString() {
        String out = "";
        out = "******* Dados do Funcionário *******\n"
                + "Nome: " + super.getName() + "\n"
                + "CPF: " + super.getCpf() + "\n"
                + "Telefone/Whatsapp: " + super.getTel() + "\n"
                + "E-mail: " + super.getEmail() + "\n"
                + "Endereço: " + super.getAddress() + "\n"
                + "Cargo: " + this.getPosition() + "\n"
                + "Horas Trabalhadas: " + this.getWorkHours()+ "\n"
                + "Salário Fixo: " + this.getSalary()+ "\n"
                + "Data de Contração: " + this.getContractDate()+ "\n"
                + "***********************************\n";
        return out;
    }
    
    public void copyEmployee(Employee other) {
        super.setName(other.getName());
        super.setCpf(other.getCpf());
        super.setTel(other.getTel());
        super.setEmail(other.getEmail());
        super.setAddress(other.getAddress());
        this.setPosition(other.getPosition());  
        this.setWorkHours(other.getWorkHours());
        this.setSalary(other.getSalary());
        this.setContractDate(other.getContractDate());
    }

    /**
     * @return the position
     */
    public String getPosition() {
        return position;
    }

    /**
     * @param position the position to set
     */
    public void setPosition(String position) {
        this.position = position;
    }

    /**
     * @return the workHours
     */
    public int getWorkHours() {
        return workHours;
    }

    /**
     * @param workHours the workHours to set
     */
    public void setWorkHours(int workHours) {
        this.workHours = workHours;
    }

    /**
     * @return the salary
     */
    public String getSalary() {
        return salary;
    }

    /**
     * @param salary the salary to set
     */
    public void setSalary(String salary) {
        this.salary = salary;
    }

    /**
     * @return the contractDate
     */
    public String getContractDate() {
        return contractDate;
    }

    /**
     * @param contractDate the contractDate to set
     */
    public void setContractDate(String contractDate) {
        this.contractDate = contractDate;
    }

 
   
}

