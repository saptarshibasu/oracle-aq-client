package com.saptarshibasu.poc.oracleaqclient.jms;

import javax.jms.ConnectionFactory;
import javax.sql.DataSource;

import oracle.jms.AQjmsFactory;
 
public class OracleAqConnectionFactoryCreator  
{
    private DataSource dataSource;   
    
	public OracleAqConnectionFactoryCreator(DataSource dataSource) 
    {
        this.dataSource = dataSource;
    }
   
    public ConnectionFactory getConnectionFactory() throws Exception 
    {
         ConnectionFactory connectionFactory = AQjmsFactory.getConnectionFactory(dataSource);
        
         return connectionFactory;
    }
 
}