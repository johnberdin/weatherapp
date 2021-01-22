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
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.font.GraphicAttribute;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;


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
     private String selectedCountry= ""; //This string gets the selected country
     private Stage window;
     private Scene scene2;
     private Scene scene1;
     private ImageView countryImage;
     private Button btnCity1 = new Button();
     private Button btnCity2 = new Button();
     private Button btnCity3 = new Button();
     private Button btnCity4 = new Button();
     private Button btnCity5 = new Button();
     //to display the name of the cities
     private Label[] arrLabelCitynames = new Label[5];
     //These String-variables will have to be filled with the name of the weather in each city
     //ARE PREFILLED WITH CONTENT FOR TEST PURPOSES; should be filled in the switch-case after selection
     private String strWeatherCity1 = "Sunny";
     private String strWeatherCity2 = "Storm";
     private String strWeatherCity3 = "Rain";
     private String strWeatherCity4 = "Clouds";
     private String strWeatherCity5 = "Snow";
     //the positions of each city
     private Double dblViennaX = 813.0;
     private Double dblViennaY = 287.0;
     private Double dblLinzX = 595.0;
     private Double dblLinzY = 277.0;
     private Double dblGrazX = 700.0;
     private Double dblGrazY = 430.0;
     private Double dblKlagenfurtX = 590.0;
     private Double dblKlagenfurtY = 485.0;
     private Double dblInnsbruckX = 292.0;
     private Double dblInnsbruckY = 415.0;




    //This will run before the primaryStage/window. Important for loading assets.
    @Override
    public void init() throws Exception {
        System.out.println("---init code starts");
        //Countries are added to the choice box
        cbCountry.getItems().add("Austria");
        cbCountry.getItems().add("Czech Republic");
        cbCountry.getItems().add("Germany");

        cbCountry.getSelectionModel().select(0); //The first possible option is set as default
        //labels are initialized and adjusted
        for(int i = 0; i< arrLabelCitynames.length; i++){
            arrLabelCitynames[i] = new Label();
            arrLabelCitynames[i].setMinWidth(100);
            arrLabelCitynames[i].setAlignment(Pos.CENTER);
            arrLabelCitynames[i].setTextFill(Color.WHITE);
            arrLabelCitynames[i].setFont(new Font("Champagne & Limousines", 11));
        }
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

        /**
         * Setting the dimensions of the stage
         */
        window.setWidth(1000);
        window.setHeight(800);


        /**
         * scene1 is created
         */
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
        City.ReadAPI("Linz");


        /**
         * scene2 is created
         */
        Pane paneScene2 = new Pane(); //a  pane for elements will be used for scene2 to ease sizing and positioning
        paneScene2.setOnMousePressed(pressEvent -> {
            paneScene2.setOnMouseDragged(dragEvent -> {
                primaryStage.setX(dragEvent.getScreenX() - pressEvent.getSceneX());
                primaryStage.setY(dragEvent.getScreenY() - pressEvent.getSceneY());
            });
        });
        scene2 = new Scene(paneScene2, 1000, 800);
        scene2.getStylesheets().add("/styleScene2.css");
        scene2.setFill(Color.TRANSPARENT);
        Button btnBackToScene1 = new Button(); //button to go back to scene1 (country selection)
        Button btnCloseScene2 = new Button();
        btnCloseScene2.setId("btnCloseScene2");
        Button btnMinimizeScene2 = new Button();
        btnMinimizeScene2.setId("btnMinimizeScene2");
        Label lblApptitle = new Label("WeatherAPP");
        lblApptitle.setLayoutX(50);
        lblApptitle.setLayoutY(50);
        lblApptitle.setId("lblApptitle");
        countryImage = new ImageView();
        //the elements are added
        paneScene2.getChildren().addAll(lblApptitle, btnMinimizeScene2, btnCloseScene2, btnBackToScene1, countryImage, btnCity1, btnCity2, btnCity3,
                btnCity4, btnCity5, arrLabelCitynames[0], arrLabelCitynames[1], arrLabelCitynames[2],
                arrLabelCitynames[3], arrLabelCitynames[4]);

        /**
         * Actions for the buttons are set here
         */
        btnBackToScene1.setOnAction(e -> window.setScene(scene1));
        btnCloseScene2.setOnAction(e -> window.close());
        btnCloseScene1.setOnAction(e -> window.close());
        btnMinimizeScene1.setOnAction(e -> window.setIconified(true));
        btnMinimizeScene2.setOnAction(e -> window.setIconified(true));
        btnConfirmCountry.setOnAction(eventConfirmCountry);
        btnCity1.setOnAction(eventCityClicked);
        btnCity2.setOnAction(eventCityClicked);
        btnCity3.setOnAction(eventCityClicked);
        btnCity4.setOnAction(eventCityClicked);
        btnCity5.setOnAction(eventCityClicked);


        /**
         * Size and position of buttons in scene1 and 2, which are in Panes, are set
         */
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
        btnCity1.getStyleClass().add("cityButtons");
        btnCity2.getStyleClass().add("cityButtons");
        btnCity3.getStyleClass().add("cityButtons");
        btnCity4.getStyleClass().add("cityButtons");
        btnCity5.getStyleClass().add("cityButtons");
        /**
         * The stage (first view) is set and shown
         */
        window.setScene(scene1); //The first scene is set
        window.show(); //The stage will show up.
    }

    //Events are created here
    EventHandler<ActionEvent> eventCityClicked = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            Button btnEventsource = (Button)event.getSource(); //Value of Button is taken, only Vienna returns a value for TEST PURPOSES
            final Stage stgPopup = new Stage();
            stgPopup.setResizable(false);
            stgPopup.setWidth(300);
            stgPopup.setHeight(400);
            stgPopup.initStyle(StageStyle.TRANSPARENT);
            stgPopup.initModality(Modality.APPLICATION_MODAL);
            Pane lytPopup = new Pane();
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
            Label lblChosenCity = new Label(btnEventsource.getText());
            lblChosenCity.setId("lblChosenCity");
            lblChosenCity.setLayoutX(20);
            lblChosenCity.setLayoutY(50);
            //This label will have to be filled with the full detailed information
            Label lblInformation = new Label("Weather: " + "Sunny"+System.lineSeparator()+
                    "Description: " + "sunny" +System.lineSeparator());
            lblInformation.setId("lblInformation");
            lblInformation.setLayoutX(20);
            lblInformation.setLayoutY(95);
            lytPopup.getChildren().addAll(lblInformation, lblChosenCity, btnClosePopup);
            stgPopup.setScene(scene1);
            stgPopup.show();
        }
    };


    EventHandler<ActionEvent> eventConfirmCountry = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            window.setScene(scene2);
            selectedCountry = getChoice(cbCountry);
            switch (selectedCountry){ //the right elements will get placed according to the selected country
                case "Austria":
                    System.out.println("Austria has been selected.");
                    countryImage.setImage(imgAustria);
                    countryImage.setFitHeight(540);
                    countryImage.setFitWidth(850);
                    countryImage.setX(75);
                    countryImage.setY(100);
                    //the buttons of the cities are placed
                    btnCity1.setText("Vienna"); //TEST PURPOSES, the popup gets the name from it
                    btnCity1.setTranslateX(dblViennaX);
                    btnCity1.setTranslateY(dblViennaY);
                    btnCity2.setTranslateX(dblLinzX);
                    btnCity2.setTranslateY(dblLinzY);
                    btnCity3.setTranslateX(dblGrazX);
                    btnCity3.setTranslateY(dblGrazY);
                    btnCity4.setTranslateX(dblKlagenfurtX);
                    btnCity4.setTranslateY(dblKlagenfurtY);
                    btnCity5.setTranslateX(dblInnsbruckX);
                    btnCity5.setTranslateY(dblInnsbruckY);
                    //the correct images will be placed onto the buttons of each city
                    btnCity1.setGraphic(selectWeatherImage(strWeatherCity1));
                    btnCity2.setGraphic(selectWeatherImage(strWeatherCity2));
                    btnCity3.setGraphic(selectWeatherImage(strWeatherCity3));
                    btnCity4.setGraphic(selectWeatherImage(strWeatherCity4));
                    btnCity5.setGraphic(selectWeatherImage(strWeatherCity5));
                    //TOOLTIP DOENST BELONG HERE
                    Tooltip ttipOnCity = new Tooltip("Click for more detailed information");
                    ttipOnCity.setShowDelay(Duration.seconds(0.2));
                    btnCity1.setTooltip(ttipOnCity);
                    btnCity2.setTooltip(ttipOnCity);
                    btnCity3.setTooltip(ttipOnCity);
                    btnCity4.setTooltip(ttipOnCity);
                    btnCity5.setTooltip(ttipOnCity);
                    //the names are added to the labels
                    arrLabelCitynames[0].setText("Vienna");
                    arrLabelCitynames[1].setText("Linz");
                    arrLabelCitynames[2].setText("Graz");
                    arrLabelCitynames[3].setText("Klagenfurt");
                    arrLabelCitynames[4].setText("Innsbruck");
                    //the labels are placed
                    arrLabelCitynames[0].setTranslateX(dblViennaX-38);
                    arrLabelCitynames[0].setTranslateY(dblViennaY+23);
                    arrLabelCitynames[1].setTranslateX(dblLinzX-38);
                    arrLabelCitynames[1].setTranslateY(dblLinzY+23);
                    arrLabelCitynames[2].setTranslateX(dblGrazX-38);
                    arrLabelCitynames[2].setTranslateY(dblGrazY+23);
                    arrLabelCitynames[3].setTranslateX(dblKlagenfurtX-38);
                    arrLabelCitynames[3].setTranslateY(dblKlagenfurtY+23);
                    arrLabelCitynames[4].setTranslateX(dblInnsbruckX-38);
                    arrLabelCitynames[4].setTranslateY(dblInnsbruckY+23);
                    //To display the borders for help "LABELNAME".setStyle("-fx-border-color: white;");

                    break;
                case "Czech Republic":
                    System.out.println("Czech Republic has been selected.");
                    countryImage.setImage(imgCzech);
                    countryImage.setFitHeight(530);
                    countryImage.setFitWidth(800);
                    countryImage.setX(100);
                    countryImage.setY(100);
                    break;
                case "Germany":
                    System.out.println("Germany has been selected.");
                    countryImage.setImage(imgGermany);
                    countryImage.setFitHeight(600);
                    countryImage.setFitWidth(800);
                    countryImage.setX(100);
                    countryImage.setY(50);
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
            case "Sunny":
                ivWeather = new ImageView(imgSunny);
                break;
            case "Rain":
                ivWeather = new ImageView(imgRain);
                break;
            case "Snow":
                ivWeather = new ImageView(imgSnow);
                break;
            case "Storm":
                ivWeather = new ImageView(imgStorm);
                break;
            case "Clouds":
                ivWeather = new ImageView(imgCloudy);
                break;
            default:
                ivWeather = new ImageView(imgThunder);
                break;
        }
        ivWeather.setFitHeight(25); //size of the weather image
        ivWeather.setPreserveRatio(true);
        return ivWeather;
    }

    public static void main(String[] args) {
        launch();
        City a = new City("Vienna");
        City b = new City("Linz");
        City c = new City("Graz");
    }


}
