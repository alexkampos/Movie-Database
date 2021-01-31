
package com.alexkampos.springcourse.restcontrollertests;

import com.alexkampos.springcourse.base.BaseRequest.Id;
import com.alexkampos.springcourse.dto.baserequest.ActorBaseRequest;
import com.alexkampos.springcourse.dto.view.ActorView;
import com.alexkampos.springcourse.dto.view.MovieView;
import com.alexkampos.springcourse.model.Actor;
import com.alexkampos.springcourse.restcontroller.ActorRestController;
import com.alexkampos.springcourse.service.ActorService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ActorRestController.class)
public class ActorRestControllerTests {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @MockBean
    private ActorService service;
    
    private final Long idNumberOne = Long.valueOf("1");
    
    @Test
    public void testGetActor() throws Exception {
        
        ActorView savedActor = new ActorView(1, "Actor 1", 25, new ArrayList<MovieView>());
        
        Mockito.when(service.getActor(idNumberOne)).thenReturn(savedActor);
        
        String url = "/actor/1";
        
        MvcResult mvcResult = mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andReturn();
        
        String actualJsonResponse = mvcResult.getResponse().getContentAsString();
        
        String expectedJsonResponse = objectMapper.writeValueAsString(savedActor);
        
        assertThat(actualJsonResponse).isEqualToIgnoringWhitespace(expectedJsonResponse);
        
    }
    
    @Test
    public void testGetAllActors() throws Exception {
        
        List<ActorView> actorsList = new ArrayList<>();
        actorsList.add(new ActorView(1, "Actor 1", 25, new ArrayList<MovieView>()));
        actorsList.add(new ActorView(2, "Actor 2", 27, new ArrayList<MovieView>()));
        actorsList.add(new ActorView(3, "Actor 3", 29, new ArrayList<MovieView>()));
        actorsList.add(new ActorView(4, "Actor 4", 30, new ArrayList<MovieView>()));
        actorsList.add(new ActorView(5, "Actor 5", 34, new ArrayList<MovieView>()));
        Page<ActorView> actors = new PageImpl<>(actorsList);
        
        Mockito.when(service.findAllActors(PageRequest.of(0, 10, Sort.by("actorId").ascending()))).thenReturn(actors);
        
        String url = "/actor";
        
        MvcResult mvcResult = mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andReturn();
        
        String actualJsonResponse = mvcResult.getResponse().getContentAsString();
        
        String expectedJsonResponse = objectMapper.writeValueAsString(actors);
        
        assertThat(actualJsonResponse).isEqualToIgnoringWhitespace(expectedJsonResponse);
        
    }
    
    @Test
    public void testCreateActor() throws Exception{
        
        ArrayList movieIdsList = new ArrayList<Id>();
        movieIdsList.add(new Id(idNumberOne));
        
        ActorBaseRequest newActor = new ActorBaseRequest("Actor 1", 25, movieIdsList);
        ActorView savedActor = new ActorView(1, "Actor 1", 25, movieIdsList);
        
//      Equals implemented on ActorBaseRequest for eq() to work correctly
        Mockito.when(service.create(eq(newActor))).thenReturn(savedActor);
        
        String url = "/actor";
        
        mockMvc.perform(
                post(url)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(newActor))
                ).andExpect(status().isCreated())
                .andExpect(content().string(objectMapper.writeValueAsString(savedActor)));
    }
    
    @Test
    public void testUpdateActor() throws Exception{
        
        Long actorId, movieId;
        actorId = movieId = idNumberOne;
        
        ArrayList movieIdsList = new ArrayList<Id>();
        movieIdsList.add(new Id(movieId));
        
        ActorBaseRequest updatedActor = new ActorBaseRequest("Actor 1", 25, movieIdsList);
        Actor actor = new Actor("Actor 1", 25, movieIdsList);
        ActorView savedActor = new ActorView(1, "Actor 1", 25, movieIdsList);

        Mockito.when(service.findActorOrThrow(actorId)).thenReturn(actor);
        Mockito.when(service.update(eq(actor), eq(updatedActor))).thenReturn(savedActor);
        
        String url = "/actor/1";
        
        mockMvc.perform(
                put(url)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(updatedActor))
                ).andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(savedActor)));
    }
    
    @Test
    public void testDeleteActor() throws Exception{
        
        Long actorId = idNumberOne;
        
        Mockito.doNothing().when(service).delete(actorId);
        
        String url = "/actor/1";
        
        mockMvc.perform(
                delete(url))
                .andExpect(status().isNoContent());
        
        Mockito.verify(service, times(1)).delete(actorId);
        
    }
    
}
