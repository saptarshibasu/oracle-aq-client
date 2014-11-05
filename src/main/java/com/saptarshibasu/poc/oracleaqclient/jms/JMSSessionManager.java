package com.saptarshibasu.poc.oracleaqclient.jms;

import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSession;
import javax.jms.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JMSSessionManager{
	
	private static final Logger LOG = LoggerFactory.getLogger(JMSSessionManager.class);
	
	private QueueConnectionFactory queueConnectionFactory;
	private MessageProcessor messageProcessor;
	private String queueName;
	private Integer concurrency;

	private QueueConnection queueConnection;
	public QueueConnection getConnection() {
		return queueConnection;
	}

	private QueueSession queueSession;

	public JMSSessionManager(QueueConnectionFactory queueConnectionFactory, MessageProcessor messageProcessor, String queueName, Integer concurrency)
	{
		this.queueConnectionFactory = queueConnectionFactory;
		this.messageProcessor = messageProcessor;
		this.queueName = queueName;
		this.concurrency = concurrency;
	}
	
	public void initializeConnection()
	{

		try 
		{
			queueConnection = queueConnectionFactory.createQueueConnection();
			for(int i=0; i<concurrency;i++)
			{
				queueSession = queueConnection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
				queueSession.createReceiver(queueSession.createQueue(queueName));
				queueSession.setMessageListener(messageProcessor);
			}
			queueConnection.start();
		} 
		catch (JMSException e) 
		{
			LOG.error(e.getMessage(), e);
		}
		
	}
	
	public void closeConnection()
	{
		try {
			queueConnection.close();
		} catch (JMSException e) {
			LOG.error(e.getMessage(), e);
		}
	}
	
	public void setExceptionListener(ExceptionListener exceptionListener)
	{
		try {
			queueConnection.setExceptionListener(exceptionListener);
		} catch (JMSException e) {
			LOG.error(e.getMessage(), e);
		}
	}
}
