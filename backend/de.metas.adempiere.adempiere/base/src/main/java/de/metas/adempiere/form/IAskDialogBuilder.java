package de.metas.adempiere.form;

 


/**
 * Asks the user a OK/Cancel question.
 * 
 * @author tsa
 *
 */
public interface IAskDialogBuilder
{
	/**
	 * Shows the popup which asks the user and gets the answer.
	 * 
	 * @return user's answer (true=OK, false=Cancel)
	 */
	boolean getAnswer();

	IAskDialogBuilder setParentWindowNo(int windowNo);

	IAskDialogBuilder setParentComponent(final Object parentCompObj);

	IAskDialogBuilder setAD_Message(String adMessage, Object... params);

	IAskDialogBuilder setAdditionalMessage(final String additionalMessage);

	/**
	 * Sets default answer that will be preselected when the popup is displayed to user.
	 * 
	 * @param defaultAnswer
	 * @return this
	 */
	IAskDialogBuilder setDefaultAnswer(boolean defaultAnswer);
}
