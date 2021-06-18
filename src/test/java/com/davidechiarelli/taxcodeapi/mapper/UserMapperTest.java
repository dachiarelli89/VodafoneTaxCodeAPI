package com.davidechiarelli.taxcodeapi.mapper;

import com.davidechiarelli.taxcodeapi.dto.UserDTO;
import com.davidechiarelli.taxcodeapi.model.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class UserMapperTest {

    @Test
    void successMap_male(){
        User user = generateUser("M");

        UserDTO userDTO = new UserMapper().mapToDTO(user);

        assertThat(userDTO)
                .isNotNull()
                .isEqualTo(new UserMapper().mapToDTO(user));
    }

    @Test
    void successMap_female(){
        User user = generateUser("F");

        UserDTO userDTO = new UserMapper().mapToDTO(user);

        assertThat(userDTO)
                .isNotNull()
                .isEqualTo(new UserMapper().mapToDTO(user));
    }


    private User generateUser(String gender){
        return new User("name", "surname", gender, LocalDate.now(), "city", "province");
    }
}
