package de.metas;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.google.common.annotations.VisibleForTesting;
import de.metas.util.Check;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.util.lang.ExtendedMemorizingSupplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
     
 * #L%
 */

@UtilityClass
public class JsonObjectMapperHolder
{
	private static final Logger logger = LoggerFactory.getLogger(JsonObjectMapperHolder.class);

	public static ObjectMapper sharedJsonObjectMapper()
	{
		return sharedJsonObjectMapper.get();
	}

	@VisibleForTesting
	public static void resetSharedJsonObjectMapper()
	{
		sharedJsonObjectMapper.forget();
	}

	private static final ExtendedMemorizingSupplier<ObjectMapper> sharedJsonObjectMapper = ExtendedMemorizingSupplier.of(JsonObjectMapperHolder::newJsonObjectMapper);

	public static ObjectMapper newJsonObjectMapper()
	{
		// important to register the jackson-datatype-jsr310 module which we have in our pom and
		// which is needed to serialize/deserialize java.time.Instant
		Check.assumeNotNull(com.fasterxml.jackson.datatype.jsr310.JavaTimeModule.class, ""); // just to get a compile error if not present

		final ObjectMapper objectMapper = new ObjectMapper()
				.findAndRegisterModules()
				.registerModule(new GuavaModule())
				.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
				.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE)
				.enable(MapperFeature.USE_ANNOTATIONS);

		logger.info("Created a new JSON ObjectMapper: {} \nRegistered modules: {}", objectMapper, objectMapper.getRegisteredModuleIds());

		return objectMapper;
	}

	@Nullable
	public static String toJson(@Nullable final Object object)
	{
		if (object == null)
		{
			return null;
		}
		try
		{
			return sharedJsonObjectMapper().writeValueAsString(object);
		}
		catch (JsonProcessingException e)
		{
			throw Check.mkEx("Failed converting object to JSON: " + object, e);
		}
	}

	@Nullable
	public static <T> T fromJson(@Nullable final String json, @NonNull final Class<T> valueType)
	{
		if (json == null)
		{
			return null;
		}
		try
		{
			return sharedJsonObjectMapper().readValue(json, valueType);
		}
		catch (JsonProcessingException e)
		{
			throw Check.mkEx("Failed converting JSON to " + valueType.getSimpleName() + ": `" + json + "`", e);
		}
	}

	@NonNull
	public static String toJsonNonNull(@NonNull final Object object)
	{
		try
		{
			return sharedJsonObjectMapper().writeValueAsString(object);
		}
		catch (final JsonProcessingException e)
		{
			throw Check.mkEx("Failed converting object to JSON: " + object, e);
		}
	}
}
