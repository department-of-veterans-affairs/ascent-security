package gov.va.ascent.security.jwt;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class UserStatusTest {

	@Test
	public final void testUserStatus() {
		assertNotNull(UserStatus.ACTIVE);
		assertNotNull(UserStatus.PERMANENT);
		assertNotNull(UserStatus.TEMPORARY);
	}

	@Test
	public final void testValue() {
		assertTrue("A".equals(UserStatus.ACTIVE.value()));
		assertTrue("P".equals(UserStatus.PERMANENT.value()));
		assertTrue("T".equals(UserStatus.TEMPORARY.value()));
	}

	@Test
	public final void testFromValue() {
		assertTrue(UserStatus.fromValue("A").equals(UserStatus.ACTIVE));
		assertTrue(UserStatus.fromValue("P").equals(UserStatus.PERMANENT));
		assertTrue(UserStatus.fromValue("T").equals(UserStatus.TEMPORARY));
	}

}
