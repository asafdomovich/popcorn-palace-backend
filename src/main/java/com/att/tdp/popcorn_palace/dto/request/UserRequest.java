package com.att.tdp.popcorn_palace.dto.request;

import jakarta.validation.constraints.Email;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequest {
    private String name;
    @Email(message = "email is not valid")
    private String email;
}
