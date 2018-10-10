package gov.va.ascent.security.jwt;

/**
 * Vaules for user status codes.
 */
public enum UserStatus {

	/** The permanent. */
	PERMANENT("P"),
	/** The active. */
	ACTIVE("A"),
	/** Fault. */
	TEMPORARY("T");

	/** The string value. */
	private final String status;

	/**
	 * Instantiates a new status.
	 *
	 * @param stringValue
	 *            the string value
	 */

	private UserStatus(final String status) {
		this.status = status;
	}

	/**
	 * Value.
	 *
	 * @return the actual status code.
	 */
	public String value() {
		return status;
	}

	/**
	 * From value.
	 *
	 * @param stringValue
	 *            the string value
	 * @return the enumeration
	 */
	public static UserStatus fromValue(final String stringValue) {
		for (UserStatus s : UserStatus.values()) {
			if (s.value().equals(stringValue)) {
				return s;
			}
		}
		throw new IllegalArgumentException("No enum constant for value \"" + stringValue + "\".");
	}

}
