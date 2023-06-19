package com.sii.conference.data;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "THEMED_PATH")
public class ThemedPath implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String theme;

    @JsonManagedReference
    @OneToMany(mappedBy = "themedPath", cascade = CascadeType.REMOVE)
    private List<Lecture> lectures;

    @Override
    public String toString() {
        return "Lecture{" +
                "id=" + id +
                ", theme='" + theme +
                '}';
    }
}
