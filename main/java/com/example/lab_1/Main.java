package com.example.lab_1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;


public class Main extends Application
{
    public static Stage stage;
    public static Scene scenemenu;
    public static Scene scenemain;
    @Override
    public void start(Stage prrimaryStage) throws IOException
    {
        stage=prrimaryStage;
        FXMLLoader fxmlLoadermenu = new FXMLLoader(Main.class.getResource("Menu.fxml"));
        scenemenu = new Scene(fxmlLoadermenu.load());
        FXMLLoader fxmlLoadermain = new FXMLLoader(Main.class.getResource("Main.fxml"));
        scenemain = new Scene(fxmlLoadermain.load());
        Habitat habitat = fxmlLoadermain.getController();
        scenemain.setOnKeyPressed(keyEvent -> {
            switch (keyEvent.getCode()) {
                case B:
                    habitat.start();
                    break;
                case S:
                    habitat.stop();
                    break;
                case E:
                    habitat.end();
                    break;
                case R:
                    habitat.reset();
                    break;
                case ESCAPE:
                    habitat.ESC();
                case T:
                    habitat.show();
                default:
                    break;
            }
        });
        stage.setTitle("Lab_1");
        stage.setScene(scenemenu);
        stage.show();
    }
    public static void main(String[] args)
    {
        launch();
    }
}