package com.runner;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("runner");
        Group root = new Group();
        Pane pane = new Pane(root);
        Pane menuPane = new Pane(root);
        Pane endPane = new Pane(root);
        Pane victoryPane =new Pane(root);
        EndScene victoryScene = new EndScene(victoryPane,primaryStage,600,400,"D:\\java\\projet_runner\\images\\victoryScene.png","D:\\java\\projet_runner\\images\\texteVictoire.png",330);
        EndScene endScene = new EndScene(endPane,primaryStage,600,400,"D:\\java\\projet_runner\\images\\blackscreen.png","D:\\java\\projet_runner\\images\\texteFin.png",300);
        GameScene gameScene = new GameScene(pane,endScene,victoryScene,primaryStage, 600,400, "D:\\java\\projet_runner\\images\\desert_poke.png","D:\\java\\projet_runner\\images\\heros2.png","D:\\java\\projet_runner\\images\\pokeballVie.png");
        StartScene startScene = new StartScene(menuPane,pane,gameScene,primaryStage,600,400,"D:\\java\\projet_runner\\images\\menuComplet.png");

        endScene.setStartScene(startScene);
        victoryScene.setStartScene(startScene);
        primaryStage.setScene(startScene);
        startScene.getTimer().handle(0);
        startScene.getTimer().start();
        primaryStage.setResizable(false);
        startScene.keyListen();
        primaryStage.show();
        }


    public static void main(String[] args) {
        launch(args);
    }
}