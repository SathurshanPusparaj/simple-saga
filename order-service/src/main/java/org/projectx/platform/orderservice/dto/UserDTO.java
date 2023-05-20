package org.projectx.platform.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class UserDTO {

    long id;

    String name;

    String email;
}
