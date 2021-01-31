package com.alexkampos.springcourse.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
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
@Table(name = "m_movies")
public class Movie {

    @Id
    @Column(name = "movie_id")
    @GenericGenerator(
            name = "m_movies_id_seq",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                @Parameter(name = "sequence_name", value = "m_movies_id_seq"),
                @Parameter(name = "INCREMENT", value = "1"),
                @Parameter(name = "MINVALUE", value = "1"),
                @Parameter(name = "MAXVALUE", value = "2147483647")
            }
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "m_movies_id_seq")
    private long movieId;

    @Column(name = "title")
    private String title;

    @Column(name = "duration_minutes")
    private int durationMinutes;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "m_movies_actors",
            joinColumns = {
                @JoinColumn(name = "movie_id")},
            inverseJoinColumns = {
                @JoinColumn(name = "actor_id")})
    private List<Actor> actors = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "m_movies_genres",
            joinColumns = {
                @JoinColumn(name = "movie_id")},
            inverseJoinColumns = {
                @JoinColumn(name = "genre_id")})
    private List<Genre> genres = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "m_movies_languages",
            joinColumns = {
                @JoinColumn(name = "movie_id")},
            inverseJoinColumns = {
                @JoinColumn(name = "language_id")})
    private List<Language> languages = new ArrayList<>();

    public Movie(String title, int durationMinutes, List<Actor> actors, List<Genre> genres, List<Language> languages) {
        this.title = title;
        this.durationMinutes = durationMinutes;
        this.actors = actors;
        this.genres = genres;
        this.languages = languages;
    }
    
}
