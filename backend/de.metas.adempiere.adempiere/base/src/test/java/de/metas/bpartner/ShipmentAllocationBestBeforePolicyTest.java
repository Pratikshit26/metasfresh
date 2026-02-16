package de.metas.bpartner;

import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.Value;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
     
 * #L%
 */

public class ShipmentAllocationBestBeforePolicyTest
{
	private final BestBeforeDateHolder bb_2019_09_01 = BestBeforeDateHolder.of(LocalDate.of(2019, Month.SEPTEMBER, 1));
	private final BestBeforeDateHolder bb_2019_09_02 = BestBeforeDateHolder.of(LocalDate.of(2019, Month.SEPTEMBER, 2));
	private final BestBeforeDateHolder bb_null = BestBeforeDateHolder.of(null);

	@Test
	public void comparator_ExpiringFirst()
	{
		final List<BestBeforeDateHolder> result = Stream.of(bb_2019_09_01, bb_2019_09_02)
				.sorted(ShipmentAllocationBestBeforePolicy.Expiring_First.comparator(BestBeforeDateHolder::getBestBeforeDate))
				.collect(Collectors.toList());
		assertThat(result).containsExactly(bb_2019_09_01, bb_2019_09_02);
	}

	@Test
	public void comparator_ExpiringFirst_NullsLast()
	{
		final List<BestBeforeDateHolder> result = Stream.of(bb_2019_09_01, bb_null)
				.sorted(ShipmentAllocationBestBeforePolicy.Expiring_First.comparator(BestBeforeDateHolder::getBestBeforeDate))
				.collect(Collectors.toList());
		assertThat(result).containsExactly(bb_2019_09_01, bb_null);
	}

	@Test
	public void comparator_ExpiringLast()
	{
		final List<BestBeforeDateHolder> result = Stream.of(bb_2019_09_01, bb_2019_09_02)
				.sorted(ShipmentAllocationBestBeforePolicy.Newest_First.comparator(BestBeforeDateHolder::getBestBeforeDate))
				.collect(Collectors.toList());
		assertThat(result).containsExactly(bb_2019_09_02, bb_2019_09_01);
	}

	@Test
	public void comparator_ExpiringLast_NullsLast()
	{
		final List<BestBeforeDateHolder> result = Stream.of(bb_2019_09_01, bb_null)
				.sorted(ShipmentAllocationBestBeforePolicy.Newest_First.comparator(BestBeforeDateHolder::getBestBeforeDate))
				.collect(Collectors.toList());
		assertThat(result).containsExactly(bb_2019_09_01, bb_null);
	}

	@Test
	public void test_ReferenceListAwareEnums_getAD_Reference_ID()
	{
		assertThat(ReferenceListAwareEnums.getAD_Reference_ID(ShipmentAllocationBestBeforePolicy.Expiring_First))
				.isEqualTo(ShipmentAllocationBestBeforePolicy.AD_REFERENCE_ID.getRepoId());
	}

	@Value(staticConstructor = "of")
	private static class BestBeforeDateHolder
	{
		LocalDate bestBeforeDate;
	}
}
