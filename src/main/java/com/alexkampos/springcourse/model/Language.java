package com.alexkampos.springcourse.model;

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
@Table(name = "m_languages")
public class Language {

    @Id
    @Column(name = "language_id")
    @GenericGenerator(
            name = "m_languages_id_seq",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                @Parameter(name = "sequence_name", value = "m_languages_id_seq"),
                @Parameter(name = "INCREMENT", value = "1"),
                @Parameter(name = "MINVALUE", value = "1"),
                @Parameter(name = "MAXVALUE", value = "2147483647")
            }
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "m_languages_id_seq")
    private long languageId;

    @Column(name = "name")
    private String name;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "languages")
    private List<Movie> movies = new ArrayList<>();

    public Language(String name, List<Movie> movies) {
        this.name = name;
        this.movies = movies;
    }
}
