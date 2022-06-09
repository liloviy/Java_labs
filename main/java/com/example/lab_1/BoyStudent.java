package com.example.lab_1;

import javafx.scene.image.Image;

import java.net.URISyntaxException;

public class BoyStudent extends Student implements IBehaviour
{
    public BoyStudent(double x, double y, double height, double width) throws URISyntaxException
    {
        super(x, y, height, width);
        imageView.setImage(new Image(("D:\\ВУЗ\\2 курс\\2 семак\\Прога\\Lab_1\\src\\main\\resources\\com\\example\\lab_1\\d8d5fedd96965ef3533e2afaab03221f.1024_uTTzwda.jpg")));
        imageView.setX(x);
        imageView.setY(y);
        imageView.setFitHeight(height);
        imageView.setFitWidth(width);

    }
    static protected double time_of_life;

}
