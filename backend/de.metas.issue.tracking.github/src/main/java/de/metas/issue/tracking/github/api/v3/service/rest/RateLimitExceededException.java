/*
 * #%L
 * de.metas.issue.tracking.github
     
 * #L%
 */

package de.metas.issue.tracking.github.api.v3.service.rest;

import de.metas.issue.tracking.github.api.v3.model.RateLimit;
import lombok.Getter;
import lombok.NonNull;

@Getter
public class RateLimitExceededException extends RuntimeException
{
	@NonNull
	private final String errorMessage;

	@NonNull
	private final RateLimit rateLimit;

	public RateLimitExceededException(@NonNull final String errorMessage,
			                          @NonNull final RateLimit rateLimit)
	{
		super(errorMessage);

		this.errorMessage = errorMessage;
		this.rateLimit = rateLimit;
	}
}
