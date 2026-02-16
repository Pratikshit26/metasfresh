/*
 * #%L
 * metasfresh-e2e
     
 * #L%
 */

import { DunningType } from '../../support/utils/dunning_type';
import { BPartner } from '../../support/utils/bpartner';
import { appendHumanReadableNow } from '../../support/utils/utils';

describe('create dunning type', function() {
  let dunningTypeName;
  let bPartnerName;
  let bpartnerID;

  it('Read the fixture', function() {
    cy.fixture('dunning/create_dunning_type_spec.json').then(f => {
      dunningTypeName = appendHumanReadableNow(f['dunningTypeName']);
      bPartnerName = appendHumanReadableNow(f['bPartnerName']);
    });
  });

  it('Create dunning type and bpartner', function() {
    cy.fixture('settings/dunning_type.json').then(dunningType => {
      Object.assign(new DunningType(), dunningType)
        .setName(dunningTypeName)
        .apply();
    });

    cy.fixture('sales/simple_customer.json').then(customerJson => {
      const bpartner = new BPartner({ ...customerJson, name: bPartnerName })
        .setDunning(dunningTypeName)
        .clearLocations()
        .clearContacts();

      bpartner.apply().then(bpartner => {
        bpartnerID = bpartner.id;
      });
    });
  });

  it('operations on BP', function() {
    cy.visitWindow('123', bpartnerID);
    cy.selectTab('Customer');
    cy.get('.table tbody td').should('exist');

    cy.get('.table tbody').then(el => {
      expect(el[0].innerHTML).to.include(dunningTypeName);
    });
  });
});
