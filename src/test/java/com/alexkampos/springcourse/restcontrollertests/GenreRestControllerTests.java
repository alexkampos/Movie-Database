package com.alexkampos.springcourse.restcontrollertests;

import com.alexkampos.springcourse.base.BaseRequest;
import com.alexkampos.springcourse.dto.baserequest.GenreBaseRequest;
import com.alexkampos.springcourse.dto.view.GenreView;
import com.alexkampos.springcourse.dto.view.MovieView;
import com.alexkampos.springcourse.model.Genre;
import com.alexkampos.springcourse.restcontroller.GenreRestController;
import com.alexkampos.springcourse.service.GenreService;
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

@WebMvcTest(GenreRestController.class)
public class GenreRestControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private GenreService service;

    private final Long idNumberOne = Long.valueOf("1");

    @Test
    public void testGetGenre() throws Exception {

        GenreView savedGenre = new GenreView(1, "Genre 1", new ArrayList<MovieView>());

        Mockito.when(service.getGenre(idNumberOne)).thenReturn(savedGenre);

        String url = "/genre/1";

        MvcResult mvcResult = mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andReturn();

        String actualJsonResponse = mvcResult.getResponse().getContentAsString();

        String expectedJsonResponse = objectMapper.writeValueAsString(savedGenre);

        assertThat(actualJsonResponse).isEqualToIgnoringWhitespace(expectedJsonResponse);

    }

    @Test
    public void testGetAllGenres() throws Exception {

        List<GenreView> genresList = new ArrayList<>();
        genresList.add(new GenreView(1, "Genre 1", new ArrayList<MovieView>()));
        genresList.add(new GenreView(2, "Genre 2", new ArrayList<MovieView>()));
        genresList.add(new GenreView(3, "Genre 3", new ArrayList<MovieView>()));
        genresList.add(new GenreView(4, "Genre 4", new ArrayList<MovieView>()));
        genresList.add(new GenreView(5, "Genre 5", new ArrayList<MovieView>()));
        Page<GenreView> genres = new PageImpl<>(genresList);

        Mockito.when(service.findAllGenres(PageRequest.of(0, 10, Sort.by("genreId").ascending()))).thenReturn(genres);

        String url = "/genre";

        MvcResult mvcResult = mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andReturn();

        String actualJsonResponse = mvcResult.getResponse().getContentAsString();

        String expectedJsonResponse = objectMapper.writeValueAsString(genres);

        assertThat(actualJsonResponse).isEqualToIgnoringWhitespace(expectedJsonResponse);

    }

    @Test
    public void testCreateGenre() throws Exception {

        ArrayList movieIdsList = new ArrayList<BaseRequest.Id>();
        movieIdsList.add(new BaseRequest.Id(idNumberOne));

        GenreBaseRequest newGenre = new GenreBaseRequest("Genre 1", movieIdsList);
        GenreView savedGenre = new GenreView(1, "Genre 1", movieIdsList);

//      Equals implemented on GenreBaseRequest for eq() to work correctly
        Mockito.when(service.create(eq(newGenre))).thenReturn(savedGenre);

        String url = "/genre";

        mockMvc.perform(
                post(url)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(newGenre))
        ).andExpect(status().isCreated())
                .andExpect(content().string(objectMapper.writeValueAsString(savedGenre)));
    }

    @Test
    public void testUpdateGenre() throws Exception {

        Long genreId, movieId;
        genreId = movieId = idNumberOne;

        ArrayList movieIdsList = new ArrayList<BaseRequest.Id>();
        movieIdsList.add(new BaseRequest.Id(movieId));

        GenreBaseRequest updatedGenre = new GenreBaseRequest("Genre 1", movieIdsList);
        Genre genre = new Genre("Genre 1", movieIdsList);
        GenreView savedGenre = new GenreView(1, "Genre 1", movieIdsList);

        Mockito.when(service.findGenreOrThrow(genreId)).thenReturn(genre);
        Mockito.when(service.update(eq(genre), eq(updatedGenre))).thenReturn(savedGenre);

        String url = "/genre/1";

        mockMvc.perform(
                put(url)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(updatedGenre))
                ).andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(savedGenre)));
    }

    @Test
    public void testDeleteGenre() throws Exception {

        Long genreId = idNumberOne;

        Mockito.doNothing().when(service).delete(genreId);

        String url = "/genre/1";

        mockMvc.perform(
                delete(url))
                .andExpect(status().isNoContent());

        Mockito.verify(service, times(1)).delete(genreId);

    }

}
