package gov.va.ascent.security.transform;

import java.io.IOException;
import gov.va.ascent.framework.log.AscentLogger;
import gov.va.ascent.framework.log.AscentLoggerFactory;
import gov.va.ascent.framework.transfer.ProviderTransferObjectMarker;
import gov.va.ascent.framework.transfer.ServiceTransferObjectMarker;

/**
 * Transform data between the REST provider model and the service domain model for security related end points.
 * <p>
 * Specifically transforms P "REST provider" {@link ServiceTransferObjectMarker} objects to S "service domain"
 * {@link ServiceTransferObjectMarker} objects, and vice versa.
 *
 * @param P <b>extends ServiceTransferObjectMarker</b> - the REST provider model object
 * @param S <b>extends ServiceTransferObjectMarker</b> - the service domain model object
 */
public abstract class AbstractProviderSecurityTransformer<P extends ServiceTransferObjectMarker, S extends Object> {

	/** Constant for the logger for this class */
	public static final AscentLogger LOGGER = AscentLoggerFactory.getLogger(AbstractProviderSecurityTransformer.class);

	/**
	 * Transform a "REST provider" {@link ServiceTransferObjectMarker} object to a "service domain" {@link ServiceTransferObjectMarker}
	 * object.
	 *
	 * @param toTransform P the "REST provider" ServiceTransferObjectMarker object to transform
	 * @return S the "service domain" ServiceTransferObjectMarker object
	 * @throws IOException in case of access errors (if the temporary store fails for the MultipartFile)
	 */
	public abstract S transformToService(P toTransform);

	/**
	 * Transform a 'service domain" {@link ServiceTransferObjectMarker} to a "REST provider" {@link ProviderTransferObjectMarker}
	 *
	 * @param toTransform S the "service domain" ServiceTransferObjectMarker object to transform
	 * @return P the "REST provider" ServiceTransferObjectMarker object
	 */
	public abstract P transformToProvider(S toTransform);

}
