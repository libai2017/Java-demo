package dao;

import java.util.List;

import entity.Book;
import entity.User;

public interface RelationDao {
	//������������ѯ
	public User findUserAndBooks(String UserId);
	
	public User findUserAndBooks2(String UserId);
	
	//������������
	public List<Book> findBookAndUser();
	
	public List<Book> findBookAndUser2();
}
