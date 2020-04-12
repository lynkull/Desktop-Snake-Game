package snake;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.Timer;
/**
 *
 * @author eduar
 */
// https://www.youtube.com/watch?v=S_n3lryyGZM

public class Snake implements ActionListener, KeyListener{
    
    public static Snake snake;      // this is to be able to access Snake() from anywhere   (making a static instance of snake)
    public JFrame jframe;
    public RenderPanel renderPanel;
    public Timer timer = new Timer(20, this);
    public ArrayList<Point> snakeParts = new ArrayList<Point>();
    public static final int UP = 0, DOWN = 1, LEFT = 2, RIGHT = 3, SCALE = 10;
    public int ticks = 0, direction, score, tailLength, time;
    public Point head, cherry;
    public Random random;
    public boolean over = false, paused;
    public Dimension dim;
    
    public static void main(String args[]){
        snake = new Snake();    //call the snake in the main method
    }
    
    public Snake() {
        dim = Toolkit.getDefaultToolkit().getScreenSize();
      //  Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        jframe = new JFrame("Snake game"); // makes the object and sets the title 'Snake'
        jframe.setVisible(true);     //sets jframe visible
        jframe.setSize(805, 700);   // set size of jframe
      //  jframe.setResizable(false);
        jframe.setLocation(dim.width / 2 - jframe.getWidth() / 2, 
                dim.height / 2 - jframe.getHeight() / 2);       // centers the jframe into the center of the screen
        jframe.add(renderPanel = new RenderPanel());
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //operation ends when jframe is closed
        jframe.addKeyListener(this);
        startGame();
    }
    
    public void startGame(){
        over = false;
        paused = false;
        time = 0;
        score = 0;
        tailLength = 40;
        ticks = 0;
        direction = DOWN;
        head = new Point(0, 0);
        random = new Random();
        snakeParts.clear();
        cherry = new Point(random.nextInt(79), random.nextInt(66));
        timer.start(); 
    }
    
    //implements the snake class abstract methods
    @Override
    public void actionPerformed(ActionEvent arg0) {
//        System.out.print(cherry.x + ", " + cherry.y  + "\t\t\t\t\t\t");
//        System.out.println(head.x + ", " + head .y);
        
        renderPanel.repaint();  // it will repaint everytime the method is called
        ticks++;
        if(ticks % 2 == 0  &&  head != null  && !over && !paused)/* speed of snake : ticks % 2 ==0 */{
            time++;
            snakeParts.add(new Point(head.x, head.y));   // casted the object
            if(direction == UP)
                if(head.y - 1 >= 0 && noTailAt(head.x, head.y - 1))
                    head = new Point(head.x, head.y - 1);
                else
                    over = true;
            if(direction == DOWN)
                if(head.y + 1 < 67 && noTailAt(head.x, head.y + 1))
                    head = new Point(head.x, head.y + 1);
                else
                    over = true;
            if(direction == LEFT)
                if(head.x - 1 >= 0 && noTailAt(head.x - 1, head.y))
                    head = new Point(head.x -1, head.y);
                else
                    over = true;
            if(direction == RIGHT)
                if(head.x + 1 < 80 && noTailAt(head.x + 1, head.y))
                    head = new Point(head.x + 1, head.y);
                else
                    over = true;
            if(snakeParts.size() > tailLength){
                snakeParts.remove(0);
            }
            if(cherry != null){             // makes cherry
                if(head.equals(cherry)){
                    score+= 10;
                    tailLength++;
                    cherry.setLocation(random.nextInt(79), random.nextInt(66));  //random location in the map
                }
            }
        }
    }
    
    public boolean noTailAt(int x,int y){
        for(Point point: snakeParts){
            if(point.equals(new Point(x, y))){
                return false;
            }
        }
        return true;
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        int i = e.getKeyCode();
        if(i == KeyEvent.VK_A && direction != RIGHT)
            direction  = LEFT;
        if(i == KeyEvent.VK_D && direction != LEFT)
            direction  = RIGHT;
        if(i == KeyEvent.VK_W && direction != DOWN)
            direction  = UP;
        if(i == KeyEvent.VK_S && direction != UP)
            direction  = DOWN;
        if(i == KeyEvent.VK_SPACE)
            if(over)
                startGame();
            else
                paused = !paused;
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
