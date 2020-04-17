package com.tarena.shoot;
import java.awt.image.BufferedImage;

//Ӣ�ۻ�
public class Hero extends FlyingObject {
	private int life;   //��
	private int doubleFire=0;   //����ֵ
	private BufferedImage[] images; //����ͼƬ�л�
	private int index; //Э��ͼƬ�л�
	
	public Hero() {
		image = ShootGame.hero0;
		width = image.getWidth();  //��ȡͼƬ�Ŀ�
		height = image.getHeight(); //��ȡͼƬ�ĸ�
		x = 150;  //x:��ʼֵ
		y = 400;  //y:��ʼֵ
		life = 3; //Ĭ��3����
		images = new BufferedImage[] {ShootGame.hero0,ShootGame.hero1};
		// ��ʼ��images������ͼƬ
		index = 0; //Э���л�
	}
	
	// ��д
	public void step() {
		index++;
		image = images[index/10%images.length];  //ÿ100�������л�һ��
	}
	// Ӣ�ۻ������ӵ�
	public Bullet[] shoot() {
		int xStep = this.width/4;
		int yStep = 20;
		if(doubleFire > 0) {
			Bullet[] bs = new Bullet[2];
			bs[0] = new Bullet(this.x+1*xStep,this.y-yStep);
			bs[1] = new Bullet(this.x+3*xStep,this.y-yStep);
			doubleFire -=2; //����һ��˫��������2 
			return bs;
		}else {
			Bullet[] bs = new Bullet[1];
			bs[0] = new Bullet(this.x+2*xStep,this.y-yStep);
			return bs;
		}
		
	}
	// Ӣ�ۻ���������ƶ�
	public void moveTo(int x, int y) {
		this.x = x - this.width/2;  //ʹ��Ӣ�ۻ����Ķ�׼�����
		this.y = y - this.height/2;
	}
	
	// ��д�Ƿ�Խ�纯��
	public boolean outOfBounds() {
		return false;  //����Խ��
	}
	
	// ����
	public void addLife() {
		life++;
	}
	// ������
	public int getLife() {
		return life;
	}
	
	public void reduceLife() {
		life--;
	}
	
	// �ӻ���
	public void addDoubleFire() {
		doubleFire += 40;
	}
	public void zeroDoubleFire() {
		doubleFire = 0;
	}
	
	// Ӣ�ۻ�ײ����,,true��ʾ��ײ
	public boolean hit(FlyingObject obj) {
		int x1 = this.x - this.width/2;
		int x2 = this.x + this.width/2;
		int y1 = this.y - this.height/2;
		int y2 = this.y + this.height/2;
		if(obj.x<x2 && obj.x>x1 && obj.y<y2 && obj.y>y1) {
			return true;
		}else {
			return false;
		}
		
	}
}
