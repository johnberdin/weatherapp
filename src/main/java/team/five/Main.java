package team.five;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.event.ActionListener;
import java.lang.invoke.SwitchPoint;


public class Main extends Application {
     private ChoiceBox<String> cbCountry = new ChoiceBox<>(); //Dropdown for the selection of countries
     private Image austria = new Image("/Austria.png");
     private Image czech = new Image("/Czech.png");
     private Image germany = new Image("/Germany.png");
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

        /**
         * Setting the dimensions of the stage
         */
        window.setWidth(1000);
        window.setHeight(800);

        /**
         * This code would open a new window
         *         Stage secondWindow = new Stage();
         *         secondWindow.setTitle("second window");
         *         secondWindow.show();
         */

        /**
         * scene1 is created
         */
        BorderPane lytScene1 = new BorderPane();
        lytScene1.setStyle("-fx-background-color: AZURE");
        scene1 = new Scene(lytScene1, 400, 400); //scene1 is a child node
        Label lblSelectACountry = new Label("Select a country: ");
        Button btnConfirmCountry = new Button("Confirm"); //button to go to scene2 (weather view)
        VBox lytSelectAndConfirm = new VBox();
        lytSelectAndConfirm.setSpacing(30); //the spacing between the elements in a Vbox-layout is set
        lytSelectAndConfirm.setAlignment(Pos.CENTER);
        lytScene1.setCenter(lytSelectAndConfirm);
        HBox lytLabelAndDropdown = new HBox();
        lytLabelAndDropdown.setAlignment(Pos.CENTER);
        lytSelectAndConfirm.getChildren().addAll(lytLabelAndDropdown, btnConfirmCountry);
        lytLabelAndDropdown.getChildren().addAll(lblSelectACountry, cbCountry);

        /**
         * scene2 is created
         */
        Pane paneScene2 = new Pane(); //a  pane for elements will be used for scene2 to ease sizing and positioning
        //scene2 gets the group as parent and fills out the whole stage
        scene2 = new Scene(paneScene2, 1000, 800);
        Button btnBackToScene1 = new Button("Exit"); //button to go back to scene1 (country selection)
        //background-color of scene2 is set
        paneScene2.setStyle("-fx-background-color: AZURE");
        countryImage = new ImageView();
        //the elements are added
        paneScene2.getChildren().addAll(btnBackToScene1, countryImage, btnCity1, btnCity2, btnCity3, btnCity4, btnCity5);



        /**
         * Actions for the buttons are set here
         */
        btnBackToScene1.setOnAction(e ->
                window.setScene(scene1)
        );

        btnConfirmCountry.setOnAction(eventConfirmCountry);
        /**
         * Size and position of buttons in scene2 are set
         */
        btnBackToScene1.setLayoutX(50);
        btnBackToScene1.setLayoutY(700);
        btnBackToScene1.setPrefSize(60,40);

        /**
         * The stage (first view) is set and shown
         */
        window.setScene(scene1); //The first scene is set
        window.show(); //The stage will show up.

        /**
         * This would close the stage/window
         *         primaryStage.close();
         */
    }

    //Events are created here
    EventHandler<ActionEvent> eventConfirmCountry = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            window.setScene(scene2);
            selectedCountry = getChoice(cbCountry);
            switch (selectedCountry){ //the right elements will get placed according to the selected country
                case "Austria":
                    System.out.println("Austria has been selected.");
                    countryImage.setImage(austria);
                    countryImage.setFitHeight(530);
                    countryImage.setFitWidth(800);
                    countryImage.setX(100);
                    countryImage.setY(100);
                    btnCity1.setText("Wien");
                    break;
                case "Czech Republic":
                    System.out.println("Czech Republic has been selected.");
                    countryImage.setImage(czech);
                    countryImage.setFitHeight(530);
                    countryImage.setFitWidth(800);
                    countryImage.setX(100);
                    countryImage.setY(100);
                    break;
                case "Germany":
                    System.out.println("Germany has been selected.");
                    countryImage.setImage(germany);
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

    public static void main(String[] args) {
        launch();
    }
}
