package com.student.models.payloads;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginRequest {

    String username;
    String password;
}
