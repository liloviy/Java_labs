package com.example.lab_1;

import javafx.application.Platform;

import java.net.URISyntaxException;
import java.util.Timer;
import java.util.TimerTask;

public class GirlStudentThread extends BaseAI
{
    @Override
    synchronized public void run() {
        for (int i = 0; i < Habitat.students.size(); i++)
        {
            if (Habitat.students.get(i).getClass() == GirlStudent.class) {
                if ((Habitat.second-Habitat.students.get(i).time_of_birtсh) < Habitat.students.get(i).time_of_change)
                {
                    Habitat.students.get(i).imageView.setX(Habitat.students.get(i).imageView.getX() + Habitat.students.get(i).x_girl_change);
                    Habitat.students.get(i).imageView.setY(Habitat.students.get(i).imageView.getY() + Habitat.students.get(i).y_girl_change);
                }
                else
                {
                    Habitat.students.get(i).x_girl_change=Math.random()*(10 +10) - 10;
                    Habitat.students.get(i).y_girl_change=Math.random()*(10 +10) - 10;
                    Habitat.students.get(i).time_of_change+=2;
                }
            }
        }
    }
}