package com.tinvio.accounting.service;

import com.tinvio.accounting.model.dto.User;

public interface UserService {
    /**
     * Get user information by token
     * @param authToken authentication token
     * @return User
     */
    User getUserInformation(String authToken);

    /**
     * Get supplier user information by user ID
     * @param id user's ID
     * @return User
     */
    User getSupplierUserInformation(Integer id);

    /**
     * Get merchant user information by user ID
     * @param id user's ID
     * @return User
     */
    User getMerchantUserInformation(Integer id);
}
