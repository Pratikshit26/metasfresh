/**
 * Expressions evaluations package.
 * 
 * To compile and expression please use {@link org.adempiere.ad.expression.api.IExpressionFactory} service.
 * 
 * <h2>String expressions</h2>
 * See {@link org.adempiere.ad.expression.api.IStringExpression}.
 * 
 * <h2>Logic expressions</h2> 
 * 
 * The packages permits the evaluation of logical functions. It converts strings to an expression tree, which it can evaluate.
 * Parentheses are permitted and expressions can be evaluated with or without operator precedence. 
 * Each expression is converted to a tree once, then evaluated whenever parameters are changed.
 * 
 * Expression have the following format:
 * <pre>
 * format := [parenthesis] {expression} [parenthesis] [{logic} [parenthesis] {expression} [parenthesis]]
 * expression := @{context}@{operand}{value} or @{context}@{operand}@{context}@
 * logic := {|}|{&}
 * parenthesis := {(}|{)}
 * context := any global or window context 
 * value := strings or numbers
 * operand := eq{=}, gt{>}, le{<}, not{^!}
 * </pre>
 *  
 * Examples:
 * <pre>
 * ((@a@='5' | @b@!@c@) & @d@>3)| (   @x@<'10'& (@y@!@z@) )
 * @AD_Table_ID@=14 | @Language@!GERGER 
 * @PriceLimit@>10 | @PriceList@>@PriceActual@
 * @Name@>J Strings may be in single quotes (optional)
 * </pre>
 * 
 * @see org.adempiere.ad.expression.api.ILogicExpression
 */
package org.adempiere.ad.expression.api;

 


