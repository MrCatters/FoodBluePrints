package com.recipe.model.users;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInformationResponse {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
}
