package view;

import controller.ProviderController;
import model.Provider;
import model.dao.ProviderDAO;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

public class ProviderForm extends javax.swing.JFrame {

    private List<Provider> list;
    private Provider providerEditing;

    private ProviderController providerController;
    
    public ProviderForm(ProviderController providerController) {
        this.list = new ArrayList<>();
        this.providerEditing = null;
        
        initComponents();
        try {
            MaskFormatter maskTel = new MaskFormatter("(##)#####-####");
            MaskFormatter maskCnpj = new MaskFormatter("##.###.###/####-##");
            MaskFormatter maskCnpj2 = new MaskFormatter("##.###.###/####-##");
            MaskFormatter maskCpf = new MaskFormatter("###.###.###-##");
            
            maskTel.install(ftxtTel);
            maskCnpj.install(ftxtCnpj);
            maskCnpj2.install(ftxtSearch);
            maskCpf.install(ftxtCpf);
        } catch(ParseException ex) {
            Logger.getLogger(EmployeeForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.enableFields(false);
        this.clearFields();
    }
    
    public void enableFields(boolean flag) {
        for(int i = 0; i < pnlInputs.getComponents().length; i++){
            pnlInputs.getComponent(i).setEnabled(flag);
        }
    }
    
    public void clearFields() {
        txtCompanyName.setText("");
        ftxtCnpj.setText("");
        ftxtTel.setText("");
        ftxtEmail.setText("");
        txtAddress.setText("");
        txtProduct.setText("");
        txtResponsibleName.setText("");
        ftxtCpf.setText("");
    }
    
    public void fieldsToObject() {
        Provider p = new Provider();
        
        p.setCompanyName(txtCompanyName.getText());
        p.setCnpj(ftxtCnpj.getText());
        p.setTel(ftxtTel.getText());
        p.setEmail(ftxtEmail.getText());
        p.setAddress(txtAddress.getText());
        p.setProduct(txtProduct.getText());
        p.setName(txtResponsibleName.getText());
        p.setCpf(ftxtCpf.getText());
        
        list.add(p);  // adicionando em uma lista, sem BD
        ProviderDAO clientDAO = new ProviderDAO();
        clientDAO.insert(p);
    }
    
    public void objectToFields(Provider p) {        
        txtCompanyName.setText(p.getCompanyName());
        ftxtCnpj.setText(p.getCnpj());
        ftxtTel.setText(p.getTel());
        ftxtEmail.setText(p.getEmail());
        txtAddress.setText(p.getAddress());
        txtProduct.setText(p.getProduct());
        txtResponsibleName.setText(p.getName());  
        ftxtCpf.setText(p.getCpf());  
    }
    
    public Provider searchProvider(String code) {
        for(int i = 0; i < list.size(); i++) {
            if(list.get(i).getCnpj().equals(code)) return list.get(i);
        }
        return null;
    }
    
    public void printProviderList() {
        String [] column = {"Empresa", "Responsável", "Telefone/Whatsapp", "Tipo de Produto"};
        DefaultTableModel model = new DefaultTableModel(column, 0);
        
        for(int i = 0; i < list.size(); i++) {
            Object [] row = {list.get(i).getCompanyName(), list.get(i).getName(), list.get(i).getTel(), list.get(i).getProduct()};
            model.addRow(row);
        }
        tblListing.setModel(model);
    }
    
    public void printProvider(Provider p) {
        String [] column = {"Empresa", "Responsável", "Telefone/Whatsapp", "Tipo de Produto"};
        DefaultTableModel model = new DefaultTableModel(column, 0);
        
        Object [] row = {p.getCompanyName(), p.getName(), p.getTel(), p.getProduct()};
        model.addRow(row);
        tblListing.setModel(model);
    }
    
    public boolean verifyCNPJ(String cpf) {
        //TODO
        return true;
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
        if(!txtCompanyName.getText().replace(" ", "").matches("[A-Za-z]{3,}")){
            JOptionPane.showMessageDialog(this, "Preencha o nome da empresa corretamente. (Obs.: sem acento e somente letras)");
            txtCompanyName.requestFocus();
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
        
        if(ftxtCnpj.getText().replace(" ", "").length() < 18){
            JOptionPane.showMessageDialog(this, "Preencha o CNPJ corretamente.");
            ftxtCnpj.requestFocus();
            return false;
        } else {
            if(!verifyCNPJ(ftxtCnpj.getText())) {
                JOptionPane.showMessageDialog(this, "CNPJ inválido.");
                ftxtCnpj.setText("");
                ftxtCnpj.requestFocus();
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
        if(!ftxtEmail.getText().replace(" ", "").matches("([a-z]){1,}([a-z0-9._-]){1,}([@]){1}([a-z]){2,}([.]){1}([a-z]){2,}([.]?){1}([a-z]?){2,}")){
            JOptionPane.showMessageDialog(this, "Preencha o email corretamente. (Ex.: exemplo@gmail.com)");
            ftxtEmail.requestFocus();
            return false;
        }
        if(!txtResponsibleName.getText().replace(" ", "").matches("[A-Za-z]{3,}")){
            JOptionPane.showMessageDialog(this, "Preencha o nome do responsável corretamente. (Obs.: sem acento e somente letras)");
            txtResponsibleName.requestFocus();
            return false;
        }
        if(txtProduct.getText().replace(" ", "").length() < 5){
            JOptionPane.showMessageDialog(this, "Preencha o tipo de produto corretamente.");
            txtProduct.requestFocus();
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
        btnDelete = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnNew = new javax.swing.JButton();
        BtnCancel = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        pnlInputs = new javax.swing.JPanel();
        lblName = new javax.swing.JLabel();
        txtCompanyName = new javax.swing.JTextField();
        lblAddress = new javax.swing.JLabel();
        txtAddress = new javax.swing.JTextField();
        lblCnpj = new javax.swing.JLabel();
        lblTel = new javax.swing.JLabel();
        lblEmail = new javax.swing.JLabel();
        lblPosition = new javax.swing.JLabel();
        lblContractDate = new javax.swing.JLabel();
        ftxtCnpj = new javax.swing.JFormattedTextField();
        ftxtTel = new javax.swing.JFormattedTextField();
        ftxtEmail = new javax.swing.JFormattedTextField();
        txtProduct = new javax.swing.JFormattedTextField();
        txtResponsibleName = new javax.swing.JTextField();
        lblCpf = new javax.swing.JLabel();
        ftxtCpf = new javax.swing.JFormattedTextField();
        lblOutput = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblListing = new javax.swing.JTable();
        btnRefresh2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Sistema - Cadastro de Fornecedor");
        setMinimumSize(new java.awt.Dimension(674, 490));

        label1.setAlignment(java.awt.Label.CENTER);
        label1.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        label1.setText("Cadastro de Fornecedor");

        btnSearch.setIcon(new javax.swing.ImageIcon("C:\\Users\\Rayanne\\Desktop\\Estudos\\IF\\2020.2\\LPS\\Oficina\\MVC\\src\\main\\java\\images\\search.png")); // NOI18N
        btnSearch.setIconTextGap(0);
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
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

        btnNew.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        btnNew.setIcon(new javax.swing.ImageIcon("C:\\Users\\Rayanne\\Desktop\\Estudos\\IF\\2020.2\\LPS\\Oficina\\MVC\\src\\main\\java\\images\\add.png")); // NOI18N
        btnNew.setText("Novo");
        btnNew.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewActionPerformed(evt);
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

        pnlInputs.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 204, 204)));

        lblName.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblName.setText("Nome da Empresa:");

        lblAddress.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblAddress.setText("Endereço Completo:");

        lblCnpj.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblCnpj.setText("CNPJ:");

        lblTel.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblTel.setText("Telefone / Whatsapp:");

        lblEmail.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblEmail.setText("E-mail:");

        lblPosition.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblPosition.setText("Nome do Responsável:");

        lblContractDate.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblContractDate.setText("Tipo de Produto:");

        lblCpf.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblCpf.setText("CPF:");

        javax.swing.GroupLayout pnlInputsLayout = new javax.swing.GroupLayout(pnlInputs);
        pnlInputs.setLayout(pnlInputsLayout);
        pnlInputsLayout.setHorizontalGroup(
            pnlInputsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInputsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlInputsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlInputsLayout.createSequentialGroup()
                        .addGroup(pnlInputsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblPosition)
                            .addComponent(txtResponsibleName, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlInputsLayout.createSequentialGroup()
                        .addGroup(pnlInputsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlInputsLayout.createSequentialGroup()
                                .addComponent(lblAddress)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(pnlInputsLayout.createSequentialGroup()
                                .addGroup(pnlInputsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnlInputsLayout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addGroup(pnlInputsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblCpf)
                                            .addComponent(ftxtCpf, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(txtAddress))
                                .addGap(10, 10, 10)
                                .addGroup(pnlInputsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblEmail)
                                    .addComponent(ftxtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblContractDate)))
                            .addGroup(pnlInputsLayout.createSequentialGroup()
                                .addGroup(pnlInputsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtCompanyName, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblName))
                                .addGap(14, 14, 14)
                                .addGroup(pnlInputsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblCnpj)
                                    .addComponent(ftxtCnpj, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(pnlInputsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(ftxtTel)
                                    .addGroup(pnlInputsLayout.createSequentialGroup()
                                        .addComponent(lblTel)
                                        .addGap(0, 60, Short.MAX_VALUE)))))
                        .addContainerGap())))
        );
        pnlInputsLayout.setVerticalGroup(
            pnlInputsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlInputsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlInputsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblName)
                    .addComponent(lblCnpj)
                    .addComponent(lblTel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlInputsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCompanyName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ftxtCnpj, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ftxtTel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlInputsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlInputsLayout.createSequentialGroup()
                        .addComponent(lblAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlInputsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ftxtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pnlInputsLayout.createSequentialGroup()
                        .addComponent(lblEmail)
                        .addGap(26, 26, 26)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                .addGroup(pnlInputsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlInputsLayout.createSequentialGroup()
                        .addGroup(pnlInputsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblPosition)
                            .addComponent(lblContractDate))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlInputsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtProduct, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtResponsibleName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlInputsLayout.createSequentialGroup()
                        .addComponent(lblCpf)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ftxtCpf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        lblOutput.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblOutput.setText("Output:");

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

        btnRefresh2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/refresh.png"))); // NOI18N
        btnRefresh2.setIconTextGap(0);
        btnRefresh2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefresh2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(ftxtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jSeparator2, javax.swing.GroupLayout.DEFAULT_SIZE, 654, Short.MAX_VALUE)
                                .addComponent(jSeparator1))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnNew, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(BtnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pnlInputs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblOutput)
                        .addGap(557, 557, 557)
                        .addComponent(btnRefresh2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 624, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnSearch)
                            .addComponent(ftxtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BtnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNew)
                    .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlInputs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(lblOutput))
                    .addComponent(btnRefresh2, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(51, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        String chosenCpf = ftxtSearch.getText();
        Provider p = this.searchProvider(chosenCpf);

        if(chosenCpf.isEmpty()) {
            JOptionPane.showMessageDialog(this, "O CNPJ não foi informado!");
            printProviderList();
            ftxtSearch.requestFocus();
        }
        else if(ftxtSearch.getText().replace(" ", "").length() < 18){
            JOptionPane.showMessageDialog(this, "Preencha o CNPJ corretamente.");
            ftxtSearch.requestFocus();
        }
        else if(p == null) {
            JOptionPane.showMessageDialog(this, "Não existe fornecedor para o CNPJ informado!");
            printProviderList();
        } else {
            ftxtSearch.setText("");
            printProvider(p);
        }
    }//GEN-LAST:event_btnSearchActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        String chosenCode = JOptionPane.showInputDialog("Informe o CNPJ do fornecedor que deseja excluir:", "");

        Provider p = this.searchProvider(chosenCode);

        if(p == null) {
            JOptionPane.showMessageDialog(this, "Não foi encontrado um fornecedor para o CNPJ informado.");
        } else {
            Object[] options = { "Sim", "Não", "Cancelar" };
            int i = JOptionPane.showOptionDialog(this, "O fornecedor foi encontrado.", "Deseja realmente excluir?", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
            if(i == 0) {
                list.remove(p);
                JOptionPane.showMessageDialog(this, "O fornecedor foi excluído com sucesso.");
            }else {
                JOptionPane.showMessageDialog(this, "Exclusão cancelada.");
            }
        }
        printProviderList();
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        String chosenCode = JOptionPane.showInputDialog("Informe o CNPJ do fornecedor que deseja editar:", "");

        this.providerEditing = this.searchProvider(chosenCode);

        if(providerEditing == null) {
            JOptionPane.showMessageDialog(this, "Não foi encontrado um fornecedor para o CNPJ informado.");
        } else {
            this.clearFields();
            this.enableFields(true);
            this.objectToFields(providerEditing);
            txtCompanyName.requestFocus();
        }
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        this.enableFields(true);
        this.clearFields();
        txtCompanyName.requestFocus();
    }//GEN-LAST:event_btnNewActionPerformed

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
            if(this.providerEditing == null) { // inserindo novo funcionário
                this.fieldsToObject();
            } else { // salvando um funcionário que foi alterado
                this.list.remove(this.providerEditing);
                this.fieldsToObject();
            }
            this.clearFields();
            this.enableFields(false);

            JOptionPane.showMessageDialog(this, "O fornecedor foi salvo com sucesso.");
            printProviderList();
        }

        
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnRefresh2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefresh2ActionPerformed
        printProviderList();
    }//GEN-LAST:event_btnRefresh2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ProviderForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ProviderForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ProviderForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ProviderForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnCancel;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnRefresh2;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnSearch;
    private javax.swing.JFormattedTextField ftxtCnpj;
    private javax.swing.JFormattedTextField ftxtCpf;
    private javax.swing.JFormattedTextField ftxtEmail;
    private javax.swing.JFormattedTextField ftxtSearch;
    private javax.swing.JFormattedTextField ftxtTel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private java.awt.Label label1;
    private javax.swing.JLabel lblAddress;
    private javax.swing.JLabel lblCnpj;
    private javax.swing.JLabel lblContractDate;
    private javax.swing.JLabel lblCpf;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblOutput;
    private javax.swing.JLabel lblPosition;
    private javax.swing.JLabel lblTel;
    private javax.swing.JPanel pnlInputs;
    private javax.swing.JTable tblListing;
    private javax.swing.JTextField txtAddress;
    private javax.swing.JTextField txtCompanyName;
    private javax.swing.JFormattedTextField txtProduct;
    private javax.swing.JTextField txtResponsibleName;
    // End of variables declaration//GEN-END:variables
}
