package co.icreated.portal.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;

@ResponseStatus(code = NOT_ACCEPTABLE, reason = "Old password is not correct")
public class OldPasswordNotCorrectException extends RuntimeException {

}
