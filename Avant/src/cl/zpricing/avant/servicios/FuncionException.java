package cl.zpricing.avant.servicios;

import java.util.ArrayList;

import cl.zpricing.avant.model.Funcion;

public class FuncionException extends Exception{
	
	private Funcion funcion;
	
	public FuncionException(Funcion funcion, String strMessage) {
		super(strMessage);
		this.funcion = funcion;
	}
	
	public FuncionException(Funcion funcion, ArrayList<String> errores) {
		super(errores.toString());
		this.funcion = funcion;
	}
	
	public Funcion getFuncion() {
		return funcion;
	}

	public void setFuncion(Funcion funcion) {
		this.funcion = funcion;
	}

	
}
