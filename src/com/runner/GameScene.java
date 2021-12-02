package com.runner;

/* La game scene correspond à la scène principale de jeu, elle apparait après la scene d'entrée et peut conduire à la scène de
 défaite (appelée endScene) ou la scène de victoire.*/

import javafx.animation.AnimationTimer;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;


public class GameScene extends Scene {
    private Camera camera;
    private EndScene endScene; // scène de défaite
    private EndScene victoryScene;
    private Stage stage;
    private StaticThing backgroundLeft;
    private StaticThing backgroundRight;
    private Projectil blackScreen;
    private LifeBar nbCoeur;
    private StaticThing badgeBox;
    private Hero hero;
    private Pane pane;
    private int j = 0; //controle la fréquence des ennemis
    private int h = 0; // controle la fréquence des badges
    private Text texte= new Text("Hello World"); // texte qui affiche l'avancée du héros
    private Text textePoke = new Text("afficher le nb de pokeball"); // texte qui va afficher le nombre de pokéball du héros
    private ArrayList<Ennemi> dracaufeuList = new ArrayList<Ennemi>();
    private ArrayList<Ennemi> listBadge = new ArrayList<Ennemi>();
    private AnimationTimer timer = new AnimationTimer() {
        private long lastUpdate = 0;
        @Override
        public void handle(long l) {
            if (l - lastUpdate >= 40000000){
                if(!(hero.heroIsDead()) && hero.getNbBadge()<8) {  //si le héros est vivant et possède moins de 8 badges
                    hero.updateProjectil(hero.getX(), hero.getY(), pane);
                    hero.update();
                    camera.update();
                    backgroundLeft.getImageview().setX(-((camera.getX()) % 800));
                    backgroundRight.getImageview().setX(800 - (camera.getX()) % 800);
                    hero.getImageView().setX(hero.getX() - camera.getX());
                    hero.getImageView().setY(hero.getY());
                    textUpdate(hero.getX(), pane, texte);
                    updateAfficheNbPoke(hero.getNbFireball(), textePoke);
                    nbCoeur.getImageview().setViewport(new Rectangle2D(0,150-25*nbCoeur.getNbCoeur(), nbCoeur.getLongueur(), nbCoeur.getLargeur()));

                    for (int i = 0; i < hero.getProjectilist().size(); i++) {
                        hero.getProjectilist().get(i).getImageView().setX(hero.getProjectilist().get(i).getX());
                        hero.getProjectilist().get(i).getImageView().setY(hero.getProjectilist().get(i).getY());

                        //System.out.println(hero.getProjectilist().get(i).getImagePath());
                    }

                    //génération et update des ennemis :
                    j++;
                    if (j == 50) {
                        newEnnemi(pane, dracaufeuList, 90, 83, 650, 255, 0, 0, "D:\\java\\projet_runner\\images\\dracaufeu.png");
                        j = 0;
                    }

                    for (int i = 0; i < dracaufeuList.size(); i++) {
                        dracaufeuList.get(i).updateDracaufeu();
                        dracaufeuList.get(i).getImageView().setX(dracaufeuList.get(i).getX());
                        dracaufeuList.get(i).getImageView().setY(dracaufeuList.get(i).getY());
                        if (dracaufeuList.get(i).getX() <= -50) {
                            supEnnemi(dracaufeuList.get(i), pane, dracaufeuList);
                        }
                    }

                    //génération et update des badges
                    h++;
                    if(h==125){
                        newEnnemi(pane, listBadge, 40,40,650,150,40* hero.getNbBadge(),0,"D:\\java\\projet_runner\\images\\badge.png");
                        h=0;
                    }
                    for(int i=0;i<listBadge.size();i++){
                        listBadge.get(i).updateBadge();
                        listBadge.get(i).getImageView().setX(listBadge.get(i).getX());
                        listBadge.get(i).getImageView().setY(listBadge.get(i).getY());
                        if (listBadge.get(i).getX() <= -50) {
                            supEnnemi(listBadge.get(i), pane, listBadge);
                        }
                    }
                    if(hero.getNbBadge()==0){
                        badgeBox.getImageview().setViewport(new Rectangle2D(0,85* hero.getNbBadge(),141,80));
                    }


                    //test des colisions
                    checkCollision();

                }
                if((hero.heroIsDead())){  //si le heros est mort on fait défiler un écran noir et on affiche l'écran de défaite
                    for(int i=0;i<listBadge.size();i++){
                        supEnnemi(listBadge.get(i), pane, listBadge);
                    }

                    if(blackScreen.getOffset()==0){
                        pane.getChildren().addAll(blackScreen.getImageView());
                        blackScreen.setOffset(1);
                    }
                    if(blackScreen.getHauteur()<400) {
                        blackScreen.setHauteur(blackScreen.getHauteur() + 10);
                        blackScreen.getImageView().setViewport(new Rectangle2D(0, 0, 600, blackScreen.getHauteur()));
                        blackScreen.getImageView().setX(blackScreen.getX());
                        blackScreen.getImageView().setY(blackScreen.getY());


                    }
                    if(blackScreen.getHauteur()>=400) {
                        pane.getChildren().remove(blackScreen.getImageView());
                        blackScreen.setOffset(0);
                        blackScreen.setHauteur(10);
                        timer.stop();
                        endScene.getTimerEnd().handle(0);
                        endScene.getTimerEnd().start();
                        endScene.keyListen();
                        stage.setScene(endScene);
                    }
                }

                if(hero.getNbBadge()>=8){ //si le héros possède les 8 badges, on affiche la scène de victoire avec le même effet d'écran noir que précédemment sauf que le heros continue à courir.
                    hero.update();
                    hero.getImageView().setX(hero.getX() - camera.getX());
                    hero.getImageView().setY(hero.getY());
                    for (int i = 0; i < dracaufeuList.size(); i++) {
                        supEnnemi(dracaufeuList.get(i), pane, dracaufeuList);
                    }
                    for (int i = 0; i < hero.getProjectilist().size(); i++) {
                        hero.supProjectil(hero.getProjectilist().get(i),pane);
                    }
                    if(blackScreen.getOffset()==0){
                        pane.getChildren().addAll(blackScreen.getImageView());
                        blackScreen.setOffset(1);
                    }
                    if(blackScreen.getHauteur()<400) {
                        blackScreen.setHauteur(blackScreen.getHauteur() + 10);
                        blackScreen.getImageView().setViewport(new Rectangle2D(0, 0, 600, blackScreen.getHauteur()));
                        blackScreen.getImageView().setX(blackScreen.getX());
                        blackScreen.getImageView().setY(blackScreen.getY());


                    }
                    if(blackScreen.getHauteur()>=400) {
                        pane.getChildren().remove(blackScreen.getImageView());
                        blackScreen.setOffset(0);
                        blackScreen.setHauteur(10);
                        timer.stop();

                        victoryScene.getTimerEnd().handle(0);
                        victoryScene.getTimerEnd().start();
                        victoryScene.keyListen();
                        stage.setScene(victoryScene);
                    }
                }

                lastUpdate = l;


            }
        }
    };


