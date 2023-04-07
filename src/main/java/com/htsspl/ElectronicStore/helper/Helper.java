package com.htsspl.ElectronicStore.helper;

import com.htsspl.ElectronicStore.dtos.CategoryDto;
import com.htsspl.ElectronicStore.dtos.PageableResponse;
import com.htsspl.ElectronicStore.entities.Category;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class Helper {


    public static <U,V> PageableResponse<V> getPageableResponse(Page<U> page, Class<V> type) {
        List<U> entity = page.getContent();
        List<V> dtoList = entity.stream().map(object -> new ModelMapper().map(object, type)).collect(Collectors.toList());

   //     List<UserDtos> listUserDtos = userRepository.findAll(pageReq).getContent().stream().filter(user -> user.getIsActive().equals(AppConstants.YES))
   //             .map(user -> modelMapper.map(user, UserDtos.class)).collect(Collectors.toList());

        PageableResponse<V> response = new PageableResponse<>();
        response.setContent(dtoList);
        response.setPageNumber(page.getNumber());
        response.setPageSize(page.getSize());
        response.setTotalElement(page.getTotalElements());
        response.setTotalPages(page.getTotalPages());
        response.setLastPage(page.isLast());
        return response;
    }

//    public static <entity, dto> PageableResponse<dto> getPageableResponse(Page<entity> page, Class<dto> type) {
//        List<entity> users = page.getContent();
//        List<dto> listUserDtos = users.stream().filter(user -> user.getIsActive().equals(AppConstants.YES))
//                .map(obj -> new ModelMapper().map(obj, type)).collect(Collectors.toList());
//
////        List<UserDtos> listUserDtos = userRepository.findAll(pageReq).getContent().stream().filter(user -> user.getIsActive().equals(AppConstants.YES))
////                .map(user -> modelMapper.map(user, UserDtos.class)).collect(Collectors.toList());
//
//        PageableResponse<dto> response = new PageableResponse<>();
//        response.setContent(listUserDtos);
//        response.setPageNumber(page.getNumber());
//        response.setPageSize(page.getSize());
//        response.setTotalElement(page.getTotalElements());
//        response.setTotalPages(page.getTotalPages());
//        response.setLastPage(page.isLast());
//        return response;
//    }
//
//

}
