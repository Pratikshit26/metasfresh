package de.metas.ordercandidate.spi;

import de.metas.ordercandidate.model.I_C_OLCand;
import de.metas.ordercandidate.spi.impl.DefaultOLCandValidator;
import de.metas.quantity.Quantity;
import lombok.NonNull;

import java.util.Optional;

/*
 * #%L
 * de.metas.salescandidate.base
     
 * #L%
 */

/**
 * Note to dev: this interface's implementor is injected into {@link DefaultOLCandValidator}. The implementation is part of {@code de.metas.util}.
 * We need this because for UOMs such as "TO" and "COLI", we need to turn to {@code de.metas.util} in order to get the PIIP's capacity information.
 *
 * Disclaimer: I don't think this approach is great (just look at the amount of javadoc!); but I think this way it's at clear(er) what's going on and why.
 */
public interface IOLCandWithUOMForTUsCapacityProvider
{
	/**
	 * @param olCand shall not be changed by this method.
	 * @return {@code true} iff the given {@code olCand} (because of it's UOM) requires this provider to compute the CU-per-TU capacity.
	 */
	boolean isProviderNeededForOLCand(@NonNull I_C_OLCand olCand);

	/**
	 * Assume that {@link #isProviderNeededForOLCand(I_C_OLCand)} was called and returned {@code true} before.
	 *
	 * @param olCand shall not be changed by this method.
	 * @return the number of CUs that fit into the {@code olCand}'s TU, in the respective product's stock-UOM. Might also be {@link Quantity#isInfinite()}. Throw a user-friendly exception if the capacity can't be found.
	 */
	@NonNull
	Optional<Quantity> computeQtyItemCapacity(@NonNull I_C_OLCand olCand);
}
