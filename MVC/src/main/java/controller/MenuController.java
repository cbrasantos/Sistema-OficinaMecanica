
package controller;

import view.MenuForm;

public class MenuController {
    
    public void showView() {
        MenuForm menuView = new MenuForm();
        menuView.setVisible(true);
        menuView.setLocationRelativeTo(null);
    }
}
