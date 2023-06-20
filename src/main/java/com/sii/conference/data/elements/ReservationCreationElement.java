package com.sii.conference.data.elements;

import com.sii.conference.data.Reservation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationCreationElement {

    private Integer userid;

    private Integer lectureId;
}
