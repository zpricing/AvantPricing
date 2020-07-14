package cl.zpricing.avant.etl;

import java.io.IOException;

import scriptella.execution.EtlExecutorException;

import cl.zpricing.avant.model.Complejo;

public interface CinemaEtl {
	public boolean execute(Complejo complejo, boolean isFullDataExtraction)throws IOException, EtlExecutorException;
}
