package de.metas.document;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.google.common.collect.Interner;
import com.google.common.collect.Interners;
import de.metas.util.StringUtils;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
     
 * #L%
 */

@Value
@JsonIncludeProperties({ "docBaseType", "docSubType" })
public class DocBaseAndSubType
{
	public static DocBaseAndSubType of(@NonNull final DocBaseType docBaseType)
	{
		return interner.intern(new DocBaseAndSubType(docBaseType, DocSubType.ANY));
	}

	public static DocBaseAndSubType of(@NonNull final String docBaseType, @Nullable final String docSubType)
	{
		return interner.intern(new DocBaseAndSubType(DocBaseType.ofCode(docBaseType), DocSubType.ofNullableCode(docSubType)));
	}

	public static DocBaseAndSubType of(@NonNull final DocBaseType docBaseType, @Nullable final String docSubType)
	{
		return interner.intern(new DocBaseAndSubType(docBaseType, DocSubType.ofNullableCode(docSubType)));
	}

	public static DocBaseAndSubType of(@NonNull final DocBaseType docBaseType, @NonNull final DocSubType docSubType)
	{
		return interner.intern(new DocBaseAndSubType(docBaseType, docSubType));
	}

	@Nullable
	public static DocBaseAndSubType ofNullable(
			@Nullable final String docBaseType,
			@Nullable final String docSubType)
	{
		final String docBaseTypeNorm = StringUtils.trimBlankToNull(docBaseType);
		return docBaseTypeNorm != null ? of(docBaseTypeNorm, docSubType) : null;
	}

	private static final Interner<DocBaseAndSubType> interner = Interners.newStrongInterner();

	@NonNull DocBaseType docBaseType;
	@NonNull DocSubType docSubType;

	private DocBaseAndSubType(
			@NonNull final DocBaseType docBaseType,
			@NonNull final DocSubType docSubType)
	{
		this.docBaseType = docBaseType;
		this.docSubType = docSubType;
	}

	// DocBaseAndSubTypeChecks
	public boolean isSalesInvoice() {return docBaseType.isSalesInvoice() && docSubType.isNone();}

	public boolean isFinalInvoice() {return docBaseType.isPurchaseInvoice() && docSubType.isFinalInvoice();}

	public boolean isFinalCreditMemo() {return docBaseType.isPurchaseCreditMemo() && docSubType.isFinalCreditMemo();}

	public boolean isInterimInvoice() {return docBaseType.isPurchaseInvoice() && docSubType.IsInterimInvoice();}

	public boolean isDefinitiveInvoice() {return docBaseType.isPurchaseInvoice() && docSubType.isDefinitiveInvoice();}

	public boolean isDefinitiveCreditMemo() {return docBaseType.isPurchaseInvoice() && docSubType.isDefinitiveCreditMemo();}

	public boolean isSalesFinalInvoice() {return docBaseType.isSalesInvoice() && docSubType.isFinalInvoice();}

	public boolean isSalesFinalCreditMemo() {return docBaseType.isSalesCreditMemo() && docSubType.isFinalCreditMemo();}

	public boolean isModularManufacturingOrder() {return docBaseType.isModularManufacturingOrder() && docSubType.isNone();}

	public boolean isProformaSO() {return docBaseType.isSalesOrder() && isProformaSubType();}

	public boolean isProformaShipment() {return docBaseType.isShipment() && isProformaSubType();}

	public boolean isProformaShippingNotification() {return docBaseType.isShippingNotification() && isProformaSubType();}

	public boolean isPrepaySO() {return docBaseType.isSalesOrder() && docSubType.isPrepay();}

	public boolean isInternalVendorInvoice() {return docBaseType.isPurchaseInvoice() && docSubType.isInternalVendorInvoice();}

	public boolean isDeliveryInstruction() {return docBaseType.isShipperTransportation() && docSubType.isDeliveryInstruction();}

	public boolean isCallOrder() {return (docBaseType.isSalesOrder() || docBaseType.isPurchaseOrder()) && docSubType.isCallOrder();}

	public boolean isFrameAgreement() { return ( docBaseType.isSalesOrder() || docBaseType.isPurchaseOrder() ) && docSubType.isFrameAgreement(); }

	public boolean isMediated() {return (docBaseType.isPurchaseOrder()) && docSubType.isMediated();}

	public boolean isRequisition() {return (docBaseType.isPurchaseOrder()) && docSubType.isRequisition();}

	// BaseTypeOnlyChecks
	public boolean isShippingNotificationBaseType() {return docBaseType.isShippingNotification();}

	// SubTypeOnlyChecks
	public boolean isProformaSubType() {return docSubType.isProforma();}
}