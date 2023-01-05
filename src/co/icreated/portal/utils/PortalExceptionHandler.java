package co.icreated.portal.utils;

import org.adempiere.exceptions.AdempiereException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class PortalExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(value = {AdempiereException.class, IllegalArgumentException.class,
      IllegalStateException.class})
  protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {

    String bodyOfResponse = "Web Portal Exception";
    return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.CONFLICT,
        request);
  }

}