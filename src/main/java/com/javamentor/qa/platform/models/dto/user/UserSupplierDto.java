package com.javamentor.qa.platform.models.dto.user;

import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserSupplierDto implements Serializable {
    private Long id;
    private String email;
    private String fullName;
    private String linkImage;
    private String city;
    private int reputation;
    private long reputationLong;

    public UserSupplierDto(Long id, String email, String fullName, String linkImage, String city, long reputationLong) {
        this.id = id;
        this.email = email;
        this.fullName = fullName;
        this.linkImage = linkImage;
        this.city = city;
        this.reputationLong = reputationLong;
        this.reputation = (int)reputationLong;
    }
}
