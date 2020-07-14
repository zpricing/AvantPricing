package cl.zpricing.avant.model.restriccion;

import cl.zpricing.avant.model.Funcion;
import cl.zpricing.avant.model.FuncionArea;

public interface Rule {
	public boolean apply(Funcion funcion, FuncionArea funcionArea);
}
