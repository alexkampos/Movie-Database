package com.alexkampos.springcourse.restcontrollertests;

import com.alexkampos.springcourse.base.BaseRequest;
import com.alexkampos.springcourse.dto.baserequest.LanguageBaseRequest;
import com.alexkampos.springcourse.dto.view.LanguageView;
import com.alexkampos.springcourse.dto.view.MovieView;
import com.alexkampos.springcourse.model.Language;
import com.alexkampos.springcourse.restcontroller.LanguageRestController;
import com.alexkampos.springcourse.service.LanguageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LanguageRestController.class)
public class LanguageRestControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private LanguageService service;
    
    private final Long idNumberOne = Long.valueOf("1");

    @Test
    public void testGetLanguage() throws Exception {

        LanguageView savedLanguage = new LanguageView(1, "Language 1", new ArrayList<MovieView>());

        Mockito.when(service.getLanguage(idNumberOne)).thenReturn(savedLanguage);

        String url = "/language/1";

        MvcResult mvcResult = mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andReturn();

        String actualJsonResponse = mvcResult.getResponse().getContentAsString();

        String expectedJsonResponse = objectMapper.writeValueAsString(savedLanguage);

        assertThat(actualJsonResponse).isEqualToIgnoringWhitespace(expectedJsonResponse);

    }
    
    @Test
    public void testGetAllLanguages() throws Exception {

        List<LanguageView> languagesList = new ArrayList<>();
        languagesList.add(new LanguageView(1, "Language 1", new ArrayList<MovieView>()));
        languagesList.add(new LanguageView(2, "Language 2", new ArrayList<MovieView>()));
        languagesList.add(new LanguageView(3, "Language 3", new ArrayList<MovieView>()));
        languagesList.add(new LanguageView(4, "Language 4", new ArrayList<MovieView>()));
        languagesList.add(new LanguageView(5, "Language 5", new ArrayList<MovieView>()));
        Page<LanguageView> languages = new PageImpl<>(languagesList);

        Mockito.when(service.findAllLanguages(PageRequest.of(0, 10, Sort.by("languageId").ascending()))).thenReturn(languages);

        String url = "/language";

        MvcResult mvcResult = mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andReturn();

        String actualJsonResponse = mvcResult.getResponse().getContentAsString();

        String expectedJsonResponse = objectMapper.writeValueAsString(languages);

        assertThat(actualJsonResponse).isEqualToIgnoringWhitespace(expectedJsonResponse);

    }
    
    @Test
    public void testCreateLanguage() throws Exception{
        
        ArrayList movieIdsList = new ArrayList<BaseRequest.Id>();
        movieIdsList.add(new BaseRequest.Id(idNumberOne));
        
        LanguageBaseRequest newLanguage = new LanguageBaseRequest("Language 1", movieIdsList);
        LanguageView savedLanguage = new LanguageView(1, "Language 1", movieIdsList);
        
//      Equals implemented on LanguageBaseRequest for eq() to work correctly
        Mockito.when(service.create(eq(newLanguage))).thenReturn(savedLanguage);
        
        String url = "/language";
        
        mockMvc.perform(
                post(url)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(newLanguage))
                ).andExpect(status().isCreated())
                .andExpect(content().string(objectMapper.writeValueAsString(savedLanguage)));
    }
    
    @Test
    public void testUpdateLanguage() throws Exception{
        
        Long languageId, movieId;
        languageId = movieId = idNumberOne;
        
        ArrayList movieIdsList = new ArrayList<BaseRequest.Id>();
        movieIdsList.add(new BaseRequest.Id(movieId));
        
        LanguageBaseRequest updatedLanguage = new LanguageBaseRequest("Language 1", movieIdsList);
        Language language = new Language("Language 1", movieIdsList);
        LanguageView savedLanguage = new LanguageView(1, "Language 1", movieIdsList);

        Mockito.when(service.findLanguageOrThrow(languageId)).thenReturn(language);
        Mockito.when(service.update(eq(language), eq(updatedLanguage))).thenReturn(savedLanguage);
        
        String url = "/language/1";
        
        mockMvc.perform(
                put(url)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(updatedLanguage))
                ).andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(savedLanguage)));
    }
    
    @Test
    public void testDeleteLanguage() throws Exception{
        
        Long languageId = idNumberOne;
        
        Mockito.doNothing().when(service).delete(languageId);
        
        String url = "/language/1";
        
        mockMvc.perform(
                delete(url))
                .andExpect(status().isNoContent());
        
        Mockito.verify(service, times(1)).delete(languageId);
        
    }
}
