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

    private static List<String> TempList = new ArrayList<>();
    private static List<String> CityList = new ArrayList<>();


    public City (String city) {
        //Methodenaufruf von ReadAPI mit übergebenem Städtenamen um JSON File zu erzeugen
        ReadAPI(city);

      /*  this.weathername = jsonList.getJSONObject(0).getJSONArray("weather").getJSONObject(0).getString("main");
        System.out.println(this.weathername);

        this.description = jsonList.getJSONObject(0).getJSONArray("weather").getJSONObject(0).getString("description");
        System.out.println(this.description);

        this.start_Date = jsonList.getJSONObject(0).getString("dt_txt");
        System.out.println(this.start_Date);

        this.windspeed = jsonList.getJSONObject(0).getJSONObject("wind").getFloat("speed");
        System.out.println(this.windspeed);


       */
        for (int i = 1; i < jsonList.length(); i++) {

            //Temporäre Liste löschen
            TempList.clear();
            //Arraylist Stelle 1: Temperatur, Stelle 2: feels like, Stelle 3: Feuchtigkeit
            TempList.add(jsonList.getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("main"));
            TempList.add(jsonList.getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("description"));
            TempList.add(String.valueOf(jsonList.getJSONObject(i).getJSONObject("main").getInt("temp")));
            TempList.add(String.valueOf(jsonList.getJSONObject(i).getJSONObject("main").getInt("feels_like")));
            TempList.add(String.valueOf(jsonList.getJSONObject(i).getJSONObject("main").getInt("humidity")));
            TempList.add(String.valueOf(jsonList.getJSONObject(i).getJSONObject("wind").getFloat("speed")));

            //System.out.println("Temp: " + temp_array[i] + " Feel: " + feeltemp_array[i] + " hum: " + humidity_array[i]);

            /*emporäre Liste füllen
            TempList.add(weather_array[i]);
            TempList.add(description_array[i]);
            TempList.add(String.valueOf(windspeed_array[i]));
            TempList.add(String.valueOf(feeltemp_array[i]));
            TempList.add(String.valueOf(feeltemp_array[i]));
            TempList.add(String.valueOf(humidity_array[i]));
             */

            CityList = TempList;

            System.out.println(CityList);
        }
    }
    public static List<String> getWeather(){
        return CityList;
    }


    public static void ReadAPI(String city)
    {
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
