package com.hahn.erms.dtos;

import com.hahn.erms.models.Account;
import lombok.Data;

@Data
public class LoginResponse {
    private String token;
    private String refreshToken;
    private Account account;
}
