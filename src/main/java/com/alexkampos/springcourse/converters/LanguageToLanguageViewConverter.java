package com.alexkampos.springcourse.converters;

import com.alexkampos.springcourse.dto.view.LanguageView;
import com.alexkampos.springcourse.dto.view.MovieView;
import com.alexkampos.springcourse.model.Language;
import com.alexkampos.springcourse.model.Movie;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class LanguageToLanguageViewConverter implements Converter<Language, LanguageView> {

    @Autowired
    private MovieToMovieViewConverter movieToMovieViewConverter;

    @Override
    public LanguageView convert(@NonNull Language language) {
        LanguageView languageView = new LanguageView();
        languageView.setLanguageId(language.getLanguageId());
        languageView.setName(language.getName());
        languageView.setMovies(convertMovieListToMovieViewList(language.getMovies()));
        return languageView;
    }

    private List<MovieView> convertMovieListToMovieViewList(List<Movie> languageMovies) {

        List<MovieView> languageMoviesView = new ArrayList<>();
        languageMovies.forEach(movie -> {
            MovieView movieView = movieToMovieViewConverter.convert(movie, "language");
            languageMoviesView.add(movieView);
        });
        return languageMoviesView;
    }

}