    public GameScene(Pane pane, EndScene endScene, EndScene victoryScene,Stage stage, double longueur, double largeur, String path, String pathHero, String pathCoeur){
        super(pane, longueur,  largeur, true);
        this.pane = pane;
        this.endScene = endScene;
        this.stage = stage;
        this.victoryScene = victoryScene;
        this.nbCoeur = new LifeBar("nombre de coeur", 10,10, 170,20,pathCoeur,6);
        this.hero = new Hero( "hero", 84, 100,40, 250, pathHero,0,0,0);
        this.camera = new Camera(0,0, hero);
        this.backgroundLeft = new StaticThing("backgroundleft", -200+camera.getX(),0,800,400,path);
        this.backgroundRight = new StaticThing("backgroundRight", 600+camera.getX(),0,800,400,path);
        this.blackScreen = new Projectil("blackScreen",600,10,0,0,"D:\\java\\projet_runner\\images\\blackscreen.png",0,0,0);

        this.badgeBox = new StaticThing("boite de badges",370,6,141,80,"D:\\java\\projet_runner\\images\\boite_badge.png");
        badgeBox.getImageview().setViewport(new Rectangle2D(0,0,141,80));
        pane.getChildren().addAll(backgroundLeft.getImageview(),backgroundRight.getImageview(),hero.getImageView(),nbCoeur.getImageview(),badgeBox.getImageview());


        pane.getChildren().add(texte);
        final Font times30BoldItalicFont = Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 20);
        texte.setFont(times30BoldItalicFont);
        texte.setFill(Color.RED);

