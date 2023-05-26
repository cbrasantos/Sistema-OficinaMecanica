package com.mycompany.crud;

import connection.DBConnection;
import gui.MenuForm;

/**
 *
 * @author 
 * Rayanne ("https://www.github.com/rayanneblima")
 */

public class ProjetoCRUD {
    
    public static void main(String[] args){
        MenuForm tela = new MenuForm();
        tela.setVisible(true);
        
        DBConnection conn = new DBConnection();
        conn.getConnection();
        
        // trabalhando com datas
        /*Date now = new Date();
        SimpleDateFormat formataHora = new SimpleDateFormat("H");     
        int hora = Integer.parseInt(formataHora.format(now));
        // ou
        Calendar now2 = Calendar.getInstance();
        int hora2 = now2.get(Calendar.HOUR_OF_DAY);
        
        System.out.println(now2);*/
    }
    
}
