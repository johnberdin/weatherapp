package team.five;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;


public class City extends Main{


    private String weathername;
    private String start_Date;
    private String description;
    private float windspeed;
    private static JSONArray jsonList;
    //Index 0 ist the most recent dateset, every next index is the data in +3 hours
    private List<Integer> tempList = new ArrayList<>();
    private List<String> tempfeelslikeList = new ArrayList<>();
    private List<Integer> humidityList = new ArrayList<>();
    private List<String> weathernameList = new ArrayList<>(); //Weathername: "Clouds", "Rain",....
    private List<String> descriptionList = new ArrayList<>();//Weatherdescription: "dark clouds",...
    private List<Float>  windspeedList = new ArrayList<>();
    private List<String> dateList = new ArrayList<>();



    public City (String city) {
        //Methodenaufruf von ReadAPI mit übergebenem Städtenamen um JSON File zu erzeugen
        ReadAPI(city);

        for (int i = 0; i < jsonList.length(); i++) {
            //Temporäre Liste löschen
            //Arraylist Stelle 1: Temperatur, Stelle 2: feels like, Stelle 3: Feuchtigkeit
            weathernameList.add(jsonList.getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("main"));
            descriptionList.add(jsonList.getJSONObject(0).getJSONArray("weather").getJSONObject(0).getString("description"));
            tempList.add(Integer.valueOf(jsonList.getJSONObject(i).getJSONObject("main").getInt("temp")));
            tempfeelslikeList.add(String.valueOf(jsonList.getJSONObject(i).getJSONObject("main").getInt("feels_like")));
            humidityList.add((jsonList.getJSONObject(i).getJSONObject("main").getInt("humidity")));
            windspeedList.add(Float.valueOf(jsonList.getJSONObject(i).getJSONObject("wind").getFloat("speed")));
            dateList.add(jsonList.getJSONObject(i).getString("dt_txt"));

            //for controlpurposes
            System.out.println( city + " --- Temperature: " + tempList.get(i) + " -- Feelslike: " + tempfeelslikeList.get(i) +
                    " -- Humidity: " + humidityList.get(i) + " -- Weathername: " + weathernameList.get(i)+
                    " -- Description: " + descriptionList.get(i) + " -- Date: " + dateList.get(i));
        }
    }




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


    public static String parse(String responseBody) {
        JSONObject mainWeather = new JSONObject(responseBody);
        JSONArray array = mainWeather.getJSONArray("list");
        jsonList = array;
        return null;
    }
}
