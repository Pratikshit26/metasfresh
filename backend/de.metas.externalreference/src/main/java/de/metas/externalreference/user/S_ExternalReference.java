/*
 * #%L
 * de.metas.serviceprovider.base
     
 * #L%
 */

package de.metas.externalreference.user;

import de.metas.externalreference.ExternalReferenceTypes;
import de.metas.externalreference.ExternalUserReferenceType;
import de.metas.externalreference.IExternalReferenceType;
import de.metas.externalreference.model.I_S_ExternalReference;
import de.metas.logging.LogManager;
import de.metas.user.UserId;
import de.metas.user.api.IUserDAO;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.ModelValidator;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

@Interceptor(I_S_ExternalReference.class)
@Callout(I_S_ExternalReference.class)
@Component
public class S_ExternalReference
{
	private final static transient Logger logger = LogManager.getLogger(S_ExternalReference.class);

	private final IUserDAO userDAO;
	private final IADTableDAO adTableDAO = Services.get(IADTableDAO.class);
	private final ExternalReferenceTypes externalReferenceTypes;

	public S_ExternalReference(
			@NonNull final IUserDAO userDAO,
			@NonNull final ExternalReferenceTypes externalReferenceTypes)
	{
		this.userDAO = userDAO;
		this.externalReferenceTypes = externalReferenceTypes;
	}

	@Init
	public void registerCallout()
	{
		Services.get(IProgramaticCalloutProvider.class).registerAnnotatedCallout(this);
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_CHANGE, ModelValidator.TYPE_BEFORE_NEW })
	public void beforeSave(final I_S_ExternalReference record)
	{
		final IExternalReferenceType externalReferenceType = externalReferenceTypes.ofCode(record.getType()).orElse(null);

		if (externalReferenceType instanceof ExternalUserReferenceType)
		{
			switch (ExternalUserReferenceType.cast(externalReferenceType))
			{
				case USER_ID:
					validateUserId(UserId.ofRepoId(record.getRecord_ID()));
					break;
				default:
					throw new AdempiereException("There is no validation in place for ExternalReferenceType: " + externalReferenceType.getCode());
			}
		}
		else
		{
			logger.debug("Ignore unrelated IExternalReferenceType={}", externalReferenceType.getCode());
		}
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_CHANGE, ModelValidator.TYPE_BEFORE_NEW }, ifColumnsChanged = { I_S_ExternalReference.COLUMNNAME_Record_ID })
	@CalloutMethod(columnNames = { I_S_ExternalReference.COLUMNNAME_Record_ID })
	public void updateReferenced_Record_ID(final I_S_ExternalReference record)
	{
		record.setReferenced_Record_ID(record.getRecord_ID());
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_CHANGE, ModelValidator.TYPE_BEFORE_NEW }, ifColumnsChanged = { I_S_ExternalReference.COLUMNNAME_Type })
	@CalloutMethod(columnNames = { I_S_ExternalReference.COLUMNNAME_Type })
	public void updateReferenced_Table_ID(final I_S_ExternalReference record)
	{
		final IExternalReferenceType externalReferenceType = externalReferenceTypes.ofCode(record.getType())
				.orElseThrow(() -> new AdempiereException("Unsupported S_ExternalReference.Type=" + record.getType())
						.appendParametersToMessage()
						.setParameter("S_ExternalReference", record));
		record.setReferenced_AD_Table_ID(adTableDAO.retrieveTableId(externalReferenceType.getTableName()));
	}

	private void validateUserId(@NonNull final UserId userId)
	{
		try
		{
			userDAO.getByIdInTrx(userId);
		}
		catch (final AdempiereException ex)
		{
			throw new AdempiereException("No user found for the given recordId! ", ex)
					.appendParametersToMessage()
					.setParameter("recordId", userId.getRepoId());
		}
	}
}
