package com.davidechiarelli.taxcodeapi.service;

import com.davidechiarelli.taxcodeapi.model.User;
import org.springframework.stereotype.Service;

@Service
public interface TaxCodeService {
    public String calculateTaxCode(User user);
    public User parseUser(String taxCode);
}
