
package com.alexkampos.springcourse.servicetests;

import com.alexkampos.springcourse.base.BaseRequest.Id;
import com.alexkampos.springcourse.dto.baserequest.GenreBaseRequest;
import com.alexkampos.springcourse.dto.view.GenreView;
import com.alexkampos.springcourse.dto.view.MovieView;
import com.alexkampos.springcourse.model.Genre;
import com.alexkampos.springcourse.model.Movie;
import com.alexkampos.springcourse.repository.GenreRepo;
import com.alexkampos.springcourse.service.GenreService;
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
public class GenreServiceTests {
    
    @Autowired
    private GenreRepo genreRepo;

    @Autowired
    private GenreService genreService;

    @Test
    public void testFindGenreOrThrow() {

        Genre newGenre = genreRepo
                .save(new Genre(1, "Genre 1", new ArrayList<Movie>()));

        Genre savedGenre = genreService
                .findGenreOrThrow(newGenre.getGenreId());

        assertEquals(newGenre, savedGenre);

    }

    @Test
    public void testGetGenre() {

        Genre newGenre = genreRepo
                .save(new Genre(1, "Genre 1", new ArrayList<Movie>()));

        GenreView finalGenreView = new GenreView(1, "Genre 1", new ArrayList<MovieView>());

        GenreView savedGenreView = genreService
                .getGenre(newGenre.getGenreId());

        assertEquals(finalGenreView, savedGenreView);

    }

    @Test
    public void testCreate() {

        GenreBaseRequest newGenre = new GenreBaseRequest("Genre 1", new ArrayList<Id>());

        GenreView finalGenreView = new GenreView(1, "Genre 1", new ArrayList<MovieView>());

        GenreView savedGenreView = genreService.create(newGenre);

        assertEquals(finalGenreView, savedGenreView);

    }

    @Test
    public void testFindAllGenres() {

        genreRepo.save(new Genre(1, "Genre 1", new ArrayList<Movie>()));

        genreRepo.save(new Genre(2, "Genre 2", new ArrayList<Movie>()));

        Pageable pageable = PageRequest.of(0, 10, Sort.by("GenreId").ascending());

        List<GenreView> finalGenreViewsList = Arrays
                .asList(new GenreView(1, "Genre 1", new ArrayList<MovieView>()), new GenreView(2, "Genre 2", new ArrayList<MovieView>()));

        Page<GenreView> finalGenreViewsPage
                = new PageImpl<>(finalGenreViewsList, pageable, finalGenreViewsList.size());

        Page<GenreView> savedGenreViewsPage = genreService.findAllGenres(pageable);

        assertEquals(finalGenreViewsPage, savedGenreViewsPage);

    }

    @Test
    public void testDeleteGenre() {

        Genre newGenre = genreRepo
                .save(new Genre(1, "Genre 1", new ArrayList<Movie>()));

        assertTrue(genreRepo.findById(newGenre.getGenreId()).isPresent());

        genreService.delete(newGenre.getGenreId());

        assertTrue(genreRepo.findById(newGenre.getGenreId()).isEmpty());

    }

    @Test
    public void testUpdateGenre() {

       Genre newGenre = genreRepo
                .save(new Genre(1, "Genre 1", new ArrayList<Movie>()));
       
       GenreBaseRequest GenreUpdates = new GenreBaseRequest("Genre 1 updated", new ArrayList<Id>());
       
       GenreView afterUpdateGenreView = new GenreView(1, "Genre 1 updated", new ArrayList<MovieView>());
       
       GenreView savedGenreAfterUpdates = genreService.update(newGenre, GenreUpdates);
       
       assertEquals(afterUpdateGenreView, savedGenreAfterUpdates);

    }
    
}
