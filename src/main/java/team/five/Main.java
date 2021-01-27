package team.five;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;


public class Main extends Application {
     private ChoiceBox<String> cbCountry = new ChoiceBox<>(); //Dropdown for the selection of countries
     private Image imgAustria = new Image("/Austria.png");
     private Image imgCzech = new Image("/Czech.png");
     private Image imgGermany = new Image("/Germany.png");
     private Image imgSnow = new Image("/Snow.png");
     private Image imgRain = new Image("/Rain.png");
     private Image imgStorm = new Image("/Storm.png");
     private Image imgSunny = new Image("/Sunny.png");
     private Image imgCloudy = new Image("/Cloudy.png");
     private Image imgThunder = new Image("/Thunder.png"); //still not sure, if it is used
     private Image imgCzechflag = new Image("/CzechFlag.png");
     private Image imgGermanyflag = new Image("/GermanyFlag.png");
     private Image imgAustriaflag = new Image("/AustriaFlag.png");
     private String selectedCountry= ""; //This string gets the selected country

     private Stage window;
     private Scene scene2;
     private Scene scene1;
     private ImageView countryImage;
     //new version
     private City[] cityArray = new City[5]; //instead of btnCity1-5
     //arrays are used for the positioning of the cities to ease the placement
     private Double[] austriaXPos = {813.0, 595.0, 700.0, 590.0, 292.0};
     private Double[] austriaYPos = {287.0, 277.0, 430.0, 485.0, 415.0};
     private Double[] czechXPos = {400.0, 275.0, 600.0, 730.0, 675.0};
     private Double[] czechYPos = {310.0, 370.0, 480.0, 460.0, 340.0};
     private Double[] germanyXPos = {610.0, 464.0, 320.0, 420.0, 555.0};
     private Double[] germanyYPos = {235.0, 165.0, 330.0, 500.0, 548.0};
     private Pane paneScene2;
     private Button btnBackToScene1 = new Button(); //button to go back to scene1 (country selection)
     private Button btnCloseScene2 = new Button();
     private Button btnMinimizeScene2 = new Button();
     private Label lblApptitle = new Label("WeatherAPP");
     private Label lblCountryname = new Label();
     private ImageView ivCountryflag = new ImageView();




    //This will run before the primaryStage/window. Important for loading assets.
    @Override
    public void init() throws Exception {
        System.out.println("---init code starts");
        //Countries are added to the choice box
        cbCountry.getItems().add("Austria");
        cbCountry.getItems().add("Czech Republic");
        cbCountry.getItems().add("Germany");
        cbCountry.getSelectionModel().select(0); //The first possible option is set as default

        System.out.println("---init code stops");
    }

