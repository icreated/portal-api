package co.icreated.portal.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.compiere.model.MUser;
import org.compiere.util.CLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import co.icreated.portal.bean.SessionUser;

@Service
public class UserService {
	
	public static final String CACHE = "users";
	
	CLogger log = CLogger.getCLogger(UserService.class);
	
	@Autowired
	Properties ctx;
	
    @Autowired
	JdbcTemplate jdbcTemplate;

//    @Cacheable(CACHE) // Cache not implemented yet
	public SessionUser findSessionUserByValue(String value) {
		

		String sql = "SELECT AD_User_ID, u.Value, u.Name, Email, " + 
				"Password, u.Salt, bp.C_BPartner_ID, u.isExpired, " + 
				"u.isLocked, u.isActive, bp.isActive " +
				"FROM AD_User u " +
				"INNER JOIN C_BPartner bp ON bp.C_BPartner_ID = u.C_BPartner_ID " +
				"WHERE u.Value LIKE ?";
		
		

		SessionUser sessionUser = jdbcTemplate.queryForObject(sql,
				new Object[]{value},
				(rs, rowNum) ->
				new SessionUser(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), 
						rs.getString(5), rs.getString(6), rs.getInt(7), rs.getString(8).equals("N"), 
						rs.getString(9).equals("N"), true, rs.getString(10).equals("Y") && rs.getString(11).equals("Y"))
        );

		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		
		// check other roles for future implementations
//		MUser user = MUser.get(ctx, sessionUser.getUserId());
//		MRole[] roles = user.getRoles(Env.getAD_Role_ID(ctx));
//		for (MRole role : roles) {
//			authorities.add(new SimpleGrantedAuthority(role.getName()));
//		}
		
		return sessionUser;

	}
    
    
    /**
     * 
     * @param newPassword
     * @param AD_User_ID
     * @return
     */
	public boolean changePassword(String newPassword, int AD_User_ID) {
		
		MUser user = MUser.get(ctx, AD_User_ID);
		user.setPassword(newPassword);
		user.setIsLocked(false);
		user.setDatePasswordChanged(new Timestamp(System.currentTimeMillis()));
		user.setEMailVerifyCode(user.getEMailVerifyCode(), "By Changing password");
		return user.save();
	}
	

	

}
