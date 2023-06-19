package com.sii.conference.data;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "RESERVATION")
public class Reservation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(cascade = CascadeType.DETACH)
    private User user;

    @ManyToOne(cascade = CascadeType.DETACH)
    private Lecture lecture;

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", user='" + user +
                ", lecture='" + lecture +
                '}';
    }
}
