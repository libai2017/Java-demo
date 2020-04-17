package com.tarena.shoot;
import java.awt.Component;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Graphics;

import java.util.Timer;
import java.util.TimerTask;

import java.util.Arrays;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.awt.Color;
import java.awt.Font;

//��������
public class ShootGame extends JPanel {
	public static final int WIDTH = 400;  //���ڿ�
	public static final int HEIGHT = 654; //���ڸ�
	
	public static final int START = 0;    //����״̬
	public static final int RUNNING = 1;  //����״̬
	public static final int PAUSE = 2;    //ֹͣ״̬
	public static final int GAME_OVER = 3;//����״̬
	private int state = START;  //��ǰ״̬
	
	public static BufferedImage background;
	public static BufferedImage start;
	public static BufferedImage pause;
	public static BufferedImage gameover;
	public static BufferedImage airplane;
	public static BufferedImage bee;
	public static BufferedImage bullet;
	public static BufferedImage hero0;
	public static BufferedImage hero1;
	
	private Hero hero = new Hero();
	private FlyingObject[] flyings = {}; //��������
	private Bullet[] bullets = {}; //�ӵ�����
	
	static { //��ʼ����̬��Դ(ͼƬ)
		try {
			background = ImageIO.read(ShootGame.class.getResource("background.png"));
			start= ImageIO.read(ShootGame.class.getResource("start.png"));
			pause = ImageIO.read(ShootGame.class.getResource("pause.png"));
			gameover = ImageIO.read(ShootGame.class.getResource("gameover.png"));
			airplane = ImageIO.read(ShootGame.class.getResource("airplane.png"));
			bee = ImageIO.read(ShootGame.class.getResource("bee.png"));
			bullet = ImageIO.read(ShootGame.class.getResource("bullet.png"));
			hero0 = ImageIO.read(ShootGame.class.getResource("hero0.png"));
			hero1 = ImageIO.read(ShootGame.class.getResource("hero1.png"));
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	// ������ɵ��˶���
	public FlyingObject nextOne() {
		Random rand = new Random();
		int type = rand.nextInt(2);
		if(type == 0) {
			return new Bee();
		}else {
			return new Airplane();
		}
	}
	
	
	// �����볡
	int flyEnteredIndex = 0;
	public void enterAction() {
		//���ɵ��˶��󣬽�������ӵ�flyings������
		flyEnteredIndex++;
		if(flyEnteredIndex%40==0) {
			FlyingObject one = nextOne();
			flyings = Arrays.copyOf(flyings, flyings.length+1);
			flyings[flyings.length-1] = one;
		}
	}
	// ��������һ��
	public void stepAction() {
		hero.step();
		for(int i=0; i<flyings.length; i++) {
			flyings[i].step();
		}
		for(int i=0; i<bullets.length; i++) {
			bullets[i].step();
		}
	}
	// �ӵ��볡---Ӣ�ۻ������ӵ�
	int shootIndex = 0;
	public void shootAction() {
		shootIndex++;
		if(shootIndex%30 == 0) {
			Bullet[] bs = hero.shoot();
//			System.arraycopy(bs,0, bullets, bullets.length-bs.length, bs.length);
			bullets = Arrays.copyOf(bullets, bullets.length+bs.length);
			for(int i=0; i<bs.length; i++) {
				bullets[bullets.length-bs.length+i] = bs[i];
			}
		}
	}
	
	// ɾ��Խ��ĵ���,,�л���С�۷䣬�ӵ�
	public void outOfBoundsAction() {
		// ɾ��Խ��ĵл���С�۷�
		int index = 0;
		FlyingObject[] flyingLives = new FlyingObject[flyings.length];
		for(int i=0; i<flyings.length; i++) {
			if(!flyings[i].outOfBounds()) {
				flyingLives[index] = flyings[i];
				index++;
			}
		}
		flyings = Arrays.copyOf(flyingLives, index);
		
		// ɾ��Խ����ӵ�
		index = 0;
		Bullet[] bulletLives = new Bullet[bullets.length];
		for(int i=0; i<bullets.length; i++) {
			if(!bullets[i].outOfBounds()) {
				bulletLives[index] = bullets[i];
				index++;
			}
		}
		bullets = Arrays.copyOf(bulletLives, index);
	}
	
	// �����ӵ������е���(�л�+С�۷�)����ײ
	public void bangAction() {
		for(int a=0; a<bullets.length; a++) {
			bang(a);
		}
	}
	// һ���ӵ������е��˵���ײ
	int score = 0;
	public void bang(int a) {
		Bullet b = bullets[a];
		int bangPoint = -1;
		for(int i=0; i<flyings.length; i++) {
			if(flyings[i].shootBy(b)) {
				bangPoint = i;
				break;
			}
		}
		if(bangPoint != -1) {
			FlyingObject obj = flyings[bangPoint];
			if(obj instanceof Enemy) {
				Enemy one = (Enemy)obj;
				score += one.getScore();
			}
			if(obj instanceof Award) {
				Award two = (Award)obj;
				int type = two.getType();
				switch(type) {
				case Award.DOUBLE_FILE:
					hero.addDoubleFire();
					break;
				case Award.LIFE:
					hero.addLife();
					break;
				}
			}
			FlyingObject l = flyings[flyings.length-1];  //���һ������(�л���С�۷�)
			flyings[bangPoint] = l;                      //�ŵ���ײ�����λ����
			flyings = Arrays.copyOf(flyings, flyings.length-1); //ֱ������
			
			Bullet ll = bullets[bullets.length-1];  //���һ���ӵ�
			bullets[a] = ll;                        //�ŵ���ײ�ӵ���λ����
			bullets = Arrays.copyOf(bullets, bullets.length-1); //ֱ������
		}
	}
	
	// �����Ϸ�Ƿ����
	public void checkGameOverAction() {
		for(int i=0; i<flyings.length; i++) {
			if(hero.hit(flyings[i])) {
				//��ײ
				hero.reduceLife();
				hero.zeroDoubleFire();
				if(hero.getLife() <= 0) {
					state = GAME_OVER;
				}
				FlyingObject l = flyings[flyings.length-1];  //���һ������(�л���С�۷�)
				flyings[i] = l;                              //�ŵ���ײ�����λ����
				flyings = Arrays.copyOf(flyings, flyings.length-1); //ֱ������
			}
		}
	}
	int num=0;
	public void action() {
		MouseAdapter l = new MouseAdapter() {
			// ����ƶ��¼�
			public void mouseMoved(MouseEvent e) {
				if(state == RUNNING) {
					hero.moveTo(e.getX(), e.getY());
				}
			}
			// ������¼�
			public void mouseClicked(MouseEvent e) {
				switch(state) { //���ݲ�ͬ��״̬����ͬ�Ĵ���
				case START:  //����״̬ʱ��Ϊ����״̬
					state = RUNNING;
					break;
				case GAME_OVER: //��Ϸ����״̬ʱ��Ϊ����״̬
					score = 0;  //�������
					hero = new Hero();
					flyings = new FlyingObject[0];
					bullets = new Bullet[0];
					state = START;
					break;
				}
			}
			//��������¼�
			public void mouseEntered(MouseEvent e) {
				if(state == PAUSE) {
					state = RUNNING;
				}
			}
			
			//����Ƴ��¼�
			public void mouseExited(MouseEvent e) {
				if(state == RUNNING) {
					state = PAUSE;
				}
			}
		};
		this.addMouseListener(l); //�����������¼�
		this.addMouseMotionListener(l); //������껬���¼�
		
		Timer timer = new Timer(); //������ʱ������
		int interval = 10; //ʱ���� (�Ժ���Ϊ��λ)
		timer.schedule(new TimerTask() {
			public void run() {
				if(state == RUNNING) {
					enterAction();
					stepAction();
					shootAction();
					outOfBoundsAction(); //ɾ��Խ��ĵ���
					bangAction(); //�ӵ��͵�����ײ
				}
				checkGameOverAction();  //Ӣ�ۻ��͵��˵���ײ
				repaint();
			}
		}, interval,interval);
	}

	
//	��дpaint()  g:����
	public void paint(Graphics g) {
		g.drawImage(background,0, 0, null);
		paintHero(g);
		paintFlyingObjects(g);
		paintBullets(g);
		paintScoreandLife(g);
		paintState(g); //��״̬
	}
	//��Ӣ�ۻ�����
	public void paintHero(Graphics g) {
		g.drawImage(hero.image, hero.x, hero.y,null);
	}
	//�����˶���
	public void paintFlyingObjects(Graphics g) {
		for(int i=0; i<flyings.length; i++) {
			g.drawImage(flyings[i].image,flyings[i].x,flyings[i].y,null);			
		}
	}
	//���ӵ�����
	public void paintBullets(Graphics g) {
		for(int i=0; i<bullets.length; i++) {
			g.drawImage(bullets[i].image, bullets[i].x, bullets[i].y, null);
		}
	}
	
	//���ֺ͵���
	public void paintScoreandLife(Graphics g) {
		g.setColor(new Color(0xFF0000)); //���û��ʺ�ɫ
		g.setFont(new Font(Font.SANS_SERIF, Font.BOLD,24)); //����������ʽ
		g.drawString("SCORE: "+score, 10, 25);
		g.drawString("LIFE: "+hero.getLife(), 10, 45);
	}
	
	public void paintState(Graphics g) {
		switch(state) { //����״̬�Ĳ�ͬ������ͬ��ͼƬ
		case START:
			g.drawImage(start, 0, 0, null);
			break;
		case PAUSE:
			g.drawImage(pause, 0, 0, null);
			break;
		case GAME_OVER:
			g.drawImage(gameover, 0, 0, null);
			break;
		}
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame("Fly"); //����
		ShootGame game = new ShootGame(); //���
		frame.add(game); //�������ӵ�������
		
		frame.setSize(WIDTH, HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true); //1.���ô��ڿɼ���2.�������paint()����
		
		game.action(); //���������ִ��
	}
}
