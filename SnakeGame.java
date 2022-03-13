import javax.swing.*;
import java.awt.event.*;
import java.util.Random;
import java.awt.*;
import javax.swing.JPanel;
import java.awt.event.MouseAdapter;

public class SnakeGame extends JPanel implements ActionListener{
    private static final int SCREEN_WIDTH=600, SCREEN_HEIGHT=600;
    private static final int UNIT_SIZE=25;
    private static final int GAME_UNIT=(SCREEN_HEIGHT*SCREEN_WIDTH)/UNIT_SIZE;
    private static final int DELAY=75;
    private final int x[]=new int[GAME_UNIT];
    private final int y[]=new int[GAME_UNIT];
    private int appleX, appleY;
    private int bodySnake=6;
    private int appleEaten=0;
    public char direction='D';
    public boolean running=true;
    public Timer timer;
    public Random random;
    public boolean menu=false;
    JFrame currentFrame;
    private boolean blank=false;
    public boolean over=false;
    
    public SnakeGame(JFrame frame){
        random= new Random();
        currentFrame=frame;
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        this.addMouseListener(new MouseHandler());
        this.addMouseMotionListener(new MouseHandler());
        menuOn();
    }
    public void menuOn(){
        menu=true; 
    }
    public void start(){
        menu=false;
        x[0]=0;
        y[0]=100;
        drawApple();
        running=true;
        timer=new Timer(DELAY, this);
        timer.start();
    }
    public void exit(){
        currentFrame.setVisible(false);
    }
    public void paintComponent(Graphics g){
        super.paintComponents(g);
        draw(g);
    }
    public void blank(){
        blank=true;
    }
    public void draw(Graphics g){
        if(blank){
            g.setColor(Color.black);
            g.fillRect(50, 50, 600, 700);
            blank=false;
        }
        if(menu){
            //Name
            g.setColor(Color.green);
            g.setFont(new Font("Time new roman",Font.BOLD,25));
            FontMetrics metrics3= getFontMetrics(g.getFont());
            g.drawString("Snake", (SCREEN_WIDTH - metrics3.stringWidth("Snake"))/2, 135);
            //Play button
            g.setColor(Color.gray);
            g.fillRect(200, 200, 200, 50);
            g.setColor(Color.black);
            g.setFont(new Font("Time new roman",Font.BOLD,25));
            FontMetrics metrics1= getFontMetrics(g.getFont());
            g.drawString("PLAY", (SCREEN_WIDTH - metrics1.stringWidth("PLAY"))/2, 235);
            //Exit button
            g.setColor(Color.gray);
            g.fillRect(200, 300, 200, 50);
            g.setColor(Color.black);
            g.setFont(new Font("Time new roman",Font.BOLD,25));
            FontMetrics metrics2= getFontMetrics(g.getFont());
            g.drawString("EXIT", (SCREEN_WIDTH - metrics2.stringWidth("EXIT"))/2, 335);
            
        }
        else if(running){
            g.setColor(Color.red);
            g.fillOval(appleX*UNIT_SIZE, appleY*UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);
            for(int i=0;i<=bodySnake;i++){
                if(i==bodySnake){
                    g.setColor(Color.BLACK);
                    g.fillRect(x[bodySnake], y[bodySnake], UNIT_SIZE, UNIT_SIZE);
                }
                else if(i==0){
                    g.setColor(Color.green);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
                else{
                    g.setColor(new Color(45,180,0));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
            g.setColor(Color.gray);
            g.fillRect(0, 0, SCREEN_WIDTH, 100);
            g.setColor(Color.red);
            g.setFont(new Font("Ink free",Font.BOLD,25));
            FontMetrics metrics1= getFontMetrics(g.getFont());
            g.drawString("Score: "+appleEaten, (SCREEN_WIDTH - metrics1.stringWidth("Score"+appleEaten))/2, 50);
        }
        else {
            gameOver(g);
        }
    }
    public void drawApple(){
        appleX=random.nextInt(SCREEN_WIDTH/UNIT_SIZE);
        appleY=random.nextInt(SCREEN_HEIGHT/UNIT_SIZE);
        if(appleY>=0 && appleY<4){
            appleY+=4;
        }
    }
    public void move(){
        for(int i=bodySnake;i>0;i--){
            x[i]=x[i-1];
            y[i]=y[i-1];
        }
        switch(direction){
            case 'U':{
                y[0]=y[0]-UNIT_SIZE;
                break;
            }
            case 'D':{
                y[0]=y[0]+UNIT_SIZE;
                break;
            }
            case 'R':{
                x[0]=x[0]+UNIT_SIZE;
                break;
            }
            case 'L':{
                x[0]=x[0]-UNIT_SIZE;
                break;
            }
        }
    }
    public void checkApple(){
        if(x[0]==appleX*UNIT_SIZE && y[0]==appleY*UNIT_SIZE){
            bodySnake++;
            appleEaten++;
            drawApple();
        }
    }
    public void checkFail(){
        //head collies with body
        for(int i=bodySnake;i>0;i--){
            if(x[i]==x[0] && y[i]==y[0])
                running=false;
        }
        //head collies Line
        if(x[0]<0 || x[0]==SCREEN_WIDTH || y[0]<100 || y[0]==SCREEN_HEIGHT)
            running=false;

        if(running==false)
            timer.stop();
    }
    public void gameOver(Graphics g){
        over=true;
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT+100);
        g.setColor(Color.red);
        g.setFont(new Font("Time New Roman",Font.BOLD,75));
        FontMetrics metrics= getFontMetrics(g.getFont());
        g.drawString("GAME OVER", (SCREEN_WIDTH - metrics.stringWidth("GAME OVER"))/2, SCREEN_HEIGHT/2-50);
        g.setColor(Color.gray);
        g.fillRect(200,350, 200, 50);
        g.setColor(Color.black);
        g.setFont(new Font("Time new roman",Font.BOLD,25));
        FontMetrics metrics3= getFontMetrics(g.getFont());
        g.drawString("PLAY AGAIN", (SCREEN_WIDTH - metrics3.stringWidth("PLAY AGAIN"))/2, 385);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
            if(running){
                move();
                checkApple();
                checkFail();
        }
            repaint();
    }

    public class MyKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){
            switch(e.getKeyCode()){
                case KeyEvent.VK_LEFT:{

                    if(direction!='R'){
                        direction='L';
                    }
                    break;
                }
                case KeyEvent.VK_RIGHT:{
                    if(direction!='L'){
                        direction='R';
                    }
                    break;
                }
                case KeyEvent.VK_UP:{
                    if(direction!='D'){
                        direction='U';
                    }
                    break;
                }
                case KeyEvent.VK_DOWN:{
                    if(direction!='U'){
                        direction='D';
                    }
                    break;
                }
            }
        }
    }

    public class MouseHandler extends MouseAdapter{
        public void mousePressed(MouseEvent e){
            int mx=e.getX();
            int my=e.getY();
            if((mx>200 && mx<400) && (my>200 && my<250)){
                blank();
                start();
            }
            if((mx>200 && mx<400) && (my>300 && my<450)){
                if(over){
                    blank();
                    start();
                }
                else exit();
            }
        }
    }

}




