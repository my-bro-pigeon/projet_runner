package com.runner;

/* Le hero possède un nombre de projectiles, un nombre de coeurs et un nombre de badges */

public class Hero extends AnimatedThing{
    public double nbFireball;
    public double nbCoeur=6;
    public double nbBadge=0;

    public Hero(String fileName, double longueur, double hauteur, double x, double y, String imagePath, double positionX, double positionY,double nbFireball) {
        super(fileName, longueur, hauteur, x, y, imagePath, positionX, positionY);
        this.nbFireball = nbFireball;
    }

    public double getNbFireball() {
        return nbFireball;
    }

    public void setNbFireball(double nbFireball) {
        this.nbFireball = nbFireball;
    }

    /* Methode permettant de savoir si le héros est en vie ou non */
    public boolean heroIsDead(){
        if(nbCoeur==0){
            return true;
        }
        return false;
    }
    public void takeDmg(){nbCoeur--;}

    public void setNbCoeur(double nbCoeur) {
        this.nbCoeur = nbCoeur;
    }

    public double getNbBadge() {
        return nbBadge;
    }

    public void setNbBadge(double nbBadge) {
        this.nbBadge = nbBadge;
    }
}
