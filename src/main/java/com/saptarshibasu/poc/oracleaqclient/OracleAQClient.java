package com.saptarshibasu.poc.oracleaqclient;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class OracleAQClient {

	public static void main(String[] args) 
	{
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("application-context.xml");
		while(true)
		{
			try {
				Thread.sleep(100000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
