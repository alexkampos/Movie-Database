
package com.alexkampos.springcourse.servicetests;

import com.alexkampos.springcourse.base.BaseRequest;
import com.alexkampos.springcourse.base.BaseRequest.Id;
import com.alexkampos.springcourse.dto.baserequest.MovieBaseRequest;
import com.alexkampos.springcourse.dto.view.ActorView;
import com.alexkampos.springcourse.dto.view.GenreView;
import com.alexkampos.springcourse.dto.view.LanguageView;
import com.alexkampos.springcourse.dto.view.MovieView;
import com.alexkampos.springcourse.dto.view.MovieView;
import com.alexkampos.springcourse.model.Actor;
import com.alexkampos.springcourse.model.Genre;
import com.alexkampos.springcourse.model.Language;
import com.alexkampos.springcourse.model.Movie;
import com.alexkampos.springcourse.model.Movie;
import com.alexkampos.springcourse.repository.MovieRepo;
import com.alexkampos.springcourse.service.MovieService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.transaction.Transactional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class MovieServiceTests {
    
    @Autowired
    private MovieRepo movieRepo;

    @Autowired
    private MovieService movieService;

    @Test
    public void testFindMovieOrThrow() {

        Movie newMovie = movieRepo
                .save(new Movie(1, "Movie 1", 125, new ArrayList<Actor>(), new ArrayList<Genre>(), new ArrayList<Language>()));

        Movie savedMovie = movieService
                .findMovieOrThrow(newMovie.getMovieId());

        assertEquals(newMovie, savedMovie);

    }

    @Test
    public void testGetMovie() {

        Movie newMovie = movieRepo
                .save(new Movie(1, "Movie 1", 125, new ArrayList<Actor>(), new ArrayList<Genre>(), new ArrayList<Language>()));

        MovieView finalMovieView = new MovieView(1, "Movie 1", 125, new ArrayList<ActorView>(), new ArrayList<GenreView>(), new ArrayList<LanguageView>());

        MovieView savedMovieView = movieService
                .getMovie(newMovie.getMovieId());

        assertEquals(finalMovieView, savedMovieView);

    }

    @Test
    public void testCreate() {

        MovieBaseRequest newMovie = new MovieBaseRequest("Movie 1", 125, new ArrayList<Id>(), new ArrayList<Id>(), new ArrayList<Id>());

        MovieView finalMovieView = new MovieView(1, "Movie 1", 125, new ArrayList<ActorView>(), new ArrayList<GenreView>(), new ArrayList<LanguageView>());

        MovieView savedMovieView = movieService.create(newMovie);

        assertEquals(finalMovieView, savedMovieView);

    }

    @Test
    public void testFindAllMovies() {

        movieRepo.save(new Movie(1, "Movie 1", 125, new ArrayList<Actor>(), new ArrayList<Genre>(), new ArrayList<Language>()));

        movieRepo.save(new Movie(2, "Movie 2", 130, new ArrayList<Actor>(), new ArrayList<Genre>(), new ArrayList<Language>()));

        Pageable pageable = PageRequest.of(0, 10, Sort.by("MovieId").ascending());

        List<MovieView> finalMovieViewsList = Arrays
                .asList(new MovieView(1, "Movie 1", 125, new ArrayList<ActorView>(), new ArrayList<GenreView>(), new ArrayList<LanguageView>()),
                        new MovieView(2, "Movie 2", 130, new ArrayList<ActorView>(), new ArrayList<GenreView>(), new ArrayList<LanguageView>()));

        Page<MovieView> finalMovieViewsPage
                = new PageImpl<>(finalMovieViewsList, pageable, finalMovieViewsList.size());

        Page<MovieView> savedMovieViewsPage = movieService.findAllMovies(pageable);

        assertEquals(finalMovieViewsPage, savedMovieViewsPage);

    }

    @Test
    public void testDeleteMovie() {

        Movie newMovie = movieRepo
                .save(new Movie(1, "Movie 1", 125, new ArrayList<Actor>(), new ArrayList<Genre>(), new ArrayList<Language>()));

        assertTrue(movieRepo.findById(newMovie.getMovieId()).isPresent());

        movieService.delete(newMovie.getMovieId());

        assertTrue(movieRepo.findById(newMovie.getMovieId()).isEmpty());

    }

    @Test
    public void testUpdateMovie() {

       Movie newMovie = movieRepo
                .save(new Movie(1, "Movie 1", 125, new ArrayList<Actor>(), new ArrayList<Genre>(), new ArrayList<Language>()));
       
       MovieBaseRequest MovieUpdates = new MovieBaseRequest("Movie 1 updated", 125, new ArrayList<Id>(), new ArrayList<Id>(), new ArrayList<Id>());
       
       MovieView afterUpdateMovieView = new MovieView(1, "Movie 1 updated", 125, new ArrayList<ActorView>(), new ArrayList<GenreView>(), new ArrayList<LanguageView>());
       
       MovieView savedMovieAfterUpdates = movieService.update(newMovie, MovieUpdates);
       
       assertEquals(afterUpdateMovieView, savedMovieAfterUpdates);

    }
    
}
