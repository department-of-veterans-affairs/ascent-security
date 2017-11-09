package gov.va.ascent.security.jwt;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum UserStatus {

	/** The permanent. */
	PERMANENT("P"),
	/** The active. */
	ACTIVE("A"),
	/** Fault. */
	TEMPORARY("T");

	/** The string value. */
	private final String status;

	/** The Constant lookup. */
	private static final Map<String, UserStatus> LOOKUP = new HashMap<>();
	
	/**
	 * Instantiates a new status.
	 * 
	 * @param stringValue
	 *            the string value
	 */

	UserStatus(final String status) {
		this.status = status;
	}


	/**
	 * In order to do a lookup of an enum based on it's string value.
	 */
	static {
		for (final UserStatus s : EnumSet.allOf(UserStatus.class)) {
			LOOKUP.put(s.value(), s);
		}
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
		return LOOKUP.get(stringValue);
	}

}
