package pl.kkisiel.java;

import javax.swing.JFrame;

public class App 
{
    public static void main( String[] args )
    {
    	MainWindow window = new MainWindow();
    	window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	window.setVisible(true);
    }
}
