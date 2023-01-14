package co.icreated.portal.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PortalInvalidInputException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public PortalInvalidInputException() {
    super();
  }

  public PortalInvalidInputException(String message, Throwable cause) {
    super(message, cause);
  }

  public PortalInvalidInputException(String message) {
    super(message);
  }

  public PortalInvalidInputException(Throwable cause) {
    super(cause);
  }
}
