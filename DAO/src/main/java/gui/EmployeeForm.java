package gui;

import classes.Employee;
import dao.EmployeeDAO;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

public class EmployeeForm extends javax.swing.JFrame {

    private List<Employee> list;
    private Employee employeeEditing;

    public EmployeeForm() {
        this.list = new ArrayList<>();
        this.employeeEditing = null;
        
        initComponents();
        try {
            MaskFormatter maskDate = new MaskFormatter("##/##/####");
            MaskFormatter maskTel = new MaskFormatter("(##) #####-####");
            MaskFormatter maskCpf = new MaskFormatter("###.###.###-##");
            MaskFormatter maskCpf2 = new MaskFormatter("###.###.###-##");
            
            maskDate.install(ftxtContractDate);
            maskTel.install(ftxtTel);
            maskCpf.install(ftxtCpf);
            maskCpf2.install(ftxtSearch);
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
        txtName.setText("");
        ftxtCpf.setText("");
        ftxtTel.setText("");
        ftxtEmail.setText("");
        txtAddress.setText("");
        txtPosition.setText("");
        txtWorkHours.setText("");
        ftxtSalary.setText("");
        ftxtContractDate.setText("");
    }
    
    public void fieldsToObject() {
        
        Employee e = new Employee();
            
        e.setName(txtName.getText());
        e.setCpf(ftxtCpf.getText());
        e.setTel(ftxtTel.getText());
        e.setEmail(ftxtEmail.getText());
        e.setAddress(txtAddress.getText());
        e.setPosition(txtPosition.getText());
        e.setWorkHours(Integer.parseInt(txtWorkHours.getText()));
        e.setSalary(ftxtSalary.getText());
        e.setContractDate(ftxtContractDate.getText());
            
        list.add(e);  // adicionando em uma lista, sem BD
        EmployeeDAO clientDAO = new EmployeeDAO();
        clientDAO.insert(e);
    }
    
    public void objectToFields(Employee e) {        
        txtName.setText(e.getName());
        ftxtCpf.setText(e.getCpf());
        ftxtTel.setText(e.getTel());
        ftxtEmail.setText(e.getEmail());
        txtAddress.setText(e.getAddress());
        txtPosition.setText(e.getPosition());
        String hours = Integer.toString(e.getWorkHours());
        txtWorkHours.setText(hours);
        ftxtSalary.setText(e.getSalary());
        ftxtContractDate.setText(e.getContractDate());       
    }

    public Employee searchEmployee(String code) {
        for(int i = 0; i < list.size(); i++) {
            if(list.get(i).getCpf().equals(code)) return list.get(i);
        }
        return null;
    }
    
    public void printEmployeeList() {
        String [] column = {"Nome", "CPF", "Telefone/Whatsapp", "Salário"};
        DefaultTableModel model = new DefaultTableModel(column, 0);
        
        for(int i = 0; i < list.size(); i++) {
            Object [] row = {list.get(i).getName(), list.get(i).getCpf(), list.get(i).getTel(), list.get(i).getSalary()};
            model.addRow(row);
        }
        tblListing.setModel(model);
    }
    
    public void printEmployee(Employee e) {
        String [] column = {"Nome", "CPF", "Telefone/Whatsapp", "Salário"};
        DefaultTableModel model = new DefaultTableModel(column, 0);
        
        Object [] row = {e.getName(), e.getCpf(), e.getTel(), e.getSalary()};
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
            JOptionPane.showMessageDialog(this, "Preencha o nome do funcionário corretamente. (Obs.: sem acento e somente letras)");
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
        if(!ftxtEmail.getText().replace(" ", "").matches("([a-z]){1,}([a-z0-9._-]){1,}([@]){1}([a-z]){2,}([.]){1}([a-z]){2,}([.]?){1}([a-z]?){2,}")){
            JOptionPane.showMessageDialog(this, "Preencha o email corretamente. (Ex.: exemplo@gmail.com)");
            ftxtEmail.requestFocus();
            return false;
        }
        if(!txtPosition.getText().replace(" ", "").matches("[A-Za-z]{5,}")){
            JOptionPane.showMessageDialog(this, "Preencha o cargo corretamente. (Obs.: sem acento e somente letras)");
            txtPosition.requestFocus();
            return false;
        }
        if(!txtWorkHours.getText().replace(" ", "").matches("[0-9]{1,}")){
            JOptionPane.showMessageDialog(this, "Preencha corretamente as horas trabalhadas.");
            txtWorkHours.requestFocus();
            return false;
        }
        if(ftxtSalary.getText().replace(" ", "").matches("((R\\$)?)(([1-9]\\d{0,2}(\\.\\d{3})*)|(([1-9]\\.\\d*)?\\d))(\\,\\d\\d)?")){
            JOptionPane.showMessageDialog(this, "Preencha o salário corretamente. (Ex.: R$0.000,00)");
            ftxtSalary.requestFocus();
            return false;
        }
        if(!ftxtContractDate.getText().replace(" ", "").matches("[0-9]{2}[\\/]{1}[0-9]{2}[\\/]{1}[0-9]{4}")){
            JOptionPane.showMessageDialog(this, "Preencha a data de contratação corretamente. (Ex.: 11/11/2000)");
            ftxtContractDate.requestFocus();
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
        pnlInputs = new javax.swing.JPanel();
        lblName = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        lblAddress = new javax.swing.JLabel();
        txtAddress = new javax.swing.JTextField();
        lblCPF = new javax.swing.JLabel();
        lblTel = new javax.swing.JLabel();
        lblEmail = new javax.swing.JLabel();
        lblPosition = new javax.swing.JLabel();
        txtPosition = new javax.swing.JTextField();
        lblWorkHours = new javax.swing.JLabel();
        txtWorkHours = new javax.swing.JTextField();
        lblSalary = new javax.swing.JLabel();
        lblContractDate = new javax.swing.JLabel();
        ftxtCpf = new javax.swing.JFormattedTextField();
        ftxtTel = new javax.swing.JFormattedTextField();
        ftxtEmail = new javax.swing.JFormattedTextField();
        ftxtSalary = new javax.swing.JFormattedTextField();
        ftxtContractDate = new javax.swing.JFormattedTextField();
        lblOutput = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblListing = new javax.swing.JTable();
        btnRefresh2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sistema - Cadastro de Funcionário");
        setMaximumSize(null);
        setMinimumSize(new java.awt.Dimension(674, 499));

        label1.setAlignment(java.awt.Label.CENTER);
        label1.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        label1.setText("Cadastro de Funcionário");

        btnSearch.setIcon(new javax.swing.ImageIcon("C:\\Users\\Rayanne\\Desktop\\Estudos\\IF\\2020.2\\LPS\\ERE_LPS_TAREFA1_RAYANNE\\CRUD\\src\\main\\java\\images\\search.png")); // NOI18N
        btnSearch.setIconTextGap(0);
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        btnNew.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        btnNew.setIcon(new javax.swing.ImageIcon("C:\\Users\\Rayanne\\Desktop\\Estudos\\IF\\2020.2\\LPS\\ERE_LPS_TAREFA1_RAYANNE\\CRUD\\src\\main\\java\\images\\add.png")); // NOI18N
        btnNew.setText("Novo");
        btnNew.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewActionPerformed(evt);
            }
        });

