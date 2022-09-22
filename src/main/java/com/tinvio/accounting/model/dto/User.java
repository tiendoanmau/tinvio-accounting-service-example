package com.tinvio.accounting.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class User {

    private int id;
    private String userName;
    private String phoneNumber;
    private String email;
    private String userStatus;

}
