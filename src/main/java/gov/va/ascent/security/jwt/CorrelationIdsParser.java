package gov.va.ascent.security.jwt;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author rthota
 */
public class CorrelationIdsParser {
	/**
	 * The Constant USVHA.
	 */
	private static final String USVHA = "USVHA";

	/**
	 * The Constant USVBA.
	 */
	private static final String USVBA = "USVBA";

	/**
	 * The Constant USDOD.
	 */
	private static final String USDOD = "USDOD";

	/**
	 * The Constant ICN_ASSIGNING_FACILITY.
	 */
	private static final String ICN_ASSIGNING_FACILITY = "200M";

	/**
	 * The Constant USDOD_ASSIGNING_FACILITY.
	 */
	private static final String USDOD_ASSIGNING_FACILITY = "200DOD";

	/**
	 * The Constant USDOD_ASSIGNING_FACILITY.
	 */
	private static final String BIRLS_ASSIGNING_FACILITY = "200BRLS";

	/**
	 * The Constant USDOD_ASSIGNING_FACILITY.
	 */
	private static final String CORP_ASSIGNING_FACILITY = "200CORP";

	/**
	 * The Constant NATIONAL_IDENTIFIER.
	 */
	private static final String NATIONAL_IDENTIFIER = "NI";

	/**
	 * The Constant PATIENT_IDENTIFIER.
	 */
	private static final String PATIENT_IDENTIFIER = "PI";

	/**
	 * The Constant SS.
	 */
	private static final String SS = "SS";
	/**
	 * The Constant MAX_FIELD_COUNT.
	 */
	private static final int MAX_FIELD_COUNT = 4;

	/**
	 * The Constant SS_FIELD_COUNT.
	 */
	private static final int SS_FIELD_COUNT = 2;

	/**
	 * The Constant ELEMENT_ID_INDEX.
	 */
	private static final int ELEMENT_ID_INDEX = 0;

	/**
	 * The Constant TYPE_INDEX.
	 */
	private static final int TYPE_INDEX = 1;

	/**
	 * The Constant SOURCE_INDEX.
	 */
	private static final int SOURCE_INDEX = 2;

	/**
	 * The Constant ISSUER_INDEX.
	 */
	private static final int ISSUER_INDEX = 3;

	/**
	 * The Constant ISSUER_INDEX.
	 */
	private static final int STATUS_INDEX = 4;

	private final HashMap<String, String> hmap = new HashMap<>();

	private static final Logger LOG = LoggerFactory.getLogger(CorrelationIdsParser.class);

	/**
	 *
	 * @param list
	 * @return
	 */
	public Map<String, String> parseCorrelationIds(final List<String> list) {
		if (list != null) {
			for (final String token : list) {
				processToken(token);
			}
		}

		return hmap;
	}

	/**
	 * process the token and populate the map with values.
	 *
	 * @param tokenId
	 */
	private void processToken(final String tokenId) {
		final String[] tokens = tokenId.split("\\^");
		if (tokens.length >= MAX_FIELD_COUNT) {
			final String elementId = tokens[ELEMENT_ID_INDEX];
			final String type = tokens[TYPE_INDEX];
			final String assigningFacility = tokens[SOURCE_INDEX];
			final String assigningAuthority = tokens[ISSUER_INDEX];
			final UserStatus status = UserStatus.fromValue(tokens[STATUS_INDEX]);

			if (status == null) {
				final StringBuilder sb = new StringBuilder();
				sb.append("Id: ").append(elementId).append(", Id Type: ").append(type).append(", Assigning Facility: ")
						.append(assigningFacility).append(", Assigning Authority: ").append(assigningAuthority)
						.append(", Id Status: ").append(tokens[STATUS_INDEX]);

				LOG.warn("The status of the id is invalid - {}", sb);
				return;
			}

			determinePidAndFileNumber(elementId, type, assigningAuthority, assigningFacility);
			determineEdipiAndIcn(elementId, type, assigningAuthority, assigningFacility);
			determinePnIDAndPnIdType(elementId, type, assigningAuthority);

		} else if (tokens.length == SS_FIELD_COUNT) {
			hmap.put("pnid", tokens[ELEMENT_ID_INDEX]);
			hmap.put("pnidType", SS);
		}
	}

	/**
	 *
	 * @param elementId
	 * @param type
	 * @param assigningAuthority
	 * @param assigningFacility
	 */
	private void determinePidAndFileNumber(final String elementId, final String type, final String assigningAuthority,
			final String assigningFacility) {
		if (type.equals(PATIENT_IDENTIFIER) && assigningAuthority.equals(USVBA)) {
			if (assigningFacility.equals(CORP_ASSIGNING_FACILITY)) {
				hmap.put("pid", elementId);
			} else if (assigningFacility.equals(BIRLS_ASSIGNING_FACILITY)) {
				hmap.put("fileNumber", elementId);
			}
		}
	}

	/**
	 *
	 * @param elementId
	 * @param type
	 * @param assigningAuthority
	 * @param assigningFacility
	 */
	private void determineEdipiAndIcn(final String elementId, final String type, final String assigningAuthority,
			final String assigningFacility) {
		if (type.equals(NATIONAL_IDENTIFIER)) {
			if (assigningFacility.equals(USDOD_ASSIGNING_FACILITY) && assigningAuthority.equals(USDOD)) {
				hmap.put("dodedipnid", elementId);
			}
			if (assigningFacility.equals(ICN_ASSIGNING_FACILITY) && assigningAuthority.equals(USVHA)) {
				hmap.put("icn", elementId);
			}
		}
	}

	/**
	 *
	 * @param elementId
	 * @param type
	 * @param assigningAuthority
	 */
	private void determinePnIDAndPnIdType(final String elementId, final String type, final String assigningAuthority) {
		if (type.equals(SS) && assigningAuthority.equals(USVBA)) {
			hmap.put("pnid", elementId);
			hmap.put("pnidType", type);
		}
	}
}