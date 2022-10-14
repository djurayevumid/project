package com.javamentor.qa.platform.models.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserAuthDto {
    private Long id;
    private String nickname;
    private String linkImage;
}
