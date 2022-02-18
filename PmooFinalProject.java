package pmoofinalproject;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.Timer;

/**
 * @author Chacrit
 */
public class PmooFinalProject extends JPanel implements ActionListener,
        MouseListener ,MouseMotionListener{

    static int border = 50;
    static int boardSize = 700;
    Timer timer = new Timer(17,this);//60fps = 1sec/60frame
    static boolean pause = true;
    //static boolean win = false;
    
    static int playerSize = 30;
    static int player_x = 0;
    static int player_y = 0;
    //static float movespeed = 10f;

    static int score = 0;
    
    static int enemySize = 15;
    static int enemyspeed = 20;
    static int[] enem1 = {0,0};
    static int[] enem2 = {0,0};
    static int[] enem3 = {0,0};
    static int[] enem4 = {0,0};
    static int[] enem5 = {0,0};

    static int preyspeed = 5;
    static int[] prey1 = {0,0};
    static int[] prey2 = {0,0};
    static int[] prey3 = {0,0};

    public static void main(String[] args) {
        JFrame jf = new JFrame("Chase And Run");
        
        jf.setSize(boardSize+100,boardSize+100);
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        setStartPosition();
        
        jf.add(new PmooFinalProject());
    }
    
    PmooFinalProject(){
        this.setBackground(Color.BLACK);
        addMouseMotionListener(this);
        addMouseListener(this);
        setFocusable(true);
        
        StartAndPause();
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
        //enemy move
        enemyMove(enem1);
        enemyMove(enem2);
        enemyMove(enem3);
        enemyMove(enem4);
        enemyMove(enem5);
        //prey move
        preyMove(prey1);
        preyMove(prey2);
        preyMove(prey3);
        //update
        repaint();
    }
    
    @Override
    public void mouseMoved(MouseEvent e){
        if(pause){
            player_x = e.getX();
            player_y = e.getY();
        }
    }
    @Override
    public void mouseDragged(MouseEvent e){
        ///there is nothing here
    }
    @Override
    public void mousePressed(MouseEvent e){
        if((e.getX() < player_x + 20
                && e.getX() > player_x - 20)
                &&
                (e.getY() < player_y + 20
                && e.getY() > player_y - 20)){
            
            StartAndPause();
        }
    }
    @Override
    public void mouseReleased(MouseEvent e){
        ///there is nothing here
    }
    @Override
    public void mouseClicked(MouseEvent e){
        ///there is nothing here
    }
    @Override
    public void mouseEntered(MouseEvent e){
        ///there is nothing here
    }
    @Override
    public void mouseExited(MouseEvent e){
        ///there is nothing here
    }
    
    void StartAndPause(){
        if(pause){
            pause = false;
            timer.stop();
        } else {
            pause = true;
            timer.start();
        }
    }
    
    
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        //create field
        g.setColor(Color.WHITE);
        g.fillRect(border, border, boardSize, boardSize);
        //hint
        g.setColor(Color.BLACK);
        Font hintFont = new Font("Sanserif", Font.BOLD, 14);
        g.setFont(hintFont);
        g.drawString("Click black circle to play/pause game", 60, 70);
        g.setColor(Color.GRAY);
        g.drawString("Collect BLUE to get a score and try to avoid RED", 60, 90);
        //score
        g.setColor(Color.WHITE);
        Font scoreFont = new Font("Sanserif", Font.BOLD, 45);
        g.setFont(scoreFont);
        g.drawString("SCORE : ", boardSize/2 - 100, 40);
        g.drawString(String.valueOf(score), boardSize/2 + 100, 40);
        
        //create player
        g.setColor(Color.BLACK);
        g.fillOval(player_x - (int)(playerSize/2),
                player_y  - (int)(playerSize/2)
                , playerSize, playerSize);
        
        //spawn enemy
        spawnEnemy(g,enem1);
        spawnEnemy(g,enem2);
        spawnEnemy(g,enem3);
        spawnEnemy(g,enem4);
        spawnEnemy(g,enem5);
        //spawn prey
        spawnPrey(g,prey1);
        spawnPrey(g,prey2);
        spawnPrey(g,prey3);
    }
    
    static void setStartPosition(){
        //player start position
        player_x = (int)(boardSize/2) + border - (int)(playerSize/2);
        player_y = (int)(boardSize/2) + border - (int)(playerSize/2);
        //enemy position
        int distanceToPlayer = 200;
        for (int i = 0; i < enem1.length; i++) enem1[i] = RandomPos(distanceToPlayer);
        for (int i = 0; i < enem2.length; i++) enem2[i] = RandomPos(distanceToPlayer);
        for (int i = 0; i < enem3.length; i++) enem3[i] = RandomPos(distanceToPlayer);
        for (int i = 0; i < enem4.length; i++) enem4[i] = RandomPos(distanceToPlayer);
        for (int i = 0; i < enem5.length; i++) enem5[i] = RandomPos(distanceToPlayer);
        //prey position
        for (int i = 0; i < prey1.length; i++) prey1[i] = RandomPos(0);
        for (int i = 0; i < prey2.length; i++) prey2[i] = RandomPos(0);
        for (int i = 0; i < prey3.length; i++) prey3[i] = RandomPos(0);
    }
    
    void spawnEnemy(Graphics g, int[] pos){
        g.setColor(Color.RED);
        g.fillOval(pos[0] + border,
                pos[1] + border, 
                enemySize, enemySize);
    }
    
    void spawnPrey(Graphics g, int[] pos){
        g.setColor(Color.BLUE);
        g.fillOval(pos[0] + border,
                pos[1] + border, 
                enemySize, enemySize);
    }
    
    static int RandomPos(int distanceToPlayer){
        Random rand = new Random();
        int result = rand.nextInt(boardSize - 1) + 1;
        while(result - player_x < distanceToPlayer 
                && result - player_x > -distanceToPlayer 
                ||
                result - player_y < distanceToPlayer 
                && result - player_y > -distanceToPlayer){
            result = rand.nextInt(boardSize - 1) + 1;
        }
        return result;
    }
    
    static int EnemyRandomSpeed(){
        Random rand = new Random();
        return rand.nextInt(enemyspeed*2) - rand.nextInt(enemyspeed);
    }
    static int preyRandomSpeed(){
        Random rand = new Random();
        return rand.nextInt(preyspeed) - rand.nextInt(preyspeed*2);
    }
    
    static int[] enemyMove(int[] pos){
        int playerGraphicPosition = playerSize*2;
        if(pos[0] >= player_x - playerGraphicPosition) 
            pos[0] -= EnemyRandomSpeed();
        if(pos[0] <= player_x - playerGraphicPosition) 
            pos[0] += EnemyRandomSpeed();
        if(pos[1] >= player_y - playerGraphicPosition) 
            pos[1] -= EnemyRandomSpeed();
        if(pos[1] <= player_y - playerGraphicPosition) 
            pos[1] += EnemyRandomSpeed();
        
        //hit player
        if((pos[0] - player_x + playerGraphicPosition < 15
                && pos[0] - player_x + playerGraphicPosition > -15)
                &&
                (pos[1] - player_y + playerGraphicPosition < 15
                && pos[1] - player_y + playerGraphicPosition > -15)){
            for (int i = 0; i < pos.length; i++) pos[i] = RandomPos(100);
            score--;
        }
        
        return pos;
    }
    
    static int[] preyMove(int[] pos){
        int speed = preyRandomSpeed();
        int playerGraphicPosition = playerSize*2;
        if(pos[0] >= player_x - playerGraphicPosition) 
            pos[0] -= speed;
        if(pos[0] <= player_x - playerGraphicPosition) 
            pos[0] += speed;
        if(pos[1] >= player_y - playerGraphicPosition) 
            pos[1] -= speed;
        if(pos[1] <= player_y - playerGraphicPosition) 
            pos[1] += speed;
        
        //hit border
        if(pos[0] < 0) pos[0] = RandomPos(0);
        if(pos[1] < 0) pos[1] = RandomPos(0);
        if(pos[0] > boardSize) pos[0] = RandomPos(0);
        if(pos[1] > boardSize) pos[1] = RandomPos(0);
        
        //hit player
        if((pos[0] - player_x + playerGraphicPosition < 20
                && pos[0] - player_x + playerGraphicPosition > -20)
                &&
                (pos[1] - player_y + playerGraphicPosition < 20
                && pos[1] - player_y + playerGraphicPosition > -20)){
            
            for (int i = 0; i < pos.length; i++) pos[i] = RandomPos(100);
            if(score < 100){
                score++;
            }else{
                //win = true;
            }
        }
        return pos;
    }
}
