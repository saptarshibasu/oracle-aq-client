package com.saptarshibasu.poc.oracleaqclient.initializer;

import java.lang.management.ManagementFactory;
import java.util.List;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.saptarshibasu.poc.oracleaqclient.monitoring.Monitoring;

public class MonitoringInitializer implements Initializer{
	
	private static final Logger LOG = LoggerFactory.getLogger(MonitoringInitializer.class);
	
	private List<Monitoring> monitoringMBeans;
	
	public MonitoringInitializer(List<Monitoring> monitoringMBeans)
	{
		this.monitoringMBeans = monitoringMBeans;
	}

	@Override
	public void init() {
		for(Monitoring monitoringMBean : monitoringMBeans)
		{
			MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
			try {
				mBeanServer.registerMBean(monitoringMBean, new ObjectName("com.saptarshi.poc:name="+monitoringMBean.getClass().getSimpleName()));
			} catch (InstanceAlreadyExistsException e) {
				LOG.error(e.getMessage(), e);
			} catch (MBeanRegistrationException e) {
				LOG.error(e.getMessage(), e);
			} catch (NotCompliantMBeanException e) {
				LOG.error(e.getMessage(), e);
			} catch (MalformedObjectNameException e) {
				LOG.error(e.getMessage(), e);
			}
		}
	}

}
