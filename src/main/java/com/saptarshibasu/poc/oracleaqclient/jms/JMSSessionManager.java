package com.saptarshibasu.poc.oracleaqclient.jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JMSSessionManager{
	
	private static final Logger LOG = LoggerFactory.getLogger(JMSSessionManager.class);
	
	private ConnectionFactory connectionFactory;
	private MessageProcessor messageProcessor;
	private String destination;
	private String subscriptionName;
	private Integer concurrency;

	private Connection connection;
	public Connection getConnection() {
		return connection;
	}

	private Session session;

	public JMSSessionManager(ConnectionFactory connectionFactory, MessageProcessor messageProcessor, String destination, String subscriptionName, Integer concurrency)
	{
		this.connectionFactory = connectionFactory;
		this.messageProcessor = messageProcessor;
		this.destination = destination;
		this.subscriptionName = subscriptionName;
		this.concurrency = concurrency;
	}
	
	public void initializeConnection()
	{

		try 
		{
			connection = connectionFactory.createConnection();
			for(int i=0; i<concurrency;i++)
			{
				session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
				session.createDurableSubscriber(session.createTopic(destination), subscriptionName);
				session.setMessageListener(messageProcessor);
			}
			connection.start();
		} 
		catch (JMSException e) 
		{
			LOG.error(e.getMessage(), e);
		}
		
	}
	
	public void closeConnection()
	{
		try {
			connection.close();
		} catch (JMSException e) {
			LOG.error(e.getMessage(), e);
		}
	}
	
	public void setExceptionListener(ExceptionListener exceptionListener)
	{
		try {
			connection.setExceptionListener(exceptionListener);
		} catch (JMSException e) {
			LOG.error(e.getMessage(), e);
		}
	}
}
