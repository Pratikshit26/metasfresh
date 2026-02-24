package org.adempiere.ad.expression.api;

 


import org.adempiere.ad.expression.exceptions.ExpressionEvaluationException;
import org.compiere.util.Evaluatee;

/**
 * Expression Evaluator
 * 
 * @author tsa
 * 
 * @param <ET> expression type
 * @param <V> expression evaluation result type
 */
public interface IExpressionEvaluator<ET extends IExpression<V>, V>
{
	enum OnVariableNotFound
	{
		/**
		 * Use default value for unparsed token. This means:
		 * <ul>
		 * <li>in case of {@link IStringExpression} it will replace the unparsed token with empty string
		 * <li>in case of {@link ILogicExpression} this is not supported and {@link ExpressionEvaluationException} will be thrown
		 * </ul>
		 */
		Empty
		/**
		 * Preserve context variable as it was. This means:
		 * <ul>
		 * <li>in case of {@link IStringExpression} it will preserve the unparsed token EXACTLY how it is (together with it's markers, e.g. <code>@MyVariable@</code>)
		 * <li>in case of {@link ILogicExpression} this is not supported and {@link ExpressionEvaluationException} will be thrown
		 * </ul>
		 */
		, Preserve
		/**
		 * Immediately return no result. This means:
		 * <ul>
		 * <li>in case of {@link IStringExpression} it will return {@link IStringExpression#EMPTY_RESULT}
		 * <li>in case of {@link ILogicExpression} it will return <code>false</code>
		 * </ul>
		 */
		, ReturnNoResult
		/**
		 * Fail and throw {@link ExpressionEvaluationException}
		 */
		, Fail
		//
		;
	}

	/**

	 * @return resulting value or "no result" (which you could check it with {@link #isNoResult(Object)}.
	 * @throws ExpressionEvaluationException in case evaluation failed.
	 */
	V evaluate(final Evaluatee ctx, final ET expression, final OnVariableNotFound onVariableNotFound) throws ExpressionEvaluationException;
}
