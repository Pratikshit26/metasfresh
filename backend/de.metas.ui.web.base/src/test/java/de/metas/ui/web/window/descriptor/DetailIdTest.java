package de.metas.ui.web.window.descriptor;

import static org.assertj.core.api.Assertions.assertThat;

import org.adempiere.ad.element.api.AdTabId;
import org.junit.jupiter.api.Test;

/*
 * #%L
 * metasfresh-webui-api
     
 * #L%
 */

public class DetailIdTest
{

	@Test
	public void fromAD_Tab_ID()
	{
		final DetailId detailId = DetailId.fromAD_Tab_ID(AdTabId.ofRepoId(39));

		assertThat(detailId.toAdTabId()).isEqualTo(AdTabId.ofRepoId(39));
		assertThat(detailId.toJson()).isEqualTo("AD_Tab-39");
	}

	@Test
	public void fromJson()
	{
		final DetailId detailId = DetailId.fromJson("AD_Tab-39");

		assertThat(detailId.toAdTabId()).isEqualTo(AdTabId.ofRepoId(39));
		assertThat(detailId.toJson()).isEqualTo("AD_Tab-39");
	}

	@Test
	public void fromPrefixAndId()
	{
		final DetailId detailId = DetailId.fromPrefixAndId("prefix", 38);

		assertThat(detailId.toJson()).isEqualTo("prefix-38");
		assertThat(detailId).isEqualTo(DetailId.fromJson("prefix-38"));
	}
}
