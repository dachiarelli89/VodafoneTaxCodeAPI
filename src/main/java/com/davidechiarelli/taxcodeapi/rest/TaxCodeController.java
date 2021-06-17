package com.davidechiarelli.taxcodeapi.rest;

import com.davidechiarelli.taxcodeapi.dto.TaxCodeDTO;
import com.davidechiarelli.taxcodeapi.dto.UserDTO;
import com.davidechiarelli.taxcodeapi.mapper.UserMapper;
import com.davidechiarelli.taxcodeapi.model.User;
import com.davidechiarelli.taxcodeapi.service.TaxCodeService;
import com.davidechiarelli.taxcodeapi.service.impl.TaxCodeServiceImpl;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Api("TAX Code Controller")
public class TaxCodeController {
    TaxCodeService service;

    @Autowired
    public TaxCodeController(TaxCodeServiceImpl taxCodeService){
        this.service = taxCodeService;
    }

    @PostMapping(value = "/api/calculateTaxCode", produces = MediaType.APPLICATION_JSON_VALUE)
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
