package secure.app.chat;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class AutoTest {
	private Authentication authentication;
	private PublicExponentGenerator pkg;
 	
	@Test
	void testAuthenticationFail() {
		authentication = new Authentication();
		assertFalse(authentication.authenticateUser("test", "incorrectPass"));
		authentication = null;
	}
	
	@Test
	void testAuthenticationSuccess() {
		authentication = new Authentication();
		assertTrue(authentication.authenticateUser("fs1g15", "Shellshockws13"));
		authentication = null;
	}
	
	@Test
	void testConvertId() {
		pkg = new PublicExponentGenerator();
		assertEquals(pkg.convertID("abc"), "123");
		pkg = null;
	}
	
	@Test
	void testIsPrime() {
		pkg = new PublicExponentGenerator();
		assertTrue(pkg.isPrime(3));
		pkg = null;
	}
	
	@Test
	void testIsntPrime() {
		pkg = new PublicExponentGenerator();
		assertFalse(pkg.isPrime(4));
		pkg = null;
	}
	
	@Test
	void testgenExponent() {
		pkg = new PublicExponentGenerator();
		assertEquals(pkg.generateExponent("fs1g15"), "6191711");
		pkg = null;
	}
	
}
