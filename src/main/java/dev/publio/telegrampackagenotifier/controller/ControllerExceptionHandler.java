package dev.publio.telegrampackagenotifier.controller;

import dev.publio.telegrampackagenotifier.exceptions.CompanyNotSupportedException;
import dev.publio.telegrampackagenotifier.exceptions.NoUpdatesFoundException;
import dev.publio.telegrampackagenotifier.exceptions.UnableToGetShippingUpdateException;
import dev.publio.telegrampackagenotifier.exceptions.model.AttributeMessage;
import dev.publio.telegrampackagenotifier.exceptions.model.ExceptionResponse;
import java.util.ArrayList;
import java.util.List;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
@Log4j2
public class ControllerExceptionHandler {

	private static final String VALIDATION_EXCEPTION_MSG = "Validation Exception";

	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseBody
	public ResponseEntity<ExceptionResponse> handlingConstraintValidationException(ConstraintViolationException e) {
		log.error("Handling ConstraintValidationException");

		String message = e.getConstraintViolations()
				.stream()
				.findFirst()
				.map(ConstraintViolation::getMessage)
				.orElse("");

		ExceptionResponse err = new ExceptionResponse(HttpStatus.BAD_REQUEST, message);

		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(err);
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	@ResponseBody
	public ResponseEntity<ExceptionResponse> dataIntegrityViolationException(DataIntegrityViolationException e) {
		log.error("Handling DataIntegrityViolationException");

		ExceptionResponse err = new ExceptionResponse(HttpStatus.BAD_REQUEST, e.getMessage());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(err);
	}

	@ExceptionHandler(Exception.class)
	@ResponseBody
	public ResponseEntity<ExceptionResponse> handlingException(Exception e) {
		log.error("Handling Exception");

		ExceptionResponse err = new ExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(err);
	}

	@ExceptionHandler(MissingServletRequestParameterException.class)
	@ResponseBody
	public ResponseEntity<ExceptionResponse> handleMissingServletRequestParameter(
			MissingServletRequestParameterException ex) {

		log.error("Handling MissingServletRequestParameter");

		ExceptionResponse err = new ExceptionResponse(HttpStatus.BAD_REQUEST, ex.getMessage());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(err);

	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseBody
	public ResponseEntity<ExceptionResponse> httpMessageNotReadableException(HttpMessageNotReadableException e) {
		ExceptionResponse err = new ExceptionResponse(HttpStatus.BAD_REQUEST, e.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(err);
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	@ResponseBody
	public ResponseEntity<ExceptionResponse> conversionFailedException(MethodArgumentTypeMismatchException e) {
		ExceptionResponse err = new ExceptionResponse(HttpStatus.BAD_REQUEST, e.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(err);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	@ResponseBody
	public ResponseEntity<ExceptionResponse> illegalArgumentException(IllegalArgumentException e) {
		ExceptionResponse err = new ExceptionResponse(HttpStatus.BAD_REQUEST, e.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(err);
	}

	@ExceptionHandler(CompanyNotSupportedException.class)
	@ResponseBody
	public ResponseEntity<ExceptionResponse> companyNotSupportedException(CompanyNotSupportedException e) {
		ExceptionResponse err = new ExceptionResponse(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage());
		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
				.body(err);
	}

	@ExceptionHandler(UnableToGetShippingUpdateException.class)
	@ResponseBody
	public ResponseEntity<ExceptionResponse> unableToGetShippingUpdateException(UnableToGetShippingUpdateException e) {
		ExceptionResponse err = new ExceptionResponse(HttpStatus.CONFLICT, "Unable to update");
		return ResponseEntity.status(HttpStatus.CONFLICT)
				.body(err);
	}

	@ExceptionHandler(NoUpdatesFoundException.class)
	@ResponseBody
	public ResponseEntity<ExceptionResponse> noUpdatesFoundException(NoUpdatesFoundException e) {
		ExceptionResponse err = new ExceptionResponse(HttpStatus.NO_CONTENT, String.format("Current track id [%s] has not updates", e.getMessage()));
		return ResponseEntity.status(HttpStatus.NO_CONTENT)
				.body(err);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseBody
	public ResponseEntity<ExceptionResponse> methodArgumentNotValidException(MethodArgumentNotValidException e) {

		List<AttributeMessage> attributeMessages = new ArrayList<>();
		for (ObjectError objectError : e.getBindingResult().getAllErrors()) {
			attributeMessages.add(new AttributeMessage(((FieldError) objectError).getField(), objectError.getDefaultMessage()));
		}
		ExceptionResponse err = new ExceptionResponse(HttpStatus.BAD_REQUEST, VALIDATION_EXCEPTION_MSG, attributeMessages);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(err);
	}


}