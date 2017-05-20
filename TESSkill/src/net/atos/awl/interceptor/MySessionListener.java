package net.atos.awl.interceptor;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MySessionListener implements HttpSessionListener {

	private static final Log logger = LogFactory.getLog(MySessionListener.class);

	/**
	 * our apps security service bean responsible from doing necessary
	 * operations at login/logout progress...
	 */
	/*
	 * private ISecurityService securityService;
	 */
	public MySessionListener() {
	}

	public void sessionCreated(HttpSessionEvent event) {
		logger.debug("session created : " + event.getSession().getId());
	}

	public void sessionDestroyed(HttpSessionEvent event) {
		// get the destroying session...
		HttpSession session = event.getSession();
		logger.debug("session destroyed :" + session.getId() + " Logging out user...");

		try {
			// prepareLogoutInfoAndLogoutActiveUser(session);
		} catch (Exception e) {
			logger.error("error while logging out at session destroyed : " + e.getMessage(), e);
		}
	}

}