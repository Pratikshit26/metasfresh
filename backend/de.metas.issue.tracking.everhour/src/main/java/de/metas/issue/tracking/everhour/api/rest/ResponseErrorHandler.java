/*
 * #%L
 * de.metas.issue.tracking.everhour
     
 * #L%
 */

package de.metas.issue.tracking.everhour.api.rest;

import de.metas.util.Check;
import lombok.NonNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.DefaultResponseErrorHandler;

import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Optional;

import static java.time.temporal.ChronoUnit.SECONDS;

@Component
public class ResponseErrorHandler extends DefaultResponseErrorHandler
{

	@Override
	public void handleError(final ClientHttpResponse response) throws IOException
	{
		if (response.getStatusCode().equals(HttpStatus.TOO_MANY_REQUESTS))
		{
			final Optional<Duration> retryAfter = getRetryAfter(response.getHeaders());
			retryAfter.ifPresent(duration -> { throw new LimitExceededException(duration); });
		}

		super.handleError(response);
	}

	@NonNull
	private Optional<Duration> getRetryAfter(@NonNull final HttpHeaders httpHeaders)
	{
		final List<String> retryAfter = httpHeaders.get(HttpHeaders.RETRY_AFTER);

		if (!Check.isEmpty(retryAfter))
		{
			return Optional.of(Duration.of(Long.parseLong(retryAfter.get(0)), SECONDS));
		}
		return Optional.empty();
	}
}
