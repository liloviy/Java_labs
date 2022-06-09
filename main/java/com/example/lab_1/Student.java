package com.example.lab_1;

import javafx.scene.image.ImageView;

public abstract class Student
{
    public Student(double x, double y, double height, double width)
    {
        this.x=x;
        this.y=y;
        this.height=height;
        this.width=width;
        imageView=new ImageView();
    }
    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
    public double getHeight() {
        return height;
    }
    public double getWidth() {
        return width;
    }
    public ImageView getImageView() {
        return imageView;
    }
    public void setX(double x) {
        this.x=x;
    }
    public void setY(double y)  {
        this.y=y;
    }
    public void setHeight(double height){
        this.height=height;
    }
    public void setWidth(double width){
        this.width=width;
    }

    protected double x,y;
    protected double height,width;
    protected ImageView imageView;
    protected double time_of_birtсh;
    public double time_of_change=2;
    public double x_girl_change =Math.random()*(10 +10) - 10;
    public double y_girl_change =Math.random()*(10 +10) - 10;
    protected int id;
}




