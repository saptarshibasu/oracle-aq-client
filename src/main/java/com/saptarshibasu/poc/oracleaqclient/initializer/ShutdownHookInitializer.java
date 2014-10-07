package com.saptarshibasu.poc.oracleaqclient.initializer;

import com.saptashibasu.poc.oracleaqclient.shutdownhook.ShutdownHook;

public class ShutdownHookInitializer implements Initializer{
	
	private ShutdownHook shutdownHook;
	
	public ShutdownHookInitializer(ShutdownHook shutdownHook)
	{
		this.shutdownHook = shutdownHook;
	}

	@Override
	public void init() {
		Runtime.getRuntime().addShutdownHook(shutdownHook);
	}
}
