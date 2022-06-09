package com.example.lab_1;

import javafx.scene.image.Image;
import java.net.URISyntaxException;

public class GirlStudent extends Student implements IBehaviour
{
    public GirlStudent(double x, double y, double height, double width) throws URISyntaxException
    {
        super(x, y, height, width);
        imageView.setImage(new Image("D:\\ВУЗ\\2 курс\\2 семак\\Прога\\Lab_1\\src\\main\\resources\\com\\example\\lab_1\\krasivye-devushki-v-ochkah-dlja-zrenija-65-foto-11.jpg"));
        imageView.setX(x);
        imageView.setY(y);
        imageView.setFitHeight(height);
        imageView.setFitWidth(width);
    }
    static protected double time_of_life;
}
