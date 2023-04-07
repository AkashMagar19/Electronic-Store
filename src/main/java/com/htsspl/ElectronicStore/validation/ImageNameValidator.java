package com.htsspl.ElectronicStore.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ImageNameValidator implements ConstraintValidator<Imagenamevalid ,String > {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {

        if(value.isBlank()){
            return false;
        }else{
            return true;
        }

    }

}
