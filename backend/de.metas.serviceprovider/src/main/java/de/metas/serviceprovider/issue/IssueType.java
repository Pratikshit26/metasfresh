/*
 * #%L
 * de.metas.serviceprovider.base
     
 * #L%
 */

package de.metas.serviceprovider.issue;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import java.util.Optional;
import java.util.stream.Stream;

import static de.metas.serviceprovider.model.X_S_Issue.ISSUETYPE_External;
import static de.metas.serviceprovider.model.X_S_Issue.ISSUETYPE_Internal;

@AllArgsConstructor
@Getter
public enum IssueType
{
	INTERNAL(ISSUETYPE_Internal),
	EXTERNAL(ISSUETYPE_External);

	private final String value;

	@NonNull
	public static Optional<IssueType> getTypeByValue(final String value)
	{
		return Stream.of(values())
				.filter(v -> v.getValue().equals(value))
				.findFirst();
	}
}
