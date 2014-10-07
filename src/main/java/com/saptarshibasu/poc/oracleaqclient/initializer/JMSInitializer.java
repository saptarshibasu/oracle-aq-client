package com.saptarshibasu.poc.oracleaqclient.initializer;

import com.saptarshibasu.poc.oracleaqclient.jms.JMSExceptionListener;
import com.saptarshibasu.poc.oracleaqclient.jms.JMSSessionManager;

public class JMSInitializer implements Initializer {

	private JMSSessionManager jmsSessionManager;
	
	private JMSExceptionListener jmsExceptionListener;
	
	public JMSInitializer(JMSSessionManager jmsSessionManager, JMSExceptionListener jmsExceptionListener)
	{
		this.jmsSessionManager = jmsSessionManager;
		this.jmsExceptionListener = jmsExceptionListener;
	}
	
	@Override
	public void init() {
		jmsSessionManager.initializeConnection();
		jmsSessionManager.setExceptionListener(jmsExceptionListener);
	}

}
