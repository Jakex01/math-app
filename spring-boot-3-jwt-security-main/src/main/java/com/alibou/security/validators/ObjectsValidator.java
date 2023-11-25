package com.alibou.security.validators;

import com.alibou.security.exceptions.ObjectNotValidException;
import com.alibou.security.request.ChangePasswordRequest;
import jakarta.validation.*;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ObjectsValidator<T> {

    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    public void validate(T objectToValidate){
        Set<ConstraintViolation<T>> violation = validator.validate(objectToValidate);

        if(!violation.isEmpty()){
          var errorMessages =  violation
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.toSet());
          throw new ObjectNotValidException(errorMessages);
        }
    }


}
