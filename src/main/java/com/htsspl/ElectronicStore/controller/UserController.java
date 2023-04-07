package com.htsspl.ElectronicStore.controller;

import com.htsspl.ElectronicStore.dtos.ImageResponse;
import com.htsspl.ElectronicStore.dtos.PageableResponse;
import com.htsspl.ElectronicStore.dtos.UserDto;
import com.htsspl.ElectronicStore.helper.AppConstants;
import com.htsspl.ElectronicStore.helper.AppResponse;
import com.htsspl.ElectronicStore.service.FileService;
import com.htsspl.ElectronicStore.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private FileService fileService;

    @Value("${user.profile.image.path}")
    private String imageUploadPath;



    @PostMapping()
    public ResponseEntity<UserDto> saveUser(@Valid @RequestBody UserDto userDto) {
        logger.info("Initiating Service request for save user UserDtos  : {}" + userDto);
        UserDto userDto1 = userService.createUser(userDto);
        logger.info("Completed Service request for save user UserDtos  :{}" + userDto);
        return new ResponseEntity<>(userDto1, HttpStatus.CREATED);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDtos, @PathVariable Long userId) {
        logger.info("Initiating Service request for update user UserId {} :" + userId);
        UserDto userDtos1 = userService.updateUser(userDtos, userId);
        logger.info("Completed Service request for update user UserId {} :" + userId);
        return new ResponseEntity<>(userDtos1, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<AppResponse> deleteUser(@PathVariable Long userId) {
        logger.info("Initiating Service request for delete user UserId  : {} " + userId);
        userService.deleteUser(userId);
        AppResponse appResponse = AppResponse.builder()
                .message(AppConstants.USER_DELETED_SUCCESS)
                .success(true)
                .status(HttpStatus.OK).build();
        logger.info("Completed Service request for delete user UserId  : {} " + userId);
        return new ResponseEntity<>(appResponse, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<PageableResponse<UserDto>> getAllUser(
            @RequestParam(value = "pageNumber" , defaultValue = "0" ,required = false) Integer pageNumber,
            @RequestParam(value = "pageSize" , defaultValue = "10" , required = false) Integer pageSize ,
            @RequestParam(value = "sortBy" , defaultValue = "name" , required = false) String   sortBy ,
            @RequestParam(value = "sortDir" , defaultValue = "asc" ,required = false) String sortDir
    ) {
        logger.info("Initiating Service request for get All user");
        PageableResponse<UserDto> allUser = userService.getAllUser(pageNumber , pageSize , sortBy , sortDir);
        logger.info("Completed Service request for get All user");
        return new ResponseEntity<>(allUser, HttpStatus.FOUND);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getByUserId(@PathVariable Long userId) {
        logger.info("Initiating Service request for get_User By Id UserId {} :" + userId);
        UserDto userDto = userService.getUserById(userId);
        logger.info("Completed Service request for get_User By Id UserId {} :" + userId);
        return new ResponseEntity<>(userDto, HttpStatus.FOUND);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email) {
        logger.info("Initiating Service request for get_User By Email Email {} :" + email);
        UserDto userDto = userService.getUserByEmail(email);
        logger.info("Completed Service request for get_User By Email Email {} :" + email);
        return new ResponseEntity<>(userDto, HttpStatus.FOUND);
    }

    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<UserDto>> searchNameContainning(@PathVariable String keyword) {
        logger.info("Initiating Service request for searching User By Name userName {} :" + keyword);
        List<UserDto> listUserDtos = userService.searching(keyword);
        logger.info("Initiating Service request for searching User By Name userName {} :" + keyword);
        return new ResponseEntity<>(listUserDtos, HttpStatus.FOUND);
    }

    // Upload User Image

    @PostMapping("/image/{userId}")
    public ResponseEntity<ImageResponse> uploadUserImage(@RequestParam ("userImage")MultipartFile image, @PathVariable Long userId) throws IOException {
        logger.info("Initiating Upload User Image in UserController");
        String imageName = fileService.uploadFile(image, imageUploadPath);
        UserDto user = userService.getUserById(userId);
        user.setImageName(imageName);
        UserDto userDto = userService.updateUser(user, userId);
        ImageResponse imageResponse = ImageResponse.builder().imageName(imageName).success(true).status(HttpStatus.CREATED).build();
        logger.info("Initiating Upload User Image in UserController is Completed");
        return new ResponseEntity<>(imageResponse, HttpStatus.CREATED);
    }

    //serve user image
    @GetMapping("/image/{userId}")
    public void serveUserImage(@PathVariable Long userId, HttpServletResponse response) throws IOException {
        logger.info("Initiating Serve User Image in UserController");
        UserDto user = userService.getUserById(userId);
        logger.info(" User image name : {} ",user.getImageName());
        InputStream resource = fileService.getResource(imageUploadPath, user.getImageName());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        logger.info("Initiating Serve User Image in UserController Completed");
        StreamUtils.copy(resource,response.getOutputStream());
    }
}
