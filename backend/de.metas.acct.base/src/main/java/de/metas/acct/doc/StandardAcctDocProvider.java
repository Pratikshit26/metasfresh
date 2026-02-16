package de.metas.acct.doc;

import com.google.common.collect.ImmutableMap;
import de.metas.acct.gljournal_sap.acct.Doc_SAPGLJournal;
import de.metas.acct.model.I_SAP_GLJournal;
import org.compiere.acct.Doc_AllocationHdr;
import org.compiere.acct.Doc_Cash;
import org.compiere.acct.Doc_CostRevaluation;
import org.compiere.acct.Doc_GLJournal;
import org.compiere.acct.Doc_Inventory;
import org.compiere.acct.Doc_Invoice;
import org.compiere.acct.Doc_MatchInv;
import org.compiere.acct.Doc_MatchPO;
import org.compiere.acct.Doc_Movement;
import org.compiere.acct.Doc_Order;
import org.compiere.acct.Doc_Payment;
import org.compiere.acct.Doc_ProjectIssue;
import org.compiere.acct.Doc_Requisition;
import org.compiere.model.I_C_AllocationHdr;
import org.compiere.model.I_C_Cash;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_Payment;
import org.compiere.model.I_C_ProjectIssue;
import org.compiere.model.I_GL_Journal;
import org.compiere.model.I_M_CostRevaluation;
import org.compiere.model.I_M_Inventory;
import org.compiere.model.I_M_MatchInv;
import org.compiere.model.I_M_MatchPO;
import org.compiere.model.I_M_Movement;
import org.compiere.model.I_M_Requisition;
import org.springframework.stereotype.Component;

/*
 * #%L
 * de.metas.acct.base
     
 * #L%
 */

@Component
public class StandardAcctDocProvider extends AcctDocProviderTemplate
{
	private static final ImmutableMap<String, AcctDocFactory> DOC_FACTORIES = ImmutableMap.<String, AcctDocFactory>builder()
			.put(I_C_AllocationHdr.Table_Name, Doc_AllocationHdr::new)
			.put(I_C_Cash.Table_Name, Doc_Cash::new)
			.put(I_GL_Journal.Table_Name, Doc_GLJournal::new)
			.put(I_SAP_GLJournal.Table_Name, Doc_SAPGLJournal::new)
			.put(I_M_Inventory.Table_Name, Doc_Inventory::new)
			.put(I_C_Invoice.Table_Name, Doc_Invoice::new)
			.put(I_M_MatchInv.Table_Name, Doc_MatchInv::new)
			.put(I_M_MatchPO.Table_Name, Doc_MatchPO::new)
			.put(I_M_Movement.Table_Name, Doc_Movement::new)
			.put(I_C_Order.Table_Name, Doc_Order::new)
			.put(I_C_Payment.Table_Name, Doc_Payment::new)
			.put(I_C_ProjectIssue.Table_Name, Doc_ProjectIssue::new)
			.put(I_M_Requisition.Table_Name, Doc_Requisition::new)
			.put(I_M_CostRevaluation.Table_Name, Doc_CostRevaluation::new)
			.build();

	public StandardAcctDocProvider()
	{
		super(DOC_FACTORIES);
	}
}
