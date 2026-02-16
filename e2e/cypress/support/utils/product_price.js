/*
 * #%L
 * metasfresh-e2e
     
 * #L%
 */

export class ProductPrice {
  setProduct(product) {
    this.product = product;
    return this;
  }

  setIsAttributeDependant(isAttributeDependant) {
    this.isAttributeDependant = isAttributeDependant;
    return this;
  }

  setPriceListVersion(priceListVersion) {
    this.priceListVersion = priceListVersion;
    return this;
  }

  setPackingItem(packingItem) {
    this.packingItem = packingItem;
    return this;
  }

  setStandardPrice(standardPrice) {
    this.standardPrice = standardPrice;
    return this;
  }

  setUOM(uom) {
    this.uom = uom;
    return this;
  }

  setTaxCategory(taxCategory) {
    this.taxCategory = taxCategory;
    return this;
  }

  apply() {
    cy.log(`ProductPrice - apply START ${this.product}`);
    applyProductPrice(this);
    cy.log(`ProductPrice - apply STOP ${this.product}`);
  }
}

function applyProductPrice(productPrice) {
  cy.visitWindow('540325', 'NEW');

  cy.writeIntoLookupListField('M_Product_ID', productPrice.product, productPrice.product);

  cy.setCheckBoxValue('IsAttributeDependant', productPrice.isAttributeDependant);
  if (productPrice.isAttributeDependant) {
    //cy.get('.M_AttributeSetInstance_ID').should('exist'); // idk what to look for here, but this class should exist if isAttributeDependant is true.
  }

  cy.selectInListField('M_PriceList_Version_ID', productPrice.priceListVersion);
  if (productPrice.packingItem !== undefined) {
    cy.selectInListField('M_HU_PI_Item_Product_ID', productPrice.packingItem);
  }
  cy.writeIntoStringField('PriceStd', productPrice.standardPrice);
  if (productPrice.uom !== undefined) {
    cy.selectInListField('C_UOM_ID', productPrice.uom);
  }
  cy.selectInListField('C_TaxCategory_ID', productPrice.taxCategory);

  cy.getStringFieldValue('M_Product_Category_ID').should('not.be.empty');
}
