package com.saptarshibasu.poc.oracleaqclient.initializer;

import java.util.List;

public class ServerInitializer implements Initializer{
	
	public List<Initializer> initializers;
	
	public ServerInitializer(List<Initializer> initializers)
	{
		this.initializers = initializers;
	}
	
	public void init()
	{
		for(Initializer initializer : initializers)
		{
			initializer.init();
		}
	}
}
