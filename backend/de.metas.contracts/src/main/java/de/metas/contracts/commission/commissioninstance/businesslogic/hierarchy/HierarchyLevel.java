package de.metas.contracts.commission.commissioninstance.businesslogic.hierarchy;

import static de.metas.util.Check.assumeGreaterOrEqualToZero;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.commission
     
 * #L%
 */

@Value
public class HierarchyLevel implements Comparable<HierarchyLevel>
{
	public static final HierarchyLevel ZERO = of(0);

	@JsonCreator
	public static HierarchyLevel of(int level)
	{
		return new HierarchyLevel(level);
	}

	@Getter(AccessLevel.NONE)
	int level;

	@JsonValue
	public int toInt()
	{
		return level;
	}

	private HierarchyLevel(final int level)
	{
		this.level = assumeGreaterOrEqualToZero(level, "level");
	}

	public HierarchyLevel incByOne()
	{
		return of(this.toInt() + 1);
	}

	@Override
	public int compareTo(@NonNull final HierarchyLevel other)
	{
		return Integer.compare(this.level, other.level);
	}
}
