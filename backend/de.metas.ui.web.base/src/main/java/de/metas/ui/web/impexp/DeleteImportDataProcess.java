package de.metas.ui.web.impexp;

import java.util.Optional;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.api.Params;
import org.compiere.SpringContextHolder;

import de.metas.impexp.DataImportService;
import de.metas.impexp.processing.ImportDataDeleteMode;
import de.metas.impexp.processing.ImportDataDeleteRequest;
import de.metas.process.IProcessPrecondition;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.process.adprocess.ViewBasedProcessTemplate;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.view.descriptor.SqlViewRowsWhereClause;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.ui.web.window.model.sql.SqlOptions;
import lombok.Getter;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-webui-api
     
 * #L%
 */

public class DeleteImportDataProcess extends ViewBasedProcessTemplate implements IProcessPrecondition
{
	private final DataImportService dataImportService = SpringContextHolder.instance.getBean(DataImportService.class);

	@Param(parameterName = "ImportDeleteMode", mandatory = true)
	@Getter
	private ImportDataDeleteMode deleteMode;

	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		if (getView().size() <= 0)
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("view is empty");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		final String importTableName = getTableName();
		final ImportDataDeleteMode deleteMode = getDeleteMode();
		final String viewSqlWhereClause = getViewSqlWhereClause(DocumentIdsSelection.ALL)
				.map(SqlViewRowsWhereClause::toSqlString)
				.orElse(null);
		final String selectionSqlWhereClause = ImportDataDeleteMode.ONLY_SELECTED.equals(deleteMode)
				? getSelectionSqlWhereClause().map(SqlViewRowsWhereClause::toSqlString).orElse(null)
				: null;

		final int deletedCount = dataImportService.deleteImportRecords(ImportDataDeleteRequest.builder()
				.importTableName(importTableName)
				.mode(deleteMode)
				.viewSqlWhereClause(viewSqlWhereClause)
				.selectionSqlWhereClause(selectionSqlWhereClause)
				.additionalParameters(Params.copyOf(getParameterAsIParams()))
				.build());

		return "@Deleted@ " + deletedCount;
	}

	@Override
	protected void postProcess(final boolean success)
	{
		invalidateView();
	}

	private Optional<SqlViewRowsWhereClause> getSelectionSqlWhereClause()
	{
		final DocumentIdsSelection rowIds = getSelectedRowIds();
		if (rowIds.isEmpty())
		{
			throw new AdempiereException("@NoSelection@");
		}

		return getViewSqlWhereClause(rowIds);
	}

	private Optional<SqlViewRowsWhereClause> getViewSqlWhereClause(@NonNull final DocumentIdsSelection rowIds)
	{
		final IView view = getView();
		final String importTableName = getTableName();
		final SqlViewRowsWhereClause viewRowsWhereClause = view.getSqlWhereClause(rowIds, SqlOptions.usingTableName(importTableName));
		return Optional.ofNullable(viewRowsWhereClause);
	}
}
