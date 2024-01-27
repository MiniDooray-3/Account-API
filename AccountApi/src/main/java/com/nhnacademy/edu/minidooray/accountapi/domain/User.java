package com.nhnacademy.edu.minidooray.accountapi.domain;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Users")
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @Getter
    private String id;

    private String email;

    private String password;

    @Getter
    private String status;

    @Column(name = "last_login")
    @Getter
    private LocalDate lastLogin;

}
