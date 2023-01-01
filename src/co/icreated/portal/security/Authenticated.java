package co.icreated.portal.security;

import org.springframework.security.core.context.SecurityContextHolder;

import co.icreated.portal.bean.SessionUser;

public interface Authenticated {
  default SessionUser getSessionUser() {
    return (SessionUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
  }
}
