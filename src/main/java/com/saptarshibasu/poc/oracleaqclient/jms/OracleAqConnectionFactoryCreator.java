package com.saptarshibasu.poc.oracleaqclient.jms;

import javax.jms.ConnectionFactory;
import javax.jms.QueueConnectionFactory;
import javax.sql.DataSource;

import oracle.jms.AQjmsFactory;
 
public class OracleAqConnectionFactoryCreator  
{
    private DataSource dataSource;   
    
	public OracleAqConnectionFactoryCreator(DataSource dataSource) 
    {
        this.dataSource = dataSource;
    }
   
    public QueueConnectionFactory getQueueConnectionFactory() throws Exception 
    {
         QueueConnectionFactory connectionFactory = AQjmsFactory.getQueueConnectionFactory(dataSource);
        
         return connectionFactory;
    }
 
}