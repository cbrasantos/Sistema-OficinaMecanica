package view;

import controller.ClientController;
import controller.EmployeeController;
import controller.MenuController;
import controller.ProductController;
import controller.ProviderController;
import javax.swing.JOptionPane;

public class MenuForm extends javax.swing.JFrame {
    
    private String openedWindows;
    
    public MenuForm() {
        initComponents();
        this.openedWindows = "";
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        label1 = new java.awt.Label();
        btnClientForm = new javax.swing.JButton();
        btnProductForm = new javax.swing.JButton();
        btnEmployeeForm = new javax.swing.JButton();
        btnProviderForm = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sistema - Oficina / Auto-Peças");

        label1.setAlignment(java.awt.Label.CENTER);
        label1.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        label1.setText("Sistema - Oficina / Auto-Peças");

        btnClientForm.setText("Cadastrar Cliente");
        btnClientForm.setMinimumSize(new java.awt.Dimension(200, 100));
        btnClientForm.setPreferredSize(new java.awt.Dimension(200, 100));
        btnClientForm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClientFormActionPerformed(evt);
            }
        });

        btnProductForm.setText("Cadastrar Produto");
        btnProductForm.setMinimumSize(new java.awt.Dimension(200, 100));
        btnProductForm.setPreferredSize(new java.awt.Dimension(200, 100));
        btnProductForm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProductFormActionPerformed(evt);
            }
        });

        btnEmployeeForm.setText("Cadastrar Funcionário");
        btnEmployeeForm.setMinimumSize(new java.awt.Dimension(200, 100));
        btnEmployeeForm.setPreferredSize(new java.awt.Dimension(200, 100));
        btnEmployeeForm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEmployeeFormActionPerformed(evt);
            }
        });

        btnProviderForm.setText("Cadastrar Fornecedor");
        btnProviderForm.setMinimumSize(new java.awt.Dimension(200, 100));
        btnProviderForm.setPreferredSize(new java.awt.Dimension(200, 100));
        btnProviderForm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProviderFormActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(118, 118, 118)
                        .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnEmployeeForm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnClientForm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnProductForm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnProviderForm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(105, 105, 105)))
                .addGap(128, 128, 128))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnClientForm, btnEmployeeForm, btnProductForm, btnProviderForm});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addComponent(btnClientForm, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnEmployeeForm, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(btnProductForm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnProviderForm, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(54, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnClientForm, btnEmployeeForm, btnProductForm, btnProviderForm});

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnClientFormActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClientFormActionPerformed
        ClientController clientController = new ClientController();
        clientController.showView();
    }//GEN-LAST:event_btnClientFormActionPerformed

    private void btnEmployeeFormActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEmployeeFormActionPerformed
        EmployeeController employeeController = new EmployeeController();
        employeeController.showView();
    }//GEN-LAST:event_btnEmployeeFormActionPerformed

    private void btnProductFormActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProductFormActionPerformed
        ProductController productController = new ProductController();
        productController.showView();
        this.openedWindows = "Product";
    }//GEN-LAST:event_btnProductFormActionPerformed

    private void btnProviderFormActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProviderFormActionPerformed
        ProviderController providerController = new ProviderController();
        providerController.showView();
        this.openedWindows = "Provider";
    }//GEN-LAST:event_btnProviderFormActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClientForm;
    private javax.swing.JButton btnEmployeeForm;
    private javax.swing.JButton btnProductForm;
    private javax.swing.JButton btnProviderForm;
    private java.awt.Label label1;
    // End of variables declaration//GEN-END:variables
}
