package team.five;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;


public class City extends Main{


    private static JSONArray jsonList;
    //Index 0 ist the most recent dateset, every next index is the data in +3 hours
    private List<String> tempList = new ArrayList<>();
    private List<String> tempfeelslikeList = new ArrayList<>();
    private List<Integer> humidityList = new ArrayList<>();
    private List<String> weathernameList = new ArrayList<>(); //Weathername: "Clouds", "Rain",....
    private List<String> descriptionList = new ArrayList<>();//Weatherdescription: "dark clouds",...
    private List<String>  windspeedList = new ArrayList<>();
    private List<String> dateList = new ArrayList<>();

    private Button btnCity;
    private String strCity;
    private Label lblCityname;



    public City (String city) {
        //Methodenaufruf von ReadAPI mit übergebenem Städtenamen um JSON File zu erzeugen
        ReadAPI(city);

        for (int i = 0; i < jsonList.length(); i++) {
            this.weathernameList.add(jsonList.getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("main"));
            this.descriptionList.add(jsonList.getJSONObject(0).getJSONArray("weather").getJSONObject(0).getString("description"));
            this.tempList.add(String.valueOf(Math.round(jsonList.getJSONObject(i).getJSONObject("main").getDouble("temp"))));
            this.tempfeelslikeList.add(String.valueOf(Math.round(jsonList.getJSONObject(i).getJSONObject("main").getDouble("feels_like"))));
            this.humidityList.add((jsonList.getJSONObject(i).getJSONObject("main").getInt("humidity")));
            this.windspeedList.add(String.valueOf(jsonList.getJSONObject(i).getJSONObject("wind").getFloat("speed")));
            this.dateList.add(jsonList.getJSONObject(i).getString("dt_txt"));

            //for controlpurposes -> will output all datasets of the city
            //System.out.println( city + " --- Temperature: " + tempList.get(i) + " -- Feelslike: " + tempfeelslikeList.get(i) +" -- Humidity: " + humidityList.get(i) + " -- Weathername: " + weathernameList.get(i)+" -- Description: " + descriptionList.get(i) + " -- Date: " + dateList.get(i));
            //System.out.println(city + "-object has been created");
        }
        this.btnCity = new Button(city);
        btnCity.setUserData(this);//to give the clicked Button the corresponding city obj
        this.strCity = city;

        this.lblCityname = new Label(city);
        this.lblCityname.setMinWidth(100);
        this.lblCityname.setAlignment(Pos.CENTER);
        this.lblCityname.setTextFill(Color.WHITE);
        this.lblCityname.setFont(new Font("Champagne & Limousines", 11));
    }

    /**
     * This method creates the connection to the API and runs the parse-Method.
     * @param city This is the name of the city
     */
    public static void ReadAPI(String city) {
            //Create the http Client
            HttpClient client = HttpClient.newHttpClient();
            //Build the http Request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://api.openweathermap.org/data/2.5/forecast?q="+city+"&units=metric&appid=1b2945f53a2ff9caf2467a2d4f134c7c")).build();
            client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(HttpResponse::body)
                    //.thenAccept(System.out::)
                    .thenApply(City::parse)
                    .join();
    }


    /**
     * This method extracts the JSON-Array and puts it in a public static variable.
     * @param responseBody
     * @return null, it is needed
     */
    public static String parse(String responseBody) {
        JSONObject mainWeather = new JSONObject(responseBody);
        JSONArray array = mainWeather.getJSONArray("list");
        jsonList = array;
        return null;
    }

    public Button getBtnCity(){
        return this.btnCity;
    }

    public List<String> getTempList(){
        return this.tempList;
    }

    public List<String> getTempfeelslikeList(){
        return this.tempList;
    }

    public List<String> getWeathernameList(){
        return this.weathernameList;
    }

    public List<String> getDescriptionList(){
        return this.descriptionList;
    }

    public List<String> getDateList(){
        return this.dateList;
    }

    public List<Integer> getHumidityList(){
        return this.humidityList;
    }

    public Label getLblCityname(){
        return this.lblCityname;
    }

    public String getStrCity(){
        return this.strCity;
    }

    public List<String> getWindspeedList(){
        return this.windspeedList;
    }

    public String toString(){
        for(int i = 0; i<this.dateList.size(); i++){
            System.out.println(this.dateList.get(i)); 
        }
        return this.strCity + "-- current Temp.: "+ this.tempList.get(0)+ "-- Weather: "+this.weathernameList.get(0)+ "--Date: "+this.getDateList().get(0);
    }
}
