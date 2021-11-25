package com.runner;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;


public class GameScene extends Scene {
    private Camera camera;
    private StaticThing backgroundLeft;
    private StaticThing backgroundRight;
    private StaticThing nbCoeur;
    private Hero hero;
    private int tir = 0;
    private int i = 0;
    private Pane pane;
    private Text texte= new Text("Hello World");
    private Text textePoke = new Text("afficher le nb de pokeball");
    private AnimationTimer timer = new AnimationTimer() {
        private long lastUpdate = 0;
        @Override
        public void handle(long l) {
            if (l - lastUpdate >= 40000000){
                hero.updateProjectil(hero.getX(),hero.getY(),pane);
                hero.update();
                camera.update();
                backgroundLeft.getImageview().setX(-((camera.getX())%800));
                backgroundRight.getImageview().setX(800-(camera.getX())%800);
                hero.getImageView().setX(hero.getX()-camera.getX());
                hero.getImageView().setY(hero.getY());
                textUpdate(hero.getX(),pane,texte);

                    for (int i=0;i<hero.getProjectilist().size();i++){
                    hero.getProjectilist().get(i).getImageView().setX(hero.getProjectilist().get(i).getX());
                    hero.getProjectilist().get(i).getImageView().setY(hero.getProjectilist().get(i).getY());
                    //System.out.println(hero.getProjectilist().get(i).getImagePath());
                    //System.out.println("Vrai posotion:"+(hero.getProjectilist().get(i).getImageView().getX()));

                }
                //System.out.println(hero.getX());
                lastUpdate = l;


            }
        }
    };


    public GameScene(Pane pane, double longueur, double largeur, String path, String pathHero, String pathCoeur){
        super(pane, longueur,  largeur, true);
        this.pane = pane;
        this.nbCoeur = new LifeBar("nombre de coeur", 10,10, 150,40,pathCoeur,3);
        this.hero = new Hero( "hero", 84, 100,40, 250, pathHero,0,0,10);
        this.camera = new Camera(0,0, hero);
        this.backgroundLeft = new StaticThing("backgroundleft", -200+camera.getX(),0,800,400,path);
        this.backgroundRight = new StaticThing("backgroundRight", 600+camera.getX(),0,800,400,path);

        pane.getChildren().add(texte);
        final Font times30BoldItalicFont = Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 20);
        texte.setFont(times30BoldItalicFont);
        texte.setFill(Color.RED);

        pane.getChildren().add(textePoke);
        textePoke.setFont(times30BoldItalicFont);
        textePoke.setFill(Color.RED);
        updateAfficheNbPoke(hero.getNbFireball(), textePoke);

        StaticThing pokeBall = new StaticThing("pokeball",540,10,20,20,"D:\\java\\projet_runner\\poke1.png");
        pane.getChildren().add(pokeBall.getImageview());
        pokeBall.getImageview().setX(pokeBall.getX());
        pokeBall.getImageview().setY(pokeBall.getY());
        this.timer.start();

    }



    public StaticThing getBackgroundLeft() {
        return backgroundLeft;
    }

    public StaticThing getBackgroundRight() {
        return backgroundRight;
    }

    public Hero getHero() {
        return hero;
    }

    public StaticThing getNbCoeur(){return nbCoeur;}

    public AnimationTimer getTimer() { return timer;}

    public void tir(){
        tir++;
    }

    public void textUpdate(Double positionHero, Pane p,Text texte){
        int x = (int) (Math.round(positionHero)/10);
        texte.setText("Position du héros  : "+ x);
        texte.setX(200);
        texte.setY(20);

    }

    public void updateAfficheNbPoke(Double nbpokeball, Text texte){
        int x = (int) Math.round(nbpokeball);
        texte.setText(" " +x);
        texte.setX(565);
        texte.setY(25);
    }

    public void keyListener(){
        setOnKeyPressed((event)->{
            switch (event.getCode()){
                case SPACE -> {
                    System.out.println("JUMP !!");
                    getHero().jump();
                }
                case RIGHT -> {
                    System.out.println("CA VA PETER");
                    hero.tirer();
                    if (hero.getNbFireball()>0){
                        hero.shotProj(pane,hero.getX(),hero.getY(),0,0);
                        hero.setNbFireball(hero.getNbFireball()-1);
                        updateAfficheNbPoke(hero.getNbFireball(), textePoke);
                    }

                }
                case UP -> {
                    System.out.println("JUMP ET TIR !!");
                    getHero().tirEnVol();
                }
                }

        });
    }
}
