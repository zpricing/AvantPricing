package cl.zpricing.avant.test.functional;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import scriptella.execution.EtlExecutorException;

import cl.zpricing.avant.etl.variantes.EtlCompleteExtractionImpl;
import cl.zpricing.avant.model.Complejo;
import cl.zpricing.avant.servicios.ComplejoDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:test-context.xml")
public class ExtractDataFromVistaTest {
	@Autowired
	private EtlCompleteExtractionImpl etlCompleteExtraction;
	@Autowired
	private ComplejoDao complejoDao;
	
	private int complejoId = 3;
	
	@Test
	public void testScreenEtl() {
		Complejo complejo = complejoDao.obtenerComplejo(complejoId);
		boolean result = false;
		try {
			result = etlCompleteExtraction.extractScreens(complejo);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (EtlExecutorException e) {
			e.printStackTrace();
		}
		assertEquals("Screen ETL", true, result);
	}

	@Test
	public void testDistributorEtl() throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException {
		Complejo complejo = complejoDao.obtenerComplejo(complejoId);
		//Method method = EtlManager.class.getDeclaredMethod("extractDistributors", Complejo.class);
		//method.setAccessible(true);
		//boolean result = (Boolean) method.invoke(etlManager, complejo);
		boolean result = false;
		try {
			result = etlCompleteExtraction.extractDistributors(complejo);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (EtlExecutorException e) {
			e.printStackTrace();
		}
		
		assertEquals("Distributor ETL", true, result);
	}

	@Test
	public void testCategoryEtl() {
		Complejo complejo = complejoDao.obtenerComplejo(complejoId);
		boolean result = false;
		try {
			result = etlCompleteExtraction.extractCategories(complejo);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (EtlExecutorException e) {
			e.printStackTrace();
		}
		assertEquals("Category ETL", true, result);
	}

	@Test
	public void testMovieFullExtractionEtl() {
		Complejo complejo = complejoDao.obtenerComplejo(complejoId);
		boolean result = false;
		try {
			result = etlCompleteExtraction.extractMovies(complejo, true);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (EtlExecutorException e) {
			e.printStackTrace();
		}
		assertEquals("Movie ETL", true, result);
	}
	

	@Test
	public void testMovieQuickExtractionEtl() {
		Complejo complejo = complejoDao.obtenerComplejo(complejoId);
		boolean result = false;
		try {
			result = etlCompleteExtraction.extractMovies(complejo, false);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (EtlExecutorException e) {
			e.printStackTrace();
		}
		assertTrue(result);
	}
	@Test
	public void testTicketEtl() {
		Complejo complejo = complejoDao.obtenerComplejo(complejoId);
		boolean result = false;
		try {
			result = etlCompleteExtraction.extractTickets(complejo);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (EtlExecutorException e) {
			e.printStackTrace();
		}
		assertEquals("Ticket ETL", true, result);
	}
	
	@Test
	public void testPriceCardEtl() {
		Complejo complejo = complejoDao.obtenerComplejo(complejoId);
		boolean result = false;
		try {
			result = etlCompleteExtraction.extractPriceCards(complejo);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (EtlExecutorException e) {
			e.printStackTrace();
		}
		assertEquals("PriceCard ETL", true, result);
	}
	
	@Test
	public void testPriceCardPricesEtl() {
		Complejo complejo = complejoDao.obtenerComplejo(complejoId);
		boolean result = false;
		try {
			result = etlCompleteExtraction.extractPrices(complejo);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (EtlExecutorException e) {
			e.printStackTrace();
		}
		assertEquals("PriceCard ETL", true, result);
	}
	
	@Test
	public void testSessionEtl() {
		Complejo complejo = complejoDao.obtenerComplejo(complejoId);
		boolean result = false;
		try {
			result = etlCompleteExtraction.extractSessions(complejo, false);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (EtlExecutorException e) {
			e.printStackTrace();
		}
		assertEquals("Session ETL", true, result);
	}
	
	@Test
	public void testAttendanceEtl() {
		Complejo complejo = complejoDao.obtenerComplejo(complejoId);
		boolean result = false;
		try {
			result = etlCompleteExtraction.extractAttendance(complejo, false);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (EtlExecutorException e) {
			e.printStackTrace();
		}
		assertEquals("Ticket ETL", true, result);
	}
	
	@Test
	public void testGroupMovies() {
		boolean result = false;
		try {
			result = etlCompleteExtraction.groupMovies();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (EtlExecutorException e) {
			e.printStackTrace();
		}
		assertEquals(true, result);
	}
	
	public void setEtlCompleteExtraction(EtlCompleteExtractionImpl etlCompleteExtraction) {
		this.etlCompleteExtraction = etlCompleteExtraction;
	}
	
	public void setComplejoDao(ComplejoDao complejoDao) {
		this.complejoDao = complejoDao;
	}
}