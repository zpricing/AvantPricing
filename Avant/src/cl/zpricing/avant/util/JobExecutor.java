package cl.zpricing.avant.util;

import java.util.Date;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;

import cl.zpricing.commons.exceptions.JobExecutionException;
import cl.zpricing.commons.utils.ErroresUtils;

public class JobExecutor {
	protected Logger log = (Logger) Logger.getLogger(this.getClass());
	
	@Autowired
	private JobLauncher jobLauncher;
	
	private Job job;
	
	private JobParametersBuilder jobParametersBuilder;
	
	public JobExecutor() {
		this.jobParametersBuilder = new JobParametersBuilder();
	}
	
	public void execute() throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobExecutionException {
		log.info("  Executing Job name: " + job.getName());
		this.jobParametersBuilder.addDate("date", new Date());
		JobExecution jobExecution = jobLauncher.run(job, this.jobParametersBuilder.toJobParameters());
		log.debug("  Exit Status : " + jobExecution.getExitStatus().getExitCode());
		ExitStatus jobExecutionStatus = jobExecution.getExitStatus();
		
		if (jobExecutionStatus.compareTo(ExitStatus.COMPLETED) != 0) {
			Iterator<Throwable> jobExceptionsIterator = jobExecution.getAllFailureExceptions().iterator();
			while (jobExceptionsIterator.hasNext()) {
				Throwable exception = jobExceptionsIterator.next();
				ErroresUtils.extraeStackTrace(exception);
			}
			throw new JobExecutionException("Error en la ejecucion del job");
		}
	}

	public Job getJob() {
		return job;
	}
	public void setJob(Job job) {
		this.job = job;
	}

	public JobParametersBuilder getJobParametersBuilder() {
		return jobParametersBuilder;
	}
}
