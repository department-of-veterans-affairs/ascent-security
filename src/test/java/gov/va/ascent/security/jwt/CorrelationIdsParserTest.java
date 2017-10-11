/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.va.ascent.security.jwt;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Test;	
import static org.junit.Assert.*;

/**
 *
 * @author rthota
 */
public class CorrelationIdsParserTest {
      
    /**
     * Test of parseCorrelationIds method, of class CorrelationIdsParser.
     */
	@Test
	public void testParseCorrelationIds() {
		System.out.println("processMainToken");
        List<String> strArray = Arrays.asList("1020212383V608753^NI^200M^USVHA^P","100001412^PI^200BRLS^USVBA^A","20120203003^PI^200CORP^USVBA^A","1025062341^NI^200DOD^USDOD^A","123456789^SS");
		CorrelationIdsParser instance = new CorrelationIdsParser();
		Map<String, String> result = instance.parseCorrelationIds(strArray);
		assertTrue(result.get("icn").equals("1020212383V608753"));
		assertTrue(result.get("dodedipnid").equals("1025062341"));
		assertTrue(result.get("fileNumber").equals("100001412"));
		assertTrue(result.get("pid").equals("20120203003"));
		assertTrue(result.get("pnid").equals("123456789"));
		assertTrue(result.get("pnidType").equals("SS"));
	}

    /**
     * Test of parseCorrelationIds method, of class CorrelationIdsParser.
     */
	@Test
	public void testParseCorrelationIds1() {
		CorrelationIdsParser instance = new CorrelationIdsParser();
        List<String> strArray = Arrays.asList("1020212383V608753^NI^200M^USVHA^P","100001412^PI^200BRLS^USVBA^A","20120203003^PI^200CORP^USVBA^A","1025062341^NI^200DOD^USDOD^A","12345678903^SS^200CORP^USVBA^A");
		Map<String, String> result = instance.parseCorrelationIds(strArray);
		assertTrue(result.get("icn").equals("1020212383V608753"));
		assertTrue(result.get("pnid").equals("12345678903"));
		assertTrue(result.get("pnidType").equals("SS"));
	}    
	
    /**
     * Test of parseCorrelationIds method, of class CorrelationIdsParser.
     */
	@Test
	public void testParseCorrelationIds2() {
		CorrelationIdsParser instance = new CorrelationIdsParser();
		List<String> strArray = Arrays.asList("100001412^PI^553^USVHA^A","1020212383V608753^NI^200M^USVHA^P","1025062341^NI^200DOD^USDOD^A","20120203003^PI^500^USVHA^A","123456789^SS");
		Map<String, String> result = instance.parseCorrelationIds(strArray);
		assertTrue(result.get("icn").equals("1020212383V608753"));
		assertTrue(result.get("pnid").equals("123456789"));
		assertTrue(result.get("pnidType").equals("SS"));
	}    	
	
    /**
     * Test of parseCorrelationIds method, of class CorrelationIdsParser.
     */
	@Test
	public void testParseCorrelationIds3() {
		CorrelationIdsParser instance = new CorrelationIdsParser();
		List<String> strArray = Arrays.asList("12345678903^XX^200CORP^USVBA^A","1020212383V608753^NI^200M^USVHA^P","100001412^PI^200BRLS^USVBA^A","20120203003^PI^200CORP^USVBA^A","1025062341^NI^200DOD^USDOD^A");
		Map<String, String> result = instance.parseCorrelationIds(strArray);
		assertTrue(result.get("icn").equals("1020212383V608753"));
		assertTrue(result.get("pnid") == null);
		assertTrue(result.get("pnidType") == null);
	}    
	
    /**
     * Test of parseCorrelationIds method, of class CorrelationIdsParser.
     */
	@Test
	public void testParseCorrelationIds4() {
		System.out.println("processMainToken");
		List<String> strArray = Arrays.asList("1020212383V608753^NI^200M^USVBA^P","100001412^MI^200BRLS^USVBA^A","20120203003^MI^200CORP^USVBA^A","1025062341^PI^200DOD^USDOD^A","123456789^XX");
		CorrelationIdsParser instance = new CorrelationIdsParser();
		Map<String, String> result = instance.parseCorrelationIds(strArray);
		assertTrue(result.get("icn") == null);
		assertTrue(result.get("dodedipnid") == null);
		assertTrue(result.get("fileNumber") == null);
		assertTrue(result.get("pid") == null);
		assertTrue(result.get("pnid").equals("123456789"));
		assertTrue(result.get("pnidType").equals("SS"));
	}
    
	
	
    /**
     * Test of parseCorrelationIds method, of class CorrelationIdsParser.
     */
	@Test
	public void testParseCorrelationIds5() {
		System.out.println("processMainToken");
		List<String> strArray = Arrays.asList("1020212383V608753^NI^200M^USVBA^P");
		CorrelationIdsParser instance = new CorrelationIdsParser();
		Map<String, String> result = instance.parseCorrelationIds(strArray);
		assertTrue(result.get("icn") == null);
		assertTrue(result.get("dodedipnid") == null);
		assertTrue(result.get("fileNumber") == null);
		assertTrue(result.get("pid") == null);
		assertTrue(result.get("pnid") == null);
		assertTrue(result.get("pnidType") == null);
	}
}
