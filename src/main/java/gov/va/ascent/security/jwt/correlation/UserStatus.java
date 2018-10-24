package gov.va.ascent.security.jwt.correlation;

import gov.va.ascent.framework.exception.AscentRuntimeException;
import gov.va.ascent.framework.log.AscentLogger;
import gov.va.ascent.framework.log.AscentLoggerFactory;

/**
 * Vaules for user status codes.
 */
public enum UserStatus {

	/** Status of permanent */
	PERMANENT("P"),
	/** Status of active */
	ACTIVE("A"),
	/** Status of temporary */
	TEMPORARY("T");

	private static final AscentLogger LOGGER = AscentLoggerFactory.getLogger(UserStatus.class);

	/** The arbitrary string value of the enumeration */
	private String status;

	/**
	 * Private constructor for enum initialization
	 *
	 * @param status String
	 */
	private UserStatus(final String status) {
		this.status = status;
	}

	/**
	 * The arbitrary String value assigned to the enumeration.
	 *
	 * @return String
	 */
	public String value() {
		return status;
	}

	/**
	 * Get the enumeration for the associated arbitrary String value.
	 * Throws a runtime exception if the string value does not match one of the enumeration values.
	 *
	 * @param stringValue the string value
	 * @return UserStatus - the enumeration
	 * @throws AscentRuntimeException if no match of enumeration values
	 */
	public static UserStatus fromValue(final String stringValue) {
		for (UserStatus s : UserStatus.values()) {
			if (s.value().equals(stringValue)) {
				return s;
			}
		}
		String msg = "UserStatus {} does not exist: " + stringValue;
		LOGGER.error(msg);
		throw new AscentRuntimeException(msg);
	}

}
