package gov.va.ascent.security.jwt;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class JwtAuthenticationTokenTest {

	private static final String TOKEN = "testName";
	private static final String TOKEN_NEW = "testNewName";

	JwtAuthenticationToken token;

	@Before
	public void setUp() throws Exception {
		token = new JwtAuthenticationToken(TOKEN);
		assertNotNull(token);
	}

	@Test
	public final void testHashCode() {
		final JwtAuthenticationToken tokenOther = new JwtAuthenticationToken(TOKEN);
		assertTrue(tokenOther.hashCode() == token.hashCode());

		final JwtAuthenticationToken tokenNull = new JwtAuthenticationToken(null);
		assertTrue(token.hashCode() != tokenNull.hashCode());
	}

	@Test
	public final void testSetGetToken() {
		token.setToken(TOKEN_NEW);
		assertTrue(TOKEN_NEW.equals(token.getToken()));
	}

	@Test
	public final void testEqualsObject() {
		token = new JwtAuthenticationToken(TOKEN);
		final JwtAuthenticationToken tokenOther = new JwtAuthenticationToken(TOKEN);
		final JwtAuthenticationToken tokenDiff = new JwtAuthenticationToken(TOKEN_NEW);

		assertTrue(token.equals(token));
		assertTrue(((UsernamePasswordAuthenticationToken) token).equals(token));
		assertTrue(!token.equals(new Object()));
		assertTrue(token.equals(tokenOther));
		assertTrue(!token.equals(tokenDiff));

		final JwtAuthenticationToken tokenNull = new JwtAuthenticationToken(null);
		assertTrue(!tokenNull.equals(token));
	}
}
