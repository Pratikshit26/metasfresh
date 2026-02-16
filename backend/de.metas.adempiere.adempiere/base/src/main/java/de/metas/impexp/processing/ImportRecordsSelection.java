package de.metas.impexp.processing;

import de.metas.process.PInstanceId;
import de.metas.util.StringUtils;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.service.ClientId;
import org.compiere.util.DB;

import javax.annotation.Nullable;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
     
 * #L%
 */

@ToString
public final class ImportRecordsSelection
{
	private final String importTableName;
	private final String importKeyColumnName;
	private final ClientId clientId;

	@Getter
	private final PInstanceId selectionId;

	@Builder
	private ImportRecordsSelection(
			@NonNull final String importTableName,
			@NonNull final String importKeyColumnName,
			@NonNull final ClientId clientId,
			@Nullable final PInstanceId selectionId)
	{
		this.importTableName = importTableName;
		this.importKeyColumnName = importKeyColumnName;
		this.clientId = clientId;
		this.selectionId = selectionId;
	}

	/**
	 * @return `AND ...` where clause
	 */
	public String toSqlWhereClause()
	{
		return toSqlWhereClause(importTableName);
	}

	/**
	 * @return `AND ...` where clause
	 */
	public String toSqlWhereClause(@Nullable final String importTableAlias)
	{
		final String importTableAliasWithDot = StringUtils.trimBlankToOptional(importTableAlias)
				.map(alias -> alias + ".")
				.orElse("");

		final StringBuilder whereClause = new StringBuilder();

		// AD_Client
		whereClause.append(" AND ").append(importTableAliasWithDot).append("AD_Client_ID=").append(clientId.getRepoId());

		// Selection_ID
		if (selectionId != null)
		{
			final String importKeyColumnNameFQ = importTableAliasWithDot + importKeyColumnName;
			whereClause.append(" AND ").append(DB.createT_Selection_SqlWhereClause(selectionId, importKeyColumnNameFQ));
		}

		return whereClause.toString();
	}
}
