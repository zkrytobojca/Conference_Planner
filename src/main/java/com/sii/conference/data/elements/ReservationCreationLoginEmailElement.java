package com.sii.conference.data.elements;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationCreationLoginEmailElement {
    private String userLogin;
    private String userEmail;
    private Integer lectureId;
}
