package com.javamentor.qa.platform.models.dto.tag;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Data;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TagViewDto {
    private Long id;
    private String name;
    private String description;
    private Long questionCount;
    private Long questionCountOneDay;
    private Long questionCountOneWeek;
}
