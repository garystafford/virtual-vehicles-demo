package com.example.ms.valet.config;

import java.util.Properties;

import com.strategicgains.restexpress.exception.ConfigurationException;

public class MetricsConfig
{
	private static final String METRICS_IS_ENABLED_PROPERTY = "metrics.isEnabled";
	private static final String METRICS_PREFIX_PROPERTY = "metrics.prefix";
	private static final String GRAPHITE_IS_ENABLED_PROPERTY = "metrics.graphite.isEnabled";
	private static final String GRAPHITE_HOST_PROPERTY = "metrics.graphite.host";
	private static final String GRAPHITE_PORT_PROPERTY = "metrics.graphite.port";
	private static final String GRAPHITE_PUBLISHING_SECONDS_PROPERTY = "metrics.graphite.publishSeconds";

	private boolean isEnabled;
	private boolean isGraphiteEnabled;
	private String graphiteHost;
	private Integer graphitePort;
	private Integer publishSeconds;
	private String prefix;

	public MetricsConfig(Properties p)
	{
		isEnabled = Boolean.parseBoolean(p.getProperty(METRICS_IS_ENABLED_PROPERTY, "true"));
		if (!isEnabled) return;

		isGraphiteEnabled = Boolean.parseBoolean(p.getProperty(GRAPHITE_IS_ENABLED_PROPERTY, "true"));
		if (!isGraphiteEnabled) return;
		
		prefix = p.getProperty(METRICS_PREFIX_PROPERTY);
		
		if (prefix == null)
		{
			throw new ConfigurationException("Please define a metrics prefix for property: " + METRICS_PREFIX_PROPERTY);
		}

		graphiteHost = p.getProperty(GRAPHITE_HOST_PROPERTY);

		if (graphiteHost == null)
		{
			throw new ConfigurationException("Please define a graphite host for property: " + GRAPHITE_HOST_PROPERTY);
		}

		try
		{
			graphitePort = Integer.parseInt(p.getProperty(GRAPHITE_PORT_PROPERTY));
		}
		catch (NumberFormatException e)
		{
			throw new ConfigurationException("Please define a graphite port for property: " + GRAPHITE_PORT_PROPERTY, e);
		}

		try
		{
			publishSeconds = Integer.parseInt(p.getProperty(GRAPHITE_PUBLISHING_SECONDS_PROPERTY));
		}
		catch (NumberFormatException e)
		{
			throw new ConfigurationException("Please define how frequently (in seconds) to publish to graphite in property: "
			        + GRAPHITE_PUBLISHING_SECONDS_PROPERTY, e);
		}
	}

	public boolean isEnabled()
	{
		return isEnabled;
	}

	public boolean isGraphiteEnabled()
	{
		return isGraphiteEnabled;
	}

	public String getGraphiteHost()
	{
		return graphiteHost;
	}

	public Integer getGraphitePort()
	{
		return graphitePort;
	}

	public Integer getPublishSeconds()
	{
		return publishSeconds;
	}
	
	public String getPrefix()
	{
		return prefix;
	}
}
