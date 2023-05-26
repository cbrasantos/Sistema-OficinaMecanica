package view;

import controller.ClientController;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

public class ClientForm extends javax.swing.JFrame {

    private String[] clientEditing;
    private ClientController clientController;
    
    public ClientForm(ClientController clientController) {
        this.clientEditing = null;
        this.clientController = clientController;
        
        initComponents();
        
        try {
            MaskFormatter maskTel = new MaskFormatter("(##) #####-####");
            MaskFormatter maskCpf = new MaskFormatter("###.###.###-##");
            MaskFormatter maskCpf2 = new MaskFormatter("###.###.###-##");
            
            maskTel.install(ftxtTel);
            maskCpf.install(ftxtCpf);
            maskCpf2.install(ftxtSearch);
        } catch(ParseException ex) {
            Logger.getLogger(EmployeeForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.printClientList();
        this.enableFields(false);
        this.clearFields();
    }

    public void enableFields(boolean flag) {
        for(int i = 0; i < pnlInputs.getComponents().length; i++){
            pnlInputs.getComponent(i).setEnabled(flag);
        }
    }
    
    public void clearFields() {
        txtName.setText("");
        ftxtCpf.setText("");
        ftxtTel.setText("");
        txtEmail.setText("");
        txtAddress.setText("");
        txtVehicles.setText("");
    }
    
    public void fieldsToObject() {
        String[] client = new String[5];
        
        client[0] = txtName.getText();
        client[1] = (ftxtCpf.getText());
        client[2] = (ftxtTel.getText());
        client[3] = (txtAddress.getText());
        client[4] = (txtEmail.getText());
        //c.setVehicles(txtVehicles.getText());  
        
        clientController.createClient(client);
    }
    
    public void objectToFields(String[] p) {
        txtName.setText(p[0]);
        ftxtCpf.setText(p[1]);
        ftxtTel.setText(p[2]);
        txtAddress.setText(p[3]);
        txtEmail.setText(p[4]);
    }
    
    public String[] searchClient(String code) {
        String[] response = clientController.getClientByCpf(code);
        if(!response[0].equals("")) {
            return response;
        }
        return null;
    }
    
    public void printClientList() {
        String[] column = {"Nome", "CPF", "Telefone/Whatsapp"};
        DefaultTableModel model = new DefaultTableModel(column, 0);

       ArrayList<String []> response = clientController.getClients();
       
        for(int i = 0; i < response.size(); i++) {
            Object [] row = {response.get(i)[0], response.get(i)[1], response.get(i)[2]};
            model.addRow(row);
        }
        tblListing.setModel(model);
    }
    
    public void printClient(String p[]) {
        String [] column = {"Nome", "CPF", "Telefone/Whatsapp"};
        DefaultTableModel model = new DefaultTableModel(column, 0);
        
        Object [] row = {p[0], p[1], p[2]};
        model.addRow(row);
        tblListing.setModel(model);
    }
    
    public boolean verifyCPF(String cpf) {
        int digito1 = 0, digito2 = 0, calcDigito1 = 0, calcDigito2 = 0, j = 10, z = 11;
        int [] arrayCpf = new int[9];
        boolean repetido = true;
        
        digito1 = Integer.parseInt(cpf.substring(12, 13));
        digito2 = Integer.parseInt(cpf.substring(13, 14));
        
        cpf = cpf.substring(0, 3) + cpf.substring(4, 7) + cpf.substring(8, 11);
       
        for(int i = 0; i < arrayCpf.length; i++) {
            arrayCpf[i] = Integer.parseInt(cpf.substring(i, i+1));
            
            calcDigito1 += j * arrayCpf[i];
            j--;
            
            calcDigito2 += z * arrayCpf[i];
            z--;
            
            if(arrayCpf[0] != arrayCpf[i] && repetido) repetido = false;   
        }
        
        calcDigito2 += digito1 * z;
        
        calcDigito1 = calcDigito1 * 10 % 11;
        calcDigito2 = calcDigito2 * 10 % 11;
        
        if(calcDigito1 == 10) {
            calcDigito1 = 0;
        }
        
        if(calcDigito2 == 10) {
            calcDigito2 = 0;
        }
        
        if((calcDigito1 != digito1) || (calcDigito2 != digito2) || repetido) {
            return false;
        } else {
            return true;
        }
    }
    
    public boolean validateFields() {
        if(!txtName.getText().replace(" ", "").matches("[A-Za-z]{3,}")){
            JOptionPane.showMessageDialog(this, "Preencha o nome do cliente corretamente. (Obs.: sem acento e somente letras)");
            txtName.requestFocus();
            return false;
        }
        if(ftxtCpf.getText().replace(" ", "").length() < 14){
            JOptionPane.showMessageDialog(this, "Preencha o CPF corretamente.");
            ftxtCpf.requestFocus();
            return false;
        } else {
            if(!verifyCPF(ftxtCpf.getText())) {
                JOptionPane.showMessageDialog(this, "CPF inválido.");
                ftxtCpf.setText("");
                ftxtCpf.requestFocus();
                return false;
            }
        }
        if(ftxtTel.getText().replace(" ", "").length() < 13){
            JOptionPane.showMessageDialog(this, "Preencha o telefone corretamente.");
            ftxtTel.requestFocus();
            return false;
        }
        if(txtAddress.getText().replace(" ", "").length() < 20){
            JOptionPane.showMessageDialog(this, "Preencha o endereço corretamente. Ex.: Rua Exemplo, 24, Centro - Cidade/Estado");
            txtAddress.requestFocus();
            return false;
        }
        if(!txtEmail.getText().replace(" ", "").matches("([a-z]){1,}([a-z0-9._-]){1,}([@]){1}([a-z]){2,}([.]){1}([a-z]){2,}([.]?){1}([a-z]?){2,}")){
            JOptionPane.showMessageDialog(this, "Preencha o email corretamente. (Ex.: exemplo@gmail.com)");
            txtEmail.requestFocus();
            return false;
        }
        if(!txtVehicles.getText().replace(" ", "").matches("[A-Za-z]{3,}")){
            JOptionPane.showMessageDialog(this, "Preencha o veículo corretamente. (Obs.: sem acento e somente letras)");
            txtVehicles.requestFocus();
            return false;
        }
        return true;
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        label1 = new java.awt.Label();
        ftxtSearch = new javax.swing.JFormattedTextField();
        btnSearch = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        btnNew = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        BtnCancel = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        lblOutput = new javax.swing.JLabel();
        pnlInputs = new javax.swing.JPanel();
        lblName = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        lblAddress = new javax.swing.JLabel();
        txtAddress = new javax.swing.JTextField();
        scpVehicles = new javax.swing.JScrollPane();
        txtVehicles = new javax.swing.JTextArea();
        lblVehicles = new javax.swing.JLabel();
        lblCPF = new javax.swing.JLabel();
        lblTel = new javax.swing.JLabel();
        lblEmail = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        ftxtTel = new javax.swing.JFormattedTextField();
        ftxtCpf = new javax.swing.JFormattedTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblListing = new javax.swing.JTable();
        btnRefresh = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Sistema - Cadastro de Cliente");
        setMinimumSize(new java.awt.Dimension(674, 584));

        label1.setAlignment(java.awt.Label.CENTER);
        label1.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        label1.setText("Cadastro de Cliente");

        btnSearch.setIcon(new javax.swing.ImageIcon("C:\\Users\\Rayanne\\Desktop\\Estudos\\IF\\2020.2\\LPS\\Oficina\\MVC\\src\\main\\java\\images\\search.png")); // NOI18N
        btnSearch.setIconTextGap(0);
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        btnNew.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        btnNew.setIcon(new javax.swing.ImageIcon("C:\\Users\\Rayanne\\Desktop\\Estudos\\IF\\2020.2\\LPS\\Oficina\\MVC\\src\\main\\java\\images\\add.png")); // NOI18N
        btnNew.setText("Novo");
        btnNew.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewActionPerformed(evt);
            }
        });

