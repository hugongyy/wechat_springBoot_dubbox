package com.chetong.ctwechat.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("commExeSqlDAO")
public class CommExeSqlDAO {

	@Autowired
	private SqlSession sqlSession;

	public <T> T queryForObject(String sqlmap, Object params) {
		return sqlSession.selectOne(sqlmap, params);
	}

	public <T> List<T> queryForList(String sqlmap, Object params) {
		return sqlSession.selectList(sqlmap, params);
	}

	public int insertVO(String sqlstm_id, Object o) {
		return sqlSession.insert(sqlstm_id, o);
	}

	public int insertBatchVO(String sqlstm_id, List<Object> l) {
		int c = 0;
		for (int i = 0; i < l.size(); i++) {
			Object o = l.get(i);
			c = c + insertVO(sqlstm_id, o);
		}
		return c;
	}

	public int updateVO(String sqlstm_id, Object o) {
		return sqlSession.update(sqlstm_id, o);
	}

	public int updateBatchVO(String sqlstm_id, List<Object> l) {
		int c = 0;
		for (int i = 0; i < l.size(); i++) {
			Object o = l.get(i);
			c = c + updateVO(sqlstm_id, o);
		}
		return c;
	}
	
	public int deleteVO(String sqlstm_id, Object o) {
		return sqlSession.delete(sqlstm_id, o);
	}

}
