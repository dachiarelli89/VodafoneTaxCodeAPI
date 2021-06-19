package com.davidechiarelli.taxcodeapi.rest;

import com.davidechiarelli.taxcodeapi.dto.ErrorDTO;
import com.davidechiarelli.taxcodeapi.dto.TaxCodeDTO;
import com.davidechiarelli.taxcodeapi.dto.UserDTO;
import com.davidechiarelli.taxcodeapi.mapper.UserMapper;
import com.davidechiarelli.taxcodeapi.model.User;
import com.davidechiarelli.taxcodeapi.service.TaxCodeService;
import com.davidechiarelli.taxcodeapi.service.impl.TaxCodeServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * This controller class exposes the two main services of the API, calculateTaxCode and parseUser
 */

@RestController
@Api("TAX Code Controller")
public class TaxCodeController {
    TaxCodeService service;

    @Autowired
    public TaxCodeController(TaxCodeServiceImpl taxCodeService){
        this.service = taxCodeService;
    }

    @PostMapping(value = "/api/calculateTaxCode", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Returns the tax code"),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying", response = ErrorDTO.class),
            @ApiResponse(code = 403, message = "Forbidden. Use authentication API to get the JWT token", response = ErrorDTO.class),
            @ApiResponse(code = 422, message = "Unprocessable entity, data syntax is correct but their semantic is wrong", response = ErrorDTO.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorDTO.class )
    })
    public ResponseEntity<TaxCodeDTO> calculateTaxCode(@Valid @RequestBody UserDTO user) {
        String taxCode = service.calculateTaxCode(new UserMapper().mapFrom(user));

        if(StringUtils.isBlank(taxCode)){
            return ResponseEntity.badRequest().build();
        }else {
            TaxCodeDTO taxCodeResponse = new TaxCodeDTO(taxCode);
            return ResponseEntity.ok(taxCodeResponse);
        }
    }

    @PostMapping(value = "/api/parseUser", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Returns the user associated to that tax code"),
            @ApiResponse(code = 400, message = "Bad request, adjust before retrying", response = ErrorDTO.class),
            @ApiResponse(code = 403, message = "Forbidden. Use authentication API to get the JWT token", response = ErrorDTO.class),
            @ApiResponse(code = 422, message = "Unprocessable enttiy, data syntax is correct but their semantic is wrong", response = ErrorDTO.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorDTO.class )
    })
    public ResponseEntity<UserDTO> parseUser(@Valid @RequestBody TaxCodeDTO taxCode) {
        User user = service.parseUser(taxCode.getTaxCode());

        if(user == null){
            return ResponseEntity.badRequest().build();
        }else{
            UserDTO userDTO = new UserMapper().mapToDTO(user);
            return ResponseEntity.ok(userDTO);
        }
    }
}
