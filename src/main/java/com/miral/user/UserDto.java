package com.miral.user;

import javax.validation.constraints.Min;

public record UserDto (@Min(6) String username, @Min(6) String password) {

}