    //This will run after init. Important for the GUI.
    @Override
    public void start( Stage primaryStage) throws Exception {
        window = primaryStage;
        primaryStage.setTitle("Team Five - Weatherapp");//The title of the stage/window is set
        window.getIcons().add(new Image("/AppIcon.png"));
        window.initStyle(StageStyle.TRANSPARENT);
        window.setResizable(false);

        //the size of the window is set
        window.setWidth(1000);
        window.setHeight(800);

        //scene 1 is created
        BorderPane lytScene1 = new BorderPane();
        lytScene1.setOnMousePressed(pressEvent -> {
            lytScene1.setOnMouseDragged(dragEvent -> {
                primaryStage.setX(dragEvent.getScreenX() - pressEvent.getSceneX());
                primaryStage.setY(dragEvent.getScreenY() - pressEvent.getSceneY());
            });
        });
        scene1 = new Scene(lytScene1, 1000, 800); //scene1 is a child node
        scene1.getStylesheets().add("/styleScene1.css");
        scene1.setFill(Color.TRANSPARENT);
        Label lblSelectACountry = new Label("Select a country: ");
        lblSelectACountry.setFont(new Font("Champagne & Limousines", 13));
        lblSelectACountry.setStyle("-fx-font-weight: bold;");
        lblSelectACountry.setTextFill(Color.WHITE);
        Button btnConfirmCountry = new Button(); //button to go to scene2 (weather view)
        btnConfirmCountry.setId("buttonConfirm");
        Pane lytTopPart = new Pane();
        Button btnCloseScene1 = new Button();
        btnCloseScene1.setId("btnCloseScene1");
        Button btnMinimizeScene1 = new Button();
        btnMinimizeScene1.setId("btnMinimizeScene1");
        lytTopPart.getChildren().addAll(btnCloseScene1, btnMinimizeScene1);
        lytScene1.setTop(lytTopPart);
        VBox lytSelectAndConfirm = new VBox();
        lytSelectAndConfirm.setSpacing(30); //the spacing between the elements in a Vbox-layout is set
        lytSelectAndConfirm.setAlignment(Pos.CENTER);
        lytScene1.setCenter(lytSelectAndConfirm);
        HBox lytLabelAndDropdown = new HBox();
        lytLabelAndDropdown.setAlignment(Pos.CENTER);
        lytSelectAndConfirm.getChildren().addAll(new ImageView(new Image("AppIcon.png")), lytLabelAndDropdown, btnConfirmCountry);
        lytLabelAndDropdown.getChildren().addAll(lblSelectACountry, cbCountry);

        //scene 2 is created
        paneScene2 = new Pane(); //a  pane for elements will be used for scene2 to ease sizing and positioning
        paneScene2.setOnMousePressed(pressEvent -> { //the window can be moved
            paneScene2.setOnMouseDragged(dragEvent -> {
                primaryStage.setX(dragEvent.getScreenX() - pressEvent.getSceneX());
                primaryStage.setY(dragEvent.getScreenY() - pressEvent.getSceneY());
            });
        });
        scene2 = new Scene(paneScene2, 1000, 800);
        scene2.getStylesheets().add("/styleScene2.css");
        scene2.setFill(Color.TRANSPARENT);
        countryImage = new ImageView();

        //actions are added to the buttons
        btnBackToScene1.setOnAction(e -> window.setScene(scene1));
        btnCloseScene2.setOnAction(e -> window.close());
        btnCloseScene1.setOnAction(e -> window.close());
        btnMinimizeScene1.setOnAction(e -> window.setIconified(true));
        btnMinimizeScene2.setOnAction(e -> window.setIconified(true));
        btnConfirmCountry.setOnAction(eventConfirmCountry);


        /**
         * Size and position of buttons and labels in scene1 and 2, which are in Panes, are set
         */
        lblApptitle.setLayoutX(50);
        lblApptitle.setLayoutY(50);
        lblApptitle.setId("lblApptitle");
        btnCloseScene2.setId("btnCloseScene2");
        btnMinimizeScene2.setId("btnMinimizeScene2");
        btnBackToScene1.setLayoutX(890);
        btnBackToScene1.setLayoutY(700);
        btnBackToScene1.setId("btnBackToScene1");
        btnCloseScene1.setLayoutX(945);
        btnCloseScene1.setLayoutY(15);
        btnCloseScene2.setLayoutX(950);
        btnCloseScene2.setLayoutY(20);
        btnMinimizeScene1.setLayoutX(895);
        btnMinimizeScene1.setLayoutY(15);
        btnMinimizeScene2.setLayoutX(900);
        btnMinimizeScene2.setLayoutY(20);
        ivCountryflag.setLayoutX(50);
        ivCountryflag.setLayoutY(690);
        lblCountryname.setLayoutX(150);
        lblCountryname.setLayoutY(700);
        lblCountryname.setId("lblCountryname");

        /**
         * The stage (first view) is set and shown
         */
        window.setScene(scene1); //The first scene is set
        window.show(); //The stage will show up.
    }


    //Events are created here

