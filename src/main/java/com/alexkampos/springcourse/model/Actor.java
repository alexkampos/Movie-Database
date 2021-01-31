package com.alexkampos.springcourse.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "m_actors")
@JsonIgnoreProperties(value = {"movies"})
public class Actor {

    @Id
    @Column(name = "actor_id")
    @GenericGenerator(
            name = "m_actors_id_seq",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                @Parameter(name = "sequence_name", value = "m_actors_id_seq"),
                @Parameter(name = "INCREMENT", value = "1"),
                @Parameter(name = "MINVALUE", value = "1"),
                @Parameter(name = "MAXVALUE", value = "2147483647")
            }
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "m_actors_id_seq")
    private long actorId;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "age")
    private int age;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "actors")
    private List<Movie> movies = new ArrayList<>();

    public Actor(String fullName, int age, List<Movie> movies) {
        this.fullName = fullName;
        this.age = age;
        this.movies = movies;
    }

}
