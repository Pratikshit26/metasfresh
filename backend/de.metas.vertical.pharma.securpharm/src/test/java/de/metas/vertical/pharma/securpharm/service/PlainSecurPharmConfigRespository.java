package de.metas.vertical.pharma.securpharm.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;
import org.junit.Ignore;

import de.metas.user.UserId;
import de.metas.vertical.pharma.securpharm.config.SecurPharmConfig;
import de.metas.vertical.pharma.securpharm.config.SecurPharmConfigId;
import de.metas.vertical.pharma.securpharm.config.SecurPharmConfigRespository;
import lombok.ToString;

/*
 * #%L
 * metasfresh-pharma.securpharm
     
 * #L%
 */

@ToString
@Ignore
public class PlainSecurPharmConfigRespository implements SecurPharmConfigRespository
{
	public static PlainSecurPharmConfigRespository ofDefaultSandboxProperties()
	{
		return ofPropertiesFilename("./sandbox.properties");
	}

	public static PlainSecurPharmConfigRespository ofPropertiesFilename(final String propertiesFilename)
	{
		final SecurPharmConfig config = loadConfigFromPropertiesFile(propertiesFilename);
		return new PlainSecurPharmConfigRespository(config);
	}

	private static SecurPharmConfig loadConfigFromPropertiesFile(final String propertiesFilename)
	{
		try (InputStream in = new FileInputStream(propertiesFilename))
		{
			final Properties props = new Properties();
			props.load(in);
			return SecurPharmConfig.builder()
					.applicationUUID(props.getProperty("applicationUUID"))
					.authBaseUrl(props.getProperty("authBaseUrl"))
					.pharmaAPIBaseUrl(props.getProperty("pharmaAPIBaseUrl"))
					.certificatePath(props.getProperty("certificatePath"))
					.supportUserId(UserId.METASFRESH)
					.keystorePassword(props.getProperty("keystorePassword"))
					.build();
		}
		catch (final IOException e)
		{
			throw new AdempiereException("Failed loading " + propertiesFilename, e);
		}
	}

	private final Optional<SecurPharmConfig> config;

	private PlainSecurPharmConfigRespository(@Nullable final SecurPharmConfig config)
	{
		this.config = Optional.ofNullable(config);
	}

	@Override
	public Optional<SecurPharmConfig> getDefaultConfig()
	{
		return config;
	}

	@Override
	public SecurPharmConfig getById(final SecurPharmConfigId id)
	{
		throw new UnsupportedOperationException();
	}

}
