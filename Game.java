
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Game extends JPanel implements KeyListener, ActionListener 
{
	private boolean isPlaying = false;
	private int score = 0;
	
	private int totalBricks = 21;
	
	private Timer timer;
	private int delay=8;
	
	private int paddleX = 310;
	
	private int ballX = 350;
	private int ballY = 495;
	private int ballXDir = -1;
	private int ballYDir = -3;
	
	private BrickPattern map;
	
	public Game()
	{		
		map = new BrickPattern(3, 7);
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
        timer=new Timer(delay,this);
		timer.start();
	}
	
	public void paint(Graphics g)
	{    		
		
		g.setColor(Color.black);
		g.fillRect(1, 1, 692, 592);
		
		
		map.draw((Graphics2D) g);
		
		
		g.setColor(Color.yellow);
		g.fillRect(0, 0, 3, 592);
		g.fillRect(0, 0, 692, 3);
		g.fillRect(691, 0, 3, 592);
		
		
		g.setColor(Color.white);
		g.setFont(new Font("serif",Font.BOLD, 25));
		g.drawString(""+score, 590,30);
		
		
		g.setColor(Color.green);
		g.fillRect(paddleX, 550, 100, 8);
		
		
		g.setColor(Color.yellow);
		g.fillOval(ballX, ballY, 20, 20);
	
	
		if(totalBricks <= 0)
		{
			 isPlaying = false;
             ballXDir = 0;
     		 ballYDir = 0;
             g.setColor(Color.RED);
             g.setFont(new Font("serif",Font.BOLD, 30));
             g.drawString("You Won", 260,300);
             
             g.setColor(Color.RED);
             g.setFont(new Font("serif",Font.BOLD, 20));           
             g.drawString("Press (Enter) to Restart", 230,350);  
		}
		
	
		if(ballY > 570)
        {
			 isPlaying = false;
             ballXDir = 0;
     		 ballYDir = 0;
             g.setColor(Color.RED);
             g.setFont(new Font("serif",Font.BOLD, 30));
             g.drawString("Game Over, Scores: "+score, 190,300);
             
             g.setColor(Color.RED);
             g.setFont(new Font("serif",Font.BOLD, 20));           
             g.drawString("Press (Enter) to Restart", 230,350);        
        }
		
		g.dispose();
	}	

	public void keyPressed(KeyEvent e) 
	{
		if (e.getKeyCode() == KeyEvent.VK_RIGHT)
		{        
			if(paddleX >= 600)
			{
				paddleX = 600;
			}
			else
			{
				movePaddleRight();
			}
        }
		
		if (e.getKeyCode() == KeyEvent.VK_LEFT)
		{          
			if(paddleX < 10)
			{
				paddleX = 10;
			}
			else
			{
				movePaddleLeft();
			}
        }		
		if (e.getKeyCode() == KeyEvent.VK_ENTER)
		{          
			if(!isPlaying)
			{
				isPlaying = true;
				ballX = 350;
				ballY = 495;
				ballXDir = -1;
				ballYDir = -5;
				paddleX = 310;
				score = 0;
				totalBricks = 21;
				map = new BrickPattern(3, 7);
				
				repaint();
			}
        }		
	}

	public void keyReleased(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}
	
	public void movePaddleRight()
	{
		isPlaying = true;
		paddleX+=20;	
	}
	
	public void movePaddleLeft()
	{
		isPlaying = true;
		paddleX-=20;	 	
	}
	
	public void actionPerformed(ActionEvent e) 
	{
		timer.start();
		if(isPlaying)
		{			
			if(new Rectangle(ballX, ballY, 20, 20).intersects(new Rectangle(paddleX, 550, 30, 8)))
			{
				ballYDir = -ballYDir;
				ballXDir = -2;
			}
			else if(new Rectangle(ballX, ballY, 20, 20).intersects(new Rectangle(paddleX + 70, 550, 30, 8)))
			{
				ballYDir = -ballYDir;
				ballXDir = ballXDir + 1;
			}
			else if(new Rectangle(ballX, ballY, 20, 20).intersects(new Rectangle(paddleX + 30, 550, 40, 8)))
			{
				ballYDir = -ballYDir;
			}
			
					
			A: for(int i = 0; i<map.brickMap.length; i++)
			{
				for(int j =0; j<map.brickMap[0].length; j++)
				{				
					if(map.brickMap[i][j] > 0)
					{
					
						int brickX = j * map.brickWidth + 80;
						int brickY = i * map.brickHeight + 50;
						int brickWidth = map.brickWidth;
						int brickHeight = map.brickHeight;
						
						Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);					
						Rectangle ballRect = new Rectangle(ballX, ballY, 20, 20);
						Rectangle brickRect = rect;
						
						if(ballRect.intersects(brickRect))
						{					
							map.setBrickValue(0, i, j);
							score+=5;	
							totalBricks--;
							
							
							if(ballX + 19 <= brickRect.x || ballX + 1 >= brickRect.x + brickRect.width)	
							{
								ballXDir = -ballXDir;
							}
						
							else
							{
								ballYDir = -ballYDir;				
							}
							
							break A;
						}
					}
				}
			}
			
			ballX += ballXDir;
			ballY += ballYDir;
			
			if(ballX < 0)
			{
				ballXDir = -ballXDir;
			}
			if(ballY < 0)
			{
				ballYDir = -ballYDir;
			}
			if(ballX > 670)
			{
				ballXDir = -ballXDir;
			}		
			
			repaint();		
		}
	}
}
