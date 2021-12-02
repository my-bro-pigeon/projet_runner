package com.runner;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.util.ArrayList;

abstract public class AnimatedThing {
    private double longueur;
    private double hauteur;
    private ImageView imageView;
    private Integer attitude;
    private double posx;
    private double posy;
    private double x;
    private double y;
    private String imagePath;
    private boolean indiceJump = false;
    private double tpsSaut = 0;
    private boolean indiceTir = false;
    private double jump = 0;
    private double tir = 0;
    private double jumpTir = 0;
    private ArrayList<Projectil> projectilist = new ArrayList<Projectil>();



    public AnimatedThing(String fileName, double longueur, double hauteur, double x, double y, String imagePath, double positionpersodansdocX, double positionpersodansdocY){
        this.posx = positionpersodansdocX;
        this.posy = positionpersodansdocY;
        this.longueur = longueur;
        this.hauteur = hauteur;
        this.imagePath = imagePath;
        this.x = x;
        this.y = y;
        Image imageSheet = new Image(imagePath);
        this.imageView = new ImageView(imageSheet);
        imageView.setViewport(new Rectangle2D(positionpersodansdocX,positionpersodansdocY, longueur, hauteur));
        this.imageView.setX(x);
        this.imageView.setY(y);
    }

    public ImageView getImageView() {
        return imageView;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public double getLongueur() {return longueur;}
    public double getHauteur() {return hauteur;}

    public void update(){
        if ((!this.indiceJump) &&(!this.indiceTir)) {
            this.posx +=84;
            if (this.posx ==504 ){ this.posx = 0;}}
        if (jump>0) {
            if (this.tpsSaut < 105) {
                this.y = this.y - 18;
                this.tpsSaut = this.tpsSaut + 15;
                this.posx = 0;
                this.posy = 165;
            }
            if (this.tpsSaut < 210 && this.tpsSaut >= 105) {
                this.y = this.y + 18;
                this.tpsSaut = this.tpsSaut + 15;
                this.posx = 84;
                this.posy = 165;
            }
            if (this.tpsSaut >= 210) {
                this.posy = 0;
                this.indiceJump = false;
                this.tpsSaut = 0;
                this.jump = 0;
            }
        }
        if (this.tir>0) {
            this.posy = 330;
            this.posx += 84;
            if (this.posx == 504) {
                this.indiceTir = false;
                this.posy = 0;
                this.posx = 0;
                this.tir =0;
            }
        }
        if (this.jumpTir>0) {
            if (this.tpsSaut < 100) {
                this.y = this.y - 20;
                this.tpsSaut = this.tpsSaut + 20;
                this.posx = 0;
                this.posy = 495;
            }
            if (this.tpsSaut < 200 && this.tpsSaut >= 100) {
                this.y = this.y + 20;
                this.tpsSaut = this.tpsSaut + 20;
                this.posx = 84;
                this.posy = 495;
            }

            if (this.tpsSaut >= 200) {
                this.posy = 0;
                this.indiceJump = false;
                this.tpsSaut = 0;
                this.indiceTir = false;
                this.jumpTir=0;
            }
        }


        this.x +=15;
        this.imageView.setViewport(new Rectangle2D(this.posx, this.posy, this.longueur, this.hauteur));
    }

    public void updateProjectil(double x, double y,Pane p){
            for (int i=0;i<projectilist.size();i++){
                double xNmoinsUn = projectilist.get(i).getX();
                projectilist.get(i).setX(xNmoinsUn+15);

                //animation de la pokeball
                if(projectilist.get(i).getOffset() >=16 && projectilist.get(i).getOffset() <=17){
                    projectilist.get(i).setPosx(125);
                    projectilist.get(i).setOffset(projectilist.get(i).getOffset()+1);
                }
                if(projectilist.get(i).getOffset() >=8 && projectilist.get(i).getOffset() <=15){
                    projectilist.get(i).setPosx(100);
                    projectilist.get(i).setOffset(projectilist.get(i).getOffset()+1);
                }
                if(projectilist.get(i).getOffset() >=6 && projectilist.get(i).getOffset() <=7){
                    projectilist.get(i).setPosx(75);
                    projectilist.get(i).setOffset(projectilist.get(i).getOffset()+1);
                }
                if(projectilist.get(i).getOffset() >=4 && projectilist.get(i).getOffset() <=5){
                    projectilist.get(i).setPosx(50);
                    projectilist.get(i).setOffset(projectilist.get(i).getOffset()+1);
                }
                if(projectilist.get(i).getOffset() >=2 && projectilist.get(i).getOffset() <=3){
                    projectilist.get(i).setPosx(25);
                    projectilist.get(i).setOffset(projectilist.get(i).getOffset()+1);
                }
                if(projectilist.get(i).getOffset() <= 1){
                    projectilist.get(i).setPosx(0);
                    projectilist.get(i).setOffset(projectilist.get(i).getOffset()+1);
                }
                if(projectilist.get(i).getOffset() == 18){projectilist.get(i).setOffset(0);}

                projectilist.get(i).getImageView().setViewport(new Rectangle2D(projectilist.get(i).getPosx(),projectilist.get(i).getPosy(),projectilist.get(i).getLongueur(),projectilist.get(i).getHauteur()));


                if(projectilist.get(i).getX()>1000){
                    supProjectil(projectilist.get(i),p);
                }

            }
    }

    public void shotProj(Pane p,Double xHero,Double yHero,double posx, double posy){
        Projectil proj = new Projectil("projectile", 30,20, 50, 285, "D:\\java\\projet_runner\\pokeall.png",posx,posy,0);
        proj.getImageView().setViewport(new Rectangle2D(posx,posy , this.longueur, this.hauteur));
        addProjectil(proj,p);
    }


    public void addProjectil(Projectil proj, Pane p){
        p.getChildren().add(proj.getImageView());
        projectilist.add(proj);
    }

    public void supProjectil(Projectil proj, Pane p){
        projectilist.remove(proj);
        p.getChildren().remove(proj.getImageView());
    }


    public void jump(){
        this.indiceJump = !this.indiceJump;
        jump ++;

    }

    public void tirEnVol(){
        this.indiceJump = !this.indiceJump;
        this.indiceTir = !this.indiceTir;
        jumpTir ++;

    }

    public void tirer(){
        this.indiceTir = !this.indiceTir;
        tir++;
    }

    public Rectangle2D getHitBox(){return new Rectangle2D(imageView.getX()+30,imageView.getY()+30,longueur-30, hauteur-30);}
    public Rectangle2D getHitBoxEnnemi(){return new Rectangle2D(imageView.getX()+30,imageView.getY()+30,longueur-50, hauteur-30);}
    public Rectangle2D getHitBoxProj(){return new Rectangle2D(imageView.getX(),imageView.getY(),longueur, hauteur);}


    public boolean isIndiceJump() {
        return indiceJump;
    }

    public double getX() {
        return x;
    }

    public double getY(){
        return y;
    }

    public void setX(double x){
        this.x=x;
    }
    public void setY(double y){
        this.y=y;
    }


    public double getPosx() {
        return posx;
    }
    public double getPosy() {
        return posy;
    }

    public void setHauteur(double hauteur) {
        this.hauteur = hauteur;
    }

    public void setPosx(double posx) {
        this.posx = posx;
    }

    public void setPosy(double posy) {
        this.posy = posy;
    }

    public ArrayList<Projectil> getProjectilist() {
        return projectilist;
    }
}

