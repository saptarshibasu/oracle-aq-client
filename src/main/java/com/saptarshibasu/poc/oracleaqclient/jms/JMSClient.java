package com.saptarshibasu.poc.oracleaqclient.jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Session;

import com.saptarshibasu.poc.oracleaqclient.initializer.Initializer;

public class JMSClient implements Initializer{
	
	private ConnectionFactory connectionFactory;
	private MessageProcessor messageProcessor;
	private String destination;
	private String subscriptionName;
	private Integer concurrency;

	private Connection connection;
	private Session session;

	public JMSClient(ConnectionFactory connectionFactory, MessageProcessor messageProcessor, String destination, String subscriptionName, Integer concurrency)
	{
		this.connectionFactory = connectionFactory;
		this.messageProcessor = messageProcessor;
		this.destination = destination;
		this.subscriptionName = subscriptionName;
		this.concurrency = concurrency;
	}
	
	public void refresh()
	{
		try {
			connection.close();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		init();
	}

	public void init()
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
			e.printStackTrace();
		}
		
	}
}
