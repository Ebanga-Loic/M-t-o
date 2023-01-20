package com.code.OpenWeather;

import java.awt.EventQueue;
import javax.swing.JFrame;

public class Application {

  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
      @Override
      public void run() {
        MainFrame mainFrame = new MainFrame("weatho");
        mainFrame.setResizable(false);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
      }
    });

  }

}
