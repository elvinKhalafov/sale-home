package com.step.salehome.model;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;


@Data
@NoArgsConstructor
public class User {

    private int idUser;

    @NotBlank(message = "Email must not be blank")
    @Email(message = "Email pattern not match")
    private String email;

    @NotNull
    @NotBlank(message = "Password must not be blank")
    @Size(min = 3, max = 20, message = "Password length must be between 3 and 20")
    private String password;

    @NotNull
    @NotBlank(message = "Last Name must not be blank")
    private String lastName;

    @NotNull
    @NotBlank(message = "First Name must not be blank")
    private String firstName;
    private String token;
    private Role role;
    private int status;
    private List<Post> favoritePost;


}
