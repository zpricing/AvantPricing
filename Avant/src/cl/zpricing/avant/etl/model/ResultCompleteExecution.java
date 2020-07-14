package cl.zpricing.avant.etl.model;

import java.util.ArrayList;
import java.util.List;

import cl.zpricing.avant.model.Complejo;

public class ResultCompleteExecution {
	private Complejo complejo;
	private boolean successful;
	private List<ResultEtl> resultEtl;
	private List<String> errors;
	
	public ResultCompleteExecution(Complejo complejo) {
		this.complejo = complejo;
		this.resultEtl = new ArrayList<ResultEtl>();
		this.errors = new ArrayList<String>();
		this.successful = true;
	}
	
	public ResultCompleteExecution() {
		this.resultEtl = new ArrayList<ResultEtl>();
		this.errors = new ArrayList<String>();
		this.successful = true;
	}
	
	public Complejo getComplejo() {
		return complejo;
	}
	public List<ResultEtl> getResultEtl() {
		return resultEtl;
	}
	public List<String> getErrors() {
		return errors;
	}
	public void addResultEtl(ResultEtl resultEtl) {
		this.successful = this.successful ? resultEtl.isSuccessful() : false;
		this.resultEtl.add(resultEtl);
	}
	public void addError(String error) {
		this.errors.add(error);
	}
	public boolean isSuccessful() {
		return successful;
	}
}
