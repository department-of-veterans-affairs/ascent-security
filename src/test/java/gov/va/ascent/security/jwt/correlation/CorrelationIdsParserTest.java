package gov.va.ascent.security.jwt.correlation;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import gov.va.ascent.framework.exception.AscentRuntimeException;
import gov.va.ascent.framework.security.PersonTraits;

public class CorrelationIdsParserTest {

	CorrelationIdsParser parser = new CorrelationIdsParser();

	/** The count of elements in the CorrelationIds array that indicates it is only SS */
	private static final int ELEMENT_SS_COUNT = 2;

	/** The count of elements in the CorrelationIds array that indicates it does not contain SS */
	private static final int ELEMENT_MAX_COUNT = 4;

	@Before
	public void setUp() throws Exception {
		assertNotNull(parser);
	}

	@Test
	public final void testParseCorrelationIds() {
		// additional test to get full coverage on happy path
		String[] correlationIds = { "1012832469V956223^NI^200M^USVHA^P", "796046489^PI^200BRLS^USVBA^A",
				"600071516^PI^200CORP^USVBA^A", "1040626995^NI^200DOD^USDOD^A", "1040626995^SS^200BRLS^USVBA^A", "796046489^SS" };
		PersonTraits personTraits = new PersonTraits();
		parser.parseCorrelationIds(Arrays.asList(correlationIds), personTraits);

		assertTrue(personTraits.getDodedipnid().equals("1040626995"));
		assertTrue(personTraits.getFileNumber().equals("796046489"));
		assertTrue(personTraits.getIcn().equals("1012832469V956223"));
		assertTrue(personTraits.getPid().equals("600071516"));
		assertTrue(personTraits.getPnid().equals("796046489"));
		assertTrue(personTraits.getPnidType().equals("SS"));
	}

	@Test
	public final void testParseCorrelationIds_Sad() {
		// all other tests cover happy path.
		// this one covers sad paths
		PersonTraits personTraits = new PersonTraits();

		/* null list correlation ids */
		List<String> correlationIdList = new LinkedList<String>();
		correlationIdList.add("string");
		String token = correlationIdList.get(0);
		String[] tokens = token.split("\\^");
		try {
			parser.parseCorrelationIds(correlationIdList, personTraits);
			fail("Should have thrown AscentRuntimeException");
		} catch (Exception e) {
			String msg = "Invalid number of elements {} in correlation id {}, should be " + ELEMENT_MAX_COUNT + " or "
					+ ELEMENT_SS_COUNT + tokens.length + ", " + token;
			assertTrue(AscentRuntimeException.class.isAssignableFrom(e.getClass()));
			assertTrue(e.getMessage().equals(msg));
		}

		/* null or empty correlation id */
		try {
			String[] correlationIds = { null, "796046489^PI^200BRLS^USVBA^A", "600071516^PI^200CORP^USVBA^A",
					"1040626995^NI^200DOD^USDOD^A", "796046489^SS" };
			personTraits = new PersonTraits();
			parser.parseCorrelationIds(Arrays.asList(correlationIds), personTraits);
			fail("Should have thrown AscentRuntimeException");
		} catch (Exception e) {
			assertTrue(AscentRuntimeException.class.isAssignableFrom(e.getClass()));
			assertTrue(e.getMessage().equals("Cannot process blank correlation id"));
		}

		/* Invalid number of elements inside a correlation id */
		try {
			// note the short number of elements in the first correlation id (which is for ICN)
			String[] correlationIds = { "1012832469V956223^NI^200M", "796046489^PI^200BRLS^USVBA^A", "600071516^PI^200CORP^USVBA^A",
					"1040626995^NI^200DOD^USDOD^A", "796046489^SS" };
			personTraits = new PersonTraits();
			parser.parseCorrelationIds(Arrays.asList(correlationIds), personTraits);
			fail("Should have thrown AscentRuntimeException");
		} catch (Exception e) {
			assertTrue(AscentRuntimeException.class.isAssignableFrom(e.getClass()));
			assertTrue(e.getMessage().startsWith("Invalid number of elements {} in correlation id"));
		}
	}

}
