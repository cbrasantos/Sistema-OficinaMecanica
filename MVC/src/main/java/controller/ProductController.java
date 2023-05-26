package controller;

import view.ProductForm;

public class ProductController {
    
    public void showView() {
        ProductForm productView = new ProductForm(this);
        productView.setVisible(true);
        productView.setLocationRelativeTo(null);
    }
}
