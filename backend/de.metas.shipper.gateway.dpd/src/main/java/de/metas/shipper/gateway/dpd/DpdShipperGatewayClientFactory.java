/*
 * #%L
 * de.metas.shipper.gateway.dpd
     
 * #L%
 */

package de.metas.shipper.gateway.dpd;

import de.metas.shipper.gateway.dpd.logger.DpdDatabaseClientLogger;
import de.metas.shipping.ShipperId;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import de.metas.shipper.gateway.dpd.model.DpdClientConfig;
import de.metas.shipper.gateway.dpd.model.DpdClientConfigRepository;
import de.metas.shipper.gateway.spi.ShipperGatewayClient;
import de.metas.shipper.gateway.spi.ShipperGatewayClientFactory;

@Service
public class DpdShipperGatewayClientFactory implements ShipperGatewayClientFactory
{
	private final DpdClientConfigRepository configRepo;

	public DpdShipperGatewayClientFactory(final DpdClientConfigRepository configRepo)
	{
		this.configRepo = configRepo;
	}

	@Override
	public String getShipperGatewayId()
	{
		return DpdConstants.SHIPPER_GATEWAY_ID;
	}

	@Override
	public ShipperGatewayClient newClientForShipperId(@NonNull final ShipperId shipperId)
	{
		final DpdClientConfig config = configRepo.getByShipperId(shipperId);
		return DpdShipperGatewayClient.builder()
				.config(config)
				.databaseLogger(DpdDatabaseClientLogger.instance)
				.build();
	}
}
