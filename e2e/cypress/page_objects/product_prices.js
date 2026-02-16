/*
 * #%L
 * metasfresh-e2e
     
 * #L%
 */

import Metasfresh from './page';

class _ProductPrices extends Metasfresh {
  constructor() {
    super();

    this.windowId = '540325';
    this.tableRows = '.table-flex-wrapper-row';
    this.rowSelector = 'tbody tr';
    this.listHeader = '.document-list-header';
    this.selectedRows = '.row-selected';
  }

  visit() {
    cy.visitWindow(this.windowId);
  }

  getRows() {
    return cy.get(this.tableRows).find(this.rowSelector);
  }
}

export const ProductPrices = new _ProductPrices();
