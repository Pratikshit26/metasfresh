/*
 * #%L
 * de.metas.issue.tracking.everhour
     
 * #L%
 */

package de.metas.issue.tracking.everhour.api.model;

import lombok.Builder;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;
import org.springframework.util.MultiValueMap;

import javax.annotation.Nullable;
import java.util.List;

@Value
@Builder
@ToString(exclude = "apiKey")
public class GetRequest
{
	@NonNull
	final String apiKey;

	@NonNull
	final String baseURL;

	@Nullable
	final List<String> pathVariables;

	@Nullable
	final MultiValueMap<String, String> queryParameters;
}
