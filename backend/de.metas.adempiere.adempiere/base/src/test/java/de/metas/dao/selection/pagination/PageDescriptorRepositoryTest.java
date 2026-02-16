package de.metas.dao.selection.pagination;

import static org.assertj.core.api.Assertions.assertThat;
import java.time.Instant;

import de.metas.common.util.time.SystemTime;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
     
 * #L%
 */

class PageDescriptorRepositoryTest
{
	private PageDescriptorRepository pageDescriptorRepository;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		pageDescriptorRepository = new PageDescriptorRepository();
	}

	@Test
	void test()
	{
		final Instant time = SystemTime.asInstant();
		final PageDescriptor pageDescriptor = PageDescriptor.createNew("querySelectionUUID", 10, 100, time);
		pageDescriptorRepository.save(pageDescriptor);

		final PageIdentifier pageIdentifier = pageDescriptor.getPageIdentifier();
		//final String pageUid = pageIdentifier.getPageUid();

		final PageDescriptor result = pageDescriptorRepository.getBy(pageIdentifier.getCombinedUid());

		assertThat(result).isEqualTo(pageDescriptor);

	}

}
