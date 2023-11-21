package com.example.legacydb.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Member {
    private int id;
    private String name;
    private int number;
    private String grade;
    private String auth;
}
