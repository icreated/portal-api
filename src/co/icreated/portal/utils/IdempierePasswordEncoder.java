package co.icreated.portal.utils;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.logging.Level;

import org.compiere.model.MSysConfig;
import org.compiere.util.CLogger;
import org.compiere.util.SecureEngine;
import org.springframework.security.crypto.password.PasswordEncoder;

public class IdempierePasswordEncoder implements PasswordEncoder  {
	
	private CLogger log = CLogger.getCLogger(IdempierePasswordEncoder.class);
	
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
		
		boolean valid = false;
		String encoded = null;
		
		boolean hash_password = MSysConfig.getBooleanValue(MSysConfig.USER_PASSWORD_HASH, false);
		System.out.println("MATCH SALT="+salt);
		if (!hash_password) {
			return password.toString().equals(hash);
		}

		// Uses a secure Random not a simple Random
		SecureRandom random;
		try {
			random = SecureRandom.getInstance("SHA1PRNG");
			// Salt generation 64 bits long
			byte[] bSalt = new byte[8];
			random.nextBytes(bSalt);
			// Digest computation
			encoded = SecureEngine.getSHA512Hash(1000, password.toString(), bSalt);
	        valid= encoded.equals(hash);
	        
		} catch (NoSuchAlgorithmException e) {
			log.log(Level.SEVERE, "", e);
		} catch (UnsupportedEncodingException e) {
			log.log(Level.SEVERE, "", e);
		}
		
		return valid;
	}

	



}