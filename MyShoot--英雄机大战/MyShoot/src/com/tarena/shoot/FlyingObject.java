package com.tarena.shoot;
import java.awt.image.BufferedImage;

//��������
public abstract class FlyingObject {
	protected BufferedImage image; //ͼƬ
	protected int width;  		   //��
	protected int height;		   //��
	protected int x;			   //����
	protected int y;
	
	// ��������һ��
	public abstract void step();
	
	//����Ƿ���磬������true��ʾ��Խ��
	public abstract boolean outOfBounds();
	
	public boolean shootBy(Bullet b) {
		return false;
	};
}
