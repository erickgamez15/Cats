package datosapp;

import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class base {
    public static final Gson gson = new Gson();
    public static final String urlSearch = "https://api.thecatapi.com/v1/images/search";
    public static final String urlFav = "https://api.thecatapi.com/v1/favourites";
    public static OkHttpClient client;
    public static Response response;
    public static Request request;
    public static Image image, background, resize;
    public static URL url;
    public static ImageIcon imageIcon;
    public static String json, idGato, option;
}
