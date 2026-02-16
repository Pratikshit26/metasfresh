package de.metas.invoicecandidate.internalbusinesslogic;

import static de.metas.common.util.CoalesceUtil.coalesce;

import java.math.BigDecimal;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import de.metas.pricing.InvoicableQtyBasedOn;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.quantity.StockQtyAndUOMQtys;
import de.metas.util.lang.Percent;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.swat.base
     
 * #L%
 */

@Value
public class ReceiptData
{
	ProductId productId;

	Quantity qtyTotalInStockUom;
	Quantity qtyTotalNominal;
	Quantity qtyTotalCatch;

	Quantity qtyWithIssuesInStockUom;
	Quantity qtyWithIssuesNominal;
	Quantity qtyWithIssuesCatch;

	@Builder
	@JsonCreator
	public ReceiptData(
			@JsonProperty("productId") @NonNull final ProductId productId,
			@JsonProperty("qtyTotalInStockUom") @NonNull final Quantity qtyTotalInStockUom,
			@JsonProperty("qtyTotalNominal") @NonNull final Quantity qtyTotalNominal,
			@JsonProperty("qtyTotalCatch") @Nullable final Quantity qtyTotalCatch,
			@JsonProperty("qtyWithIssuesInStockUom") @NonNull final Quantity qtyWithIssuesInStockUom,
			@JsonProperty("qtyWithIssuesNominal") @NonNull final Quantity qtyWithIssuesNominal,
			@JsonProperty("qtyWithIssuesCatch") @Nullable final Quantity qtyWithIssuesCatch)
	{
		this.productId = productId;

		this.qtyTotalInStockUom = qtyTotalInStockUom;
		this.qtyTotalNominal = qtyTotalNominal;
		this.qtyTotalCatch = qtyTotalCatch;

		this.qtyWithIssuesInStockUom = qtyWithIssuesInStockUom;
		this.qtyWithIssuesNominal = qtyWithIssuesNominal;
		this.qtyWithIssuesCatch = qtyWithIssuesCatch;
	}

	public StockQtyAndUOMQty getQtysTotal(@NonNull final InvoicableQtyBasedOn invoicableQtyBasedOn)
	{
		Quantity deliveredInUom;
		switch (invoicableQtyBasedOn)
		{
			case CatchWeight:
				deliveredInUom = coalesce(getQtyTotalCatch(), getQtyTotalNominal());
				break;
			case NominalWeight:
				deliveredInUom = getQtyTotalNominal();
				break;
			default:
				throw new AdempiereException("Unexpected InvoicableQtyBasedOn=" + invoicableQtyBasedOn);
		}
		return StockQtyAndUOMQty.builder()
				.productId(productId)
				.stockQty(qtyTotalInStockUom)
				.uomQty(deliveredInUom).build();
	}

	public StockQtyAndUOMQty getQtysWithIssues(@NonNull final InvoicableQtyBasedOn invoicableQtyBasedOn)
	{
		Quantity deliveredInUom;
		switch (invoicableQtyBasedOn)
		{
			case CatchWeight:
				deliveredInUom = coalesce(getQtyWithIssuesCatch(), getQtyWithIssuesNominal());
				break;
			case NominalWeight:
				deliveredInUom = getQtyWithIssuesNominal();
				break;
			default:
				throw new AdempiereException("Unexpected InvoicableQtyBasedOn=" + invoicableQtyBasedOn);
		}
		return StockQtyAndUOMQty.builder()
				.productId(productId)
				.stockQty(qtyWithIssuesInStockUom)
				.uomQty(deliveredInUom).build();
	}

	public StockQtyAndUOMQty computeQtysWithIssuesEffective(
			@Nullable final Percent qualityDiscountOverride,
			@NonNull final InvoicableQtyBasedOn invoicableQtyBasedOn)
	{
		if (qualityDiscountOverride == null)
		{
			return getQtysWithIssues(invoicableQtyBasedOn);
		}

		final Quantity qtyTotal = getQtysTotal(invoicableQtyBasedOn).getUOMQtyNotNull();
		final Quantity qtyTotalInStockUom = getQtysTotal(invoicableQtyBasedOn).getStockQty();

		final BigDecimal qtyWithIssuesEffective = qualityDiscountOverride.computePercentageOf(
				qtyTotal.toBigDecimal(),
				qtyTotal.getUOM().getStdPrecision());

		final BigDecimal qtyWithIssuesInStockUomEffective = qualityDiscountOverride.computePercentageOf(
				qtyTotalInStockUom.toBigDecimal(),
				qtyTotalInStockUom.getUOM().getStdPrecision());

		return StockQtyAndUOMQtys.create(
				qtyWithIssuesInStockUomEffective, productId,
				qtyWithIssuesEffective, qtyTotal.getUomId());
	}

	public StockQtyAndUOMQty computeInvoicableQtyDelivered(
			@Nullable final Percent qualityDiscountOverride,
			@NonNull final InvoicableQtyBasedOn invoicableQtyBasedOn)
	{
		final StockQtyAndUOMQty qtysWithIssuesEffective = computeQtysWithIssuesEffective(qualityDiscountOverride, invoicableQtyBasedOn);
		return getQtysTotal(invoicableQtyBasedOn).subtract(qtysWithIssuesEffective);
	}

	public Percent computeQualityDiscount(@NonNull final InvoicableQtyBasedOn invoicableQtyBasedOn)
	{
		return Percent.of(
				getQtysWithIssues(invoicableQtyBasedOn).getUOMQtyNotNull().toBigDecimal(),
				getQtysTotal(invoicableQtyBasedOn).getUOMQtyNotNull().toBigDecimal());
	}
}
