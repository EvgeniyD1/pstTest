package psttest.demo.controller.requests;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@ApiModel(description = "Authentication request with login and password")
public class AuthRequest implements Serializable {

    @ApiModelProperty(required = true, allowableValues = "Ivan", dataType = "string", notes = "login")
    private String username;

    @ApiModelProperty(required = true, allowableValues = "1", dataType = "string", notes = "password")
    private String password;
}
