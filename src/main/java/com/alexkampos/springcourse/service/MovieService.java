package com.alexkampos.springcourse.service;

import com.alexkampos.springcourse.base.BaseRequest.Id;
import com.alexkampos.springcourse.dto.baserequest.MovieBaseRequest;
import com.alexkampos.springcourse.dto.view.MovieView;
import com.alexkampos.springcourse.model.Actor;
import com.alexkampos.springcourse.model.Genre;
import com.alexkampos.springcourse.model.Language;
import com.alexkampos.springcourse.model.Movie;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MovieService {

    public Movie findMovieOrThrow(Long movieId);

    public MovieView getMovie(Long movieId);

    public MovieView create(MovieBaseRequest req);

    public Page<MovieView> findAllMovies(Pageable pageable);

    public void delete(Long movieId);

    public MovieView update(Movie movie, MovieBaseRequest req);
    
    public void addActorsToMovies(Actor actor, List<Id> movieIdsActorPlayed);
    
    public void addGenresToMovies(Genre genre, List<Id> movieIds);
    
    public void addLanguagesToMovies(Language language, List<Id> movieIds);

}
