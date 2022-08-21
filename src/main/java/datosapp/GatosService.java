package datosapp;

import okhttp3.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class GatosService extends base{
    public static void verGatos() throws IOException {
        //Trayendo datos de la api desde postman
        client = new OkHttpClient().newBuilder().build();
        request = new Request.Builder().url(urlSearch).get().build();
        response = client.newCall(request).execute();
        json = Objects.requireNonNull(response.body()).string();
        //Quitar los corchetes que trae oir defecto el archivo Json de Postman
        json = json.substring(1, json.length());// Corta el primer corchete
        json = json.substring(0, json.length() - 1);//Corta el ultimo corchete

        //Objeto de la clase Gson
        Gatos gatos = gson.fromJson(json, Gatos.class);

        //Redimensionar imagen
        try {
            //Para poder mostrar la imagen del gato
            url = new URL(gatos.getUrl());
            image = ImageIO.read(url);//Para convertir la url a tipo imagen
            imageIcon = new ImageIcon(image);
            if (imageIcon.getIconWidth() > 800) {
                //Redimensionamos
                background = imageIcon.getImage();
                resize = background.getScaledInstance(800, 600, Image.SCALE_SMOOTH);//El último parámetro es el tipo de redimensionado
                imageIcon = new ImageIcon(resize);
            }
            //Text block en java
            String menu = """
                    Opciones:\s
                    1. Ver otra imagen\s
                    2. Marcar gato como favorito\s
                    3. Volver al menú\s
                    """;
            String[] buttons = {
                    "ver otra imagen",
                    "favorito",
                    "volver"
            };
            idGato = gatos.getId();
            option = (String) JOptionPane.showInputDialog(
                    null,
                    menu,
                    idGato,
                    JOptionPane.INFORMATION_MESSAGE,
                    imageIcon,
                    buttons,
                    buttons[0]
            );

            int selection = -1;
            for (int i = 0; i < buttons.length; i++)
                if (option.equals(buttons[i]))
                    selection = i;
            switch (selection) {
                case 0 -> verGatos();
                case 1 -> favoritoGato(gatos);
                default -> JOptionPane.showMessageDialog(null, "Seleccione una opción");

            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public static void favoritoGato(Gatos gato) throws IOException {
        try {
            client = new OkHttpClient().newBuilder().build();
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create("{\r\n    \"image_id\": \"" + gato.getId() + "\"\r\n}", mediaType);
            request = new Request.Builder().url(urlFav)
                    .post(body)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("x-api-key", gato.getApiKey())
                    .build();
            response = client.newCall(request).execute();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public static void verFavoritos(String apiKey) throws IOException {
        client = new OkHttpClient().newBuilder().build();
        request = new Request.Builder().url(urlFav).get()
                .addHeader("Content-Type", "application/json")
                .addHeader("x-api-key", apiKey).build();
        response = client.newCall(request).execute();
        json = Objects.requireNonNull(response.body()).string();
        GatosFav[] gatosArray = gson.fromJson(json, GatosFav[].class);
        if (gatosArray.length > 0) {
            int min = 1;
            int max = gatosArray.length;
            int random = (int) (Math.random() * ((max-min) + 1)) + min;
            int index = random - 1;
            GatosFav gatosFav = gatosArray[index];
            //Redimensionar imagen
            try {
                //Para poder mostrar la imagen del gato
                url = new URL(gatosFav.image.getUrl());
                image = ImageIO.read(url);//Para convertir la url a tipo imagen
                imageIcon = new ImageIcon(image);
                if (imageIcon.getIconWidth() > 800) {
                    //Redimensionamos
                    background = imageIcon.getImage();
                    resize = background.getScaledInstance(800, 600, Image.SCALE_SMOOTH);//El último parámetro es el tipo de redimensionado
                    imageIcon = new ImageIcon(resize);
                }
                //Text block en java
                String menu = """
                        Opciones:\s
                        1. Ver otra imagen\s
                        2. Eliminar gato de favorito\s
                        3. Volver al menú\s
                        """;
                String[] buttons = {
                        "ver otra imagen",
                        "eliminar favorito",
                        "volver"
                };
                idGato = gatosFav.getId();
                option = (String) JOptionPane.showInputDialog(
                        null,
                        menu,
                        idGato,
                        JOptionPane.INFORMATION_MESSAGE,
                        imageIcon,
                        buttons,
                        buttons[0]
                );

                int selection = -1;
                for (int i = 0; i < buttons.length; i++)
                    if (option.equals(buttons[i]))
                        selection = i;
                switch (selection) {
                    case 0 -> verFavoritos(apiKey);
                    case 1 -> borrarFavorito(gatosFav);
                    default -> JOptionPane.showMessageDialog(null, "Seleccione una opción");

                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        }
    }

    public static void borrarFavorito(GatosFav gatoFav){

    }
}
