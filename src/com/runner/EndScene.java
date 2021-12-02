package com.runner;
/* Scene qui apparaît en cas de victoire ou de défaite et qui renvoit ensuite à la scene de départ
 elle est composée d'un background fixe et d'un texte que l'on fait varier en appuyant sur SPACE*/
import javafx.animation.AnimationTimer;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class EndScene extends Scene {
    private StaticThing background;
    private Projectil texte; //variable qui correspond au texte qui varie, elle aurait pu être faites avec un StaticThing
    private boolean indiceEnter=true;
    private Pane endPane;
    private Stage stage;
    private StartScene startScene;
    private AnimationTimer timerEnd = new AnimationTimer() {
        private long lastUpdate = 0;
        @Override
        public void handle(long l) {
            if (l - lastUpdate >= 50000000) {


                lastUpdate = l;
            }
        }
    };


    public EndScene(Pane endPane, Stage stage, double longueur, double largeur, String path, String textePath, double y){
        super(endPane, longueur, largeur, true);
        this.endPane = endPane;
        this.stage = stage;
        this.background = new StaticThing("menuBackground",0,0,600,400,path);
        texte = new Projectil("texte",500,60,50,y,textePath,0,0,0);
        endPane.getChildren().addAll(background.getImageview(   ),texte.getImageView());


    }

    public void setStartScene(StartScene startScene) {
        this.startScene = startScene;
    }

    public AnimationTimer getTimerEnd() {
        return timerEnd;
    }
    //Methode qui choisit le texte a afficher en fonction du nombre de fois que l'on a appuyé sur espace
    public void textUpdate(){
                texte.setPosy(texte.getPosy()+65);
                texte.getImageView().setViewport(new Rectangle2D(texte.getPosx(), texte.getPosy(), texte.getLongueur(), texte.getHauteur()));
                texte.getImageView().setX(texte.getX());
                texte.getImageView().setY(texte.getY());
                texte.setOffset(texte.getOffset()+1);


    }
    public void keyListen(){
        setOnKeyPressed((event)->{
            switch (event.getCode()) {
                case SPACE -> {
                    if(texte.getOffset()==3){
                        timerEnd.stop();
                        startScene.getTimer().handle(0);
                        startScene.getTimer().start();
                        startScene.keyListen();
                        stage.setScene(startScene);
                        texte.setOffset(-1);
                        texte.setPosy(-65);
                        textUpdate();
                    }
                    else{
                        textUpdate();
                    }
                }
            }
        });
    }
}
