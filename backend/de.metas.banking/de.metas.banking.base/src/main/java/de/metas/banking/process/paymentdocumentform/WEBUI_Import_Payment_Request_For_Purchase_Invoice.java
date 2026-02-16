/*
 * #%L
 * de.metas.banking.base
     
 * #L%
 */

package de.metas.banking.process.paymentdocumentform;

import de.metas.banking.model.I_C_Payment_Request;
import de.metas.banking.payment.IPaymentStringDataProvider;
import de.metas.banking.payment.PaymentString;
import de.metas.banking.payment.spi.exception.PaymentStringParseException;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.i18n.AdMessageKey;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.service.IInvoiceDAO;
import de.metas.process.IProcessParametersCallout;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.util.Check;
import de.metas.util.Services;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_BP_BankAccount;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Invoice;

import java.math.BigDecimal;

/**
 * IProcessParametersCallout is used to update frontend data from backend inside "onParameterChanged. Nice!
 */
public class WEBUI_Import_Payment_Request_For_Purchase_Invoice extends JavaProcess implements IProcessParametersCallout, IProcessPrecondition
{

	private static final String PARAM_fullPaymentString = "FullPaymentString";
	@Param(parameterName = PARAM_fullPaymentString)
	private String fullPaymentStringParam;

	private static final String PARAM_C_BPartner_ID = "C_BPartner_ID";
	@Param(parameterName = PARAM_C_BPartner_ID)
	private I_C_BPartner bPartnerParam;

	private static final String PARAM_C_BP_BankAccount_ID = "C_BP_BankAccount_ID";
	@Param(parameterName = PARAM_C_BP_BankAccount_ID)
	private I_C_BP_BankAccount bankAccountParam;

	private static final String PARAM_Amount = "Amount";
	@Param(parameterName = PARAM_Amount)
	private BigDecimal amountParam;

	private static final String PARAM_Will_Create_a_new_Bank_Account = "Will_Create_a_new_Bank_Account";
	@Param(parameterName = PARAM_Will_Create_a_new_Bank_Account)
	private boolean willCreateANewBandAccountParam;

	private static final String PARAM_BankAccountNo = "BankAccountNo";
	@Param(parameterName = PARAM_BankAccountNo)
	private String bankAccountNumberParam;

	
	// services
	private final transient PaymentStringProcessService paymentStringProcessService = SpringContextHolder.instance
			.getBean(PaymentStringProcessService.class);
	private final IInvoiceDAO invoiceDAO = Services.get(IInvoiceDAO.class);
	private final IBPartnerDAO bPartnerDAO = Services.get(IBPartnerDAO.class);

	// trl
	private static final AdMessageKey MSG_CouldNotFindOrCreateBPBankAccount = AdMessageKey
			.of("de.metas.payment.CouldNotFindOrCreateBPBankAccount");
	
	@Override
	protected String doIt() throws Exception
	{
		final IPaymentStringDataProvider dataProvider = getDataProvider();

		final I_C_BP_BankAccount bpBankAccount;
		if (willCreateANewBandAccountParam)
		{
			bpBankAccount = dataProvider.createNewC_BP_BankAccount(this, bPartnerParam.getC_BPartner_ID());
		}
		else
		{
			Check.assumeNotNull(bankAccountParam, "bankAccountParam not null");
			bpBankAccount = bankAccountParam;
		}

		final PaymentString paymentString = dataProvider.getPaymentString();
		final I_C_Payment_Request paymentRequestTemplate = paymentStringProcessService.createPaymentRequestTemplate(bpBankAccount, amountParam, paymentString);

		paymentStringProcessService.createPaymentRequestFromTemplate(getActualInvoice(), paymentRequestTemplate);
		return MSG_OK;
	}

	@Override
	public void onParameterChanged(final String parameterName)
	{
		if (!PARAM_fullPaymentString.equals(parameterName) && !PARAM_C_BPartner_ID.equals(parameterName))
		{
			// only update on magic payment string or bpartner
			return;
		}

		if (Check.isEmpty(fullPaymentStringParam, true))
		{
			// do nothing if it's empty
			return;
		}

		final IPaymentStringDataProvider dataProvider;
		try
		{
			dataProvider = getDataProvider();
		}
		catch (final PaymentStringParseException pspe)
		{
			final String adMessage = pspe.getLocalizedMessage() + " (\"" + fullPaymentStringParam + "\")";
			throw new AdempiereException(adMessage);
		}

		final PaymentString paymentString = dataProvider.getPaymentString();

		final I_C_Invoice actualInvoice = getActualInvoice();
		final I_C_BP_BankAccount bpBankAccountExisting = paymentStringProcessService.getAndVerifyBPartnerAccountOrNull(dataProvider, actualInvoice.getC_BPartner_ID());
		if (bpBankAccountExisting != null)
		{
			bPartnerParam = bPartnerDAO.getById(bpBankAccountExisting.getC_BPartner_ID());
			bankAccountParam = bpBankAccountExisting;
		}
		else
		{
			/*
			 * If the C_BPartner is set from the field (manually by the user),
			 * C_BP_BankAccount will be created by this process when the user presses "Start".
			 *
			 * If the C_BPartner is NOT set, show error and ask the user to set the partner before adding the magic payment string.
			 * */
			if (bPartnerParam == null)
			{
				throw new AdempiereException(MSG_CouldNotFindOrCreateBPBankAccount, paymentString.getPostAccountNo());
			}
			else
			{
				bankAccountNumberParam = paymentString.getPostAccountNo();
				willCreateANewBandAccountParam = true;
			}
		}

		amountParam = paymentString.getAmount();
	}

	private IPaymentStringDataProvider getDataProvider()
	{
		return paymentStringProcessService.parsePaymentString(getCtx(), fullPaymentStringParam);
	}
	
	private I_C_Invoice getActualInvoice()
	{
		return invoiceDAO.getByIdInTrx(InvoiceId.ofRepoId(getRecord_ID()));
	}

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final IProcessPreconditionsContext context)
	{
		return paymentStringProcessService.checkPreconditionsApplicable(context);
	}
}
