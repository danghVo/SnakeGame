import java.awt.Color;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
public class GameFrame extends JFrame{
    public GameFrame(){
        this.add(new SnakeGame()); 
        this.pack();
        this.setVisible(true);
        this.setBackground(Color.BLACK);
        this.setTitle("SNAKE GAME");
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
    }

 
            
    }
