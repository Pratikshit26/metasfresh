/*
 * #%L
 * de.metas.issue.tracking.github
     
 * #L%
 */

package de.metas.issue.tracking.github.api.v3.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Value;

@Value
@JsonDeserialize(builder = GithubMilestone.GithubMilestoneBuilder.class)
public class GithubMilestone
{
	@JsonProperty("id")
	String id;

	@JsonProperty("html_url")
	String htmlUrl;

	@JsonProperty("title")
	String title;

	@JsonProperty("description")
	String description;

	@JsonProperty("state")
	String state;

	@JsonProperty("due_on")
	String dueDate;

	@Builder
	@JsonIgnoreProperties(ignoreUnknown = true)
	public GithubMilestone(@JsonProperty("id") final String id,
			@JsonProperty("html_url") final String htmlUrl,
			@JsonProperty("title") final String title,
			@JsonProperty("description") final String description,
			@JsonProperty("state") final String state,
			@JsonProperty("due_on") final String dueDate)
	{
		this.id = id;
		this.htmlUrl = htmlUrl;
		this.title = title;
		this.description = description;
		this.state = state;
		this.dueDate = dueDate;
	}
}


