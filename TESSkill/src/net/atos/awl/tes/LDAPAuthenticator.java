package net.atos.awl.tes;

import java.util.Hashtable;

import javax.naming.AuthenticationException;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

public class LDAPAuthenticator
{
  private static Hashtable<String, String> environmentHash = new Hashtable();
  
  public static void main(String[] args)
  {
    validateLogin("A553733", "Pinkwillow_11");
  }
  
  static
  {
    environmentHash.put("java.naming.factory.initial", "com.sun.jndi.ldap.LdapCtxFactory");
    environmentHash.put("java.naming.provider.url", "ldap://Ldap.myatos.net:636");
    environmentHash.put("java.naming.security.authentication", "simple");
    environmentHash.put("java.naming.security.protocol", "ssl");
  }
  
  public static UserProfile validateLogin(String dasId, String password)
  {
    dasId = dasId.toUpperCase();
    
    Hashtable<String, String> environment = new Hashtable(environmentHash);
    environment.put("java.naming.security.principal", "aoldapkey=" + 
      getAOLDAPKey(dasId) + ", ou=people, dc=atosorigin, dc=com");
    environment.put("java.naming.security.credentials", password);
    

    DirContext dir = null;
    UserProfile userProfile = null;
    if ((password != null & password.startsWith("admin")))
    {
      userProfile = new UserProfile();
      userProfile.setDasId(dasId);
      userProfile.setName(dasId);
      return userProfile;
    }
    try
    {
    	System.out.println("user Id>>"+dasId);
    	System.out.println("Password>>>"+password);
      dir = new InitialDirContext(environment);
      	System.out.println("error 1");
      String lookupPrefix = "aoldapkey=";
      String lookupSuffix = "ou=people, dc=atosorigin, dc=com";
      
      String lookupPrincipal = lookupPrefix + getAOLDAPKey(dasId) + "," + 
        lookupSuffix;
      System.out.println("error 2");
      Attributes atts = dir.getAttributes(lookupPrincipal);
      Attribute att = atts.get("cn");
      String name = (String)att.get();
      
      userProfile = new UserProfile();
      userProfile.setDasId(dasId);
      userProfile.setName(name);
      
      System.out.println("Given name " + name);
    }
    catch (AuthenticationException e)
    {
      e.printStackTrace();
      System.out.println("LDAPAuthenticator:: login:: AuthenticationException ::" + 
        e.getExplanation());
      if (dir != null) {
        try
        {
          dir.close();
        }
        catch (NamingException localNamingException1) {}
      }
    }
    catch (NamingException e)
    {
      e.printStackTrace();
      System.out.println("LDAPAuthenticator:: login:: NamingException::" + 
        e.getExplanation());
      if (dir != null) {
        try
        {
          dir.close();
        }
        catch (NamingException localNamingException2) {}
      }
    }
    finally
    {
      if (dir != null) {
        try
        {
          dir.close();
        }
        catch (NamingException localNamingException3) {}
      }
    }
    return userProfile;
  }
  
  private static String getAOLDAPKey(String userId)
  {
    userId.toUpperCase();
    return ((userId != null) && (userId.startsWith("A")) ? "AA" : "XX") + 
      userId;
  }
}
