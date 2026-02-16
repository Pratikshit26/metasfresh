package de.metas.acct.doc;

import com.google.common.collect.ImmutableMap;
import de.metas.acct.api.AcctSchema;
import lombok.NonNull;
import org.adempiere.ad.persistence.TableModelLoader;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.acct.Doc;
import org.compiere.model.PO;
import org.compiere.util.Env;

import java.util.List;
import java.util.Map;
import java.util.Set;

/*
 * #%L
 * de.metas.acct.base
     
 * #L%
 */

/**
 * Convenient {@link IAcctDocProvider} implementation.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
public abstract class AcctDocProviderTemplate implements IAcctDocProvider
{
	private final ImmutableMap<String, AcctDocFactory> docFactoriesByTableName;

	protected AcctDocProviderTemplate(final Map<String, AcctDocFactory> docFactoriesByTableName)
	{
		this.docFactoriesByTableName = ImmutableMap.copyOf(docFactoriesByTableName);
	}

	@Override
	public final Set<String> getDocTableNames()
	{
		return docFactoriesByTableName.keySet();
	}

	@Override
	public final Doc<?> getOrNull(
			@NonNull final AcctDocRequiredServicesFacade services,
			@NonNull final List<AcctSchema> acctSchemas,
			@NonNull final TableRecordReference documentRef)
	{
		final String tableName = documentRef.getTableName();

		final AcctDocFactory docFactory = docFactoriesByTableName.get(tableName);
		if (docFactory == null)
		{
			return null;
		}

		return docFactory.createAcctDoc(AcctDocContext.builder()
				.services(services)
				.acctSchemas(acctSchemas)
				.documentModel(retrieveDocumentModel(documentRef))
				.build());
	}

	private AcctDocModel retrieveDocumentModel(final TableRecordReference documentRef)
	{
		final PO po = TableModelLoader.instance.getPO(
				Env.getCtx(),
				documentRef.getTableName(),
				documentRef.getRecord_ID(),
				ITrx.TRXNAME_ThreadInherited);
		if (po == null)
		{
			throw new AdempiereException("No document found for " + documentRef);
		}
		return new POAcctDocModel(po);
	}

	@FunctionalInterface
	protected interface AcctDocFactory
	{
		Doc<?> createAcctDoc(AcctDocContext ctx);
	}

}
