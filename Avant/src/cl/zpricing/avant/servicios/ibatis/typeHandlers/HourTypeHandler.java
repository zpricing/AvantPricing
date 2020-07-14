package cl.zpricing.avant.servicios.ibatis.typeHandlers;

import java.sql.SQLException;
import java.sql.Types;

import org.apache.log4j.Logger;

import cl.zpricing.commons.model.Hour;

import com.ibatis.sqlmap.client.extensions.ParameterSetter;
import com.ibatis.sqlmap.client.extensions.ResultGetter;
import com.ibatis.sqlmap.client.extensions.TypeHandlerCallback;

public class HourTypeHandler implements TypeHandlerCallback {
	private Logger log = (Logger) Logger.getLogger(this.getClass());
	
	@Override
	public Object getResult(ResultGetter getter) throws SQLException {
		log.debug("HourTypeHandler:getResult");
		if (getter != null && getter.getString() != null) {
			log.debug("  String: " + getter.getString());
			return new Hour(getter.getString());
		}
		return null;
	}

	@Override
	public void setParameter(ParameterSetter setter, Object parameter) throws SQLException {
		log.debug("HourTypeHandler:setParameter");
		if (parameter == null) {
			setter.setNull(Types.VARCHAR);
		}
		
		Hour hour = (Hour) parameter;
		setter.setString(hour.toString());
	}

	@Override
	public Object valueOf(String s) {
		log.debug("HourTypeHandler:valueOf");
		return s;
	}

}
