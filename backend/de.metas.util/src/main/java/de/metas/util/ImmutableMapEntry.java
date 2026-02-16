package de.metas.util;

import javax.annotation.Nullable;
import java.util.Map;

/*
 * #%L
 * de.metas.util
     
 * #L%
 */

@lombok.Value(staticConstructor = "of")
public class ImmutableMapEntry<K, V> implements Map.Entry<K, V>
{
	@Nullable K key;
	@Nullable V value;

	@Override
	@Deprecated
	public V setValue(final V value)
	{
		throw new UnsupportedOperationException();
	}

	public boolean isKeyNotNull()
	{
		return key != null;
	}

	public boolean isValueNotNull()
	{
		return key != null;
	}

	public boolean isKeyAndValueNotNull()
	{
		return key != null && value != null;
	}
}
