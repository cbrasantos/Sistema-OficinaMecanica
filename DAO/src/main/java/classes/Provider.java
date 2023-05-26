package classes;

import java.util.Scanner;

public class Provider extends Person {
    private int id_provider;
    private String companyName;
    private String cnpj;
    private String product;
    
    public Provider() {
        super();
        this.companyName = "";
        this.cnpj = "";   
        this.product = "";
    }
    
    public void fillProvider() {
        Scanner scan = new Scanner(System.in);
        System.out.println("******* Preencha o Fornecedor *******");
        System.out.print("Informe o nome da empresa: ");
        this.setCompanyName(scan.nextLine());
        System.out.print("Informe o CNPJ do fornecedor: ");
        this.setCnpj(scan.nextLine());
        System.out.print("Informe o telefone/whatsapp do fornecedor: ");
        super.setTel(scan.nextLine());
        System.out.print("Informe o e-mail do fornecedor: ");
        super.setEmail(scan.nextLine());
        System.out.print("Informe o endereço completo do fornecedor: ");
        super.setAddress(scan.nextLine());
        System.out.print("Informe o tipo de produto do fornecedor: ");
        this.setProduct(scan.nextLine());
        System.out.print("Informe o nome do responsável: ");
        super.setName(scan.nextLine());
        System.out.print("Informe o CPF do responsável: ");
        super.setCpf(scan.nextLine());
    }
    
    public void printProvider() {
        System.out.println("******* Dados do Fornecedor *******");
        System.out.print("Nome da Empresa: " + this.getCompanyName());
        System.out.print("CNPJ: " + this.getCnpj());
        System.out.print("Telefone/Whatsapp: " + super.getTel());
        System.out.print("E-mail: " + super.getEmail());
        System.out.print("Endereço: " + super.getAddress());
        System.out.print("Tipo de Produto: " + this.getProduct());
        System.out.print("Nome do Responsável: " + super.getName());
        System.out.print("CPF do Responsável: " + super.getCpf());
        System.out.println("*******************************");
    }
    
    public String printProviderToString() {
        String out = "";
        out = "******* Dados do Fornecedor *******\n"
                + "Nome da Empresa: " + this.getCompanyName()+ "\n"
                + "CNPJ: " + this.getCnpj() + "\n"
                + "Telefone/Whatsapp: " + super.getTel() + "\n"
                + "E-mail: " + super.getEmail() + "\n"
                + "Endereço: " + super.getAddress() + "\n"
                + "Tipo de Produto: " + this.getProduct()+ "\n"
                + "Nome do Responsável: " + super.getName()+ "\n"
                + "CPF do Responsável: " + super.getCpf()+ "\n"
                + "***********************************\n";
        return out;
    }
    
    public void copyProvider(Provider other) {
        this.setCompanyName(other.getCompanyName());
        this.setCnpj(other.getCnpj());
        super.setTel(other.getTel());
        super.setEmail(other.getEmail());
        super.setAddress(other.getAddress());
        this.setProduct(other.getProduct());  
        super.setName(other.getName());
        super.setCpf(other.getCpf());
    }

    /**
     * @return the companyName
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * @param companyName the companyName to set
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * @return the cnpj
     */
    public String getCnpj() {
        return cnpj;
    }

    /**
     * @param cnpj the cnpj to set
     */
    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    /**
     * @return the product
     */
    public String getProduct() {
        return product;
    }

    /**
     * @param product the product to set
     */
    public void setProduct(String product) {
        this.product = product;
    }

}
