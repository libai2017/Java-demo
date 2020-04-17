package com.tarena.shoot;

import java.util.Random;

public class Bullet extends FlyingObject{
	private int speed = 3;
	
	public Bullet(int x,int y) {
		image = ShootGame.bullet;
		width = image.getWidth();  //��ȡͼƬ�Ŀ�
		height = image.getHeight(); //��ȡͼƬ�ĸ�
		Random rand = new Random(); //���������
		this.x = x;  //x����Ӣ�ۻ�
		this.y = y;  //y����Ӣ�ۻ�
	}
	
	// ��д
	public void step() {
		y-=speed;
	}
	
	// ��д�Ƿ�Խ�纯��
	public boolean outOfBounds() {
		if(y<-this.height) {
			return true;
		}else {
			return false;
		}
	}
}
