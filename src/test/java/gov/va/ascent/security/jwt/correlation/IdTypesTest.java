package gov.va.ascent.security.jwt.correlation;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import gov.va.ascent.framework.exception.AscentRuntimeException;

public class IdTypesTest {

	@Test
	public final void testIdTypes() {
		assertNotNull(IdTypes.NATIONAL);
		assertNotNull(IdTypes.PATIENT);
		assertNotNull(IdTypes.SOCIAL);
	}

	@Test
	public final void testValue() {
		assertTrue("NI".equals(IdTypes.NATIONAL.value()));
		assertTrue("PI".equals(IdTypes.PATIENT.value()));
		assertTrue("SS".equals(IdTypes.SOCIAL.value()));
	}

	@Test
	public final void testFromValue() {
		assertTrue(IdTypes.fromValue("NI").equals(IdTypes.NATIONAL));
		assertTrue(IdTypes.fromValue("PI").equals(IdTypes.PATIENT));
		assertTrue(IdTypes.fromValue("SS").equals(IdTypes.SOCIAL));

		try {
			IdTypes.fromValue("XX");
			fail("Should have thrown AscentRuntimeException");
		} catch (Exception e) {
			assertTrue(AscentRuntimeException.class.isAssignableFrom(e.getClass()));
			assertTrue(e.getMessage().startsWith("IdType {} does not exist:"));
		}
	}

}
