package classes;

import java.util.Scanner;


public class Product {
    private String name;
    private String costPrice;
    private String salePrice;
    private int quantity;
    private String code;
    private String description;
    
    public Product() {
        this.name = "";
        this.costPrice = "";
        this.salePrice = "";
        this.quantity = 0;
        this.code = "";
        this.description = "";
    }
    
    public void fillProduct() {
        Scanner scan = new Scanner(System.in);
        System.out.println("******* Preencha o Produto *******");
        System.out.print("Informe o nome do produto: ");
        this.name = scan.nextLine();
        System.out.print("Informe o preço de custo do produto: ");
        this.costPrice = scan.nextLine();
        System.out.print("Informe o preço de venda do produto: ");
        this.salePrice = scan.nextLine();
        System.out.print("Informe a quantidade do produto: ");
        this.quantity = scan.nextInt();
        System.out.print("Informe o código do produto: ");
        this.setCode(scan.next());
        System.out.print("Informe a descrição do produto: ");
        this.description = scan.nextLine();
    }
    
    public void printProduct() {
        System.out.println("******* Dados do Produto *******");
        System.out.print("Nome: " + this.name);
        System.out.print("Preço de Custo: " + this.costPrice);
        System.out.print("Preço de Venda: " + this.salePrice);
        System.out.print("Quantidade: " + this.quantity);
        System.out.print("Código: " + this.getCode());
        System.out.print("Descrição: " + this.description);
        System.out.println("*******************************");
    }
    
    public String printProductToString() {
        String out = "";
        out = "******* Dados do Produto *******\n"
                + "Nome: " + this.name + "\n"
                + "Preço de Custo: " + this.costPrice + "\n"
                + "Preço de Venda: " + this.salePrice + "\n"
                + "Quantidade: " + this.quantity + "\n"
                + "Código: " + this.getCode() + "\n"
                + "Descrição: " + this.description + "\n"
                + "***********************************\n";
        return out;
    }
    
    public void copyProduct(Product other) {
        this.name = other.getName();
        this.costPrice = other.getCostPrice();
        this.salePrice = other.getSalePrice();
        this.quantity = other.getQuantity();
        this.setCode(other.getCode());
        this.description = other.getDescription();       
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
     * @return the cost price
     */
    public String getCostPrice() {
        return costPrice;
    }

    /**
     * @param costPrice the cost price to set
     */
    public void setCostPrice(String costPrice) {
        this.costPrice = costPrice;
    }
    
    /**
     * @return the sale price
     */
    public String getSalePrice() {
        return salePrice;
    }

    /**
     * @param salePrice thesale  price to set
     */
    public void setSalePrice(String salePrice) {
        this.salePrice = salePrice;
    }

    /**
     * @return the quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * @param quantity the quantity to set
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }
    
    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }
   
}
