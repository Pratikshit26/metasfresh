package de.metas.ui.web.order.products_proposal.process;

import de.metas.process.JavaProcess;
import de.metas.process.ProcessExecutionResult.ViewOpenTarget;
import de.metas.process.ProcessExecutionResult.WebuiViewToOpen;
import de.metas.ui.web.view.CreateViewRequest;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.view.IViewsRepository;
import de.metas.ui.web.view.ViewId;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.SpringContextHolder;

/*
 * #%L
 * metasfresh-webui-api
     
 * #L%
 */

abstract class WEBUI_ProductsProposal_Launcher_Template extends JavaProcess
{
	private final IViewsRepository viewsRepo = SpringContextHolder.instance.getBean(IViewsRepository.class);

	public WEBUI_ProductsProposal_Launcher_Template()
	{
		SpringContextHolder.instance.autowire(this);
	}

	@Override
	protected final String doIt()
	{
		final TableRecordReference recordRef = TableRecordReference.of(getTableName(), getRecord_ID());

		final IView view = viewsRepo.createView(createViewRequest(recordRef));
		final ViewId viewId = view.getViewId();

		getResult().setWebuiViewToOpen(WebuiViewToOpen.builder()
											   .viewId(viewId.toJson())
											   .target(ViewOpenTarget.ModalOverlay)
											   .build());

		return MSG_OK;
	}

	protected abstract CreateViewRequest createViewRequest(TableRecordReference recordRef);

}
