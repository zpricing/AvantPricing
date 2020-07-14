package cl.zpricing.avant.etl;

import java.io.IOException;

import scriptella.execution.EtlExecutorException;

public interface GroupMoviesEtl {
	public boolean execute() throws EtlExecutorException, IOException;
}
