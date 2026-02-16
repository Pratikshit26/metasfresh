package de.metas.dataentry.layout;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.dataentry.DataEntrySubTabId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.exceptions.AdempiereException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
     
 * #L%
 */

@Value
public class DataEntryLayout
{
	public static DataEntryLayout empty(@NonNull final AdWindowId windowId, @NonNull final AdTableId mainTableId)
	{
		return builder().windowId(windowId).mainTableId(mainTableId).build();
	}

	AdWindowId windowId;
	AdTableId mainTableId;
	ImmutableList<DataEntryTab> tabs;

	@Builder
	private DataEntryLayout(
			@NonNull final AdWindowId windowId,
			@NonNull final AdTableId mainTableId,
			@NonNull @Singular final List<DataEntryTab> tabs)
	{
		this.windowId = windowId;
		this.mainTableId = mainTableId;
		this.tabs = ImmutableList.copyOf(tabs);
	}

	public boolean isEmpty()
	{
		return tabs.isEmpty();
	}

	public Optional<DataEntryTab> getFirstTabMatching(@NonNull final Predicate<DataEntryTab> predicate)
	{
		return tabs.stream().filter(predicate).findFirst();
	}

	public Set<DataEntrySubTabId> getSubTabIds()
	{
		return tabs.stream()
				.flatMap(DataEntryTab::streamSubTabIds)
				.collect(ImmutableSet.toImmutableSet());
	}

	public DataEntrySubTab getSubTabById(@NonNull final DataEntrySubTabId subTabId)
	{
		return tabs.stream()
				.map(tab -> tab.getSubTabByIdIfPresent(subTabId).orElse(null))
				.filter(Objects::nonNull)
				.findFirst()
				.orElseThrow(() -> new AdempiereException("@NotFound@ " + subTabId + " in " + this));
	}
}
