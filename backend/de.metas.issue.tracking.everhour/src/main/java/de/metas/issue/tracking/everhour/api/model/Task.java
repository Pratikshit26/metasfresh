/*
 * #%L
 * de.metas.issue.tracking.everhour
     
 * #L%
 */

package de.metas.issue.tracking.everhour.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(builder = Task.TaskBuilder.class)
public class Task
{
	@JsonProperty("id")
	String id;

	@JsonProperty("url")
	String url;
}
