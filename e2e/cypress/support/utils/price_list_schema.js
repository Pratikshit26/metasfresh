/*
 * #%L
 * metasfresh-e2e
     
 * #L%
 */

export class PriceListSchema {
  constructor() {
    this.lines = [];
  }

  setName(name) {
    cy.log(`PriceListSchema - set name = ${name}`);
    this.name = name;
    return this;
  }

  addLine(priceListSchemaLine) {
    cy.log(`PriceListSchema - add line`);
    this.lines.push(priceListSchemaLine);
    return this;
  }

  apply() {
    cy.log(`PriceListSchema - apply - START (name=${this.name})`);
    applyPriceListSchema(this);
    cy.log(`PriceListSchema - apply - END (name=${this.name})`);
  }
}

export class PriceListSchemaLine {
  setProductCategory(productCategory) {
    cy.log(`PriceListSchemaLine - set productCategory = ${productCategory}`);
    this.productCategory = productCategory;
    return this;
  }

  setProduct(product) {
    cy.log(`PriceListSchemaLine - set product = ${product}`);
    this.product = product;
    return this;
  }

  setStandardPriceSurchargeAmount(surchargeAmount) {
    cy.log(`PriceListSchemaLine - set surchargeAmount = ${surchargeAmount}`);
    this.surchargeAmount = surchargeAmount;
    return this;
  }
}

function applyPriceListSchema(priceListSchema) {
  cy.visitWindow("337", "NEW");

  cy.writeIntoStringField("Name", priceListSchema.name);
  cy.writeIntoStringField("ValidFrom", "01/01/2019", false, null, true);
  cy.selectInListField("DiscountType", "Pricelist"); // maybe this needs a trl and to be a parameter

  priceListSchema.lines.forEach((line) => {
    applyPriceListSchemaLine(line);
  });
  cy.expectNumberOfRows(priceListSchema.lines.length);
}

function applyPriceListSchemaLine(schemaLine) {
  cy.selectTab("M_DiscountSchemaLine");
  cy.pressAddNewButton();
  if (schemaLine.productCategory) {
    cy.selectInListField(
      "M_Product_Category_ID",
      schemaLine.productCategory,
      true,
    );
  }
  if (schemaLine.surchargeAmount) {
    cy.writeIntoStringField("Std_AddAmt", schemaLine.surchargeAmount, true);
  }
  if (schemaLine.product) {
    cy.writeIntoLookupListField(
      "M_Product_ID",
      schemaLine.product,
      schemaLine.product,
      false,
      true,
    );
  }
  cy.pressDoneButton();
}
