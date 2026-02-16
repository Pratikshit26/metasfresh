/*
 * #%L
 * de.metas.serviceprovider.base
     
 * #L%
 */

package de.metas.serviceprovider.issue.hierarchy;

import com.google.common.collect.ImmutableList;
import de.metas.serviceprovider.issue.IssueEntity;
import de.metas.serviceprovider.issue.IssueId;
import de.metas.util.Node;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.Optional;

public class IssueHierarchy
{
	private final Node<IssueEntity> root;

	public static IssueHierarchy of(@NonNull final Node<IssueEntity> root)
	{
		return new IssueHierarchy(root);
	}

	/**
	 * @see Node#listAllNodesBelow()
	 */
	public ImmutableList<IssueEntity> listIssues()
	{
		return root.listAllNodesBelow()
				.stream()
				.map(Node::getValue)
				.collect(ImmutableList.toImmutableList());
	}

	public boolean hasNodeForId(@NonNull final IssueId issueId)
	{
		return listIssues().stream().map(IssueEntity::getIssueId).anyMatch(issueId::equals);
	}

	public ImmutableList<IssueEntity> getUpStreamForId(@NonNull final IssueId issueId)
	{
		final Optional<IssueEntity> issue = getIssueForId(issueId);

		if (!issue.isPresent())
		{
			return ImmutableList.of();
		}

		return this.root.getNode(issue.get())
				.map(Node::getUpStream)
				.orElse(new ArrayList<>())
				.stream()
				.map(Node::getValue)
				.collect(ImmutableList.toImmutableList());
	}

	private Optional<IssueEntity> getIssueForId(@NonNull final IssueId issueId)
	{
		return listIssues()
				.stream()
				.filter(issueEntity -> issueId.equalsNullSafe(issueEntity.getIssueId()))
				.findFirst();
	}

	private IssueHierarchy(final Node<IssueEntity> root)
	{
		this.root = root;
	}
}
