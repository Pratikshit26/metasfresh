package de.metas.email;

import com.google.common.collect.ImmutableMap;
import de.metas.util.Check;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

import javax.annotation.Nullable;

import static org.compiere.model.X_AD_MailConfig.CUSTOMTYPE_InvoiceRejection;
import static org.compiere.model.X_AD_MailConfig.CUSTOMTYPE_MassDunning;
import static org.compiere.model.X_AD_MailConfig.CUSTOMTYPE_OrgCompiereUtilLogin;

import javax.annotation.Nullable;
import java.util.Objects;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
     
 * #L%
 */

@Getter
@AllArgsConstructor
public enum EMailCustomType implements ReferenceListAwareEnum
{
	OrgCompiereUtilLogin(CUSTOMTYPE_OrgCompiereUtilLogin),
	InvoiceRejection(CUSTOMTYPE_InvoiceRejection),
	MassDunning(CUSTOMTYPE_MassDunning);

	private final String code;

	private static final ImmutableMap<String, EMailCustomType> typesByCode = ReferenceListAwareEnums.indexByCode(values());

	@Nullable
	public static EMailCustomType ofNullableCode(@Nullable final String code)
	{
		return code != null ? ofCode(code) : null;
	}

	@NonNull
	public static EMailCustomType ofCode(@NonNull final String code)
	{
		final EMailCustomType eMailCustomType = typesByCode.get(code);
		if (eMailCustomType == null)
		{
			throw Check.mkEx("No " + EMailCustomType.class + " found for code: " + code);
		}
		return eMailCustomType;
	}

	public static boolean equals(EMailCustomType t1, EMailCustomType t2) {return Objects.equals(t1, t2);}
}
