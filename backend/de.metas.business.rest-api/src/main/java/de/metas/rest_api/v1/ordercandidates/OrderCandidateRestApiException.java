package de.metas.rest_api.v1.ordercandidates;

/*
 * #%L
 * de.metas.ordercandidate.rest-api
     
 * #L%
 */

public class OrderCandidateRestApiException extends RuntimeException
{
	private static final long serialVersionUID = 2214884129491787091L;

	public OrderCandidateRestApiException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public OrderCandidateRestApiException(String message)
	{
		super(message);
	}
}
