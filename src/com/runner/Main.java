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
        GameScene gameScene = new GameScene(pane, 600,400, "D:\\java\\projet_runner\\desert2.png","D:\\java\\projet_runner\\heros2.png","D:\\java\\projet_runner\\hearts2.png");
        StartScene startScene = new StartScene(menuPane,pane,gameScene,primaryStage,600,400,"D:\\java\\projet_runner\\menuComplet.png");
        //root.getChildren().addAll(gameScene.getBackgroundLeft().getImageview(),gameScene.getBackgroundRight().getImageview(),gameScene.getHero().getImageView(),gameScene.getNbCoeur().getImageview());
        //gameScene.keyListener();
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