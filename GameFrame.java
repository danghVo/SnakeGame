import java.awt.Color;
import javax.swing.JFrame;
public class GameFrame extends JFrame{
    JFrame frame;
    public GameFrame(){
        frame= new JFrame();
        frame.add(new SnakeGame(frame)); 
        frame.pack();
        frame.setVisible(true);
        frame.setBackground(Color.BLACK);
        frame.setTitle("SNAKE GAME");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
    }

 
            
    }
