package co.icreated.portal.utils;

import org.compiere.model.MSysConfig;
import org.compiere.util.SecureEngine;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class IdempierePasswordEncoder implements PasswordEncoder {

  // Per-thread state because the encoder is a singleton bean but the salt and
  // algorithm belong to the user being authenticated on this request.
  private final ThreadLocal<String> currentSalt = new ThreadLocal<>();
  private final ThreadLocal<String> currentAlgorithm = new ThreadLocal<>();


  public void setSalt(String salt) {
    this.currentSalt.set(salt);
  }


  public void setAlgorithm(String algorithm) {
    this.currentAlgorithm.set(algorithm);
  }


  @Override
  public String encode(CharSequence rawPassword) {
    return null;
  }


  @Override
  public boolean matches(CharSequence password, String hash) {

    try {
      boolean hashEnabled = MSysConfig.getBooleanValue(MSysConfig.USER_PASSWORD_HASH, false);
      if (!hashEnabled) {
        return password.toString().equals(hash);
      }
      return SecureEngine.isMatchHash(currentAlgorithm.get(), hash, currentSalt.get(),
          password.toString());
    } finally {
      currentSalt.remove();
      currentAlgorithm.remove();
    }
  }

}
