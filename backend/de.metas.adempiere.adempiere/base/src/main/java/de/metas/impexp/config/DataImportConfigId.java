package de.metas.impexp.config;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_DataImport;

import javax.annotation.Nullable;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
     
 * #L%
 */

@Value
public class DataImportConfigId implements RepoIdAware
{
	@JsonCreator
	public static DataImportConfigId ofRepoId(final int repoId)
	{
		return new DataImportConfigId(repoId);
	}

	public static DataImportConfigId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? new DataImportConfigId(repoId) : null;
	}

	int repoId;

	private DataImportConfigId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, I_C_DataImport.COLUMNNAME_C_DataImport_ID);
	}

	@JsonValue
	@Override
	public int getRepoId()
	{
		return repoId;
	}

	public static int toRepoId(@Nullable final DataImportConfigId id)
	{
		return id != null ? id.getRepoId() : -1;
	}

	public TableRecordReference toRecordRef()
	{
		return TableRecordReference.of(I_C_DataImport.Table_Name, repoId);
	}
}
