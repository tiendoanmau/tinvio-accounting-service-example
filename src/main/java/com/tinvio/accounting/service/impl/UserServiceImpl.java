package com.tinvio.accounting.service.impl;

import com.tinvio.accounting.model.dto.User;
import com.tinvio.accounting.repository.UserRepository;
import com.tinvio.accounting.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.endpoint.InvalidEndpointRequestException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User getUserInformation(String authToken) throws InvalidEndpointRequestException {
        return null;
    }

    @Override
    public User getSupplierUserInformation(Integer id) {
        return null;
    }

    @Override
    public User getMerchantUserInformation(Integer id) {
        return null;
    }
}
