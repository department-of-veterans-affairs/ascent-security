package gov.va.ascent.security;

import java.net.URI;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import gov.va.ascent.security.jwt.PersonTraits;
import gov.va.ascent.security.util.GenerateToken;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AscentSecurityApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	private String token = null;

	@Before
	public void token(){
		token = GenerateToken.generateJwt(1);
	}

	@Test
	public void contextLoads() {
	}

	@Test
	public void testNoToken(){
		ResponseEntity<Map> response = restTemplate.getForEntity("/user", Map.class);
		Assert.assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
		Assert.assertEquals("Authentication Failed: No JWT Token in Header", response.getBody().get("message"));
	}

	@Test
	public void testValidToken(){
		RequestEntity<Void> requestEntity = RequestEntity.get(URI.create("/user")).header(HttpHeaders.AUTHORIZATION, "Bearer " + token).build();
		ResponseEntity<PersonTraits> response = restTemplate.exchange(requestEntity, PersonTraits.class);
		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
		Assert.assertNotNull(response.getBody());
		Assert.assertEquals(response.getBody().getFirstName(), "JANE");
	}

	@Test
	public void testInvalidToken() throws Exception{
		RequestEntity<Void> requestEntity = RequestEntity.get(URI.create("/user")).header(HttpHeaders.AUTHORIZATION, "Bearer " + token + "random").build();
		ResponseEntity<Map> response = restTemplate.exchange(requestEntity, Map.class);
		Assert.assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
		Assert.assertEquals("Authentication Failed: Invalid Token", response.getBody().get("message"));
	}


	@Test
	public void testExpiredToken() throws Exception{
		Thread.sleep(1000);
		RequestEntity<Void> requestEntity = RequestEntity.get(URI.create("/user")).header(HttpHeaders.AUTHORIZATION, "Bearer " + token ).build();
		ResponseEntity<Map> response = restTemplate.exchange(requestEntity, Map.class);
		Assert.assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
		Assert.assertEquals("Authentication Failed: Invalid Token", response.getBody().get("message"));
	}
}
