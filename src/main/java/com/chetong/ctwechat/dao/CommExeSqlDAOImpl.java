package com.chetong.ctwechat.dao;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.chetong.aic.page.domain.PageBounds;
import com.chetong.aic.page.domain.PageList;
import com.chetong.ctwechat.util.exception.DaoException;

@Repository
public class CommExeSqlDAOImpl extends SqlSessionDaoSupport implements CommExeSqlDAO {
	
	/**
	 * 免注解方式注入sqlSessionFactory
	 * @param sqlSessionFactory void
	 * @throws
	 */
	@Resource(name = "sqlSessionFactory")
	public void setSuperSessionFactory(SqlSessionFactory sqlSessionFactory) {
		super.setSqlSessionFactory(sqlSessionFactory);
	}
	
	@Override
	public <T> List<T> queryForList(String sqlstm_id, Object o) throws DaoException {
		List<T> list = new ArrayList<T>();
		list=this.getSqlSession().selectList(sqlstm_id, o);
		return list;
	}

	@Override
	public <T> T queryForObject(String sqlstm_id, Object params) throws DaoException{
		T o = this.getSqlSession().selectOne(sqlstm_id, params);
		return o;
	}

	@Override
	public int insertVO(String sqlstm_id,Object o) throws DaoException{
		try{
			return this.getSqlSession().insert(sqlstm_id, o);
		}catch(Exception e){
			throw new DaoException("insertVO("+sqlstm_id+")异常："+e);
		}
	}
	
	@Override
	public <T> int insertBatchVO(String sqlstm_id,List<T> l) throws DaoException{
		int c = 0;
		for(int i=0;i<l.size();i++){
			Object o = l.get(i);
			c=c+insertVO(sqlstm_id,o);
		}
		return c;
	}

	@Override
	public int updateVO(String sqlstm_id, Object o) throws DaoException{
		return this.getSqlSession().update(sqlstm_id, o);
	}

	@Override
	public <T> int updateBatchVO(String sqlstm_id, List<T> l) throws DaoException{
		int c = 0;
		for(int i=0;i<l.size();i++){
			Object o = l.get(i);
			c=c+updateVO(sqlstm_id,o);
		}
		return c;
	}
	
	@Override
	public int deleteVO(String sqlstm_id, Object o) throws DaoException{
		return this.getSqlSession().delete(sqlstm_id, o);
	}

	@Override
	public <T> int deleteBatchVO(String sqlstm_id, List<T> l) throws DaoException{
		int c = 0;
		for(int i=0;i<l.size();i++){
			Object o = l.get(i);
			c=c+deleteVO(sqlstm_id,o);
		}
		return c;
	}
	
	@Override
	public <T> PageList<T> queryForPage(String sqlstm_id, Object o,PageBounds page) throws DaoException{
		 List<T> list = this.getSqlSession().selectList(sqlstm_id, o, page);
		 PageList<T> pl = (PageList<T>)list;
		return pl;
	}
	
}