    //event for popup
    EventHandler<ActionEvent> eventCityClicked = new EventHandler<ActionEvent>() {
        char degreeSymbol = 176;
        @Override
        public void handle(ActionEvent event) {
            Button btnEventsource = (Button)event.getSource(); //this is needed to give the popup the title (name of city)
            //each button has a city as userdata, so the popup can be dynamically changed according to the city
            City chosenCity = (City)btnEventsource.getUserData();
            System.out.println("This city has been clicked -> " + chosenCity);
            final Stage stgPopup = new Stage();
            stgPopup.setResizable(false);
            stgPopup.setWidth(300);
            stgPopup.setHeight(400);
            stgPopup.initStyle(StageStyle.TRANSPARENT);
            stgPopup.initModality(Modality.APPLICATION_MODAL);
            Pane lytPopup = new Pane();
            //the popup will be able to get dragged around
            lytPopup.setOnMousePressed(pressEvent -> {
                lytPopup.setOnMouseDragged(dragEvent -> {
                    stgPopup.setX(dragEvent.getScreenX() - pressEvent.getSceneX());
                    stgPopup.setY(dragEvent.getScreenY() - pressEvent.getSceneY());
                });
            });
            Scene scene1 = new Scene(lytPopup, 300,400);
            scene1.getStylesheets().add("/stylePopup.css");
            scene1.setFill(Color.TRANSPARENT);
            Button btnClosePopup = new Button();
            btnClosePopup.setId("btnClosePopup");
            btnClosePopup.setOnAction(e -> stgPopup.close());
            btnClosePopup.setLayoutX(250);
            btnClosePopup.setLayoutY(20);
            //the title of the popup button is set, for the city-name
            Label lblChosenCity = new Label(btnEventsource.getText());
            //the css-part is added to the label
            lblChosenCity.setId("lblChosenCity");
            lblChosenCity.setLayoutX(20);
            lblChosenCity.setLayoutY(50);
            //the hours are always 3 hours apart
            String[] nextHours = new String[4];
            nextHours[0] = chosenCity.getDateList().get(1).substring(11,16);
            nextHours[1] = chosenCity.getDateList().get(2).substring(11,16);
            nextHours[2] = chosenCity.getDateList().get(3).substring(11,16);
            nextHours[3] = chosenCity.getDateList().get(4).substring(11,16);
            //This label shows the detailed information
            Label lblInformation = new Label(""+
                    "Temperature: "+chosenCity.getTempList().get(0)+ degreeSymbol + "C"    +System.lineSeparator()+
                    "Weather: " + chosenCity.getWeathernameList().get(0) +", "+ chosenCity.getDescriptionList().get(0)    +System.lineSeparator()+
                    System.lineSeparator()+
                    "Humidity: " + chosenCity.getHumidityList().get(0) + "%" + System.lineSeparator()+
                    "Wind speed: " + chosenCity.getWindspeedList().get(0) + " km/h" + System.lineSeparator()+ System.lineSeparator()+
                    "Forecast: " + System.lineSeparator() +
                    padLeft(chosenCity.getTempList().get(1),3)+ degreeSymbol + "C" + " | "+nextHours[0] +System.lineSeparator()+
                    padLeft(chosenCity.getTempList().get(2),3)+ degreeSymbol + "C" + " | "+nextHours[1] +System.lineSeparator()+
                    padLeft(chosenCity.getTempList().get(3),3)+ degreeSymbol + "C" + " | "+nextHours[2] +System.lineSeparator()+
                    padLeft(chosenCity.getTempList().get(4),3)+ degreeSymbol + "C" + " | "+nextHours[3] +System.lineSeparator()+
                    System.lineSeparator()+
                    System.lineSeparator()+
                    "Update from/for: "+ chosenCity.getDateList().get(0));
            lblInformation.setId("lblInformation");
            lblInformation.setLayoutX(20);
            lblInformation.setLayoutY(95);
            //the "window"-buttons (close,...) are added to the popup
            lytPopup.getChildren().addAll(lblInformation, lblChosenCity, btnClosePopup);
            stgPopup.setScene(scene1);
            stgPopup.show();
        }
    };

