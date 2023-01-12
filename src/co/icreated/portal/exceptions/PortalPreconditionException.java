package co.icreated.portal.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.PRECONDITION_FAILED)
public class PortalPreconditionException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public PortalPreconditionException() {
    super();
  }

  public PortalPreconditionException(String message, Throwable cause) {
    super(message, cause);
  }

  public PortalPreconditionException(String message) {
    super(message);
  }

  public PortalPreconditionException(Throwable cause) {
    super(cause);
  }
}
