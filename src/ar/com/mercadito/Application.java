package ar.com.mercadito;

import ar.com.mercadito.mysql.Conector;

import javax.swing.*;

public class Application {

    public void inicio(){
        Conector conector = new Conector();
        JOptionPane.showMessageDialog(null, conector.consultar());
    }

    public final static void main(String[] arg){
        Application application = new Application();
        application.inicio();
    }
}
