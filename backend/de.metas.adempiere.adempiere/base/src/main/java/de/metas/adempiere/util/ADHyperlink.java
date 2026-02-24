package de.metas.adempiere.util;

 


import java.util.HashMap;
import java.util.Map;

public class ADHyperlink
{
	public static enum Action
	{
		ShowWindow
	};

	private Action action;
	private Map<String, String> params = new HashMap<String, String>();

	public ADHyperlink(Action action, Map<String, String> params)
	{
		super();
		this.action = action;
		this.params = params;
	}

	public Action getAction()
	{
		return action;
	}

	public Map<String, String> getParameters()
	{
		return params;
	}
}
