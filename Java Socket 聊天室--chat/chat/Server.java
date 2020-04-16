package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/*
 * �����ҷ����
 * */
public class Server {
	/*
	 * �����ڷ���˵�ServerSocket��Ҫ����
	 * 1. ��ϵͳ�������˿�
	 * �ͻ��˾���ͨ������˿���֮�������ӵ�
	 * 2. ��������ķ���˿ڣ���һ���ͻ���ͨ����
	 * �˿ڳ��Խ�������ʱ��ServerSocket����
	 * ����˴���һ��Socket��ͻ��˽������ӡ�
	 * */
	
	private ServerSocket server;
	
	/*
	 * �������пͻ���������ļ���
	 * */
	private Map<String,PrintWriter> allOut;
	

	/*
	 * ������ʼ�������
	 * */
	public Server() throws IOException{
		/*
		 * ��ʼ����ͬʱ�������˿� 
		 * */
		server = new ServerSocket(8088);		
		
		allOut = new HashMap<String,PrintWriter>();
	}
	// ���������������ӵ�������
	private synchronized void addOut(String nickName,PrintWriter out) {
		allOut.put(nickName, out);
	}
	// ��������������ӹ�������ɾ��
	private synchronized void removeOut(String nickName) {
		allOut.remove(nickName);
	}
	// ����������Ϣ���͸����пͻ���
	private void sendMessage(String message) {
		Set<Map.Entry<String, PrintWriter>> entryset = allOut.entrySet();
		for(Map.Entry<String, PrintWriter> e: entryset) {
			e.getValue().println(message);
		}		
	}
	// ��������Ϣ���͸��ض������û�
	private void sendOneMessage(String whoName,String message) {
		
		String keyName = message.substring(1,message.indexOf(":"));
		PrintWriter out = allOut.get(keyName);
		System.out.println("out is "+out+keyName);
		if(out != null) {
			out.println(whoName+"����˵��"+message.substring(message.indexOf(":")));
		}else {
			System.out.println("����ʧ��");
		}
		
	}
	/*
	 * ����˿�ʼ�����ķ���
	 * */
	public void start() {
		try {
			/*
			 * ServerSocket��accept��������һ������������
			 * �����Ǽ�������˿ڣ�ֱ��һ���ͻ���	���Ӳ�����һ��
			 * Socket��ʹ�ø�Socket����������ӵĿͻ��˽��н���
			 * 
			 * */
			while(true) {
				System.out.println("�ȴ��ͻ������ӡ�������");
				Socket socket = server.accept();
				System.out.println("һ���ͻ���������");
				ClientHandler handler = new ClientHandler(socket);
				Thread t = new Thread(handler);
				t.start();
			}
	
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		try {
			Server server = new Server();
			server.start();
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("���������ʧ��");
		}
	}
	
	
	class ClientHandler implements Runnable{
		/*
		 * ���̴߳���ͻ��˵�Socket
		 * */
		private Socket socket;
		
		private String nickName;
		/*
		 * �ͻ��˵ĵ�ַ��Ϣ
		 * */
		private String host;
		
		public ClientHandler(Socket socket) {
			this.socket = socket;
			/*
			 * ͨ��Socket���Ի�ȡԶ�˼�����ĵ�ַ��Ϣ��
			 * */
			InetAddress address = socket.getInetAddress();
			/*
			 * ��ȡip��ַ
			 * */
			host = address.getHostAddress();
		}
		
		public void run() {
			/*
			 * Socket�ṩ�ķ���
			 * InputStream getInputStream()
			 * �÷������Ի�ȡһ�����������Ӹ�����ȡ�����ݾ��Ǵ�Զ�˼�������͹�����
			 * */
			PrintWriter pw = null;
			try {
				InputStream input = socket.getInputStream();
				InputStreamReader isr = new InputStreamReader(input,"UTF-8");
				BufferedReader br = new BufferedReader(isr);
				
				OutputStream out = socket.getOutputStream();
				OutputStreamWriter osw = new OutputStreamWriter(out,"UTF-8");
				pw = new PrintWriter(osw,true);
				
				/*
				 * br.readLine()�ڶ�ȡ�ͻ��˷��͹�������Ϣʱ�����ڿͻ��˶��ߣ��������ϵͳ�Ĳ�ͬ��
				 * �����ȡ�Ľ����ͬ��
				 * ��windows�Ŀͻ��˶Ͽ�ʱ��br.readLine���׳��쳣
				 * ��linux�Ŀͻ��˶Ͽ�ʱ��br.readLine�᷵��null
				 * 
				 * */
				nickName = br.readLine();
				
				System.out.println(host+" "+nickName+" ������");
				
				// ���ÿͻ��˵���������뵽��������
				addOut(nickName,pw);
				System.out.println(allOut);
				System.out.println("�ȴ�������Ϣ");
				while(true) {
					String message = br.readLine();
					if(message == null) {
						break;
					}
					System.out.println(host+" "+nickName+": "+message);
					if(message.startsWith("@")) {
						sendOneMessage(nickName,message);
					}else {
						sendMessage(host+nickName+": "+message);
					}
				}

				
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					socket.close();
					removeOut(nickName);
					System.out.println(nickName+"�Ͽ�����");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}
