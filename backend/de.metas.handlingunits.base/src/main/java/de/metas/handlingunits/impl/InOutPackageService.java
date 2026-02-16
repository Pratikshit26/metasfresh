/*
 * #%L
 * de.metas.handlingunits.base
     
 * #L%
 */

package de.metas.handlingunits.impl;

import de.metas.mpackage.Package;
import de.metas.mpackage.PackageId;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class InOutPackageService
{
	private final InOutPackageRepository inOutPackageRepository;

	public Package getPackageByIdIncludingDetails(@NonNull final PackageId packageId)
	{
		 return inOutPackageRepository.getPackageById(packageId);
	}

}
