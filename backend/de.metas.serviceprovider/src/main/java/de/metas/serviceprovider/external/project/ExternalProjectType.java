/*
 * #%L
 * de.metas.serviceprovider.base
     
 * #L%
 */

package de.metas.serviceprovider.external.project;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

import static de.metas.serviceprovider.model.X_S_ExternalProjectReference.PROJECTTYPE_Budget;
import static de.metas.serviceprovider.model.X_S_ExternalProjectReference.PROJECTTYPE_Development;

@AllArgsConstructor
@Getter
public enum ExternalProjectType
{
	EFFORT(PROJECTTYPE_Development),
	BUDGET(PROJECTTYPE_Budget);

	private final String value;

	public static Optional<ExternalProjectType> getTypeByValue( final String value )
	{
		return Arrays.stream(values())
				.filter(projectType -> projectType.getValue().equals(value))
				.findFirst();
	}
}
