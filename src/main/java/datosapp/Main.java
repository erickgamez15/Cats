package datosapp;

import javax.swing.*;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        int optionMenu = 0;
        String[] buttons = {
                "1. Ver gatos",
                "2. Favoritos",
                "3. Salir"
        };
        Gatos gatos = new Gatos();
        do {
            String option = (String) JOptionPane.showInputDialog(
                    null,
                    "Gatitos Java",
                    "Menu Principal",
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    buttons,
                    buttons[0]
            );
            //Validamos la opción del usuario
            for (int i = 0; i < buttons.length; i++) {
                if (option.equals(buttons[i])) {
                    optionMenu = i;
                }
            }
            switch (optionMenu){
                case 0 -> GatosService.verGatos();
                case 1 -> GatosService.verFavoritos(gatos.getApiKey());
                case 2 -> JOptionPane.showMessageDialog(null, "Adios");
                default -> JOptionPane.showMessageDialog(null, "Elija una opción del menú");
            }
        }while (optionMenu != 1);
    }
}