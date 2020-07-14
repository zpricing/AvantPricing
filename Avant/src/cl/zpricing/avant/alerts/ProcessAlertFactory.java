package cl.zpricing.avant.alerts;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

public class ProcessAlertFactory implements AlertFactory{

	@Autowired
	private AutowireCapableBeanFactory beanFactory;
	@Autowired
	@Qualifier("mongoTemplate")
	private MongoOperations mongoOperation;
	
	@Override
	public Alert makeAlert(String title, String description) {
		ProcessAlert alert = new ProcessAlert();
		beanFactory.autowireBean(alert);
		alert.setTitle(title);
		alert.setDescription(description);
		mongoOperation.save(alert, "alert");
		return alert;
	}

	@Override
	public boolean markAsRead(Alert alert) {
		alert.setUnread(false);
		mongoOperation.save(alert, "alert");
		return true;
	}
	
	@Override
	public boolean markAllAsRead() {
		Update update = new Update();
		update.set("unread", false);
		mongoOperation.updateMulti(new Query(Criteria.where("unread").is(true)), update, "alert");
		return true;
	}

	@Override
	public Alert getAlert(String alertId) {
		return mongoOperation.findOne(new Query(Criteria.where("id").is(alertId)), Alert.class);
	}

	@Override
	public List<Alert> getUnreadAlerts() {
		return mongoOperation.find(new Query(Criteria.where("unread").is(true)), Alert.class);
	}

	@Override
	public int countUnreadAlerts() {
		return (int) mongoOperation.count(new Query(Criteria.where("unread").is(true)), Alert.class);
	}
	
	public void setBeanFactory(AutowireCapableBeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}
	
	public void setMongoOperation(MongoOperations mongoOperation) {
		this.mongoOperation = mongoOperation;
	}
}
