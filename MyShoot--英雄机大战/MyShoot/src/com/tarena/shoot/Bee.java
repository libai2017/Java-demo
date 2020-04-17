package com.tarena.shoot;

import java.util.Random;

public class Bee extends FlyingObject implements Award{
	private int xSpeed = 1; //X�����ٶ�
	private int ySpeed = 2; //Y�����ٶ�
	private int awardType;  //���������� 0/1
	
	public Bee() {
		image = ShootGame.bee;
		width = image.getWidth();
		height = image.getHeight();
		Random rand = new Random();
		x = rand.nextInt(ShootGame.WIDTH - this.width+1);
		y = -this.height;
		awardType = rand.nextInt(2); //0��1֮��
		
		int xSymbol = rand.nextInt(2);
		if(xSymbol == 0) {
			xSpeed = -xSpeed;
		}
	}
	public int getType() {
		return awardType;   //���ؽ������� 0/1
	}
	
	// ��д
	public void step() {
		y+=ySpeed;
		x+=xSpeed;
		if(x>ShootGame.WIDTH-this.width || x<=0) {
			xSpeed = -xSpeed;
		}
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
