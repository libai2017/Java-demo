package util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import org.apache.commons.codec.binary.Base64;

public class NoteUtil {
	/** ����MD5���м��� */
	public static String EncoderByMd5(String str) {
		try {
			// ȷ�����㷽��
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			Base64 base64 = new Base64();
			// ���ܺ���ַ���
			String newstr = base64.encodeToString(md5.digest(str.getBytes("utf-8")));
			return newstr;
		}catch(Exception e) {
			e.printStackTrace();
			throw new RuntimeException("��������ʧ��");
		}
	}

	/**�ж��û������Ƿ���ȷ
	   *newpasswd �û����������
	   *oldpasswd ��ȷ����*/
	  public static boolean checkpassword(String newpasswd,String oldpasswd) {
		  try {
			  if(EncoderByMd5(newpasswd).equals(oldpasswd))
			      return true;
			  else
				  return false;
		  } catch(Exception e) {
			  e.printStackTrace();
			  return false;
		  }
	  }
	  
	  /*
	   * ����UUID�㷨��������
	   * */
	  public static String createId() {
		  UUID uuid = UUID.randomUUID();
		  String id = uuid.toString().replaceAll("-", "");
		  return id;
	  }
}
