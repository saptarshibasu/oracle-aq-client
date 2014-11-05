package com.saptarshibasu.poc.oracleaqclient.jms;

import java.util.HashMap;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.perf4j.StopWatch;
import org.perf4j.slf4j.Slf4JStopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageProcessor implements MessageListener {
	
	private static final Logger LOG = LoggerFactory.getLogger(MessageProcessor.class);
	
	private static final String MSG_TYPE = "MSG_TYPE"; 
	
    private Map<String,Long> statistics = new HashMap<String, Long>();
    
    public Map<String, Long> getStatistics() {
		return statistics;
	}

	public void onMessage(Message msg) {
		StopWatch stopWatch = new Slf4JStopWatch("JMS Message");
		try {

			String messageType = msg.getStringProperty(MSG_TYPE);
			synchronized(statistics)
			{
				if(statistics.get(messageType) == null)
				{
					statistics.put(messageType, 1L);
				}
				else
				{
					statistics.put(messageType, statistics.get(messageType) + 1);
				}
			}
			
			String msgText;
			if (msg instanceof TextMessage) {
				msgText = ((TextMessage) msg).getText();
			} else {

				msgText = msg.toString();
			}

			LOG.debug("Message Received: {}, {}, {}", msgText, msg.getJMSRedelivered(), msg.getJMSMessageID());
			//throw new RuntimeException();
		} catch (JMSException jmse) {
			LOG.error("An exception occurred: {}", jmse.getMessage(), jmse);
		}
		finally
		{
			stopWatch.stop();
		}
	}
}
