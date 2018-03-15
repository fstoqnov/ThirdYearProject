import java.util.Hashtable;

import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NameNotFoundException;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.SizeLimitExceededException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

public class LDAPTesting {
	public static void main(String[] args) {
		// Set up the environment for creating the initial context#
		SecurityCredentials sec = new SecurityCredentials();
		
		Hashtable env = new Hashtable();
		env.put(Context.INITIAL_CONTEXT_FACTORY, 
		    "com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL, "ldap://ldap.soton.ac.uk");
		
		
		env.put(Context.REFERRAL, "follow");
		
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		/*env.put(Context.SECURITY_PRINCIPAL, sec.userName);
		env.put(Context.SECURITY_CREDENTIALS, sec.password);*/

		DirContext ctx;
		
		/*try {
			ctx = new InitialDirContext(env);
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		NamingEnumeration<SearchResult> results = null;*/
		
		try {
			SearchResult result = null;
			ctx = new InitialDirContext(env);
			NamingEnumeration<SearchResult> results = null;
			SearchControls controls = new SearchControls();
			controls.setSearchScope(SearchControls.SUBTREE_SCOPE); // Search Entire Subtree
			controls.setCountLimit(1);   //Sets the maximum number of entries to be returned as a result of the search
			controls.setTimeLimit(50000); // Sets the time limit of these SearchControls in milliseconds
		
			String searchString = "(&(objectCategory=user)(sAMAccountName=" + sec.userName + "))";
			
			
			results = ctx.search("dc=soton,dc=ac,dc=uk", searchString, controls);
			
		
			
			if(results.hasMore()) {
				result = (SearchResult) results.next();
				Attributes attrs = result.getAttributes();
				Attribute dnAttr = attrs.get("distinguishedName");
				String dn = (String) dnAttr.get();

				// User Exists, Validate the Password

				env.put(Context.SECURITY_PRINCIPAL, dn);
				env.put(Context.SECURITY_CREDENTIALS, sec.password);
				
				new InitialDirContext(env); 
				System.out.println(true);
				// Exception will be thrown on Invalid case	
			}
		} catch (AuthenticationException e) { // Invalid Login
			e.printStackTrace();
	    } catch (NameNotFoundException e) { // The base context was not found.
	    	e.printStackTrace();
	    } catch (SizeLimitExceededException e) {
	        throw new RuntimeException("LDAP Query Limit Exceeded, adjust the query to bring back less records", e);
	    } catch (NamingException e) {
	       throw new RuntimeException(e);
	    } 
	}
}
