package co.icreated.portal.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PortalNotFoundException extends RuntimeException {

  public PortalNotFoundException() {
    super();
  }

  public PortalNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }

  public PortalNotFoundException(String message) {
    super(message);
  }

  public PortalNotFoundException(Throwable cause) {
    super(cause);
  }
}
