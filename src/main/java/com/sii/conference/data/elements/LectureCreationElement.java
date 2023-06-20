package com.sii.conference.data.elements;

import com.sii.conference.data.Lecture;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LectureCreationElement {

    private Lecture lecture;
    private Integer themedPathId;
}
