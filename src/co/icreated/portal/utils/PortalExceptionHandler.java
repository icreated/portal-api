package co.icreated.portal.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.adempiere.exceptions.AdempiereException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.util.NestedServletException;

import co.icreated.portal.bean.ErrorDetails;
import co.icreated.portal.exceptions.PortalBusinessException;
import co.icreated.portal.exceptions.PortalInvalidInputException;
import co.icreated.portal.exceptions.PortalNotFoundException;

@RestControllerAdvice
public class PortalExceptionHandler extends ResponseEntityExceptionHandler {

  private static final Logger log = LoggerFactory.getLogger(PortalExceptionHandler.class);


  @ExceptionHandler(value = {PortalInvalidInputException.class})
  public ResponseEntity<Object> handleInvalidInputException(PortalInvalidInputException exception) {

    log.warn(exception.getMessage());
    return new ResponseEntity<Object>(exception.getMessage(), HttpStatus.BAD_REQUEST);

  }


  @ExceptionHandler(value = {PortalNotFoundException.class})
  public ResponseEntity<Object> handleNotFoundException(PortalNotFoundException exception) {

    log.warn(exception.getMessage());
    return new ResponseEntity<Object>(exception.getMessage(), HttpStatus.NOT_FOUND);

  }


  @ExceptionHandler(value = {PortalBusinessException.class, AdempiereException.class,
      IllegalArgumentException.class, IllegalStateException.class, NestedServletException.class})
  protected ResponseEntity<Object> handleConflict(RuntimeException exception) {
    log.error("Portal exception: ", exception);
    return new ResponseEntity<Object>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(value = {Exception.class})
  protected ResponseEntity<Object> handleCommonException(Exception exception) {
    log.error("Portal exception: ", exception);
    return new ResponseEntity<Object>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
  }
  

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
      List<String> errorList = ex
              .getBindingResult()
              .getFieldErrors()
              .stream()
              .map(FieldError::getDefaultMessage)
              .collect(Collectors.toList());
      ErrorDetails errorDetails = new ErrorDetails(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errorList);
      return handleExceptionInternal(ex, errorDetails, headers, errorDetails.getStatus(), request);
  }


}
