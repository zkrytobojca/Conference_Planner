package com.sii.conference.data;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "LECTURE")
public class Lecture implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String topic;

    @Column(name = "start_time", nullable = false)
    private Date startTime;

    @Column(name = "end_time", nullable = false)
    private Date endTime;

    @ManyToOne(cascade = CascadeType.DETACH)
    private ThemedPath themedPath;

    @OneToMany(mappedBy = "lecture", cascade = CascadeType.REMOVE)
    private List<Reservation> reservations;

    @Override
    public String toString() {
        return "Lecture{" +
                "id=" + id +
                ", topic='" + topic +
                ", startTime='" + startTime +
                ", endTime='" + endTime +
                ", themedPath='" + themedPath +
                '}';
    }
}
