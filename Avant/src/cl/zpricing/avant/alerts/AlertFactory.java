package cl.zpricing.avant.alerts;

import java.util.List;

public interface AlertFactory {
	public Alert makeAlert(String title, String description);
	public boolean markAsRead(Alert alert);
	public boolean markAllAsRead();
	public Alert getAlert(String alertId);
	public List<Alert> getUnreadAlerts();
	public int countUnreadAlerts();
}
