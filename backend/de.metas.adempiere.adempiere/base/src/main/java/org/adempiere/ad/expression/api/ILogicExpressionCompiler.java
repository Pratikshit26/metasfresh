package org.adempiere.ad.expression.api;

 



public interface ILogicExpressionCompiler extends IExpressionCompiler<Boolean, ILogicExpression>
{
	String SYSCONFIG_UseOperatorPrecedence = "org.adempiere.ad.expression.api.ILogicExpressionCompiler.UseOperatorPrecedence";
	boolean DEFAULT_UseOperatorPrecedence = false;

	boolean isUseOperatorPrecedence();

	void setUseOperatorPrecedence(boolean enabled);

}
