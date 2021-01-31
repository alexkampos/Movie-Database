
package com.alexkampos.springcourse.restcontrollertests;

import com.alexkampos.springcourse.base.BaseRequest;
import com.alexkampos.springcourse.dto.baserequest.MovieBaseRequest;
import com.alexkampos.springcourse.dto.view.ActorView;
import com.alexkampos.springcourse.dto.view.GenreView;
import com.alexkampos.springcourse.dto.view.LanguageView;
import com.alexkampos.springcourse.dto.view.MovieView;
import com.alexkampos.springcourse.model.Movie;
import com.alexkampos.springcourse.restcontroller.MovieRestController;
import com.alexkampos.springcourse.service.MovieService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.Mockito;
import static org.mockito.Mockito.times;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MovieRestController.class)
public class MovieRestControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MovieService service;
    
    private final Long idNumberOne = Long.valueOf("1");

    @Test
    public void testGetMovie() throws Exception {
        
        MovieView savedMovie = new MovieView(1, "Movie 1", 120, new ArrayList<ActorView>(), new ArrayList<GenreView>(), new ArrayList<LanguageView>());
        
        Mockito.when(service.getMovie(idNumberOne)).thenReturn(savedMovie);
        
        String url = "/movie/1";
        
        MvcResult mvcResult = mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andReturn();
        
        String actualJsonResponse = mvcResult.getResponse().getContentAsString();
        
        String expectedJsonResponse = objectMapper.writeValueAsString(savedMovie);
        
        assertThat(actualJsonResponse).isEqualToIgnoringWhitespace(expectedJsonResponse);
        
    }
    
    @Test
    public void testGetAllMovies() throws Exception {

        List<MovieView> moviesList = new ArrayList<>();
        moviesList.add(new MovieView(1, "Movie 1", 120,  new ArrayList<ActorView>(), new ArrayList<GenreView>(), new ArrayList<LanguageView>()));
        moviesList.add(new MovieView(2, "Movie 2", 125,  new ArrayList<ActorView>(), new ArrayList<GenreView>(), new ArrayList<LanguageView>()));
        moviesList.add(new MovieView(3, "Movie 3", 140,  new ArrayList<ActorView>(), new ArrayList<GenreView>(), new ArrayList<LanguageView>()));
        moviesList.add(new MovieView(4, "Movie 4", 100,  new ArrayList<ActorView>(), new ArrayList<GenreView>(), new ArrayList<LanguageView>()));
        moviesList.add(new MovieView(5, "Movie 5", 145,  new ArrayList<ActorView>(), new ArrayList<GenreView>(), new ArrayList<LanguageView>()));
        Page<MovieView> movies = new PageImpl<>(moviesList);

        Mockito.when(service.findAllMovies(PageRequest.of(0, 10, Sort.by("movieId").ascending()))).thenReturn(movies);

        String url = "/movie";

        MvcResult mvcResult = mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andReturn();

        String actualJsonResponse = mvcResult.getResponse().getContentAsString();

        String expectedJsonResponse = objectMapper.writeValueAsString(movies);

        assertThat(actualJsonResponse).isEqualToIgnoringWhitespace(expectedJsonResponse);

    }
    
    @Test
    public void testCreateActor() throws Exception{
        
        ArrayList actorIdsList, genreIdsList, languageIdsList;
        actorIdsList = genreIdsList = languageIdsList = new ArrayList<BaseRequest.Id>();
        Long actorId, genreId, languageId;
        actorId = genreId = languageId = idNumberOne;
        actorIdsList.add(new BaseRequest.Id(actorId));
        genreIdsList.add(new BaseRequest.Id(genreId));
        languageIdsList.add(new BaseRequest.Id(languageId));
        
        MovieBaseRequest newMovie = new MovieBaseRequest("Movie 1", 120, actorIdsList, genreIdsList, languageIdsList);
        MovieView savedMovie = new MovieView(1, "Movie 1", 120, actorIdsList, genreIdsList, languageIdsList);
        
//      Equals implemented on MovieBaseRequest for eq() to work correctly
        Mockito.when(service.create(eq(newMovie))).thenReturn(savedMovie);
        
        String url = "/movie";
        
        mockMvc.perform(
                post(url)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(newMovie))
                ).andExpect(status().isCreated())
                .andExpect(content().string(objectMapper.writeValueAsString(savedMovie)));
    }
    
    @Test
    public void testUpdateMovie() throws Exception{
        
        ArrayList actorIdsList, genreIdsList, languageIdsList;
        actorIdsList = genreIdsList = languageIdsList = new ArrayList<BaseRequest.Id>();
        Long actorId, movieId, genreId, languageId;
        actorId = movieId = genreId = languageId = idNumberOne;
        actorIdsList.add(new BaseRequest.Id(actorId));
        genreIdsList.add(new BaseRequest.Id(genreId));
        languageIdsList.add(new BaseRequest.Id(languageId));
        
        MovieBaseRequest updatedMovie = new MovieBaseRequest("Movie 1", 120, actorIdsList, genreIdsList, languageIdsList);
        Movie movie = new Movie("Movie 1", 120, actorIdsList, genreIdsList, languageIdsList);
        MovieView savedMovie = new MovieView(1, "Movie 1", 120, actorIdsList, genreIdsList, languageIdsList);

        Mockito.when(service.findMovieOrThrow(movieId)).thenReturn(movie);
        Mockito.when(service.update(eq(movie), eq(updatedMovie))).thenReturn(savedMovie);
        
        String url = "/movie/1";
        
        mockMvc.perform(
                put(url)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(updatedMovie))
                ).andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(savedMovie)));
    }
    
    @Test
    public void testDeleteMovie() throws Exception{
        
        Long movieId = idNumberOne;
        
        Mockito.doNothing().when(service).delete(movieId);
        
        String url = "/movie/1";
        
        mockMvc.perform(
                delete(url))
                .andExpect(status().isNoContent());
        
        Mockito.verify(service, times(1)).delete(movieId);
        
    }
}
