package cl.zpricing.avant.alerts;

import java.util.Date;

public abstract class Alert {

	private String id;
	private boolean unread;
	private String title;
	private String description;
	private Date createdAt;
	
	public Alert() {
		this.unread = true;
		this.setCreatedAt(new Date());
	}
	
	public abstract boolean notifyAlert();
	
	public void setUnread(boolean unread) {
		this.unread = unread;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isUnread() {
		return unread;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
}
