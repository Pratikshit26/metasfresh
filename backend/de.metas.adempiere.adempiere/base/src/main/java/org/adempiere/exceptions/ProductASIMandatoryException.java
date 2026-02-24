package org.adempiere.exceptions;

 


import org.compiere.model.I_M_Product;

import de.metas.document.engine.IDocument;

public class ProductASIMandatoryException extends AdempiereException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8622998816450307889L;

	public ProductASIMandatoryException(final IDocument doc, final I_M_Product product, final int documentLineNo)
	{
		super("@M_AttributeSet_ID@ @IsMandatory@ ("
				+ "" + doc.getSummary()
				+ ", @Line@ #" + documentLineNo
				+ ", @M_Product_ID@=" + product.getValue()
				+ ")");

	}
}
