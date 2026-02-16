/*
 * #%L
 * metasfresh-e2e
     
 * #L%
 */

import Metasfresh from './page';

class _EmptiesReturn extends Metasfresh {
  constructor() {
    super();

    this.windowId = '540322';
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

export const EmptiesReturn = new _EmptiesReturn();
