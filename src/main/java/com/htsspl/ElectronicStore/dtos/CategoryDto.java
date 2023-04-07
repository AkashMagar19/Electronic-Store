package com.htsspl.ElectronicStore.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {
    private Long categoryId;
    @NotBlank(message = "title is required !!")
  //  @Min(value = 4,message = "Title Must Be Minimum 4 Characters !!")
    @Size(min = 4,message ="Title Must Be Minimum 4 Characters !!")
    private String title;

    @NotBlank(message = "Description Must Be Required  !!")
    private String description;

    @NotBlank(message = "Cover Image Required")
    private String coverImage;

}
