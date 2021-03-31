package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class Controller {

    @FXML
    private TextField city;

    @FXML
    private Button getData;

    @FXML
    private Text temp;

    @FXML
    private Text osh;

    @FXML
    private Text max;

    @FXML
    private Text min;

    @FXML
    private Text dav;

    @FXML
    void initialize() {
       getData.setOnAction(event -> {
           String getUserCity = city.getText().trim();
           if(!getUserCity.equals("")) {
               String output = getUrlContent("https://api.openweathermap.org/data/2.5/weather?q=" + getUserCity + "&appid=bd97328c98ba36611251af513e628c3c&units=metric");

               if (!output.isEmpty()) {
                   JSONObject obj = new JSONObject(output);
                   temp.setText("ТЕМПЕРАТУРА: " + obj.getJSONObject("main").getInt("temp") + " °C");
                   osh.setText("ОЩУЩАЕТСЯ: " + obj.getJSONObject("main").getInt("feels_like")+ " °C");
                   max.setText("МАКСИМУМ: " + obj.getJSONObject("main").getInt("temp_max")+ " °C");
                   min.setText("МИНИМУМ: " + obj.getJSONObject("main").getInt("temp_min")+ " °C");
                   dav.setText("ДАВЛЕНИЕ: " + obj.getJSONObject("main").getDouble("pressure") + " мм.рт.ст.");
               }
           }
       });
    }

    private static String getUrlContent(String urlAdress){
        StringBuffer content = new StringBuffer();

        try {
        URL url = new URL(urlAdress);
        URLConnection urlConn = url.openConnection();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
        String line;

        while ((line = bufferedReader.readLine()) != null) {
            content.append(line + "\n");
        }
            bufferedReader.close();
        } catch (IOException e) {
            System.out.println("Такой город не существует");
        }
        return content.toString();
    }
}

