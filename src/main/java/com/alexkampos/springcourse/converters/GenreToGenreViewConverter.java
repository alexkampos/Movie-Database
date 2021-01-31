package com.alexkampos.springcourse.converters;

import com.alexkampos.springcourse.dto.view.GenreView;
import com.alexkampos.springcourse.dto.view.MovieView;
import com.alexkampos.springcourse.model.Genre;
import com.alexkampos.springcourse.model.Movie;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class GenreToGenreViewConverter implements Converter<Genre, GenreView> {

    @Autowired
    private MovieToMovieViewConverter movieToMovieViewConverter;

    @Override
    public GenreView convert(@NonNull Genre genre) {
        GenreView genreView = new GenreView();
        genreView.setGenreId(genre.getGenreId());
        genreView.setName(genre.getName());
        genreView.setMovies(convertMovieListToMovieViewList(genre.getMovies()));
        return genreView;
    }

    private List<MovieView> convertMovieListToMovieViewList(List<Movie> genreMovies) {

        List<MovieView> genreMoviesView = new ArrayList<>();
        genreMovies.forEach(movie -> {
            MovieView movieView = movieToMovieViewConverter.convert(movie, "genre");
            genreMoviesView.add(movieView);
        });
        return genreMoviesView;
    }

}
