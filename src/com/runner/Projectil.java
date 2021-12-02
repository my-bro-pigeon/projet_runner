package com.runner;

/*classe qui me permet de faire une liste de projectiles pour le jeu mais également la liste des étoiles pour l'écran
d'acceuil.  */

public class Projectil extends AnimatedThing{
    private double offset;

    public Projectil(String fileName, double longueur, double hauteur, double x, double y, String imagePath, double positionX, double positionY,double offset){
        super(fileName,longueur,hauteur,x,y,imagePath,positionX,positionY);
        this.offset  = offset;
        this.setX(this.getX()+this.offset);

    }
    public double getOffset(){
        return offset;
    }

    public void setOffset(double offset) {
        this.offset = offset;
    }
}
