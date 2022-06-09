package com.example.lab_1;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;
import java.util.regex.Pattern;
import java.io.*;
import javafx.scene.text.Font;


public class Habitat implements Initializable
{
    public void start()
    {
        TxArea.setVisible(false);
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                second+=1;
                Platform.runLater(new Runnable() {
                    @Override
                    synchronized  public void run() {
                        try {
                            update();
                            LabelTime.setText("Текущее время: "+second + " с");
                            LabelGirls.setText("Девочек "+girl);
                            LabelBoys.setText("Мальчиков "+boy);
                            BtnStart.setDisable(true);
                            BtnStop.setDisable(false);
                        }
                        catch (URISyntaxException e) {
                            e.printStackTrace();
                        }
                    }

                });
            }
        },1000,1000);
        start_boy();
        start_girl();
    }

    public void start_boy()
    {
        moveTimerBoy = new Timer();
        moveTimerBoy.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        boythread = new BoyStudentThread();
                        boythread.start();
                        boythread.setPriority(PRIO_BOY);
                        for (int i=0;i<students.size();i++)
                            if (students.get(i).imageView.getX()>(AnchorPane.getMaxWidth()-72) || students.get(i).imageView.getY()>(AnchorPane.getMaxHeight()-72) || students.get(i).imageView.getX()<(AnchorPane.getMinWidth()) || students.get(i).imageView.getY()<(AnchorPane.getMinHeight()))
                                AnchorPane.getChildren().remove(students.get(i).getImageView());
                    }
                });
            }

        },0,20);
    }

    public void start_girl()
    {
        moveTimerGirl = new Timer();
        moveTimerGirl.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        girlthread = new GirlStudentThread();
                        girlthread.start();
                        girlthread.setPriority(PRIO_GIRL);
                        for (int i=0;i<students.size();i++)
                            if (students.get(i).imageView.getX()>(AnchorPane.getMaxWidth()-72) || students.get(i).imageView.getY()>(AnchorPane.getMaxHeight()-72) || students.get(i).imageView.getX()<(AnchorPane.getMinWidth()) || students.get(i).imageView.getY()<(AnchorPane.getMinHeight()))
                                AnchorPane.getChildren().remove(students.get(i).getImageView());
                    }
                });
            }
        },0,200);
    }
    public void start_main() throws IOException, InterruptedException {
        TimeFix();
        LifeFix();
        getp1();
        getp2();
        get_priority_boy();
        get_priority_girl();
        Main.stage.setScene(Main.scenemain);
    }

    public void start_server() throws IOException, InterruptedException {
        ServerThread serverThread = new ServerThread();
        serverThread.start();
    }

    public void show_server() throws IOException
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Диалоговое окно");
        alert.setHeaderText("Id живых объектов и время их рождения");
        TextArea textArea = new TextArea();
        textArea.setText(server_clients);
        textArea.setEditable(false);
        textArea.setFont(new Font("Calibri", 20));
        textArea.setPrefSize(400, 300);
        alert.getDialogPane().setContent(textArea);
        alert.showAndWait();
    }
    public void back_menu()
    {
        end();
        TxArea.setVisible(false);
        Main.stage.setScene(Main.scenemenu);
    }
    public void stop()
    {
        timer.cancel();
        BtnStart.setDisable(false);
        BtnStop.setDisable(true);
        moveTimerBoy.cancel();
        moveTimerGirl.cancel();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Диалоговое окно");
        alert.setHeaderText("Вы хотите Закончить симуляцию?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get()==ButtonType.OK)
        {
            TxArea.setVisible(true);
            TxArea.setText(" Результат симуляции \n Прошедшее время - " + second + " c \n Количество мальчиков - " + boy + "\n Количество девочек - " + girl) ;
            end();
        }
        if (result.get()==ButtonType.CANCEL)
            start();
    }
    public void reset()
    {
        LabelTime.setText("Текущее время: "+second + " с");
        LabelGirls.setText("Девочек "+girl);
        LabelBoys.setText("Мальчиков "+boy);
    }
    public void ESC()
    {
        Platform.exit();
    }
  synchronized  public void end()
    {
        timer.cancel();
        moveTimerBoy.cancel();
        moveTimerGirl.cancel();
        for (int i =0;i<students.size();i++)
        {
            AnchorPane.getChildren().remove(students.get(i).getImageView());
        }
        int number_in_collection=students.size();
        for (int i =0;i<number_in_collection;i++)
        {
            studentHashSet.remove(students.get(0).id);
            studentsTime.remove(students.get(0).id);
            students.remove(0);
        }
        second=0;
        girl=0;
        boy=0;
        LabelTime.setText("Текущее время: "+second + " с");
        LabelGirls.setText("Девочек "+girl);
        LabelBoys.setText("Мальчиков "+boy);
        BtnStart.setDisable(false);
    }
    public void update() throws URISyntaxException
    {
        if ((second % N2 == 0) && (Math.random() <= P2)) {
            GirlStudent girlStudent = new GirlStudent(getRandomx(), getRandomy(), 70, 70);
            students.add(girlStudent);
            AnchorPane.getChildren().add(girlStudent.getImageView());
            girlStudent.time_of_birtсh=second;
            girlStudent.id=girlStudent.hashCode();
            studentHashSet.add(girlStudent.id);
            studentsTime.put(girlStudent.id,girlStudent.time_of_birtсh);
            girl += 1;
        }
        if ((second % N1 == 0) && (Math.random() <= P1)) {
            BoyStudent boyStudent = new BoyStudent(getRandomx(), getRandomy(), 70, 70);
            students.add(boyStudent);
            AnchorPane.getChildren().add(boyStudent.getImageView());
            boyStudent.time_of_birtсh=second;
            boyStudent.id=boyStudent.hashCode();
            studentHashSet.add(boyStudent.id);
            studentsTime.put(boyStudent.id,boyStudent.time_of_birtсh);
            boy += 1;
        }
        Iterator<Student> studentIterator = students.iterator();
        while (studentIterator.hasNext())
        {
            Student student=studentIterator.next();
            if (student.getClass()==BoyStudent.class)
            {
                if (second-student.time_of_birtсh==BoyStudent.time_of_life)
                {
                    AnchorPane.getChildren().remove(student.getImageView());
                    studentHashSet.remove(student.id);
                    studentsTime.remove(student.id);
                    studentIterator.remove();
                }
            }
            if (student.getClass()==GirlStudent.class)
                if ((second-student.time_of_birtсh)==GirlStudent.time_of_life)
                {
                    AnchorPane.getChildren().remove(student.getImageView());
                    studentHashSet.remove(student.id);
                    studentsTime.remove(student.id);
                    studentIterator.remove();
                }
        }
    }
    public void show_btn()
    {
        RBtnYes.setToggleGroup(group);
        RBtnNo.setToggleGroup(group);
        if (RBtnYes.isSelected())
        {
            LabelTime.setVisible(true);
            return;
        }
        if (RBtnNo.isSelected())
        {
            LabelTime.setVisible(false);
            return;
        }
    }

    public void show()
    {
        if (LabelTime.isVisible())
        {
            RBtnNo.setSelected(true);
            RBtnYes.setSelected(false);
            LabelTime.setVisible(false);
            return;
        }
        else
        {
            RBtnYes.setSelected(true);
            RBtnNo.setSelected(false);
            LabelTime.setVisible(true);
            return;
        }

    }

    public void show_inf()
    {
        TxArea.setVisible(true);
        TxArea.setText(" Результат симуляции \n Прошедшее время - " + second + " c \n Количество мальчиков - " + boy + "\n Количество девочек - " + girl) ;
    }

    public void dntshow_inf()
    {
        TxArea.setVisible(false);
    }

    public void show_life_obj()
    {
        timer.cancel();
        moveTimerBoy.cancel();
        moveTimerGirl.cancel();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Диалоговое окно");
        alert.setHeaderText("Id живых объектов и время их рождения");
        TextArea textArea = new TextArea(
                studentsTime
                        .entrySet()
                        .stream()
                        .map(x -> x.toString()+"\n")
                        .reduce("",(x,y) -> x+y)
        );
        textArea.setEditable(false);
        textArea.setFont(new Font("Calibri", 20));
        textArea.setPrefSize(400, 300);
        alert.getDialogPane().setContent(textArea);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get()==ButtonType.OK)
        {
            start();
        }
        if (result.get()==ButtonType.CANCEL)
        {
            end();
        }
    }
    public void stop_boy_move()
    {
        if(boy_check%2!=0)
        {
            moveTimerBoy.cancel();
            BBoy_move.setText("Включить дв. мальчиков");
        }
        else
        {
            start_boy();
            BBoy_move.setText("Выключить дв. мальчиков");
        }

        boy_check++;
    }
    public void stop_girl_move()
    {
        if(girl_check%2!=0)
        {
            BGirl_move.setText("Включить дв. девочек");
            moveTimerGirl.cancel();
        }
        else
        {
            start_girl();
            BGirl_move.setText("Выключить дв. девочек");
        }
        girl_check++;
        ServerThread.serverSends.get(0).string_check="wdd";
        ServerThread.serverRecives.get(0).flag=false;
        ServerThread.serverSends.remove(0);
        ServerThread.serverRecives.remove(0);
    }
    @FXML
    public AnchorPane AnchorPane;
    @FXML
    private Label LabelTime;
    @FXML
    private Label LabelBoys;
    @FXML
    private Label LabelGirls;
    @FXML
    private Button BtnStart;
    @FXML
    private Button BtnStop;
    @FXML
    private Button BBoy_move;
    @FXML
    private Button BGirl_move;
    @FXML
    private Button BtnLifeObj;
    @FXML
    private Button BtnServ;
    @FXML
    private RadioButton RBtnYes;
    @FXML
    private RadioButton RBtnNo;
    @FXML
    private ComboBox <String> CBBoy=new ComboBox<>();
    @FXML
    private ComboBox <String> CBGirl=new ComboBox<>();
    @FXML
    private ComboBox <String> CBPriorityBoy=new ComboBox<>();
    @FXML
    private ComboBox <String> CBPriorityGirl=new ComboBox<>();
    @FXML
    private TextField TimeBoy;
    @FXML
    private TextField TimeGirl;
    @FXML
    private TextArea TxArea;
    @FXML
    private TextField TimeLifeBoy;
    @FXML
    private TextField TimeLifeGirl;
    ToggleGroup group = new ToggleGroup();

    private Timer timer;
    private Timer moveTimerBoy;
    private Timer moveTimerGirl;
    static   int  second=0;
    private  int  girl=0;
    private  int  boy=0;
    private int boy_check=1;
    private int girl_check=1;
    private String [] probabilities = {"10%","20%","30%","40%","50%","60%","70%","80%","90%","100%"};
    private String [] priority_boy = {"1","2","3","4","5","6","7","8","9","10"};
    private String [] priority_girl = {"1","2","3","4","5","6","7","8","9","10"};
    private String server_clients;

    private void  getp1()
    {
        if (CBBoy.getValue()!=null) {
            String text = CBBoy.getValue().toString();
            int startNum = 0;
            int endNum = text.length() - 1;
            text = text.substring(startNum, endNum);
            P1 = Double.parseDouble(text) / 100;
        }
        else
        {
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("Ошибка поле вероятности для мальчиков");
            alert1.setHeaderText("Выберите вероятность появления");
            alert1.setContentText("Установлены значения по умолчанию" );
            alert1.showAndWait();
            P1=0.5;
        }
    }
    private void getp2()
    {
        if (CBGirl.getValue()!=null)
        {
            String text = CBGirl.getValue().toString();
            int startNum = 0;
            int endNum = text.length() - 1;
            text = text.substring(startNum, endNum);
            P2 = Double.parseDouble(text) / 100;
        }
        else
        {
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("Ошибка поле вероятности для девочек");
            alert1.setHeaderText("Выберите вероятность появления");
            alert1.setContentText("Установлены значения по умолчанию" );
            alert1.showAndWait();
            P2=0.5;
        }
    }

    public void get_priority_boy()
    {
        if (CBPriorityBoy.getValue()!=null) {
            String text = CBPriorityBoy.getValue().toString();
            PRIO_BOY = Integer.parseInt(text);
        }
        else
        {
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("Ошибка поле приоретета для мальчиков");
            alert1.setHeaderText("Выберите приоритет");
            alert1.setContentText("Установлены значения по умолчанию" );
            alert1.showAndWait();
            PRIO_BOY = 5;
        }
    }

    public void get_priority_girl()
    {
        if (CBPriorityGirl.getValue()!=null) {
            String text = CBPriorityGirl.getValue().toString();
            PRIO_GIRL = Integer.parseInt(text);
        }
        else
        {
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("Ошибка поле приоритета для девочек");
            alert1.setHeaderText("Выберите приоритет появления");
            alert1.setContentText("Установлены значения по умолчанию" );
            alert1.showAndWait();
            PRIO_GIRL = 5;
        }
    }

    private static double N1;
    private static double N2;
    private static double P1;
    private static double P2;
    private static int PRIO_BOY;
    private static int PRIO_GIRL;

    static public List <Student> students = new ArrayList<Student>();
    private Set <Integer> studentHashSet = new HashSet<Integer>();
    private Map <Integer,Double> studentsTime=new TreeMap<>();
    GirlStudentThread girlthread;
    BoyStudentThread boythread;

    private double getRandomx(){
        return (int)(Math.random()*(AnchorPane.getWidth()-72));
    }
    private double getRandomy(){
        return (int)(Math.random()*(AnchorPane.getHeight()-72));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
      CBBoy.getItems().addAll(probabilities);
      CBGirl.getItems().addAll(probabilities);
      CBPriorityBoy.getItems().addAll(priority_boy);
      CBPriorityGirl.getItems().addAll(priority_girl);
    }
    public void TimeFix()
    {
        String text1,text2;
        text1 = TimeBoy.getText();
        text2=TimeGirl.getText();
        if (Pattern.matches("^[0-9]{1,3}$", text1))
            N1 = Double.parseDouble(text1);
        else {
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("Ошибка поле времени для мальчиков");
            alert1.setHeaderText("Промежутки времени должны быть числами до 1000");
            alert1.setContentText("Установлены значения по умолчанию" );
            alert1.showAndWait();
            N1=2;
        }
        if (Pattern.matches("^[0-9]{1,3}$", text2))
            N2 = Double.parseDouble(text2);
        else
        {
            Alert alert2 = new Alert(Alert.AlertType.ERROR);
            alert2.setTitle("Ошибка поле времени для девочек");
            alert2.setHeaderText("Промежутки времени должны быть числами до 1000");
            alert2.setContentText("Установлены значения по умолчанию" );
            alert2.showAndWait();
            N2=2;
        }
    }
    public void LifeFix()
    {
        String text1,text2;
        text1 = TimeLifeBoy.getText();
        text2=TimeLifeGirl.getText();
        if (Pattern.matches("^[0-9]{1,3}$", text1))
            BoyStudent.time_of_life = Double.parseDouble(text1);
        else {
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("Ошибка поле время жизни для мальчиков");
            alert1.setHeaderText("Промежутки времени должны быть числами до 1000");
            alert1.setContentText("Установлены значения по умолчанию" );
            alert1.showAndWait();
            BoyStudent.time_of_life=5;
        }
        if (Pattern.matches("^[0-9]{1,3}$", text2))
            GirlStudent.time_of_life = Double.parseDouble(text2);
        else
        {
            Alert alert2 = new Alert(Alert.AlertType.ERROR);
            alert2.setTitle("Ошибка поле времени жизни для девочек");
            alert2.setHeaderText("Промежутки времени должны быть числами до 1000");
            alert2.setContentText("Установлены значения по умолчанию" );
            alert2.showAndWait();
            GirlStudent.time_of_life=5;
        }
    }
}
