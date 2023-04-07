package com.htsspl.ElectronicStore.dtos;

import com.htsspl.ElectronicStore.entities.BaseEntity;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto extends BaseEntity {

    private Long userId;

    @Size(min = 3, max = 30, message = "Username is invalid , Character must in-between 3-30 !!!")
    private String name;
    @Pattern(regexp = "^[a-z0-9][-a-z0-9._]+@([-a-z0-9]+\\.)+[a-z]{2,5}$", message = "Invalid Email")
    @NotBlank(message = "Email must be required !!!")
    private String email;
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*\\W)(?!.* ).{8,16}$" ,
            message = "Password Must be with 8-16 Character,Must Contain at least 1 Uppercase, 1 lowercase, "
                    + "1 Special Character And one Numeric Between 0-9 !!!!!")
    @NotBlank(message = "Password must required !!!")
    private String password;
    @Size(min = 4, max = 6, message = "Invalid gender !!!")

    private String gender;

    @NotBlank(message = "Write something about yourself !!!")
    private String about;

    private String imageName;


}
