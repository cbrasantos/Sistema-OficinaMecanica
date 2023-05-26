package controller;

import view.ProviderForm;

public class ProviderController {
    
    public void showView() {
        ProviderForm providerView = new ProviderForm(this);
        providerView.setVisible(true);
        providerView.setLocationRelativeTo(null);
    }
}
