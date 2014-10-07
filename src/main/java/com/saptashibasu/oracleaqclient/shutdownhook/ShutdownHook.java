package com.saptashibasu.oracleaqclient.shutdownhook;

import java.util.concurrent.BlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.saptarshibasu.poc.oracleaqclient.jms.JMSSessionManager;

public class ShutdownHook extends Thread{
	
	private static final Logger LOG = LoggerFactory.getLogger(ShutdownHook.class);
	
	private JMSSessionManager jmsSessionManager;
	private BlockingQueue<String> blockingQueue;
	
	public ShutdownHook(JMSSessionManager jmsSessionManager, BlockingQueue<String> blockingQueue)
	{
		this.jmsSessionManager = jmsSessionManager;
		this.blockingQueue = blockingQueue;
	}

	@Override
	public void run()
	{
		LOG.info("Closing JMS connection.");
		jmsSessionManager.closeConnection();
		LOG.info("Shutting down server.");
		blockingQueue.offer("SHUTDOWN");
	}
}
