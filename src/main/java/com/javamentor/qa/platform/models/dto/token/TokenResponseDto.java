package com.javamentor.qa.platform.models.dto.token;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "A JSON object containing the authentication JWT")
public class TokenResponseDto {
    @ApiModelProperty(
            required = true,
            example = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9" +
                    ".eyJzdWIiOiJleGFtcGxlIiwicm9sZSI6IkFOT05ZTU9VUyIsImV4cCI6MTYzODExNzk4MiwiaWF0IjoxNjM4MTE3MzgyfQ" +
                    ".anz7a9xjNs1gZBWR0SjNPdnp2rwCOa1AnobjKV0BbSg")
    private String token;
}
