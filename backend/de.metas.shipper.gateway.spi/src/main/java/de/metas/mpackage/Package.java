/*
 * #%L
 * de.metas.swat.base
     
 * #L%
 */

package de.metas.mpackage;

import de.metas.inout.InOutId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.List;

@Value
@Builder(toBuilder = true)
public class Package
{
	@NonNull PackageId id;
	@Nullable BigDecimal weightInKg;
	@Nullable InOutId inOutId;
	@NonNull List<PackageItem> packageContents;

}
