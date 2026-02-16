/*
 * #%L
 * de.metas.serviceprovider.base
     
 * #L%
 */

package de.metas.serviceprovider.github.link;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableSet;
import de.metas.serviceprovider.github.GithubIdSearchKey;
import lombok.NonNull;
import lombok.Value;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Value
public class GithubIssueLinkMatcher
{
	static String PROJECT_OWNER_TOKEN = "ownerToken";
	static String PROJECT_REFERENCE_TOKEN = "projectToken";
	static String OWNER_GROUP = "owner";
	static String PROJECT_GROUP ="project";
	static String ISSUE_NO_GROUP = "issueNo";
	static String ISSUE_LINK_PATTERN = "https:\\/\\/github.com\\/(?<owner>ownerToken)\\/(?<project>projectToken)\\/issues\\/(?<issueNo>[0-9]+)";

	Pattern linkPattern;

	public static GithubIssueLinkMatcher of(final ImmutableSet<String> owners, final ImmutableSet<String> projects)
	{
		final Joiner joiner = Joiner.on("|");

		final String pattern = ISSUE_LINK_PATTERN
				.replace(PROJECT_OWNER_TOKEN,joiner.join(owners))
				.replace(PROJECT_REFERENCE_TOKEN, joiner.join(projects));

		return new GithubIssueLinkMatcher(Pattern.compile(pattern));
	}

	@NonNull
	public Optional<GithubIssueLink> getFirstMatch(@NonNull final String textToParse)
	{
		final Matcher matcher = linkPattern.matcher(textToParse);

		if (matcher.find())
		{
			return Optional.of(buildIssueLink(matcher));
		}

		return Optional.empty();
	}

	@NonNull
	private GithubIssueLink buildIssueLink(@NonNull final Matcher matcher)
	{
		return GithubIssueLink.builder()
				.githubIdSearchKey(
						GithubIdSearchKey
								.builder()
								.repositoryOwner(matcher.group(OWNER_GROUP))
								.repository(matcher.group(PROJECT_GROUP))
								.issueNo(matcher.group(ISSUE_NO_GROUP))
								.build())
				.url(matcher.group().trim())
				.build();
	}

	private GithubIssueLinkMatcher(@NonNull final Pattern pattern)
	{
		this.linkPattern = pattern;
	}
}
