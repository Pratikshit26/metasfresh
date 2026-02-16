package de.metas.document.engine;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.collect.ImmutableSet;
import de.metas.ad_reference.ReferenceId;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.NonNull;
import org.compiere.model.X_C_Order;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.Set;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
     
 * #L%
 */

@SuppressWarnings("BooleanMethodIsAlwaysInverted")
public enum DocStatus implements ReferenceListAwareEnum
{
	Drafted(IDocument.STATUS_Drafted),
	Completed(IDocument.STATUS_Completed),
	Approved(IDocument.STATUS_Approved),
	Invalid(IDocument.STATUS_Invalid),
	NotApproved(IDocument.STATUS_NotApproved),
	Voided(IDocument.STATUS_Voided),
	Reversed(IDocument.STATUS_Reversed),
	Closed(IDocument.STATUS_Closed),
	Unknown(IDocument.STATUS_Unknown),
	InProgress(IDocument.STATUS_InProgress),
	WaitingPayment(IDocument.STATUS_WaitingPayment),
	WaitingConfirmation(IDocument.STATUS_WaitingConfirmation),
	;

	public static final ReferenceId AD_REFERENCE_ID = ReferenceId.ofRepoId(X_C_Order.DOCSTATUS_AD_Reference_ID); // 131

	private static final ImmutableSet<DocStatus> COMPLETED_OR_CLOSED_STATUSES = ImmutableSet.of(Completed, Closed);

	private static final ReferenceListAwareEnums.ValuesIndex<DocStatus> index = ReferenceListAwareEnums.index(values());

	private final String code;

	DocStatus(final String code)
	{
		this.code = code;
	}

	@NonNull
	public static Optional<DocStatus> ofCodeOptional(@Nullable final String code)
	{
		return index.optionalOfNullableCode(code);
	}

	@Nullable
	@JsonCreator
	public static DocStatus ofNullableCode(@Nullable final String code)
	{
		return index.ofNullableCode(code);
	}

	@NonNull
	public static DocStatus ofNullableCodeOrUnknown(@Nullable final String code)
	{
		final DocStatus docStatus = ofNullableCode(code);
		return docStatus != null ? docStatus : Unknown;
	}

	public static DocStatus ofCode(@NonNull final String code) {return index.ofCode(code);}

	public static String toCodeOrNull(@Nullable final DocStatus docStatus)
	{
		return docStatus != null ? docStatus.getCode() : null;
	}

	@Override
	@JsonValue
	public String getCode() {return code;}

	public boolean isDrafted()
	{
		return this == Drafted;
	}

	public boolean isDraftedOrInProgress()
	{
		return this == Drafted
				|| this == InProgress;
	}

	public boolean isReversed()
	{
		return this == Reversed;
	}

	public boolean isReversedOrVoided()
	{
		return this == Reversed
				|| this == Voided;
	}

	public boolean isClosedReversedOrVoided()
	{
		return this == Closed
				|| this == Reversed
				|| this == Voided;
	}

	public boolean isCompleted()
	{
		return this == Completed;
	}

	public boolean isClosed()
	{
		return this == Closed;
	}

	public boolean isCompletedOrClosed()
	{
		return COMPLETED_OR_CLOSED_STATUSES.contains(this);
	}

	public static Set<DocStatus> completedOrClosedStatuses()
	{
		return COMPLETED_OR_CLOSED_STATUSES;
	}

	public boolean isCompletedOrClosedOrReversed()
	{
		return this == Completed
				|| this == Closed
				|| this == Reversed;
	}

	public boolean isCompletedOrClosedReversedOrVoided()
	{
		return this == Completed
				|| this == Closed
				|| this == Reversed
				|| this == Voided;
	}

	public boolean isWaitingForPayment()
	{
		return this == WaitingPayment;
	}

	public boolean isInProgress()
	{
		return this == InProgress;
	}

	public boolean isInProgressCompletedOrClosed()
	{
		return this == InProgress
				|| this == Completed
				|| this == Closed;
	}

	public boolean isDraftedInProgressOrInvalid()
	{
		return this == Drafted
				|| this == InProgress
				|| this == Invalid;
	}

	@SuppressWarnings("BooleanMethodIsAlwaysInverted")
	public boolean isDraftedInProgressOrCompleted()
	{
		return this == Drafted
				|| this == InProgress
				|| this == Completed;
	}

	public boolean isNotProcessed()
	{
		return isDraftedInProgressOrInvalid()
				|| this == Approved
				|| this == NotApproved;
	}

	public boolean isVoided() {return this == Voided;}

	public boolean isAccountable()
	{
		return this == Completed
				|| this == Reversed
				|| this == Voided;
	}
}
