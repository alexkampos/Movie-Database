package com.alexkampos.springcourse.dto.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class MovieView {

    private long movieId;

    private String title;

    private int durationMinutes;

    @JsonIgnoreProperties(value = {"movies"})
    private List<ActorView> actors = new ArrayList<>();

    @JsonIgnoreProperties(value = {"movies"})
    private List<GenreView> genres = new ArrayList<>();

    @JsonIgnoreProperties(value = {"movies"})
    private List<LanguageView> languages = new ArrayList<>();

}