        btnEdit.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        btnEdit.setIcon(new javax.swing.ImageIcon("C:\\Users\\Rayanne\\Desktop\\Estudos\\IF\\2020.2\\LPS\\ERE_LPS_TAREFA1_RAYANNE\\CRUD\\src\\main\\java\\images\\edit.png")); // NOI18N
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
        btnDelete.setIcon(new javax.swing.ImageIcon("C:\\Users\\Rayanne\\Desktop\\Estudos\\IF\\2020.2\\LPS\\ERE_LPS_TAREFA1_RAYANNE\\CRUD\\src\\main\\java\\images\\delete.png")); // NOI18N
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
        BtnCancel.setIcon(new javax.swing.ImageIcon("C:\\Users\\Rayanne\\Desktop\\Estudos\\IF\\2020.2\\LPS\\ERE_LPS_TAREFA1_RAYANNE\\CRUD\\src\\main\\java\\images\\cancel.png")); // NOI18N
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
        btnSave.setIcon(new javax.swing.ImageIcon("C:\\Users\\Rayanne\\Desktop\\Estudos\\IF\\2020.2\\LPS\\ERE_LPS_TAREFA1_RAYANNE\\CRUD\\src\\main\\java\\images\\save.png")); // NOI18N
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
        lblName.setText("Nome Completo:");

