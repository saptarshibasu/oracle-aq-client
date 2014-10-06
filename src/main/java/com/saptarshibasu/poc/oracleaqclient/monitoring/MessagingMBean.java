package com.saptarshibasu.poc.oracleaqclient.monitoring;

import java.util.Map;
import java.util.Set;

import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.AttributeNotFoundException;
import javax.management.DynamicMBean;
import javax.management.InvalidAttributeValueException;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanException;
import javax.management.MBeanInfo;
import javax.management.ReflectionException;

import com.saptarshibasu.poc.oracleaqclient.jms.MessageProcessor;

public class MessagingMBean implements DynamicMBean, Monitoring{
	
	private MessageProcessor messageProcessor;
	
	private int countOfmessageTypes=-1;
	
	private MBeanInfo mBeanInfo=null;
	
	private MBeanAttributeInfo[] attributes;
	
	public MessagingMBean(MessageProcessor messageProcessor)
	{
		this.messageProcessor = messageProcessor;
	}

	@Override
	public Object getAttribute(String attribute)
			throws AttributeNotFoundException, MBeanException,
			ReflectionException {
		
		return messageProcessor.getStatistics().get(attribute);
	}

	@Override
	public void setAttribute(Attribute attribute)
			throws AttributeNotFoundException, InvalidAttributeValueException,
			MBeanException, ReflectionException {
	}

	@Override
	public AttributeList getAttributes(String[] attributes) {

		AttributeList attributeList = new AttributeList();
		
		Map<String, Long> statistics = messageProcessor.getStatistics();
		Set<String> keySet = statistics.keySet();

		for(String key : keySet)
		{
			attributeList.add(new Attribute(key, statistics.get(key)));
		}

		return attributeList;
	}

	@Override
	public AttributeList setAttributes(AttributeList attributes) {
		return null;
	}

	@Override
	public Object invoke(String actionName, Object[] params, String[] signature)
			throws MBeanException, ReflectionException {
		System.out.println("invoke");
		return null;
	}

	@Override
	public MBeanInfo getMBeanInfo() {
		if(messageProcessor.getStatistics().size() != countOfmessageTypes)
			return buildMBeanInfo();
		else 
			return mBeanInfo;
	}
	
	private MBeanInfo buildMBeanInfo()
	{
		attributes = new MBeanAttributeInfo[messageProcessor.getStatistics().size()];
		
		Map<String, Long> statistics = messageProcessor.getStatistics();
		Set<String> keySet = statistics.keySet();
		
		countOfmessageTypes = statistics.size();
		
		int count = 0;
		for(String key : keySet)
		{
			attributes[count] = new MBeanAttributeInfo(key, statistics.get(key).getClass().getName(), "Count of "+ key, true, false, false);
			count++;
		}
		mBeanInfo = new MBeanInfo(this.getClass().getName(),
                "JMS client message processing statistics",
                attributes,
                null,
                null,
                null);
		
		return mBeanInfo;
	}

}
