package dao;

import java.util.List;
import java.util.Map;

import entity.Note;

public interface NoteDao {
	public List<Map> findByBookId(String notebook_id);
	
	public Map findByBookIdDetail(String note_id);
	
	public void saveNote(Note note);
	
	public void updateNoteByMap(Map<String, Object> map);
	
	public void saveNewNote(Note note);
	
	public int deleteNote(String note_id);
	
	/*
	 * map ����Ҫ��Ӳ���
	 * map = {ids:[id1,id2,id3....], status:2}
	 * ids ����ɾ���ʼǵ�ID�б�
	 * status ����ɾ���ʼǵ�����״̬
	 * */
	public void deleteNotes(Map<String, Object> map);
	
	public int deleteNote2(String id);
}
