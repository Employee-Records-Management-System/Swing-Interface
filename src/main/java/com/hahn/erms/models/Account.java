package com.hahn.erms.models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    private Long id;

    private String username;

    private String password;

    private Employee employee;

    private Role role;

}
