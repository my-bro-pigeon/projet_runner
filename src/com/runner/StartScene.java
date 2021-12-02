package com.runner;
/*StartScene est la scène qui apparaît au lancement du jeu et après chaque fin de partie, elle comporte un background, une liste d'étoiles annimées
 et un StaticThing qui clignote
 !! J'utilise une variable pressEnter dans le code mais la touche du clavier pour jouer est bien SPACE*/

import javafx.geometry.Rectangle2D;
import javafx.scene.layout.Pane;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;

public class StartScene extends Scene{
    private StaticThing background;
    private StaticThing pressEnter; //Variable qui correspond au SPACE
    private boolean indiceEnter=true;
    private int i=0;
    private Pane menuPane;
    private Pane pane;
    private GameScene gameScene;
    private Stage stage;
    private ArrayList<Projectil> starList = new ArrayList<Projectil>();
    private AnimationTimer timer = new AnimationTimer() {
        private long lastUpdate = 0;
        @Override
        public void handle(long l) {
            if (l - lastUpdate >= 50000000){
                //gérer l'annimation des étoiles :
                menuAddStar();
                menuUpdate();
                for (int i=0;i<starList.size();i++) {
                    starList.get(i).getImageView().setX(starList.get(i).getX());
                    starList.get(i).getImageView().setY(starList.get(i).getY());
                }
                //gérer le clignotement de logo PRESS SPACE
                i++;
                if (i==5) {
                    enterUpdate();
                    if (indiceEnter) {
                        menuPane.getChildren().add(pressEnter.getImageview());
                    }
                    if (!indiceEnter) {
                        menuPane.getChildren().remove(pressEnter.getImageview());
                    }
                    i=0;
                }
                lastUpdate = l;
            }
        }
    };

    public StartScene(Pane menuPane, Pane pane,  GameScene gameScene,Stage stage, double longueur, double largeur, String path){
        super(menuPane, longueur, largeur, true);
        this.menuPane = menuPane;
        this.pane = pane;
        this.gameScene=gameScene;
        this.stage = stage;
        this.background = new StaticThing("menuBackground",0,0,600,400,path);
        menuPane.getChildren().add(background.getImageview());
        pressEnter = new StaticThing("pressEnter",210,300,190,20,"D:\\java\\projet_runner\\images\\pressStart.png");
        this.menuPane.getChildren().add(pressEnter.getImageview());
        pressEnter.getImageview().setX(pressEnter.getX());
        pressEnter.getImageview().setY(pressEnter.getY());

    }
    public void keyListen(){
        setOnKeyPressed((event)->{
            switch (event.getCode()) {
                case SPACE -> {
                    //en cas d'appuie sur le bouton SPACE on lance la gameScene
                    timer.stop();
                    gameScene.getTimer().handle(0);
                    gameScene.getTimer().start();
                    gameScene.keyListener();
                    stage.setScene(gameScene);
                    gameScene.getHero().setNbCoeur(6);
                    gameScene.getNbCoeur().setNbCoeur(6);
                    gameScene.getHero().setNbBadge(0);
                    gameScene.getHero().setX(0);
                    gameScene.getHero().setNbFireball(0);


                }
            }
        });
    }
    public void menuAddStar(){
        double nbAlea =  (int) (Math.random()*600);
        Projectil star = new Projectil("star", 22,22, nbAlea, 0, "D:\\java\\projet_runner\\images\\stars.png",2,0,0);
        star.getImageView().setViewport(new Rectangle2D(0,0 ,30 ,20));
        menuPane.getChildren().add(star.getImageView());
        starList.add(star);

    }
    public void menuUpdate() {
        for (int i = 0; i < starList.size(); i++) {
            //déplacement des étoiles selon l'axe y sur la scene
            double yNmoinsUn = starList.get(i).getY();
            starList.get(i).setY(yNmoinsUn + 10);

            //animation des étoiles

            if(starList.get(i).getOffset() >=8 && starList.get(i).getOffset() <=15){
                starList.get(i).setPosy(80);
                starList.get(i).setOffset(starList.get(i).getOffset()+1);
            }
            if(starList.get(i).getOffset() >=6 && starList.get(i).getOffset() <=7){
                starList.get(i).setPosy(65);
                starList.get(i).setOffset(starList.get(i).getOffset()+1);
            }
            if(starList.get(i).getOffset() >=4 && starList.get(i).getOffset() <=5){
                starList.get(i).setPosy(47);
                starList.get(i).setOffset(starList.get(i).getOffset()+1);
            }
            if(starList.get(i).getOffset() >=2 && starList.get(i).getOffset() <=3){
                starList.get(i).setPosy(26);
                starList.get(i).setOffset(starList.get(i).getOffset()+1);
            }
            if(starList.get(i).getOffset() <= 1){
                starList.get(i).setPosy(2);
                starList.get(i).setOffset(starList.get(i).getOffset()+1);
            }
            if(starList.get(i).getOffset() == 16){starList.get(i).setOffset(0);}

            starList.get(i).getImageView().setViewport(new Rectangle2D(starList.get(i).getPosx(),starList.get(i).getPosy(),starList.get(i).getLongueur(),starList.get(i).getHauteur()));


            if(starList.get(i).getY()>400){
                starList.remove(starList.get(i));
                menuPane.getChildren().remove(starList.get(i).getImageView());

        }
    }
    }
    public void enterUpdate(){
        this.indiceEnter = !this.indiceEnter;
        }

    public AnimationTimer getTimer() {
        return timer;
    }
}

