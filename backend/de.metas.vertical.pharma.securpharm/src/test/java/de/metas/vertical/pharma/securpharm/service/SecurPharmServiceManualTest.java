package de.metas.vertical.pharma.securpharm.service;

import org.adempiere.test.AdempiereTestHelper;
import org.junit.Ignore;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.inventory.InventoryRepository;
import de.metas.user.UserId;
import de.metas.vertical.pharma.securpharm.actions.DecommissionResponse;
import de.metas.vertical.pharma.securpharm.actions.SecurPharmaActionRepository;
import de.metas.vertical.pharma.securpharm.actions.UndoDecommissionResponse;
import de.metas.vertical.pharma.securpharm.client.SecurPharmClientFactory;
import de.metas.vertical.pharma.securpharm.log.SecurPharmLogRepository;
import de.metas.vertical.pharma.securpharm.notifications.SecurPharmUserNotifications;
import de.metas.vertical.pharma.securpharm.product.DataMatrixCode;
import de.metas.vertical.pharma.securpharm.product.SecurPharmProduct;
import de.metas.vertical.pharma.securpharm.product.SecurPharmProductRepository;

/*
 * #%L
 * metasfresh-pharma.securpharm
     
 * #L%
 */

@Ignore
public class SecurPharmServiceManualTest
{
	public static void main(final String[] args)
	{
		new SecurPharmServiceManualTest().run();
	}

	private void run()
	{
		AdempiereTestHelper.get().init();
		final SecurPharmService service = createSecurPharmService();

		final DataMatrixCode datamatrix = DataMatrixCode.ofBase64Encoded("Wyk+HjA2HTlOMTExMjM0NTY4NDA4HTFUNDdVNTIxNx1EMjIwODAwHVMxODAxOTczMTUzNzYxMh4E");
		System.out.println("Sending code: " + datamatrix);

		final HuId huId = HuId.ofRepoId(1);
		final SecurPharmProduct product = service.getAndSaveProduct(datamatrix, huId);
		System.out.println("product: " + product);
	}

	private static SecurPharmService createSecurPharmService()
	{
		return SecurPharmService.builder()
				.clientFactory(new SecurPharmClientFactory())
				.configRespository(PlainSecurPharmConfigRespository.ofDefaultSandboxProperties())
				.productsRepo(new SecurPharmProductRepository())
				.actionsRepo(new SecurPharmaActionRepository())
				.logsRepo(new SecurPharmLogRepository())
				.actionRequestDispatcher(new DirectSecurPharmActionsDispatcher())
				.userNotifications(new LoggingSecurPharmUserNotifications())
				.inventoryRepo(new InventoryRepository())
				.build();
	}

	private static class LoggingSecurPharmUserNotifications implements SecurPharmUserNotifications
	{

		@Override
		public void notifyProductDecodeAndVerifyError(final UserId responsibleId, final SecurPharmProduct product)
		{
			System.out.println("ERROR on decode&verify: " + product);
		}

		@Override
		public void notifyDecommissionFailed(final UserId responsibleId, final DecommissionResponse response)
		{
			System.out.println("ERROR on decommission: " + response);
		}

		@Override
		public void notifyUndoDecommissionFailed(final UserId responsibleId, final UndoDecommissionResponse response)
		{
			System.out.println("ERROR on undo-decommission: " + response);
		}

	}
}
