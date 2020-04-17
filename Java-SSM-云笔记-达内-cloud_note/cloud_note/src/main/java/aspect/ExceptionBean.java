package aspect;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

//@Component //ɨ�赽spring����
//@Aspect	   //��������Ϊ�������
public class ExceptionBean {
	//e��Ŀ������׳����쳣����
//	@AfterThrowing(throwing="e",pointcut="with(service..*)")
	public void execute(Exception e) {
		//���쳣��Ϣ�����ļ�
		try {
			FileWriter fw = new FileWriter("D:\\note_err.log", true);
			PrintWriter pw = new PrintWriter(fw);
			Integer.parseInt("libai");
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dateStr = sdf.format(date);
			pw.println("*************************");
			pw.println("*�쳣���ͣ�" + e);
			pw.println("*�쳣ʱ�䣺" + dateStr);
			pw.println("******�쳣��ϸ��Ϣ********");
			e.printStackTrace(pw);
			
			pw.close();
			fw.close();
		}catch(Exception ex) {
			ex.printStackTrace();
			System.out.println("��¼�쳣ʧ��!!!");
		}
	}
}
