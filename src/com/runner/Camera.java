package com.runner;

public class Camera {
    private double x;
    private double y;
    private Hero h;

    public double getX(){
        return x;
    }
    public double getY(){
        return y;
    }

    public Camera(Integer x, Integer y, Hero h){
        this.x = x;
        this.y = y;
        this.h =h;
    }
    public void update(){
        this.x = h.getX();
    }

    @Override
    public String toString(){
        return "x : " +  x + ", y : " + y;
    }
}
