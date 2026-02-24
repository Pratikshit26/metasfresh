package  org.adempiere.processing.interfaces;

 


public interface IProcessablePO
{
	public static final String COLUMNNAME_IsError = "IsError";

	public void setIsError(boolean IsError);

	public boolean isError();

	public static final String COLUMNNAME_Processed = "Processed";

	public void setProcessed(boolean Processed);

	public boolean isProcessed();

	public static final String COLUMNNAME_AD_Issue_ID = "AD_Issue_ID";

	public void setAD_Issue_ID(int AD_Issue_ID);

	public int getAD_Issue_ID();
	
	
}
