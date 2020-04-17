package com.tarena.shoot;
import java.util.Random;

//�л�
public class Airplane extends FlyingObject implements Enemy{
	private int speed = 2;  //�ߵĲ���
	
	public Airplane() {
		image = ShootGame.airplane;
		width = image.getWidth();  //��ȡͼƬ�Ŀ�
		height = image.getHeight(); //��ȡͼƬ�ĸ�
		Random rand = new Random(); //���������
		x = rand.nextInt(ShootGame.WIDTH-this.width+1);
		y = -this.height;
	}
	public int getScore() {
		return 5;  //���һ���л���5��
	}
	
	// ��д
	public void step() {
		y+=speed;
	}
	
	// ��д�Ƿ�Խ�纯��
	public boolean outOfBounds() {
		if(y>ShootGame.HEIGHT) {
			return true;
		}else {
			return false;
		}
	}
	
	// ���˱��ӵ����
	public boolean shootBy(Bullet bullet) {
		int x1 = this.x;
		int x2 = this.x + this.width;
		int y1 = this.y;
		int y2 = this.y + this.height;
		// x�� x1 �� x2 ֮�䣬y��y1��y2֮�䣬������ײ��
		if(bullet.x<x2 && bullet.x>x1 && bullet.y<y2 && bullet.y>y1) {
			return true;
		}else {
			return false;
		}
	}
}
