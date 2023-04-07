package com.htsspl.ElectronicStore.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD ,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = ImageNameValidator.class)
public @interface Imagenamevalid {
    public String message() default "Invalid Image Name !!!";
    //represents group of constraints
    public Class<?>[] groups() default { };
    //represents additional information about annotation
    public Class<? extends Payload>[] payload() default { };

}
