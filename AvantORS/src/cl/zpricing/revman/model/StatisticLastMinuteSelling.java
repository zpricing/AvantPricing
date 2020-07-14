package cl.zpricing.revman.model;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "statistic")
public class StatisticLastMinuteSelling extends Statistic {
	private String cinemaId;
	private String dateQueried;
	private Integer numberOfSuggestions;
	
	
	public StatisticLastMinuteSelling() {
		this.type = "last_minute_selling";
	}
	
	public StatisticLastMinuteSelling(String cinemaId, String date, Integer numberOfSuggestions) {
		this.type = "last_minute_selling";
		this.status = "queried";
		this.date = new Date();
		this.dateQueried = date;
		this.cinemaId = cinemaId;
		this.numberOfSuggestions = numberOfSuggestions;
	}
	
	public String getCinemaId() {
		return cinemaId;
	}
	public void setCinemaId(String cinemaId) {
		this.cinemaId = cinemaId;
	}
	public Integer getNumberOfSuggestions() {
		return numberOfSuggestions;
	}
	public void setNumberOfSuggestions(Integer numberOfSuggestions) {
		this.numberOfSuggestions = numberOfSuggestions;
	}

	public String getDateQueried() {
		return dateQueried;
	}

	public void setDateQueried(String dateQueried) {
		this.dateQueried = dateQueried;
	}
}
