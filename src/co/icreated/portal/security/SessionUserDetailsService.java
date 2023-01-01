package co.icreated.portal.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import co.icreated.portal.bean.SessionUser;
import co.icreated.portal.service.UserService;
import co.icreated.portal.utils.IdempierePasswordEncoder;

@Service
public class SessionUserDetailsService implements UserDetailsService {

  UserService userService;
  IdempierePasswordEncoder passwordEncoder;

  public SessionUserDetailsService(UserService userService,
      IdempierePasswordEncoder passwordEncoder) {
    this.userService = userService;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public UserDetails loadUserByUsername(String username) {

    SessionUser user = userService.findSessionUserByValue(username);
    if (user == null) {
      throw new UsernameNotFoundException(username);
    }
    passwordEncoder.setSalt(user.getSalt());
    return user;
  }
}