    /**
     * This is the "main"-Function
     */
    EventHandler<ActionEvent> eventConfirmCountry = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            window.setScene(scene2);
            selectedCountry = getChoice(cbCountry);
            Tooltip ttipOnCity = new Tooltip("Click for more detailed information");
            ttipOnCity.setShowDelay(Duration.seconds(0.2));
            switch (selectedCountry){ //the right elements will get placed according to the selected country
                case "Austria":
                    System.out.println("Austria has been selected.");
                    lblCountryname.setText("Austria");
                    ivCountryflag.setImage(imgAustriaflag);
                    //the previous elements will get deleted/disconnected
                    paneScene2.getChildren().clear();
                    paneScene2.getChildren().addAll(ivCountryflag, lblCountryname, lblApptitle, btnMinimizeScene2, btnCloseScene2, btnBackToScene1, countryImage);
                    //the array of the city-objects is now filled
                    cityArray[0] = new City("Vienna");
                    cityArray[1] = new City("Linz");
                    cityArray[2] = new City("Graz");
                    cityArray[3] = new City("Klagenfurt");
                    cityArray[4] = new City("Innsbruck");
                    //the image of the country is set
                    countryImage.setImage(imgAustria);
                    countryImage.setFitHeight(540);
                    countryImage.setFitWidth(850);
                    countryImage.setX(75);
                    countryImage.setY(100);
                    //loop through the city-Array-objs, the buttons and labels of the city-objs are added and placed
                    for(int i = 0; i< cityArray.length; i++){
                        //the button and label, initialized in the city-obj, are added to the pane
                        paneScene2.getChildren().addAll(cityArray[i].getBtnCity(), cityArray[i].getLblCityname());
                        //css-class is added to the buttons -> main reason: button should be invisible
                        cityArray[i].getBtnCity().getStyleClass().add("cityButtons");
                        //the button of each city gets the event for the popup
                        cityArray[i].getBtnCity().setOnAction(eventCityClicked);
                        //button is placed
                        cityArray[i].getBtnCity().setTranslateX(austriaXPos[i]);
                        cityArray[i].getBtnCity().setTranslateY(austriaYPos[i]);
                        //the button of the city gets the right image of the weather, according to the weather(-name)
                        //the method: selectWeatherImage
                        cityArray[i].getBtnCity().setGraphic(selectWeatherImage(cityArray[i].getWeathernameList().get(0)));
                        cityArray[i].getBtnCity().setTooltip(ttipOnCity);
                        //label-city is placed
                        cityArray[i].getLblCityname().setTranslateX(austriaXPos[i] - 38);
                        cityArray[i].getLblCityname().setTranslateY(austriaYPos[i] + 23);
                    }
                    break;
                case "Czech Republic":
                    System.out.println("Czech Republic has been selected.");
                    lblCountryname.setText("Czech Republic");
                    ivCountryflag.setImage(imgCzechflag);
                    //the previous elements will get deleted/disconnected
                    paneScene2.getChildren().clear();
                    paneScene2.getChildren().addAll(ivCountryflag, lblCountryname, lblApptitle, btnMinimizeScene2, btnCloseScene2, btnBackToScene1, countryImage);
                    countryImage.setImage(imgCzech);
                    countryImage.setFitHeight(550);
                    countryImage.setFitWidth(800);
                    countryImage.setX(100);
                    countryImage.setY(100);
                    //the array of the city-objects is now filled
                    cityArray[0] = new City("Prague");
                    cityArray[1] = new City("Pilsen");
                    cityArray[2] = new City("Brno");
                    cityArray[3] = new City("Zlin");
                    cityArray[4] = new City("Olomouc");
                    for(int i = 0; i< cityArray.length; i++){
                        paneScene2.getChildren().addAll(cityArray[i].getBtnCity(), cityArray[i].getLblCityname());
                        cityArray[i].getBtnCity().getStyleClass().add("cityButtons");
                        cityArray[i].getBtnCity().setOnAction(eventCityClicked);
                        cityArray[i].getBtnCity().setTranslateX(czechXPos[i]);
                        cityArray[i].getBtnCity().setTranslateY(czechYPos[i]);
                        cityArray[i].getBtnCity().setGraphic(selectWeatherImage(cityArray[i].getWeathernameList().get(0)));
                        cityArray[i].getBtnCity().setTooltip(ttipOnCity);
                        cityArray[i].getLblCityname().setTranslateX(czechXPos[i] - 38);
                        cityArray[i].getLblCityname().setTranslateY(czechYPos[i] + 23);
                    }
                    break;
                case "Germany":
                    System.out.println("Germany has been selected.");
                    lblCountryname.setText("Germany");
                    ivCountryflag.setImage(imgGermanyflag);
                    countryImage.setImage(imgGermany);
                    countryImage.setFitHeight(600);
                    countryImage.setFitWidth(780);
                    countryImage.setX(100);
                    countryImage.setY(50);
                    paneScene2.getChildren().clear();
                    paneScene2.getChildren().addAll(ivCountryflag, lblCountryname, lblApptitle, btnMinimizeScene2, btnCloseScene2, btnBackToScene1, countryImage);
                    //the array of the city-objects is now filled
                    cityArray[0] = new City("Berlin");
                    cityArray[1] = new City("Hamburg");
                    cityArray[2] = new City("Cologne");
                    cityArray[3] = new City("Stuttgart,DE");
                    cityArray[4] = new City("Munich");
                    for(int i = 0; i< cityArray.length; i++){
                        paneScene2.getChildren().addAll(cityArray[i].getBtnCity(), cityArray[i].getLblCityname());
                        cityArray[i].getBtnCity().getStyleClass().add("cityButtons");
                        cityArray[i].getBtnCity().setOnAction(eventCityClicked);
                        cityArray[i].getBtnCity().setTranslateX(germanyXPos[i]);
                        cityArray[i].getBtnCity().setTranslateY(germanyYPos[i]);
                        cityArray[i].getBtnCity().setGraphic(selectWeatherImage(cityArray[i].getWeathernameList().get(0)));
                        cityArray[i].getBtnCity().setTooltip(ttipOnCity);
                        cityArray[i].getLblCityname().setTranslateX(germanyXPos[i] - 38);
                        cityArray[i].getLblCityname().setTranslateY(germanyYPos[i] + 23);
                    }
                    break;
            }
        }
    };

    //This will run after the start.
    @Override
    public void stop() throws Exception {
        System.out.println("---stop code starts");
    }
    //to get the value of the choicebox
    private String getChoice(ChoiceBox<String> choiceBox){
        return choiceBox.getValue();
    }

    /**
     * This method will return the image in the right size and corresponding to the given String.
     * It is used in combination with the String-variable containing the name of the weather of each city
     * @param nameOfWeather
     * @return an Image (ImageView) of the given weather
     */
    private ImageView selectWeatherImage(String nameOfWeather){
        ImageView ivWeather;
        switch (nameOfWeather){
            case "Drizzle":
            case "Rain":
                ivWeather = new ImageView(imgRain);
                break;
            case "Snow":
                ivWeather = new ImageView(imgSnow);
                break;
            case "Thunderstorm":
                ivWeather = new ImageView(imgStorm);
                break;
            case "Atmosphere":
            case "Clouds":
                ivWeather = new ImageView(imgCloudy);
                break;
            case "Clear":
            default:
                ivWeather = new ImageView(imgSunny);
                break;
        }
        ivWeather.setFitHeight(25); //size of the weather image
        ivWeather.setPreserveRatio(true);
        return ivWeather;
    }

    //the given string will get padded to the given size on the left side
    public static String padLeft(String s, int n) {
        return String.format("%" + n + "s", s);
    }

    //main
    public static void main(String[] args) {
        launch();
    }


}