        lblAddress.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblAddress.setText("Endereço Completo:");

        lblCPF.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblCPF.setText("CPF:");

        lblTel.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblTel.setText("Telefone / Whatsapp:");

        lblEmail.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblEmail.setText("E-mail:");

        lblPosition.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblPosition.setText("Cargo:");

        lblWorkHours.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblWorkHours.setText("Horas Trabalhadas:");

        lblSalary.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblSalary.setText("Salário:");

        lblContractDate.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblContractDate.setText("Data de Contratação:");

        javax.swing.GroupLayout pnlInputsLayout = new javax.swing.GroupLayout(pnlInputs);
        pnlInputs.setLayout(pnlInputsLayout);
        pnlInputsLayout.setHorizontalGroup(
            pnlInputsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInputsLayout.createSequentialGroup()
                .addContainerGap()
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
                    .addComponent(txtAddress)
                    .addGroup(pnlInputsLayout.createSequentialGroup()
                        .addGroup(pnlInputsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblPosition)
                            .addComponent(txtPosition, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlInputsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlInputsLayout.createSequentialGroup()
                                .addComponent(lblWorkHours)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblSalary)
                                .addGap(118, 118, 118))
                            .addGroup(pnlInputsLayout.createSequentialGroup()
                                .addComponent(txtWorkHours, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(ftxtSalary)))))
                .addGap(18, 18, 18)
                .addGroup(pnlInputsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblTel)
                    .addComponent(lblEmail)
                    .addComponent(lblContractDate)
                    .addComponent(ftxtTel)
                    .addComponent(ftxtEmail)
                    .addComponent(ftxtContractDate, javax.swing.GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE))
                .addContainerGap())
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
                            .addComponent(ftxtCpf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ftxtTel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pnlInputsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblTel)
                        .addComponent(lblCPF)))
                .addGap(18, 18, 18)
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
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(pnlInputsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPosition)
                    .addComponent(lblWorkHours)
                    .addComponent(lblSalary)
                    .addComponent(lblContractDate))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlInputsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtWorkHours, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPosition, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ftxtSalary, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ftxtContractDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(61, 61, 61))
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

        btnRefresh2.setIcon(new javax.swing.ImageIcon("C:\\Users\\Rayanne\\Desktop\\Estudos\\IF\\2020.2\\LPS\\ERE_LPS_TAREFA1_RAYANNE\\CRUD\\src\\main\\java\\images\\refresh.png")); // NOI18N
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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(ftxtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator2)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(pnlInputs, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lblOutput)
                                        .addGap(557, 557, 557)
                                        .addComponent(btnRefresh2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 624, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 10, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(5, 5, 5)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(64, 279, Short.MAX_VALUE))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jSeparator1)
                            .addGap(5, 5, 5)))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(ftxtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSearch))
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNew)
                    .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BtnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlInputs, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(lblOutput))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnRefresh2)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(24, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(14, 14, 14)
                    .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(420, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        String chosenCpf = ftxtSearch.getText();
        
        Employee e = this.searchEmployee(chosenCpf);

        if(chosenCpf.isEmpty()) {
            JOptionPane.showMessageDialog(this, "O CPF não foi informado!");
            printEmployeeList();
        }
        else if(ftxtSearch.getText().replace(" ", "").length() < 14){
            JOptionPane.showMessageDialog(this, "Preencha o CPF corretamente.");
            ftxtSearch.requestFocus();
        }
        else if(e == null) {
            JOptionPane.showMessageDialog(this, "Não existe funcionário para o CPF informado!");
            printEmployeeList();
        } else {
            ftxtSearch.setText("");
            printEmployee(e);
        }
    }//GEN-LAST:event_btnSearchActionPerformed

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        this.enableFields(true);
        this.clearFields();
        ftxtSalary.setText("R$");
        txtName.requestFocus();
    }//GEN-LAST:event_btnNewActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        String chosenCode = JOptionPane.showInputDialog("Informe o CPF do funcionário que deseja editar:", "");
        String maskCpf = chosenCode.substring(0, 3) + "." + chosenCode.substring(3, 6) + "." + chosenCode.substring(6, 9) + "-" + chosenCode.substring(9, 11);

        this.employeeEditing = this.searchEmployee(maskCpf);

        if(employeeEditing == null) {
            JOptionPane.showMessageDialog(this, "Não foi encontrado um funcionário para o CPF informado.");
        } else {
            this.clearFields();
            this.enableFields(true);
            this.objectToFields(employeeEditing);
            txtName.requestFocus();
        }
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        String chosenCode = JOptionPane.showInputDialog("Informe o CPF do funcionário que deseja excluir:", "");
        String maskCpf = chosenCode.substring(0, 3) + "." + chosenCode.substring(3, 6) + "." + chosenCode.substring(6, 9) + "-" + chosenCode.substring(9, 11);

        Employee e = this.searchEmployee(maskCpf);

        if(e == null) {
            JOptionPane.showMessageDialog(this, "Não foi encontrado um funcionário para o CPF informado.");
        } else {
            Object[] options = { "Sim", "Não", "Cancelar" };
            int i = JOptionPane.showOptionDialog(this, "O funcionário foi encontrado.", "Deseja realmente excluir?", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
            if(i == 0) {
                list.remove(e);
                JOptionPane.showMessageDialog(this, "O funcionário foi excluído com sucesso.");
            }else {
                JOptionPane.showMessageDialog(this, "Exclusão cancelada.");
            }
        }
        printEmployeeList();
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
               
        if(validateFields()){
            if(this.employeeEditing == null) { // inserindo novo funcionário
                this.fieldsToObject();
            } else { // salvando um funcionário que foi alterado
                this.list.remove(this.employeeEditing);
                this.fieldsToObject();
            }

            this.clearFields();
            this.enableFields(false);

            JOptionPane.showMessageDialog(this, "O funcionário foi salvo com sucesso.");
            printEmployeeList();
        }

    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnRefresh2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefresh2ActionPerformed
        printEmployeeList();
    }//GEN-LAST:event_btnRefresh2ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnCancel;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnRefresh2;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnSearch;
    private javax.swing.JFormattedTextField ftxtContractDate;
    private javax.swing.JFormattedTextField ftxtCpf;
    private javax.swing.JFormattedTextField ftxtEmail;
    private javax.swing.JFormattedTextField ftxtSalary;
    private javax.swing.JFormattedTextField ftxtSearch;
    private javax.swing.JFormattedTextField ftxtTel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private java.awt.Label label1;
    private javax.swing.JLabel lblAddress;
    private javax.swing.JLabel lblCPF;
    private javax.swing.JLabel lblContractDate;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblOutput;
    private javax.swing.JLabel lblPosition;
    private javax.swing.JLabel lblSalary;
    private javax.swing.JLabel lblTel;
    private javax.swing.JLabel lblWorkHours;
    private javax.swing.JPanel pnlInputs;
    private javax.swing.JTable tblListing;
    private javax.swing.JTextField txtAddress;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtPosition;
    private javax.swing.JTextField txtWorkHours;
    // End of variables declaration//GEN-END:variables
}
