package com.javamentor.qa.platform.models.dto.user;

import com.javamentor.qa.platform.models.dto.tag.TagDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserDto implements Serializable {
    private Long id;
    private String nickname;
    private String city;
    private int reputation;
    private Long reputationTotal;
    private String linkImage;
    private List<TagDto> tags;

    public UserDto(Long id, String nickname, String city, long reputationTotal, String linkImage) {
        this.id = id;
        this.nickname = nickname;
        this.city = city;
        this.reputationTotal = reputationTotal;
        this.reputation = (int) reputationTotal;
        this.linkImage = linkImage;
    }
}
