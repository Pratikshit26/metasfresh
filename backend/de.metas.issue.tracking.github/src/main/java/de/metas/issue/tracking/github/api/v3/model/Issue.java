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

import java.time.Instant;
import java.util.List;

@Value
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(builder = Issue.IssueBuilder.class)
public class Issue
{
	@JsonProperty("id")
	String id;

	@JsonProperty("html_url")
	String htmlUrl;

	@JsonProperty("number")
	Integer number;

	@JsonProperty("title")
	String title;

	@JsonProperty("labels")
	List<Label> labelList;

	@JsonProperty("state")
	String state;

	@JsonProperty("assignee")
	User assignee;

	@JsonProperty("milestone")
	GithubMilestone githubMilestone;

	@JsonProperty("body")
	String body;

	@JsonProperty("pull_request")
	PullRequest pullRequest;

	@JsonProperty("updated_at")
	Instant updatedAt;

	public boolean isPullRequest()
	{
		return pullRequest != null;
	}
}


