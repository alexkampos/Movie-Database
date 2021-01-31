package com.alexkampos.springcourse.converters;

import com.alexkampos.springcourse.dto.view.ActorView;
import com.alexkampos.springcourse.dto.view.GenreView;
import com.alexkampos.springcourse.dto.view.LanguageView;
import com.alexkampos.springcourse.dto.view.MovieView;
import com.alexkampos.springcourse.model.Actor;
import com.alexkampos.springcourse.model.Genre;
import com.alexkampos.springcourse.model.Language;
import com.alexkampos.springcourse.model.Movie;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class MovieToMovieViewConverter implements Converter<Movie, MovieView> {

    @Autowired
    private ActorToActorViewConverter actorToActorViewConverter;

    @Autowired
    private GenreToGenreViewConverter genreToGenreViewConverter;

    @Autowired
    private LanguageToLanguageViewConverter languageToLanguageViewConverter;

    @Override
    public MovieView convert(Movie s) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public MovieView convert(@NonNull Movie movie, String notIncludeEntity) {

        MovieView movieView = new MovieView();
        movieView.setMovieId(movie.getMovieId());
        movieView.setTitle(movie.getTitle());
        movieView.setDurationMinutes(movie.getDurationMinutes());
        if (!notIncludeEntity.equals("actor") && !notIncludeEntity.equals("genre") && !notIncludeEntity.equals("language")) {
            movieView.setActors(convertActorListToActorViewList(movie.getActors()));
            movieView.setGenres(convertGenreListToGenreViewList(movie.getGenres()));
            movieView.setLanguages(convertLanguageListToLanguageViewList(movie.getLanguages()));
        }

        return movieView;
    }

    private List<ActorView> convertActorListToActorViewList(List<Actor> movieActors) {

        List<ActorView> movieActorsView = new ArrayList<>();
        movieActors.forEach(actor -> {
            ActorView actorView = actorToActorViewConverter.convert(actor);
            movieActorsView.add(actorView);
        });

        return movieActorsView;
    }

    private List<GenreView> convertGenreListToGenreViewList(List<Genre> movieGenres) {

        List<GenreView> movieGenresView = new ArrayList<>();
        movieGenres.forEach(genre -> {
            GenreView genreView = genreToGenreViewConverter.convert(genre);
            movieGenresView.add(genreView);
        });

        return movieGenresView;
    }

    private List<LanguageView> convertLanguageListToLanguageViewList(List<Language> movieLanguages) {

        List<LanguageView> movieLanguagesView = new ArrayList<>();
        movieLanguages.forEach(language -> {
            LanguageView languageView = languageToLanguageViewConverter.convert(language);
            movieLanguagesView.add(languageView);
        });

        return movieLanguagesView;
    }

}
