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
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;
    GamePanel(){
        random = new Random();
        this.setPreferredSize(new Dimension(Configs.SCREEN_WIDTH, Configs.SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }
    public void gameOver(Graphics g){
        this.setBackground(Color.red);
        //Score
        g.setColor(Color.white);
        g.setFont(new Font("Arial",Font.PLAIN,40));
        FontMetrics fontMetrics1 = getFontMetrics(g.getFont());
        g.drawString("Score: "+applesEaten,(Configs.SCREEN_WIDTH-fontMetrics1.stringWidth("Score: "+applesEaten))/2,g.getFont().getSize());
        //Game Over
        g.setColor(Color.white);
        g.setFont(new Font("Arial",Font.BOLD,50));
        FontMetrics fontMetrics2 = getFontMetrics(g.getFont());
        g.drawString("GAME OVER",(Configs.SCREEN_WIDTH-fontMetrics2.stringWidth("GAME OVER"))/2,Configs.SCREEN_HEIGHT/2);
    }
    public void checkCollision(){
        for (int i = bodyParts; i>0; i--){
            if ((x[0] == x[i]) && (y[0] == y[i])) {
                running = false;
                timer.stop();
            }
        }
        if (x[0] > Configs.SCREEN_WIDTH){
            x[0] = 0;
        }
        if (x[0] < 0){
            x[0] = Configs.SCREEN_WIDTH;
        }
        if (y[0] > Configs.SCREEN_HEIGHT){
            y[0] = 0;
        }
        if (y[0] < 0){
            y[0] = Configs.SCREEN_HEIGHT;
        }
    }
    public void checkApple(){
        if (running){
            if (appleX == x[0] && appleY == y[0]){
                bodyParts++;
                applesEaten++;
                newApple();
            }
        }
    }
    public void move(){
        for (int i = bodyParts; i>0; i--){
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        switch (direction){
            case 'L':
                x[0] -= Configs.UNIT_SIZE;
                break;
            case 'U':
                y[0] -= Configs.UNIT_SIZE;
                break;
            case 'R':
                x[0] += Configs.UNIT_SIZE;
                break;
            case 'D':
                y[0] += Configs.UNIT_SIZE;
                break;
        }


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

            for (int i = 0; i<bodyParts; i++){
                g.setColor(Color.green);
                g.fillRect(x[i],y[i],Configs.UNIT_SIZE,Configs.UNIT_SIZE);
            }
            g.setColor(Color.white);
            g.setFont(new Font("Arial",Font.BOLD,40));
            FontMetrics fontMetrics = getFontMetrics(g.getFont());
            g.drawString("Score: "+applesEaten,(Configs.SCREEN_WIDTH-fontMetrics.stringWidth("Score: "+applesEaten))/2,g.getFont().getSize());

        }else{
            gameOver(g);
        }
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (running){
            move();
            checkApple();
            checkCollision();
        }
        repaint();
    }
    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()){
                case KeyEvent.VK_LEFT:
                    if (direction != 'R'){
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (direction != 'L'){
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (direction != 'U'){
                        direction = 'D';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (direction != 'D'){
                        direction = 'U';
                    }
                    break;
            }
        }
    }
}

