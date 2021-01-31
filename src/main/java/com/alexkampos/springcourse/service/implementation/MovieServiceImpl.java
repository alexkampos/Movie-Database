package com.alexkampos.springcourse.service.implementation;

import com.alexkampos.springcourse.base.BaseRequest;
import com.alexkampos.springcourse.base.BaseRequest.Id;
import com.alexkampos.springcourse.converters.MovieToMovieViewConverter;
import com.alexkampos.springcourse.dto.baserequest.MovieBaseRequest;
import com.alexkampos.springcourse.dto.view.MovieView;
import com.alexkampos.springcourse.error.EntityNotFoundException;
import com.alexkampos.springcourse.model.Actor;
import com.alexkampos.springcourse.model.Genre;
import com.alexkampos.springcourse.model.Language;
import com.alexkampos.springcourse.model.Movie;
import com.alexkampos.springcourse.repository.ActorRepo;
import com.alexkampos.springcourse.repository.GenreRepo;
import com.alexkampos.springcourse.repository.LanguageRepo;
import com.alexkampos.springcourse.repository.MovieRepo;
import com.alexkampos.springcourse.service.MovieService;
import com.alexkampos.springcourse.util.MessageUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepo movieRepo;
    private final MovieToMovieViewConverter movieToMovieViewConverter;
    private final MessageUtil messageUtil;
    private final ActorRepo actorRepo;
    private final LanguageRepo languageRepo;
    private final GenreRepo genreRepo;

    @Override
    public Movie findMovieOrThrow(Long movieId) {
        return movieRepo.findById(movieId)
                .orElseThrow(() -> new EntityNotFoundException(messageUtil.getMessage("actor.NotFound", movieId)));
    }

    @Override
    public MovieView getMovie(Long movieId) {
        Movie movie = findMovieOrThrow(movieId);
        return movieToMovieViewConverter.convert(movie, "none");
    }

    @Override
    public MovieView create(MovieBaseRequest req) {
        Movie movieSave = movieRepo.saveAndFlush(prepare(new Movie(), req));
        return movieToMovieViewConverter.convert(movieSave, "none");
    }

    @Override
    public Page<MovieView> findAllMovies(Pageable pageable) {
        Page<Movie> movies = movieRepo.findAll(pageable);
        List<MovieView> movieViews = new ArrayList<>();
        movies.forEach(movie -> {
            MovieView movieView = movieToMovieViewConverter.convert(movie, "none");
            movieViews.add(movieView);
        });
        return new PageImpl<>(movieViews, pageable, movies.getTotalElements());
    }

    @Transactional
    @Override
    public void delete(Long movieId) {
        try {
            movieRepo.deleteById(movieId);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException(messageUtil.getMessage("actor.NotFound", movieId));
        }
    }

    @Override
    public MovieView update(Movie movie, MovieBaseRequest req) {
        Movie movieForSave = movieRepo.saveAndFlush(prepare(movie, req));
        return movieToMovieViewConverter.convert(movieForSave, "none");
    }

    private Movie prepare(Movie movie, MovieBaseRequest req) {
        movie.setTitle(req.getTitle());
        movie.setDurationMinutes(req.getDurationMinutes());
        List<Actor> actorList = actorRepo.findAllById(req.getActors()
                .stream()
                .map(BaseRequest.Id::getId)
                .collect(Collectors.toList()));
        movie.setActors(actorList);
        List<Genre> genreList = genreRepo.findAllById(req.getGenres()
                .stream()
                .map(BaseRequest.Id::getId)
                .collect(Collectors.toList()));
        movie.setGenres(genreList);
        List<Language> languageList = languageRepo.findAllById(req.getLanguages()
                .stream()
                .map(BaseRequest.Id::getId)
                .collect(Collectors.toList()));
        movie.setLanguages(languageList);
        return movie;
    }

    @Override
    public void addActorsToMovies(Actor actor, List<Id> movieIds) {
        movieIds.forEach(movieId -> {
            Movie savedMovie = movieRepo.findById(movieId.getId()).get();
            savedMovie.getActors().add(actor);
            movieRepo.saveAndFlush(savedMovie);
        });
    }
    
    @Override
    public void addGenresToMovies(Genre genre, List<Id> movieIds) {
        movieIds.forEach(movieId -> {
            Movie savedMovie = movieRepo.findById(movieId.getId()).get();
            savedMovie.getGenres().add(genre);
            movieRepo.saveAndFlush(savedMovie);
        });
    }

    @Override
    public void addLanguagesToMovies(Language language, List<Id> movieIds) {
        movieIds.forEach(movieId -> {
            Movie savedMovie = movieRepo.findById(movieId.getId()).get();
            savedMovie.getLanguages().add(language);
            movieRepo.saveAndFlush(savedMovie);
        });
    }

}
