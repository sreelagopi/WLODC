package net.atos.awl.tes;

import java.util.Hashtable;

import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;

public class LdapTest {

    public static final String BASEDN = "dc=atosorigin,dc=com";

	public static void main(String[] args)
	{
		System.out.println("Login in progress ..." );
		long starttime = System.currentTimeMillis();
		UserProfile success = dasAuthenticate("A553733", "Pinkwillow_11");
		System.out.println("Login is " + success);
		System.out.println(System.currentTimeMillis() - starttime );
	} 

    public static UserProfile dasAuthenticate(String dasId, String dasPwd) {
    	
        UserProfile userProfile = null;
    	/*if(true)
    	{
            userProfile = new UserProfile();
            userProfile.setDasId(dasId);
            userProfile.setName(" Alpesh");
            return userProfile;

    	}
*/        // Set up environment for creating initial context
        Hashtable<String,String> env = new Hashtable<String,String>(11);
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, "ldap://Ldap.myatos.net:636");
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PROTOCOL, "ssl");

        env.put(Context.SECURITY_PRINCIPAL, "aoLdapKey=" + getAOLDAPKey(dasId)
                + ", ou=people, dc=atosorigin, dc=com");
        env.put(Context.SECURITY_CREDENTIALS, dasPwd); // is this your DAS


        InitialLdapContext ctx = null;

		try
		{
			ctx = new InitialLdapContext(env, null);
			userProfile = getUserProfile(dasId, ctx);

		} catch (AuthenticationException ex)
		{
			ex.printStackTrace();
			System.out.println("DASServerHandler:: login:: " + ex.getExplanation());
		} catch (NamingException ex)
		{
			ex.printStackTrace();
			System.out.println("DASServerHandler:: login:: " + ex.getExplanation());
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("DASServerHandler:: login:: " );
		}
		finally
		{
			if (ctx != null)
				try
				{
					ctx.close();
				} catch (NamingException e)
				{
					System.out.println("Error while closing context "+e.getMessage());
				}
		}

        /*try {
            // Create initial context
            DirContext ctx = new InitialDirContext(env);
            InitialLdapContext lctx = new InitialLdapContext(env, null);
        	
            userProfile = getUserProfile(dasId, lctx);
            ctx.close();
        } catch (NamingException e) {
            userProfile = null;
        
        }*/
        
        return userProfile;
    }

    public static UserProfile getUserProfile(String userId, InitialLdapContext ctx)
            throws NamingException {
    	
        UserProfile userProfile = null;
               
        String searchBase = BASEDN;
        String searchFilter = "aoldapkey=" + getAOLDAPKey(userId);
        SearchControls srchInfo = new SearchControls();
        srchInfo.setSearchScope(SearchControls.SUBTREE_SCOPE);
        
        NamingEnumeration dirObjects = ctx.search(searchBase, searchFilter, srchInfo);

        while (dirObjects != null && dirObjects.hasMoreElements()) {
            SearchResult dirObject = (SearchResult) dirObjects.next();

            userProfile = new UserProfile();
            userProfile.setDasId(userId);
            userProfile.setName("" + dirObject.getAttributes().get("cn").get());

/*            Attributes atts = dirObject.getAttributes();
            NamingEnumeration ne = atts.getAll();
*/            /*
             * while (ne != null && ne.hasMoreElements()) {
             * System.out.println(ne.nextElement().toString()); }
             */

            break;
        }

        return userProfile;
    }

    private static String getAOLDAPKey(String userId) {
        return (userId != null && (userId.startsWith("A") || userId.startsWith("a") )? "AA" : "XX") + userId;
    }

}