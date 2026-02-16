package de.metas.acct.doc;

import com.google.common.collect.ImmutableList;
import de.metas.acct.api.AcctSchema;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.util.List;

/*
 * #%L
 * de.metas.acct.base
     
 * #L%
 */

@Value
public class AcctDocContext
{
	@NonNull AcctDocRequiredServicesFacade services;
	@NonNull ImmutableList<AcctSchema> acctSchemas;
	@NonNull AcctDocModel documentModel;

	@Builder
	private AcctDocContext(
			@NonNull final AcctDocRequiredServicesFacade services,
			@NonNull final List<AcctSchema> acctSchemas,
			@NonNull final AcctDocModel documentModel)
	{
		Check.assumeNotEmpty(acctSchemas, "acctSchemas is not empty");

		this.services = services;
		this.acctSchemas = ImmutableList.copyOf(acctSchemas);
		this.documentModel = documentModel;
	}
}
