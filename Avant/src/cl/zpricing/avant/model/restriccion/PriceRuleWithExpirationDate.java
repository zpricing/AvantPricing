package cl.zpricing.avant.model.restriccion;

import java.util.Date;

import org.apache.log4j.Logger;

import cl.zpricing.avant.model.Funcion;
import cl.zpricing.avant.model.FuncionArea;

public class PriceRuleWithExpirationDate extends PriceRule {
	private Logger log = (Logger) Logger.getLogger(this.getClass());
	
	private Date expirationDate;
	
	@Override
	public boolean apply(Funcion funcion, FuncionArea funcionArea) {
		if (this.isSessionBeforeExpirationDate(funcion) ) {
			log.debug("  Regla dentro del rango de restriccion, se valida si aplica.");
			return super.apply(funcion, funcionArea);
		}
		log.debug("  Regla no aplica porque ya paso la fecha de expiracion.");
		return false;
	}
	
	private boolean isSessionBeforeExpirationDate(Funcion funcion) {
		log.debug("  Fecha de expiracion : [" + this.expirationDate + "]");
		log.debug("  Fecha Contable : [" + funcion.getFechaContable() + "]");
		return expirationDate == null || !funcion.getFechaContable().after(this.expirationDate);
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}
}
