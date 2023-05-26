package com.mycompany.crud;

import connection.DBConnection;
import controller.MenuController;

/**
 * @author 
 * Rayanne ("https://www.github.com/rayanneblima")
 */

public class ProjetoCRUD {
    
    public static void main(String[] args){
        MenuController menu = new MenuController();
        menu.showView();
        
        DBConnection conn = new DBConnection();
        conn.getConnection();
    }
    
}
