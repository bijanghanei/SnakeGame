import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {
    final int[] x = new int[Configs.GAME_UNITS];
    final int[] y = new int[Configs.GAME_UNITS];
    int bodyParts = 6;
    int applesEaten;
    int appleX;
    int appleY;
    char direction = 'U';
    boolean running = false;
    Timer timer;
    Random random;
    GamePanel(){
        random = new Random();
        this.setPreferredSize(new Dimension(Configs.SCREEN_WIDTH, Configs.SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter(direction));
        startGame();
    }
    public void gameOver(Graphics g){
        this.setBackground(Color.red);
        //Score
        g.setColor(Color.white);
        g.setFont(new Font("Arial",Font.PLAIN,40));
        FontMetrics fontMetrics1 = getFontMetrics(g.getFont());
        g.drawString("Score: "+applesEaten,(Configs.SCREEN_WIDTH-fontMetrics1.stringWidth("Score: "+applesEaten)),g.getFont().getSize());
        //Game Over
        g.setColor(Color.white);
        g.setFont(new Font("Arial",Font.BOLD,50));
        FontMetrics fontMetrics2 = getFontMetrics(g.getFont());
        g.drawString("GAME OVER",(Configs.SCREEN_WIDTH-fontMetrics2.stringWidth("GAME OVER")),g.getFont().getSize());
            }
    public void checkCollision(){

    }
    public void checkApple(){

    }
    public void move(){

    }
    public void newApple(){
        appleX = random.nextInt(Configs.SCREEN_WIDTH/Configs.UNIT_SIZE)*Configs.UNIT_SIZE;
        appleY = random.nextInt(Configs.SCREEN_HEIGHT/Configs.UNIT_SIZE)*Configs.UNIT_SIZE;
    }
    public void startGame(){
        newApple();
        running = true;
        timer = new Timer(Configs.DELAY,this);
        timer.start();
    }
    public void draw(Graphics g){
        if(running) {
            for (int i = 0; i < Configs.SCREEN_HEIGHT / Configs.UNIT_SIZE; i++) {
                g.drawLine(i * Configs.UNIT_SIZE, 0, i * Configs.UNIT_SIZE, Configs.SCREEN_HEIGHT);
                g.drawLine(0, i * Configs.UNIT_SIZE, Configs.SCREEN_WIDTH, i * Configs.UNIT_SIZE);
            }
//            g.drawOval(appleX,appleY,Configs.UNIT_SIZE,Configs.UNIT_SIZE);
            g.setColor(Color.red);
            g.fillOval(appleX,appleY,Configs.UNIT_SIZE,Configs.UNIT_SIZE);
        }
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    @Override
    public void actionPerformed(ActionEvent e) {

    }
}

