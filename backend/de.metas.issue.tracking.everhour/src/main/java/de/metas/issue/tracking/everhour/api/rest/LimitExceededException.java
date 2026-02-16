/*
 * #%L
 * de.metas.issue.tracking.everhour
     
 * #L%
 */

package de.metas.issue.tracking.everhour.api.rest;

import lombok.Getter;

import java.time.Duration;

@Getter
public class LimitExceededException extends RuntimeException
{
	private final Duration retryAfter;

	public LimitExceededException(final Duration retryAfter)
	{
		super();
		this.retryAfter = retryAfter;
	}
}
