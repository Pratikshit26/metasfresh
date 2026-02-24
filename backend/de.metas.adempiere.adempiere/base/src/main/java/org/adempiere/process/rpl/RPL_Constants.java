package org.adempiere.process.rpl;

 


public final class RPL_Constants
{
	private RPL_Constants()
	{
	}

	public static final String XML_ATTR_AD_Client_Value = "AD_Client_Value";
	public static final String XML_ATTR_Version = "Version";

	/**
	 * Attribute can be used to specifiy the caller's metasfresh AD_Session_ID
	 */
	public static final String XML_ATTR_AD_SESSION_ID = "AD_Session_ID"; // 03132

	public static final String XML_ATTR_REPLICATION_EVENT = "ReplicationEvent";
	public static final String XML_ATTR_REPLICATION_TYPE = "ReplicationType";
	public static final String XML_ATTR_REPLICATION_MODE = "ReplicationMode";

	public static final String XML_ATTR_SEQUENCE_NO = "SequenceNo"; // attribute was originally added due to 02596

	public static final String XML_ATTR_REPLICATION_TrxName = "TrxName"; // 06231
}
