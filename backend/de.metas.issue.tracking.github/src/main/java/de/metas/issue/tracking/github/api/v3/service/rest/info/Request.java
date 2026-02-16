/*
 * #%L
 * de.metas.issue.tracking.github
     
 * #L%
 */

package de.metas.issue.tracking.github.api.v3.service.rest.info;

import lombok.Builder;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;
import org.springframework.util.MultiValueMap;

import javax.annotation.Nullable;
import java.util.List;

@Value
@Builder
@ToString(exclude = "oAuthToken")
public class Request
{
	@NonNull
	final String baseURL;

	@NonNull
	private String oAuthToken;

	@Nullable
	final List<String> pathVariables;

	@Nullable
	final MultiValueMap<String, String> queryParameters;

	@Nullable
	final String requestBody;
}
