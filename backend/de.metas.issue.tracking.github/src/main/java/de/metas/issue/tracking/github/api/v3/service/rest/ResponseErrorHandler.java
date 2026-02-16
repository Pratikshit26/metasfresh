/*
 * #%L
 * de.metas.issue.tracking.github
     
 * #L%
 */

package de.metas.issue.tracking.github.api.v3.service.rest;

import de.metas.issue.tracking.github.api.v3.model.RateLimit;
import de.metas.issue.tracking.github.api.v3.service.RateLimitService;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.DefaultResponseErrorHandler;

import java.io.IOException;
import java.util.Optional;

@Component
public class ResponseErrorHandler extends DefaultResponseErrorHandler
{

	private final RateLimitService rateLimitService;

	public ResponseErrorHandler(final RateLimitService rateLimitService)
	{
		super();
		this.rateLimitService = rateLimitService;
	}

	@Override public void handleError(final ClientHttpResponse response) throws IOException
	{
		if (response.getStatusCode().is4xxClientError())
		{
			final Optional<RateLimit> rateLimit = rateLimitService.extractRateLimit(response.getHeaders());

			if (rateLimit.isPresent() && rateLimit.get().getRemainingReq() == 0)
			{
				throw new RateLimitExceededException(response.getBody().toString(), rateLimit.get());
			}
		}

		super.handleError(response);
	}
}
