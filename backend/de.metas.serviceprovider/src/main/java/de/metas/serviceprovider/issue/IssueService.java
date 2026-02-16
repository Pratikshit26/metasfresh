/*
 * #%L
 * de.metas.serviceprovider.base
     
 * #L%
 */

package de.metas.serviceprovider.issue;

import com.google.common.collect.ImmutableList;
import de.metas.serviceprovider.issue.hierarchy.IssueHierarchy;
import de.metas.serviceprovider.issue.interceptor.AddIssueProgressRequest;
import de.metas.serviceprovider.issue.interceptor.HandleParentChangedRequest;
import de.metas.serviceprovider.timebooking.TimeBooking;
import de.metas.serviceprovider.timebooking.TimeBookingRepository;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Objects;

@Service
public class IssueService
{
	private final IssueRepository issueRepository;
	private final TimeBookingRepository timeBookingRepository;

	public IssueService(final IssueRepository issueRepository, final TimeBookingRepository timeBookingRepository)
	{
		this.issueRepository = issueRepository;
		this.timeBookingRepository = timeBookingRepository;
	}

	public void handleParentChanged(@NonNull final HandleParentChangedRequest request)
	{

		if (request.getCurrentParentId() != null)
		{
			final IssueHierarchy issueHierarchy = issueRepository.buildUpStreamIssueHierarchy(request.getCurrentParentId());

			issueHierarchy
					.getUpStreamForId(request.getCurrentParentId())
					.forEach(issue -> {
								 issue.addAggregatedEffort(request.getCurrentAggregatedEffort());
								 issue.addInvoiceableChildEffort(request.getCurrentInvoicableEffort());
								 recomputeLatestActivityOnSubIssues(issue);

								 issueRepository.save(issue);
							 }
					);
		}

		if (request.getOldParentId() != null)
		{
			final IssueHierarchy issueHierarchy = issueRepository.buildUpStreamIssueHierarchy(request.getOldParentId());

			issueHierarchy.getUpStreamForId(request.getOldParentId())
					.forEach(oldParent -> {

						if (request.getOldAggregatedEffort() != null)
						{
							oldParent.addAggregatedEffort(request.getOldAggregatedEffort().negate());
						}
						if (request.getOldInvoicableEffort() != null)
						{
							oldParent.addInvoiceableChildEffort(request.getOldInvoicableEffort().negate());
						}
						recomputeLatestActivityOnSubIssues(oldParent);

						issueRepository.save(oldParent);
					});
		}
	}

	public void addIssueProgress(@NonNull final AddIssueProgressRequest request)
	{
		final IssueEntity issueEntity = issueRepository.getById(request.getIssueId());

		issueEntity.addIssueEffort(request.getBookedEffort());
		issueEntity.addAggregatedEffort(request.getBookedEffort());

		recomputeLatestActivityOnIssue(issueEntity);

		issueRepository.save(issueEntity);

		if (issueEntity.getParentIssueId() != null)
		{
			issueRepository
					.buildUpStreamIssueHierarchy(issueEntity.getParentIssueId())
					.getUpStreamForId(issueEntity.getParentIssueId())
					.forEach(parentIssue ->
							 {
								 parentIssue.addAggregatedEffort(request.getBookedEffort());

								 recomputeLatestActivityOnSubIssues(parentIssue);

								 issueRepository.save(parentIssue);
							 });
		}
	}

	public void processIssue(@NonNull final IssueId issueId)
	{
		final IssueEntity invoicedIssue = getById(issueId)
				.toBuilder()
				.status(Status.INVOICED)
				.processed(true)
				.invoicingErrorMsg(null)
				.isInvoicingError(false)
				.build();

		issueRepository.save(invoicedIssue);
	}

	@NonNull
	public IssueEntity getById(@NonNull final IssueId issueId)
	{
		return issueRepository.getById(issueId);
	}

	@NonNull
	public void save(@NonNull final IssueEntity issueEntity)
	{
		issueRepository.save(issueEntity);
	}

	private void recomputeLatestActivityOnSubIssues(@NonNull final IssueEntity issueEntity)
	{
		final ImmutableList<IssueEntity> subIssues = issueRepository.getDirectlyLinkedSubIssues(issueEntity.getIssueId());

		final Instant mostRecentActivity = subIssues
				.stream()
				.map(IssueEntity::getLatestActivity)
				.filter(Objects::nonNull)
				.max(Instant::compareTo)
				.orElse(null);

		issueEntity.setLatestActivityOnSubIssues(mostRecentActivity);
	}

	private void recomputeLatestActivityOnIssue(@NonNull final IssueEntity issueEntity)
	{
		final ImmutableList<TimeBooking> timeBookings = timeBookingRepository.getAllByIssueId(issueEntity.getIssueId());

		final Instant latestActivityDate = timeBookings.stream()
				.map(TimeBooking::getBookedDate)
				.max(Instant::compareTo)
				.orElse(null);

		issueEntity.setLatestActivityOnIssue(latestActivityDate);
	}
}
