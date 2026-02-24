package de.adempiere.model;

 


import java.math.BigDecimal;

/**
 * Immutable Product/Qty pair
 * 
 * @author tsa
 * 
 */
public class ProductQty
{
	private final int productId;
	private final BigDecimal qty;

	public ProductQty(int productId, BigDecimal qty)
	{
		super();

		this.productId = productId;
		this.qty = qty;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + productId;
		result = prime * result + ((qty == null) ? 0 : qty.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductQty other = (ProductQty)obj;
		if (productId != other.productId)
			return false;
		if (qty == null)
		{
			if (other.qty != null)
				return false;
		}
		else if (!qty.equals(other.qty))
			return false;
		return true;
	}

	@Override
	public String toString()
	{
		return "ProductQty [productId=" + productId + ", qty=" + qty + "]";
	}

	public int getProductId()
	{
		return productId;
	}

	public BigDecimal getQty()
	{
		return qty;
	}

}
