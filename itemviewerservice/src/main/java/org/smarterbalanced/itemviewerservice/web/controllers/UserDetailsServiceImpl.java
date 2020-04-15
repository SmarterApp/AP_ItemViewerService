package org.smarterbalanced.itemviewerservice.web.controllers;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.math.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.saml.SAMLCredential;
import org.springframework.security.saml.userdetails.SAMLUserDetailsService;


/**
 * @author kthotti
 *
 */
public class UserDetailsServiceImpl implements SAMLUserDetailsService {
	
    private static final Logger LOGGER = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
    
    private static final int MAX_ERROR_CODE = 100000;
    
    public static final String FULL_NAME_KEY = "cn";
    public static final String LAST_NAME_KEY = "sn";
    public static final String FIRST_NAME_KEY = "givenName";
    public static final String SBAC_UUID_KEY = "sbacUUID";
    public static final String EMAIL_KEY = "mail";
    public static final String PHONE_KEY = "telephoneNumber";
    public static final String SBAC_TENANCY_CHAIN_KEY = "sbacTenancyChain";

    private List<String> allowedRoles;
    

	/**
	 * 
	 */
	public UserDetailsServiceImpl() {
		// TODO Auto-generated constructor stub
		allowedRoles = this.getAllowedRoles();
		
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.saml.userdetails.SAMLUserDetailsService#loadUserBySAML(org.springframework.security.saml.SAMLCredential)
	 */
	@Override
	public Object loadUserBySAML(SAMLCredential samlCred) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
        UserDetails user = null;
        try {
            final String[] pipeDelimitedChain = samlCred.getAttributeAsStringArray(SBAC_TENANCY_CHAIN_KEY);
            String username = samlCred.getAttributeAsString(FULL_NAME_KEY);
            
            //Parse the string from SAML. Example format ["|1000|Item Bank Viewer|CLIENT|1000|ART_DL|||||||||||||", "1000|Administrator|CLIENT|1000|ART_DL|||||||||||||"];
        	List<String> userRoles = new ArrayList<String>();
            List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        	boolean isUserAuthorized = false;

        	for (int i = 0; i < pipeDelimitedChain.length; i++) {
        		
        		String roleString = pipeDelimitedChain[i];
        		String[] roleStringArray = roleString.split("[|]");
        		
        		String userRole = roleStringArray[2];
        		userRoles.add(userRole);
        		
        		if(!isUserAuthorized) {
					isUserAuthorized = allowedRoles.contains(userRole);
				}
        		
    		}

    		System.out.println("User roles for : " + username + ": " + userRoles);

        	LOGGER.info("User roles for : " + username + ": " + userRoles);
        	
        	if(isUserAuthorized)
            	LOGGER.info("User is authorized to access Item Bank Viewer");
        		
        	

        	for (final String role : userRoles) {
        		authorities.add(new GrantedAuthority() {
					
					@Override
					public String getAuthority() {
						// TODO Auto-generated method stub
						return "ROLE_"+role;
					}
				});
			}
        	
            if(isUserAuthorized) {
            	authorities.add(new GrantedAuthority() {
					
					@Override
					public String getAuthority() {
						// TODO Auto-generated method stub
						return "ROLE_DL_EndUser";
					}
				});
            }
            
            //Ideally it should be fetched from database and populated instance of
            // #org.springframework.security.core.userdetails.User should be returned from this method
            
            user = new User(username, "n/a", authorities);
           

        } catch (final Exception e) {
            final String referenceNumber = String.valueOf(RandomUtils.nextInt(MAX_ERROR_CODE));
            LOGGER.error("failure processing user, reference number: " + referenceNumber, e);
            throw new UsernameNotFoundException("Unable to process user " + e.getMessage());
        }

        return user;
    }

	public List<String> getAllowedRoles() {
		List<String> roles = new ArrayList<String>();
		roles.add("DL_EndUser");
		return roles;
	}


	public void setAllowedRoles(List<String> allowedRoles) {
		this.allowedRoles = allowedRoles;
	}

}