package co.icreated.portal.utils;

import org.compiere.model.MSysConfig;
import org.compiere.util.SecureEngine;
import org.springframework.security.crypto.password.PasswordEncoder;

public class IdempierePasswordEncoder implements PasswordEncoder  {
	
	
	String salt = null;

	
	public void setSalt(String salt) {
		this.salt = salt;
	}


	@Override
	public String encode(CharSequence arg0) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public boolean matches(CharSequence password, String hash) {
		
		boolean hash_password = MSysConfig.getBooleanValue(MSysConfig.USER_PASSWORD_HASH, false);
		if (!hash_password) {
			return password.toString().equals(hash);
		}
	    return SecureEngine.isMatchHash (hash, salt, password.toString());
	}

	



}