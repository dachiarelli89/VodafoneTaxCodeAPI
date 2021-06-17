package com.davidechiarelli.taxcodeapi.mapper;


import com.davidechiarelli.taxcodeapi.dto.UserDTO;
import com.davidechiarelli.taxcodeapi.model.User;

public class UserMapper {

    public UserDTO mapToDTO(User user){
        UserDTO userDto = new UserDTO();
        userDto.setCityOfBirth(user.getCityOfBirth());
        userDto.setDateOfBirth(user.getDateOfBirth());
        userDto.setFirstName(user.getFirstName());
        userDto.setGender(user.getGender().equalsIgnoreCase("M") ? UserDTO.Gender.M : UserDTO.Gender.F);
        userDto.setLastName(user.getLastName());
        userDto.setProvinceOfBirth(user.getProvinceOfBirth());
        return userDto;
    }

    public User mapFrom(UserDTO user){
        User userModel = new User();
        userModel.setCityOfBirth(user.getCityOfBirth());
        userModel.setDateOfBirth(user.getDateOfBirth());
        userModel.setFirstName(user.getFirstName());
        userModel.setGender(user.getGender().toString());
        userModel.setLastName(user.getLastName());
        userModel.setProvinceOfBirth(user.getProvinceOfBirth());
        return userModel;
    }
}
