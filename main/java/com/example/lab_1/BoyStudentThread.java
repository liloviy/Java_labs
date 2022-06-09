package com.example.lab_1;

public class BoyStudentThread extends BaseAI
{
    @Override
    synchronized public void run()
    {
        for (int i=0;i<Habitat.students.size();i++)
        {
            if (Habitat.students.get(i).getClass()==BoyStudent.class)
            {
                Habitat.students.get(i).imageView.setX(Habitat.students.get(i).imageView.getX()+0.3*Math.cos(Habitat.second));
                Habitat.students.get(i).imageView.setY(Habitat.students.get(i).imageView.getY()+0.3*Math.sin(Habitat.second));
            }
        }
      try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