        pane.getChildren().add(textePoke);
        textePoke.setFont(times30BoldItalicFont);
        textePoke.setFill(Color.RED);
        updateAfficheNbPoke(hero.getNbFireball(), textePoke);

        StaticThing pokeBall = new StaticThing("pokeball",540,10,20,20,"D:\\java\\projet_runner\\images\\poke1.png");
        pane.getChildren().add(pokeBall.getImageview());
        pokeBall.getImageview().setX(pokeBall.getX());
        pokeBall.getImageview().setY(pokeBall.getY());


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

    public LifeBar getNbCoeur(){return nbCoeur;}

    public AnimationTimer getTimer() { return timer;}

//Methode qui permet d'update le texte qui affiche la position du héros
    public void textUpdate(Double positionHero, Pane p,Text texte){
        int x = (int) (Math.round(positionHero)/10);
        texte.setText("Score  : "+ x);
        texte.setX(250);
        texte.setY(20);

    }
    //Methode qui permet d'update le texte qui affiche le nombre de pokéballs
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
                case UP -> { //ne pas utiliser car je n'ai pas eu le temps de finir ce que je voulais faire avec.
                    System.out.println("JUMP ET TIR !!");
                    getHero().tirEnVol();
                }
                }

        });
    }

    public void newEnnemi(Pane p, ArrayList<Ennemi> listEnnemi,double longueur, double hauteur, double x, double y, double posX, double posY,String path){
        Ennemi ennemi = new Ennemi("ennemi", longueur,hauteur, x, y, path,posX,posY);
        ennemi.getImageView().setViewport(new Rectangle2D(ennemi.getPosx(), ennemi.getPosy(),ennemi.getLongueur(), ennemi.getHauteur()));
        addEnnemi(ennemi,p,listEnnemi);
    }


    public void addEnnemi(Ennemi ennemi, Pane p,ArrayList<Ennemi> listEnnemi){
        p.getChildren().add(ennemi.getImageView());
        listEnnemi.add(ennemi);
    }

    public void supEnnemi(Ennemi ennemi, Pane p,ArrayList<Ennemi> listEnnemi){
        listEnnemi.remove(ennemi);
        p.getChildren().remove(ennemi.getImageView());
    }




    public void checkCollision() {
        for (int i = 0; i < dracaufeuList.size(); i++) {
            if (hero.getHitBox().intersects(dracaufeuList.get(i).getHitBoxEnnemi())){ //traitement de la collision entre le héros et un ennemi.
                hero.takeDmg();
                nbCoeur.updateNbCoeur();
                supEnnemi(dracaufeuList.get(i), pane, dracaufeuList);

            }
            for (int j = 0; j < hero.getProjectilist().size(); j++) { //traitement de la collision entre un ennemi et un projectile.
                if (hero.getProjectilist().get(j).getHitBoxProj().intersects(dracaufeuList.get(i).getHitBoxEnnemi())){
                    supEnnemi(dracaufeuList.get(i), pane, dracaufeuList);
                    hero.supProjectil(hero.getProjectilist().get(j),pane);
                }
            }
        }
        for (int i=0;i<listBadge.size();i++){
            if(hero.getHitBox().intersects(listBadge.get(i).getHitBoxProj())){ //traitement de la collision entre le héros et un badge.
               hero.setNbBadge(hero.getNbBadge()+1);
               supEnnemi(listBadge.get(i),pane,listBadge);
               hero.setNbFireball(hero.getNbFireball()+1);

               //ensuite on modifie l'apparence du badge suivant : si l'on vient de récuperer le badge n°1, le badge suivant a  apparaître sera le n°2

               if(hero.getNbBadge()<5){
                   badgeBox.getImageview().setViewport(new Rectangle2D(0,85* hero.getNbBadge(),141,80));
               }
               else{
                   badgeBox.getImageview().setViewport(new Rectangle2D(146,85* (hero.getNbBadge()-5),141,80));

               }

            }
        }
    }
}
