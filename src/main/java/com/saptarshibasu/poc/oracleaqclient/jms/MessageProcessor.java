package com.saptarshibasu.poc.oracleaqclient.jms;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.perf4j.StopWatch;
import org.perf4j.slf4j.Slf4JStopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

public class MessageProcessor implements MessageListener {
	
	private static final Logger LOG = LoggerFactory.getLogger(MessageProcessor.class);
	
	private static final String MSG_TYPE = "MSG_TYPE"; 
	
    private Map<String,Long> statistics = new HashMap<String, Long>();
    
	private JdbcTemplate jdbcTemplate; 
	
	private TransactionTemplate transactionTemplate;

    
    public JdbcTemplate getJdbcTemplate()
	{
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate)
	{
		this.jdbcTemplate = jdbcTemplate;
	}

	public TransactionTemplate getTransactionTemplate()
	{
		return transactionTemplate;
	}

	public void setTransactionTemplate(TransactionTemplate transactionTemplate)
	{
		this.transactionTemplate = transactionTemplate;
	}

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
			
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {
				@Override
				protected void doInTransactionWithoutResult(
						TransactionStatus transactionStatus) {
					try {
						LOG.debug("Message Received");
						List valueList = (List) jdbcTemplate.query("SELECT status from STATUS where status=? for update ", new Object[] { "abc" },
								new RowMapper<Object>() {
									@Override
									public Object mapRow(ResultSet rs, int arg1)
											throws SQLException {
										return rs.getString(1);
									}
								});
					}
					catch(Exception e)
					{
						LOG.error("An exception occurred: {}", e.getMessage(), e);
						transactionStatus.setRollbackOnly();
						throw e;
					}
				}
			});
		
			
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
