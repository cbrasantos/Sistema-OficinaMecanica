package classes;

import java.util.Scanner;

public class Stock {
    private int id;
    private Product[] productList;
    
    public Stock() {
        this.id = 0;
        this.productList = new Product[5];
    }
    
    public void fillStock() {
        Scanner scan = new Scanner(System.in);
        System.out.println("******* Preencha o Estoque *******");
        System.out.print("Informe o id do estoque: ");
        this.id = scan.nextInt();
        System.out.print("Informe os produtos que irão para o estoque " + this.id + ": ");

        for(int i = 0; i < 5; i++) {
            productList[i].fillProduct();
        }
    }
    
    public void printStock() {
        System.out.println("******* Dados do Estoque *******");
        System.out.print("ID: " + this.id);
        for(int i = 0; i < 5; i++) {
            productList[i].printProduct();
        }
        System.out.println("******************************");
    }
    
    public void copyStock(Stock other) {
        this.id = other.getId();
        for(int i = 0; i < 5; i++) {
            this.productList[i].copyProduct(other.getProductList()[i]);
        }
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the productList
     */
    public Product[] getProductList() {
        return productList;
    }

    /**
     * @param productList the productList to set
     */
    public void setProductList(Product[] productList) {
        this.productList = productList;
    }
    
}
