package psttest.demo.controller.requests;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "User creation model")
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class UserRequest {

    @ApiModelProperty(required = true, example = "name", dataType = "string", notes = "user first name")
    private String username;

    @ApiModelProperty(required = true, example = "12345", dataType = "string", notes = "user first name")
    private String password;

    @ApiModelProperty(dataType = "string", example = "mail@mail.ru", notes = "user email")
    private String email;

}
