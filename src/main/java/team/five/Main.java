package team.five;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.awt.*;


public class Main extends Application {
    public ChoiceBox<String> cbCountry = new ChoiceBox<>(); //Dropdown for the selection of countries

    //This will run before the primaryStage/window. Important for loading assets.
    @Override
    public void init() throws Exception {
        System.out.println("---init code starts");
        //Countries are added to the choice box
        cbCountry.getItems().add("Austria");
        cbCountry.getItems().add("Czech Republic");
        cbCountry.getItems().add("Germany");

        System.out.println("---init code stops");
    }

    //This will run after init. Important for the GUI.
    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("Team Five - Weatherapp");//The title of the stage/window is set

        /**
         * Setting the dimensions of the stage
         */
        primaryStage.setWidth(1000);
        primaryStage.setHeight(800);

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
        Scene scene1 = new Scene(lytScene1, 400, 400); //scene1 is a child node
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
        Scene scene2 = new Scene(paneScene2, 1000, 800);
        Button btnBackToScene1 = new Button("Exit"); //button to go back to scene1 (country selection)
        //the button btnBackToScene1 is placed
        paneScene2.getChildren().addAll(btnBackToScene1); //the button is added to the group
        //background-color of scene2 is set
        paneScene2.setStyle("-fx-background-color: AZURE");


        /**
         * Actions for the buttons are set here
         */
        btnBackToScene1.setOnAction(e ->
                primaryStage.setScene(scene1)
        );

        btnConfirmCountry.setOnAction(e ->
                primaryStage.setScene(scene2)
        );

        /**
         * Size and position of buttons in scene2 are set
         */
        btnBackToScene1.setLayoutX(50);
        btnBackToScene1.setLayoutY(700);
        btnBackToScene1.setPrefSize(60,40);

        /**
         * The stage (first view) is set and shown
         */
        primaryStage.setScene(scene1); //The first scene is set
        primaryStage.show(); //The stage will show up.

        /**
         * This would close the stage/window
         *         primaryStage.close();
         */
    }

    //This will run after the start.
    @Override
    public void stop() throws Exception {
        System.out.println("---stop code starts");
    }


    public static void main(String[] args) {
        launch();
    }
}
