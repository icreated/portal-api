package co.icreated.portal.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.compiere.model.MBPartner;
import org.compiere.model.MUser;
import org.compiere.util.CLogger;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import co.icreated.portal.bean.SessionUser;
import co.icreated.portal.exceptions.PortalInvalidInputException;
import co.icreated.portal.exceptions.PortalNotFoundException;
import co.icreated.portal.mapper.UserMapper;
import co.icreated.portal.utils.IdempierePasswordEncoder;
import co.icreated.portal.utils.PQuery;

@Service
public class UserService implements UserDetailsService {

  public static final String CACHE = "users";

  CLogger log = CLogger.getCLogger(UserService.class);

  Properties ctx;
  IdempierePasswordEncoder passwordEncoder;
  UserMapper userMapper;


  /**
   *
   * @param ctx
   * @param userMapper
   */
  public UserService(Properties ctx, IdempierePasswordEncoder passwordEncoder,
      UserMapper userMapper) {
    this.ctx = ctx;
    this.passwordEncoder = passwordEncoder;
    this.userMapper = userMapper;
  }


  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    SessionUser user = findSessionUserByValue(username);
    if (user == null) {
      throw new UsernameNotFoundException(username);
    }
    passwordEncoder.setSalt(user.getSalt());
    return user;
  }


  /**
   *
   * @param value
   * @return
   */
  public SessionUser findSessionUserByValue(String value) {

    MUser user = new PQuery(ctx, MUser.Table_Name, "Value LIKE ?", null).setParameters(value) //
        .first();

    if (user == null) {
      return null;
    }

    MBPartner bpartner = MBPartner.get(ctx, user.getC_BPartner_ID());

    return new SessionUser.Builder() //
        .userId(user.getAD_User_ID()) //
        .value(user.getValue()) //
        .name(user.getName()) //
        .email(user.getEMail()) //
        .password(user.getPassword()) //
        .salt(user.getSalt()) //
        .partnerId(user.getC_BPartner_ID()) //
        .accountNonExpired(user.isExpired() == false) //
        .accountNonLocked(user.isLocked() == false) //
        .credentialsNonExpired(true) //
        .enabled(user.isActive() && bpartner.isActive()) //
        .authorities(List.of(new SimpleGrantedAuthority("ROLE_USER"))).build();
  }



  /**
   *
   * @param value
   * @param sql
   * @return
   */
  public MUser getUserByParam(Object value, String sql) {

    if (value instanceof String && StringUtils.isBlank((String) value)) {
      throw new PortalInvalidInputException("User not defined");
    }

    MUser user = new PQuery(ctx, MUser.Table_Name, sql, null).setParameters(value) //
        .first();

    if (user == null) {
      throw new PortalNotFoundException("User not found");
    }
    return user;
  }



  /**
   *
   * @param newPassword
   * @param AD_User_ID
   * @return
   */
  public boolean changePassword(String newPassword, MUser user) {

    user.setPassword(newPassword);
    user.setIsLocked(false);
    user.setDatePasswordChanged(new Timestamp(System.currentTimeMillis()));
    user.setEMailVerifyCode(user.getEMailVerifyCode(), "By Changing password");
    return user.save();
  }
}
