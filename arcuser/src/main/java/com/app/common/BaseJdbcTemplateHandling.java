package com.app.common;

import com.qq.base.common.Containclob;
import com.qq.base.common.Entity;
import com.qq.base.dao.MyClob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

/**
 * @author Zhao Yuyang 类说明：数据库操控制作接口
 */
public class BaseJdbcTemplateHandling {

	private static final Logger logger = LoggerFactory
			.getLogger(BaseJdbcTemplateHandling.class);

	private JdbcTemplate jdbcTemplate = null;

	private NamedParameterJdbcTemplate namedParameterJdbcTemplate = null;

	private MyClob myClob = null;

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(
				jdbcTemplate);
	}

	public void setMyClob(MyClob myClob) {
		this.myClob = myClob;
	}

	/**
	 * Return the JDBC DataSource used by this DAO.
	 */
	public final DataSource getDataSource() {
		return (this.jdbcTemplate != null ? this.jdbcTemplate.getDataSource()
				: null);
	}

	public final Connection getConnection()
			throws CannotGetJdbcConnectionException {
		return DataSourceUtils.getConnection(getDataSource());
	}

	/**
	 * Close the given JDBC Connection, created via this DAO's DataSource, if it
	 * isn't bound to the thread.
	 * 
	 * @param con
	 *            Connection to close
	 * @see DataSourceUtils#releaseConnection
	 */
	public final void releaseConnection(Connection con) {
		DataSourceUtils.releaseConnection(con, getDataSource());
	}

	/**
	 * 通过带主键的Entity对象,返回从数据库查询组装后的entity对象
	 */
	public Entity queryForEntity(final Entity entity)
			throws DataAccessException {

		if (null == entity) {
			return null;
		}

		List list = queryForObjectList(entity.entitySelectSql(),
				entity.getClass());

		if (list.isEmpty()) {

			return null;

		}

		return (Entity) list.get(0);
	}

	/**
	 * 通过带主键的Entity对象,执行在数据库中删除entity对象
	 */
	public void deleteEntity(final Entity entity) throws DataAccessException {

		jdbcTemplate.update(entity.entityDeleteSql());
	}

	/**
	 * 通过带主键的Entity对象,执行在数据库中插入entity对象
	 */
	public void insertEntity(final Entity entity) throws DataAccessException {

		Object[] o = entity.entityInsertSqlParm();
		String sql = o[0].toString();
		Object[] p = (Object[]) o[1];
		jdbcTemplate.update(sql, p);

	}

	/**
	 * 通过带主键的Entity对象,执行在数据库中更新entity对象
	 */
	public void updateEntity(final Entity entity) throws DataAccessException {

		Object[] o = entity.entityUpdateSqlParm();
		String sql = o[0].toString();
		Object[] p = (Object[]) o[1];
		jdbcTemplate.update(sql, p);
	}

	public void execute(final String sql) {

		jdbcTemplate.execute(sql);

	}

	public Map queryForMap(final String sql) throws DataAccessException {

		return jdbcTemplate.queryForMap(sql);
	}

	public List queryForList(String sql, Class elementType)
			throws DataAccessException {

		return jdbcTemplate.queryForList(sql, elementType);
	}

	public List queryForList(final String sql) throws DataAccessException {
		return jdbcTemplate.queryForList(sql);
	}

	public List queryForList(String sql, Object[] parm) {
		return jdbcTemplate.queryForList(sql, parm);
	}

	public List queryForObjectList(String sql, Object[] parm, Class entityClass)
			throws DataAccessException {
		RowMapper rm = new BeanPropertyRowMapper(entityClass);
		return jdbcTemplate.query(sql, parm, rm);
	}

	public long queryForLong(final String sql) throws DataAccessException {

		// return jdbcTemplate.queryForLong(sql);
		Long ret = null;
		try {

			ret = jdbcTemplate.queryForObject(sql, Long.class);
		} catch (Exception e) {

			String msg = "SQL语句\n" + sql + "\n 查询为空";
			logger.error(msg);
			e.printStackTrace();
		}
		return ret;
	}

	public int queryForInt(final String sql) throws DataAccessException {

		// return jdbcTemplate.queryForInt(sql);
		Integer ret = null;
		try {

			ret = jdbcTemplate.queryForObject(sql, Integer.class);
		} catch (Exception e) {

			String msg = "SQL语句\n" + sql + "\n 查询为空";
			logger.error(msg);
			e.printStackTrace();
		}
		return ret;

	}

	public Object queryForObject(String sql, Class requiredType) {

		return jdbcTemplate.queryForObject(sql, requiredType);

	}

	public Object queryForObjectEntity(String sql, Class entityClass) {
		RowMapper rm = new BeanPropertyRowMapper(entityClass);
		List list = jdbcTemplate.query(sql, rm);
		Object o = null;
		if (list != null && list.size() > 0) {
			o = list.get(0);
		}
		return o;

	}

	public int update(final String sql) throws DataAccessException {

		return jdbcTemplate.update(sql);
	}

	public int[] batchUpdate(final String[] sqls) throws DataAccessException {

		return jdbcTemplate.batchUpdate(sqls);
	}

	public int[] batchInsert(final String[] sqls) throws DataAccessException {
		return jdbcTemplate.batchUpdate(sqls);
	}

	public int[] batchDelete(final String[] sqls) throws DataAccessException {

		return jdbcTemplate.batchUpdate(sqls);
	}

	public List queryForObjectList(String sql, Class entityClass)
			throws DataAccessException {

		RowMapper rm = new BeanPropertyRowMapper(entityClass);
		List result = jdbcTemplate.query(sql, rm);
		return result;
	}

	public List queryForListWithPrepareStatement(String sql, Map param)
			throws DataAccessException {

		return namedParameterJdbcTemplate.queryForList(sql, param);
	}

	public List queryForObjectListWithPrepareStatement(String sql, Map param,
			Class entityClass) throws DataAccessException {
		RowMapper rm = new BeanPropertyRowMapper(entityClass);
		List result = namedParameterJdbcTemplate.query(sql, param, rm);
		return result;
	}

	public void insertClob(final Containclob clob) throws DataAccessException {

		myClob.insertClob(clob, jdbcTemplate);
	}

	public void deleteClob(final Containclob clob) throws DataAccessException {

		myClob.deleteClob(clob, jdbcTemplate);
	}

	public void updateClob(final Containclob clob) throws DataAccessException {

		myClob.updateClob(clob, jdbcTemplate);
	}

	public Containclob selectClob(final Containclob clob)
			throws DataAccessException {

		return myClob.selectClob(clob, jdbcTemplate);
	}

	public void insertJforumPostBlob(final String postid,
			final String postSubject) throws DataAccessException {

		myClob.insertJforumPostBlob(jdbcTemplate, postid, postSubject);
	}

}
