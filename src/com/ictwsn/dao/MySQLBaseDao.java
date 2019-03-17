package com.ictwsn.dao;

import javax.annotation.Resource;
import org.springframework.jdbc.core.JdbcTemplate;


public class MySQLBaseDao {
	
	@Resource(name="userTemplate")
	protected JdbcTemplate jt;
	
	
	public JdbcTemplate getUserTemplate() {
		return jt;
	}

	public void setUserTemplate(JdbcTemplate jt) {
		this.jt = jt;
	}
	
	
}
