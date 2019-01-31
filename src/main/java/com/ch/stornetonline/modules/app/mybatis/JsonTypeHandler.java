package com.ch.stornetonline.modules.app.mybatis;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

public class JsonTypeHandler extends BaseTypeHandler<Object>{

	@Override
	public Object getNullableResult(ResultSet rs, String columnName)
			throws SQLException {
		return JsonTypeUtil.parse(rs.getString(columnName), Object.class);
	}

	@Override
	public Object getNullableResult(ResultSet rs, int columnIndex)
			throws SQLException {
		return JsonTypeUtil.parse(rs.getString(columnIndex), Object.class);
	}

	@Override
	public Object getNullableResult(CallableStatement cs, int columnIndex)
			throws SQLException {
		 return JsonTypeUtil.parse(cs.getString(columnIndex), Object.class);
	}

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, Object parameter,
            JdbcType jdbcType) throws SQLException {
		 ps.setString(i, JsonTypeUtil.stringify(parameter));
		
	}

}
