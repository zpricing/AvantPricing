package cl.zpricing.avant.servicios.ibatis.handlers;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bson.types.ObjectId;

import com.ibatis.sqlmap.engine.type.BaseTypeHandler;

public class ObjectIdTypeHandler extends BaseTypeHandler {

	@Override
	public Object getResult(ResultSet rs, String columnName) throws SQLException {
		return new ObjectId(rs.getString(columnName));
	}

	@Override
	public Object getResult(ResultSet rs, int columnIndex) throws SQLException {
		return new ObjectId(rs.getString(columnIndex));
	}

	@Override
	public Object getResult(CallableStatement cs, int columnIndex) throws SQLException {
		return new ObjectId(cs.getString(columnIndex));
	}

	@Override
	public void setParameter(PreparedStatement ps, int arg1, Object arg2, String arg3) throws SQLException {
		// TODO Auto-generated method stub
	}

	@Override
	public Object valueOf(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
