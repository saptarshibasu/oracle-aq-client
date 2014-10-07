package com.saptarshibasu.poc.oracleaqclient.jms;

import javax.jms.ExceptionListener;
import javax.jms.JMSException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JMSExceptionListener implements ExceptionListener{
	
	private static final Logger LOG = LoggerFactory.getLogger(JMSExceptionListener.class);

	private JMSSessionManager jmsSessionManager;
	
	public JMSExceptionListener(JMSSessionManager jmsSessionManager)
	{
		this.jmsSessionManager = jmsSessionManager;
	}
	
	@Override
	public void onException(JMSException exception) {
		LOG.error(exception.getMessage(), exception);
		LOG.error("Refreshing connection.");
		jmsSessionManager.closeConnection();
		jmsSessionManager.initializeConnection();
		jmsSessionManager.setExceptionListener(this);
	}

}
