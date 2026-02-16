package de.metas.vertical.pharma.shipment.model;

/*
 * #%L
 * metasfresh-pharma
     
 * #L%
 */

public interface I_M_Shipment_Declaration_Config extends org.compiere.model.I_M_Shipment_Declaration_Config
{
	public static final String COLUMNNAME_IsOnlyNarcoticProducts = "IsOnlyNarcoticProducts";
    public void setIsOnlyNarcoticProducts(boolean IsOnlyNarcoticProducts);
	public boolean isOnlyNarcoticProducts();
}
