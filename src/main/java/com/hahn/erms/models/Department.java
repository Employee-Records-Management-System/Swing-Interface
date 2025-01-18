package com.hahn.erms.models;


import lombok.*;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Department {

    private Long id;

    private String name;

    public Department(String name) {this.name = name;}
}
