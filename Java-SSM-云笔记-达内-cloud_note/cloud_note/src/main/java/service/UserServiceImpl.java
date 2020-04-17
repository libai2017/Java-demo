package service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dao.UserDao;
import entity.User;
import util.NoteResult;
import util.NoteUtil;

@Service("userService") //spring����ɨ��
@Transactional
public class UserServiceImpl implements UserService{

	@Resource(name="userDao")
	private UserDao userDao;
	
	public NoteResult<User> check_login(String username, String password) {
		NoteResult<User> result = new NoteResult<User>();
		User user = userDao.findByName(username);
		if(user == null) {
			result.setStatus(1);
			result.setMsg("�û���������");
			return result;
		}
		if(!NoteUtil.checkpassword(password, user.getCn_user_password())){
			result.setStatus(2);
			result.setMsg("���벻��ȷ");
			return result;
		}
		result.setStatus(0);
		result.setMsg("��½�ɹ�");
		result.setData(user);
		return result;
	}

	public NoteResult save(User user) {
		NoteResult result = new NoteResult();
		String username = user.getCn_user_name();
		
		if(userDao.findByName(username) != null) {
			result.setStatus(1);
			result.setMsg("���û����ѱ�ע�ᣬ���޸������û���");
			return result;
		}
		String password = user.getCn_user_password();
		user.setCn_user_password(NoteUtil.EncoderByMd5(password));
		user.setCn_user_id(NoteUtil.createId());
		System.out.println(user);
		userDao.save(user);
		result.setStatus(0);
		result.setMsg("ע��ɹ������¼");
		return result;
	}

}
