package org.josiasguerrero.shared.aplication.validation;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ValidationException;
import jakarta.validation.Validator;

@Component
public class DtoValidator {
  private final Validator validator;

  public DtoValidator(Validator validator) {
    this.validator = validator;
  }

  /**
   * Validates a DTO and throws ValidationException if errors
   * 
   * @param <T>
   * @param dto
   * @throws ValidationException.class
   *
   */
  public <T> void validate(T dto) {
    Set<ConstraintViolation<T>> violations = validator.validate(dto);

    if (!violations.isEmpty()) {
      String errors = violations.stream()
          .map(v -> v.getPropertyPath() + ": " + v.getMessage())
          .collect(Collectors.joining(", "));

      throw new ValidationException("Validation failed: " + errors);

    }
  }

  /**
   * Verifies if the given DTO is valid
   * 
   * @param <T>
   * @param dto
   * @return Set of strings
   */
  public <T> Set<String> getViolations(T dto) {
    return validator.validate(dto).stream()
        .map(v -> v.getPropertyPath() + ": " + v.getMessage())
        .collect(Collectors.toSet());
  }

  public <T> boolean isValid(T dto) {
    return validator.validate(dto).isEmpty();
  }

}
