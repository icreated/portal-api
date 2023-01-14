package co.icreated.portal.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class PortalBusinessException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public PortalBusinessException() {
    super();
  }

  public PortalBusinessException(String message, Throwable cause) {
    super(message, cause);
  }

  public PortalBusinessException(String message) {
    super(message);
  }

  public PortalBusinessException(Throwable cause) {
    super(cause);
  }
}
