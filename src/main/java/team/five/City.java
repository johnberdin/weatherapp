package team.five;


public class City extends Main{

    String cityname;
    float temp;
    String weathername;
    float humidity;

    public City (String city, float cityTemp, String cityWeather, float cityHumidity){

        this.cityname=city;
        this.temp=cityTemp;
        this.weathername=cityWeather;
        this.humidity=cityHumidity;

    }

}
