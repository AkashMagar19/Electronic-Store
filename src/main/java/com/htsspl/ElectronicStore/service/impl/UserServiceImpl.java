package com.htsspl.ElectronicStore.service.impl;

import com.htsspl.ElectronicStore.dtos.PageableResponse;
import com.htsspl.ElectronicStore.dtos.UserDto;
import com.htsspl.ElectronicStore.entities.User;
import com.htsspl.ElectronicStore.exception.ResourceNotFoundException;
import com.htsspl.ElectronicStore.helper.AppConstants;
import com.htsspl.ElectronicStore.helper.Helper;
import com.htsspl.ElectronicStore.repository.UserRepository;
import com.htsspl.ElectronicStore.service.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class UserServiceImpl implements UserService {

    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Value("${user.profile.image.path}")
    private String imagePath;

    @Override
    public UserDto createUser(UserDto userDto) {
        logger.info("Initiating Dao request for create user {} UserDtos:" + userDto);
        User user = modelMapper.map(userDto, User.class);
      //  user.setIsActive(AppConstants.YES);

        User savedUser = userRepository.save(user);
        UserDto userDto1 = modelMapper.map(savedUser, UserDto.class);
        logger.info("Completed Dao request for create user {} UserDtos:" + userDto);
        return userDto1;
    }

    @Override
    public UserDto updateUser(UserDto userDto, Long userId) {
        logger.info("Initiating Dao request for update User {} userId:" + userId);
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not Found !!!"));
        user.setName(userDto.getName());
        user.setPassword(userDto.getPassword());
        user.setEmail(userDto.getEmail());
        user.setGender(userDto.getGender());
        user.setAbout(userDto.getAbout());
        user.setImageName(userDto.getImageName());

        User updateUser = userRepository.save(user);
        UserDto userDto1 = modelMapper.map(updateUser, UserDto.class);
        logger.info("Completed Dao request for update User {} userId:" + userId);
        return userDto1;    }

    @Override
    public void deleteUser(Long userId) {

        logger.info("Initiating Dao request for delete by userId {} userId:" + userId);
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not Found !!!"));
        //delete user profile Image
        String fullPath = imagePath + user.getImageName();

        try
        {
            Path path = Paths.get(fullPath);
            Files.delete(path);
        }catch (NoSuchFileException ex){
            logger.info("User Image Not Found in Folder");
            ex.printStackTrace();
        }catch (IOException e){
          e.printStackTrace();
        }
        userRepository.delete(user);
    }

    @Override
    public  PageableResponse<UserDto> getAllUser(Integer pageNumber, Integer PageSize, String sortBy, String sortDir) {
        logger.info("Initiating Dao request for get All user");
        Sort sort = (sortDir.equalsIgnoreCase("asc")) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
//        PageRequest pageReq = PageRequest.of(pageNumber, PageSize, sort);
      // PageNumberDefault Start From 0
        Pageable pageable = PageRequest.of(pageNumber, PageSize, sort);
        Page<User> page = userRepository.findAll(pageable);
//         List<User> users = page.getContent();
  //      List<UserDto> listUserDto = users.stream().filter(user -> user.getIsActive().equals(AppConstants.YES))
//                .map(user -> modelMapper.map(user, UserDto.class)).collect(Collectors.toList());

        //PageableResponse<UserDto> response = new PageableResponse<>();
        //response.setContent(listUserDto);
        //response.setPageNumber(page.getNumber());
      //  response.setPageSize(page.getSize());
    //    response.setTotalElement(page.getTotalElements());
  //      response.setTotalPages(page.getTotalPages());
//        response.setLastPage(page.isLast());

        //For using Helper class -- This is done for Reusuability of code
         PageableResponse<UserDto> response = Helper.getPageableResponse(page, UserDto.class);

        logger.info("Completed Dao request for get All user");

        return response;
    }

    @Override
    public UserDto getUserById(Long userId) {
        logger.info("Initiating Dao request for getSingle user {} userId:" + userId);
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not Found !!!"));
        UserDto userDto = modelMapper.map(user, UserDto.class);
        logger.info("Completed Dao request for getSingle user{} userId:" + userId);
        return userDto;
    }

    @Override
    public UserDto getUserByEmail(String email) {
        logger.info("Initiating Dao request for get User By Email {} EmailId:" + email);
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not Found !!!"));
        UserDto userDto = modelMapper.map(user, UserDto.class);
        logger.info("Completed Dao request for get User By Email {} EmailId:" + email);
        return userDto;
    }

    @Override
    public List<UserDto> searching(String keyword) {
        logger.info("Initiating Dao request for searching User By Name {} userName:" + keyword);
        List<User> byNameContaining = userRepository.findByNameContaining(keyword);
        List<UserDto> listDto = byNameContaining.stream()
                .map((user) -> modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
        logger.info("Completed Dao request for searching User By Name {} userName:" + keyword);
        return listDto;
    }

}
