package cl.zpricing.avantors.model;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "statistic")
public class StatisticUpSelling extends Statistic {
	private String cinemaId;
	private String sessionId;
	private String movieName;
	private String originalFormatPrice;
	private String upgradePrice;
	private Integer numberOfTickets;
	
	public StatisticUpSelling() {
		this.type = "up_selling";
	}
	
	public StatisticUpSelling(String cinemaId, String sessionId, String originalFormatPrice, Integer numberOfTickets) {
		this.type = "up_selling";
		this.status = "queried";
		this.date = new Date();
		this.cinemaId = cinemaId;
		this.sessionId = sessionId;
		this.originalFormatPrice = originalFormatPrice;
		this.numberOfTickets = numberOfTickets;
	}
	
	public String getCinemaId() {
		return cinemaId;
	}
	public void setCinemaId(String cinemaId) {
		this.cinemaId = cinemaId;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getMovieName() {
		return movieName;
	}
	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}
	public String getOriginalFormatPrice() {
		return originalFormatPrice;
	}
	public void setOriginalFormatPrice(String originalFormatPrice) {
		this.originalFormatPrice = originalFormatPrice;
	}
	public String getUpgradePrice() {
		return upgradePrice;
	}
	public void setUpgradePrice(String upgradePrice) {
		this.upgradePrice = upgradePrice;
	}
	public Integer getNumberOfTickets() {
		return numberOfTickets;
	}
	public void setNumberOfTickets(Integer numberOfTickets) {
		this.numberOfTickets = numberOfTickets;
	}
}