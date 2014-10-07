package com.saptarshibasu.poc.oracleaqclient.initializer;

import java.util.List;
import java.util.concurrent.BlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerInitializer implements Initializer{
	
	private static final Logger LOG = LoggerFactory.getLogger(ServerInitializer.class);
	
	private List<Initializer> initializers;
	
	private BlockingQueue<String> blockingQueue;
	
	public ServerInitializer(List<Initializer> initializers, BlockingQueue<String> blockingQueue)
	{
		this.initializers = initializers;
		this.blockingQueue = blockingQueue;
	}
	
	public void init()
	{
		for(Initializer initializer : initializers)
		{
			initializer.init();
		}
		LOG.info("Server started.");
		blockingQueue.poll();
	}
}