        btnEdit.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        btnEdit.setIcon(new javax.swing.ImageIcon("C:\\Users\\Rayanne\\Desktop\\Estudos\\IF\\2020.2\\LPS\\Oficina\\MVC\\src\\main\\java\\images\\edit.png")); // NOI18N
        btnEdit.setText("Editar");
        btnEdit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEdit.setMaximumSize(new java.awt.Dimension(89, 33));
        btnEdit.setMinimumSize(new java.awt.Dimension(89, 33));
        btnEdit.setPreferredSize(new java.awt.Dimension(89, 33));
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        btnDelete.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        btnDelete.setIcon(new javax.swing.ImageIcon("C:\\Users\\Rayanne\\Desktop\\Estudos\\IF\\2020.2\\LPS\\Oficina\\MVC\\src\\main\\java\\images\\delete.png")); // NOI18N
        btnDelete.setText("Excluir");
        btnDelete.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDelete.setMaximumSize(new java.awt.Dimension(89, 33));
        btnDelete.setMinimumSize(new java.awt.Dimension(89, 33));
        btnDelete.setPreferredSize(new java.awt.Dimension(89, 33));
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        BtnCancel.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        BtnCancel.setIcon(new javax.swing.ImageIcon("C:\\Users\\Rayanne\\Desktop\\Estudos\\IF\\2020.2\\LPS\\Oficina\\MVC\\src\\main\\java\\images\\cancel.png")); // NOI18N
        BtnCancel.setText("Cancelar");
        BtnCancel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BtnCancel.setMaximumSize(new java.awt.Dimension(89, 33));
        BtnCancel.setMinimumSize(new java.awt.Dimension(89, 33));
        BtnCancel.setPreferredSize(new java.awt.Dimension(89, 33));
        BtnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCancelActionPerformed(evt);
            }
        });

        btnSave.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        btnSave.setIcon(new javax.swing.ImageIcon("C:\\Users\\Rayanne\\Desktop\\Estudos\\IF\\2020.2\\LPS\\Oficina\\MVC\\src\\main\\java\\images\\save.png")); // NOI18N
        btnSave.setText("Salvar");
        btnSave.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSave.setMaximumSize(new java.awt.Dimension(89, 33));
        btnSave.setMinimumSize(new java.awt.Dimension(89, 33));
        btnSave.setPreferredSize(new java.awt.Dimension(89, 33));
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        lblOutput.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblOutput.setText("Output:");

        pnlInputs.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 204, 204)));

        lblName.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblName.setText("Nome Completo:");

        lblAddress.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblAddress.setText("Endereço Completo:");

        txtVehicles.setColumns(20);
        txtVehicles.setRows(5);
        scpVehicles.setViewportView(txtVehicles);

        lblVehicles.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblVehicles.setText("Veículos:");

        lblCPF.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblCPF.setText("CPF:");

        lblTel.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblTel.setText("Telefone / Whatsapp:");

        lblEmail.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblEmail.setText("E-mail:");

        javax.swing.GroupLayout pnlInputsLayout = new javax.swing.GroupLayout(pnlInputs);
        pnlInputs.setLayout(pnlInputsLayout);
        pnlInputsLayout.setHorizontalGroup(
            pnlInputsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInputsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlInputsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlInputsLayout.createSequentialGroup()
                        .addComponent(lblVehicles)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlInputsLayout.createSequentialGroup()
                        .addGroup(pnlInputsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(scpVehicles, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlInputsLayout.createSequentialGroup()
                                .addGroup(pnlInputsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnlInputsLayout.createSequentialGroup()
                                        .addGroup(pnlInputsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblName)
                                            .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lblAddress))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(pnlInputsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addGroup(pnlInputsLayout.createSequentialGroup()
                                                .addComponent(lblCPF)
                                                .addGap(149, 149, 149))
                                            .addComponent(ftxtCpf)))
                                    .addComponent(txtAddress))
                                .addGap(18, 18, 18)
                                .addGroup(pnlInputsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(lblTel)
                                    .addComponent(lblEmail)
                                    .addComponent(txtEmail, javax.swing.GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE)
                                    .addComponent(ftxtTel))))
                        .addGap(20, 20, 20))))
        );
        pnlInputsLayout.setVerticalGroup(
            pnlInputsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlInputsLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlInputsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlInputsLayout.createSequentialGroup()
                        .addComponent(lblName)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlInputsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ftxtCpf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pnlInputsLayout.createSequentialGroup()
                        .addGroup(pnlInputsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblTel)
                            .addComponent(lblCPF))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ftxtTel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(pnlInputsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlInputsLayout.createSequentialGroup()
                        .addComponent(lblAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlInputsLayout.createSequentialGroup()
                        .addComponent(lblEmail)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(lblVehicles)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scpVehicles, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        tblListing.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tblListing);

        btnRefresh.setIcon(new javax.swing.ImageIcon("C:\\Users\\Rayanne\\Desktop\\Estudos\\IF\\2020.2\\LPS\\Oficina\\MVC\\src\\main\\java\\images\\refresh.png")); // NOI18N
        btnRefresh.setIconTextGap(0);
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(ftxtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnNew, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BtnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator2)
                    .addComponent(pnlInputs, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSeparator1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane1)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblOutput)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(11, 11, 11)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(ftxtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNew)
                    .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BtnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(9, 9, 9)
                .addComponent(pnlInputs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblOutput))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {BtnCancel, btnDelete, btnEdit, btnNew, btnSave});

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        String chosenCpf = ftxtSearch.getText();
        
        String[] p = this.searchClient(chosenCpf);
        
        if(chosenCpf.isEmpty()) {
            JOptionPane.showMessageDialog(this, "O CPF não foi informado!");
            printClientList();
        }
        else if(ftxtSearch.getText().replace(" ", "").length() < 14){
            JOptionPane.showMessageDialog(this, "Preencha o CPF corretamente.");
            ftxtSearch.requestFocus();
        }
        else if(p == null) {
            JOptionPane.showMessageDialog(this, "Não existe cliente para o CPF informado!");
            printClientList();
        } else {
            ftxtSearch.setText("");
            printClient(p);
        }
    }//GEN-LAST:event_btnSearchActionPerformed

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        this.enableFields(true);
        this.clearFields();
        txtName.requestFocus();
    }//GEN-LAST:event_btnNewActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        String chosenCode = JOptionPane.showInputDialog("Informe o CPF do cliente que deseja editar:", "");
        String maskCpf = chosenCode.substring(0, 3) + "." + chosenCode.substring(3, 6) + "." + chosenCode.substring(6, 9) + "-" + chosenCode.substring(9, 11);
        
        this.clientEditing = this.searchClient(maskCpf);
        
        if(clientEditing == null) {
            JOptionPane.showMessageDialog(this, "Não foi encontrado um cliente para o CPF informado.");
        } else {
            this.clearFields();
            this.enableFields(true);
            this.objectToFields(clientEditing);
            txtName.requestFocus();
        }
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        String chosenCode = JOptionPane.showInputDialog("Informe o CPF do cliente que deseja excluir:", "");
        String maskCpf = chosenCode.substring(0, 3) + "." + chosenCode.substring(3, 6) + "." + chosenCode.substring(6, 9) + "-" + chosenCode.substring(9, 11);
        
        String[] p = this.searchClient(maskCpf);
        
        if(p == null) {
            JOptionPane.showMessageDialog(this, "Não foi encontrado um cliente para o CPF informado.");
        } else {
            Object[] options = { "Sim", "Não", "Cancelar" };
            int i = JOptionPane.showOptionDialog(this, "O cliente foi encontrado.", "Deseja realmente excluir?", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
            if(i == 0) {
                clientController.deleteClient(maskCpf);
                JOptionPane.showMessageDialog(this, "O cliente foi excluído com sucesso.");
            }else {
                JOptionPane.showMessageDialog(this, "Exclusão cancelada.");
            }
        }
        printClientList();   
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void BtnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCancelActionPerformed
        Object[] options = { "Sim", "Não" };
        int i = JOptionPane.showOptionDialog(this, "Ao cancelar, todas as informações digitadas serão perdidas", "Deseja realmente cancelar?", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        if(i == 0) {
            this.clearFields();
            this.enableFields(false);
        }        
    }//GEN-LAST:event_BtnCancelActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        if(validateFields()) {
            if(this.clientEditing == null) { // inserindo novo cliente
                this.fieldsToObject();
            } else { // salvando um cliente que foi alterado
                clientController.updateClient(this.clientEditing);
                printClientList();
            }

            this.clearFields();
            this.enableFields(false);

            JOptionPane.showMessageDialog(this, "O cliente foi salvo com sucesso.");
            printClientList();
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        printClientList();
    }//GEN-LAST:event_btnRefreshActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnCancel;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnSearch;
    private javax.swing.JFormattedTextField ftxtCpf;
    private javax.swing.JFormattedTextField ftxtSearch;
    private javax.swing.JFormattedTextField ftxtTel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private java.awt.Label label1;
    private javax.swing.JLabel lblAddress;
    private javax.swing.JLabel lblCPF;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblOutput;
    private javax.swing.JLabel lblTel;
    private javax.swing.JLabel lblVehicles;
    private javax.swing.JPanel pnlInputs;
    private javax.swing.JScrollPane scpVehicles;
    private javax.swing.JTable tblListing;
    private javax.swing.JTextField txtAddress;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextArea txtVehicles;
    // End of variables declaration//GEN-END:variables
}
