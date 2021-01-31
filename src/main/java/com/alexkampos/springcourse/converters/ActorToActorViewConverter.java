package com.alexkampos.springcourse.converters;

import com.alexkampos.springcourse.dto.view.ActorView;
import com.alexkampos.springcourse.dto.view.MovieView;
import com.alexkampos.springcourse.model.Actor;
import com.alexkampos.springcourse.model.Movie;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class ActorToActorViewConverter implements Converter<Actor, ActorView> {

    @Autowired
    private MovieToMovieViewConverter movieToMovieViewConverter;

    @Override
    public ActorView convert(@NonNull Actor actor) {
        ActorView actorView = new ActorView();
        actorView.setActorId(actor.getActorId());
        actorView.setFullName(actor.getFullName());
        actorView.setAge(actor.getAge());
        actorView.setMovies(convertMovieListToMovieViewList(actor.getMovies()));
        return actorView;
    }

    private List<MovieView> convertMovieListToMovieViewList(List<Movie> actorMovies) {

        List<MovieView> actorMoviesView = new ArrayList<>();
        actorMovies.forEach(movie -> {
            MovieView movieView = movieToMovieViewConverter.convert(movie, "actor");
            actorMoviesView.add(movieView);
        });
        return actorMoviesView;
    }

}
