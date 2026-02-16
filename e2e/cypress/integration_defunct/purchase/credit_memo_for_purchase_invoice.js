/*
 * #%L
 * metasfresh-e2e
     
 * #L%
 */

/// <reference types="Cypress" />

import { DocumentStatusKey } from '../../support/utils/constants';
import { PurchaseInvoice, PurchaseInvoiceLine } from '../../support/utils/purchase_invoice';

describe('Create a Credit memo for Purchase Invoice', function() {
  let creditMemoVendor;
  let businessPartnerName;
  let productName;
  let quantity;

  it('Read the fixture', function() {
    cy.fixture('purchase/credit_memo_for_purchase_invoice.json').then(f => {
      creditMemoVendor = f['creditMemoVendor'];
      businessPartnerName = f['businessPartnerName'];
      productName = f['productName'];
      quantity = f['quantity'];
    });
  });

  it(`Ensure ${creditMemoVendor} is Number Controlled`, function() {
    // very bold assumption that this document type always has recordId=1000006
    cy.visitWindow('135', '1000006');
    cy.setCheckBoxValue('IsDocNoControlled', true);
  });

  it('Prepare Purchase Invoice', function() {
    new PurchaseInvoice(businessPartnerName, creditMemoVendor)
      .addLine(new PurchaseInvoiceLine().setProduct(productName).setQuantity(quantity))
      .apply();
    cy.completeDocument();
  });

  it('Purchase Invoice is Completed', function() {
    cy.expectDocumentStatus(DocumentStatusKey.Completed);
  });

  it('Purchase Invoice is not paid', function() {
    cy.getCheckboxValue('IsPaid').then(checkBoxValue => {
      cy.log(`IsPaid = ${checkBoxValue}`);
      assert.equal(checkBoxValue, false);
    });
  });
});
