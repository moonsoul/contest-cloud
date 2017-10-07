/**
 * 
 */
package com.app.common;

import com.qq.base.common.Entity;
import com.qq.base.common.Page;
import com.qq.base.dbsql.SqlFactory;
import com.qq.base.dbsql.dialect.DataBase;
import com.qq.base.exception.FuncSqlException;
import com.qq.base.util.ReflectionUtils;
import com.qq.base.web.PageCommon;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.incrementer.DataFieldMaxValueIncrementer;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.sql.DataSource;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 * </p>
 * 
 * @author Zhao YuYang 2016年3月11日 下午4:14:42
 */
@Component
public class JDBCBaseDao {
	private static final Logger logger = LoggerFactory
			.getLogger(JDBCBaseDao.class);
	@Autowired
	private BaseJdbcTemplateHandling baseJdbcTemplate = null;
	@Autowired
	private DataFieldMaxValueIncrementer nextSequenceIncrementer = null;
	@Autowired
	private SqlFactory sqlFactory = null;

	public BaseJdbcTemplateHandling getBaseJdbcTemplate() {
		return baseJdbcTemplate;
	}

	public void setBaseJdbcTemplate(BaseJdbcTemplateHandling baseJdbcTemplate) {
		this.baseJdbcTemplate = baseJdbcTemplate;
	}

	public DataFieldMaxValueIncrementer getNextSequenceIncrementer() {
		return nextSequenceIncrementer;
	}

	public void setNextSequenceIncrementer(
			DataFieldMaxValueIncrementer nextSequenceIncrementer) {
		this.nextSequenceIncrementer = nextSequenceIncrementer;
	}

	public SqlFactory getSqlFactory() {
		return sqlFactory;
	}

	public void setSqlFactory(SqlFactory sqlFactory) {
		this.sqlFactory = sqlFactory;
	}

	public final DataSource getDataSource() {
		return this.baseJdbcTemplate.getDataSource();
	}

	public final String getSequenceNextID() {
		return this.nextSequenceIncrementer.nextStringValue();
	}

	public Entity queryEntityByID(Entity entity) {
		return this.baseJdbcTemplate.queryForEntity(entity);
	}

	public void deleteEntityByID(Entity entity) {
		this.baseJdbcTemplate.deleteEntity(entity);
	}

	public String insertEntity(Entity entity) {
		String idString = "";
		try {
			idString = (String) ReflectionUtils.getDeclaredProperty(entity,
					"primaryKey");
		} catch (IllegalAccessException e) {
			logger.error("实体主键标示“primaryKey”未找到", e);
			throw new FuncSqlException("实体主键标示“primaryKey”未找到", e);
		} catch (NoSuchFieldException e) {
			logger.error("实体{}主键标识primaryKey未注册", entity.toString(), e);
			throw new FuncSqlException("实体主键标识primaryKey未注册", e);
		}
		if (StringUtils.isEmpty(idString)) {
			logger.error("实体{}主键标识primaryKey未注册", entity.toString());
			throw new FuncSqlException("实体主键标识primaryKey未注册",
					new NullPointerException());
		}
		BeanWrapper bw = new BeanWrapperImpl(entity);
		String id = getSequenceNextID();
		bw.setPropertyValue(idString, id);
		this.baseJdbcTemplate.insertEntity(entity);
		return id;
	}

	public void updateEntityByID(Entity entity) {
		this.baseJdbcTemplate.updateEntity(entity);
	}

	public Page pagedQuery(String sql, PageCommon pageInfo) {
		Long totalCount = getTotalRows(sql);
		if (totalCount.intValue() < 1) {
			return new Page();
		}
		int startIndex = Page.getStartOfPage(pageInfo.getPageNo(),
				pageInfo.getCurrentRows());

		boolean isOut = startIndex - totalCount.intValue() >= 0;
		if (isOut) {
			startIndex = 0;
		}
		String tempsql = getSortSql(sql, pageInfo.getSortMap());

		DataBase db = DataBase.create("MySQL");

		String pagingSelect = db.populatePageSQL(tempsql, startIndex,
				pageInfo.getCurrentRows());

		logger.info("分页语句：[===" + pagingSelect + "===]");

		List obj = this.baseJdbcTemplate.queryForList(pagingSelect);

		return new Page(startIndex, totalCount.intValue(),
				pageInfo.getCurrentRows(), obj);
	}

	protected Long getTotalRows(String sql) {
		String countQueryString = addSelect(removeOrders(sql));
		Long totalCount = new Long(
				this.baseJdbcTemplate.queryForLong(countQueryString));

		return totalCount;
	}

	private String getSortSql(String sql, Map sortMap) {
		String ORDERBY = "order by";

		String returnsql = "";

		Pattern p = Pattern.compile("order\\s*by", 2);

		Matcher m = p.matcher(sql);

		StringBuffer sbtempsql = new StringBuffer();
		while (m.find()) {
			m.appendReplacement(sbtempsql, "order by");
		}
		m.appendTail(sbtempsql);

		returnsql = sbtempsql.toString();

		StringBuffer tempSql = new StringBuffer(returnsql);

		boolean isLastExistOrderBy = StringUtils.lastIndexOf(returnsql,
				"order by") < 0;
		if ((sortMap != null) && (!sortMap.isEmpty())) {
			if (isLastExistOrderBy) {
				tempSql.append(" order by ");
			}
			Set keys = sortMap.keySet();
			for (Iterator iter = keys.iterator(); iter.hasNext();) {
				String fieldName = iter.next().toString();

				String orderType = sortMap.get(fieldName).toString();
				if (StringUtils.isNotBlank(fieldName)) {
					if (isLastExistOrderBy) {
						tempSql.append(" ").append(fieldName).append(" ")
								.append(orderType);
					} else {
						int offset = StringUtils.lastIndexOf(returnsql,
								"order by");
						tempSql = tempSql.delete(offset, tempSql.length());
						tempSql.append(" order by ");
						tempSql.append(" ").append(fieldName).append(" ")
								.append(orderType);
					}
				}
			}
		}
		return tempSql.toString();
	}

	private String addSelect(String sql) {
		Assert.hasText(sql);

		StringBuffer sql2 = new StringBuffer();
		sql2.append("select count(*) from ( ");
		sql2.append(sql);
		sql2.append(" ) AS totalnum");

		return sql2.toString();
	}

	private String removeOrders(String sql) {
		Assert.hasText(sql);
		Pattern p = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*", 2);
		Matcher m = p.matcher(sql);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			m.appendReplacement(sb, "");
		}
		m.appendTail(sb);
		return sb.toString();
	}

}
