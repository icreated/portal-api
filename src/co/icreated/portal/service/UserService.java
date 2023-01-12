package co.icreated.portal.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.apache.commons.lang.StringUtils;
import org.compiere.model.MBPartner;
import org.compiere.model.MUser;
import org.compiere.util.CLogger;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import co.icreated.portal.bean.SessionUser;
import co.icreated.portal.mapper.UserMapper;
import co.icreated.portal.utils.PQuery;

@Service
public class UserService {

  public static final String CACHE = "users";

  CLogger log = CLogger.getCLogger(UserService.class);

  Properties ctx;
  UserMapper userMapper;

  /**
   *
   * @param ctx
   * @param userMapper
   */
  public UserService(Properties ctx, UserMapper userMapper) {
    this.ctx = ctx;
    this.userMapper = userMapper;
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
  public MUser getUserByParam(String value, String sql) {

    if (StringUtils.isBlank(value)) {
      throw new AdempiereException("User not defined");
    }

    MUser user = new PQuery(ctx, MUser.Table_Name, sql, null).setParameters(value) //
        .first();

    if (user == null) {
      throw new AdempiereException("User doesn't exist");
    }
    return user;
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
