package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/*
 * �����ҿͻ���
 * */
public class Client {
	/*
	 * �׽���
	 * java.net.Socket
	 * ��װ��TCPЭ�飬ʹ�����Ϳ��Ի���TCPЭ���������ͨѶ��
	 * Socket�������ڿͻ��˵�
	 * */
	private Socket socket;
	
	/*
	 * ���췽����������ʼ���ͻ���
	 * */
	public Client() throws Exception {
		/*
		 * ���췽����������ʼ���ͻ���
		 * ʵ����Socket��ʱ����Ҫ��������������
		 * 1����������ַ��ͨ��IP��ַ�����ҵ��������ļ����
		 * 2���������˿ڣ�ͨ���˿ڿ����ҵ�������������Ϸ����Ӧ�ó���
		 * ʵ����Socket�Ĺ��̾������ӵĹ��̣���Զ�̼����û����Ӧ���׳��쳣��
		 * */
		System.out.println("�������ӷ���ˡ�������");
		socket = new Socket("localhost",8088);
		System.out.println("�������˽�������");
		
	}
	
	/*
	 * �����ͻ��˵ķ���
	 * */
	public void start() {
		try {
			/*
			 * Socket�ṩ�ķ�����
			 * OutputStream getOutputStream
			 * ��ȡһ���ֽ��������ͨ������д�������ݻᱻ������Զ�˼������
			 * */

			OutputStream out = socket.getOutputStream();
			OutputStreamWriter osw = new OutputStreamWriter(out,"UTF-8");
			PrintWriter pw = new PrintWriter(osw,true);
			
			
			/*
			 * ������ȡ����˷��͹�����Ϣ���߳�
			 * */
			ServerHandler handler = new ServerHandler();
			Thread t = new Thread(handler);
			t.start();
			/*
			 * ���ַ��������������
			 * */
			
			Scanner scan = new Scanner(System.in);
			while(true) {
				System.out.print("�������ǳƣ�");
				String nick = scan.nextLine();
				if(nick.length()==0) {
					System.out.println("������������������!");
				}else {
					break;
				}
			}
			
			
			while(true) {
				System.out.print("˵��ʲô�ɣ�");
				String str = scan.nextLine();
				if("".equals(str)) {
					break;
				}
				pw.println(str);
			}
			

			
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				socket.close();
				System.out.println("���ӽ���");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		try {
			Client client = new Client();
			client.start();
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("�ͻ�������ʧ��!");
		}
	}
	
	/*
	 * ���߳�������ȡ����˷��͹�������Ϣ����������ͻ��˿���̨��ʾ��
	 * */
	class ServerHandler implements Runnable{
		public void run() {
			try {
				InputStream input = socket.getInputStream();
				InputStreamReader isr = new InputStreamReader(input,"UTF-8");
				BufferedReader br = new BufferedReader(isr);
				
				while(true) {
					String str = br.readLine();
					
					System.out.println("����˻ظ���"+str);
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}


