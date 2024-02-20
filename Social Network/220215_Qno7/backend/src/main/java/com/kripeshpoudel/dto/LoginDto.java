package com.kripeshpoudel.dto;

import com.kripeshpoudel.annotation.ValidEmail;
import com.kripeshpoudel.annotation.ValidPassword;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {
    @ValidEmail
    private String email;

    @ValidPassword
    private String password;
}
