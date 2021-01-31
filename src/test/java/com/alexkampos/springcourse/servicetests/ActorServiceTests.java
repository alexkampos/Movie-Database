package com.alexkampos.springcourse.servicetests;

import com.alexkampos.springcourse.base.BaseRequest.Id;
import com.alexkampos.springcourse.dto.baserequest.ActorBaseRequest;
import com.alexkampos.springcourse.dto.view.ActorView;
import com.alexkampos.springcourse.dto.view.MovieView;
import com.alexkampos.springcourse.model.Actor;
import com.alexkampos.springcourse.model.Movie;
import com.alexkampos.springcourse.repository.ActorRepo;
import com.alexkampos.springcourse.service.ActorService;
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
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class ActorServiceTests {

    @Autowired
    private ActorRepo actorRepo;

    @Autowired
    private ActorService actorService;

    @Test
    public void testFindActorOrThrow() {

        Actor newActor = actorRepo
                .save(new Actor(1, "Actor 1", 25, new ArrayList<Movie>()));

        Actor savedActor = actorService
                .findActorOrThrow(newActor.getActorId());

        assertEquals(newActor, savedActor);

    }

    @Test
    public void testGetActor() {

        Actor newActor = actorRepo
                .save(new Actor(1, "Actor 1", 25, new ArrayList<Movie>()));

        ActorView finalActorView = new ActorView(1, "Actor 1", 25, new ArrayList<MovieView>());

        ActorView savedActorView = actorService
                .getActor(newActor.getActorId());

        assertEquals(finalActorView, savedActorView);

    }

    @Test
    public void testCreate() {

        ActorBaseRequest newActor = new ActorBaseRequest("Actor 1", 25, new ArrayList<Id>());

        ActorView finalActorView = new ActorView(1, "Actor 1", 25, new ArrayList<MovieView>());

        ActorView savedActorView = actorService.create(newActor);

        assertEquals(finalActorView, savedActorView);

    }

    @Test
    public void testFindAllActors() {

        actorRepo.save(new Actor(1, "Actor 1", 25, new ArrayList<Movie>()));

        actorRepo.save(new Actor(2, "Actor 2", 30, new ArrayList<Movie>()));

        Pageable pageable = PageRequest.of(0, 10, Sort.by("actorId").ascending());

        List<ActorView> finalActorViewsList = Arrays
                .asList(new ActorView(1, "Actor 1", 25, new ArrayList<MovieView>()), new ActorView(2, "Actor 2", 30, new ArrayList<MovieView>()));

        Page<ActorView> finalActorViewsPage
                = new PageImpl<>(finalActorViewsList, pageable, finalActorViewsList.size());

        Page<ActorView> savedActorViewsPage = actorService.findAllActors(pageable);

        assertEquals(finalActorViewsPage, savedActorViewsPage);

    }

    @Test
    public void testDeleteActor() {

        Actor newActor = actorRepo
                .save(new Actor(1, "Actor 1", 25, new ArrayList<Movie>()));

        assertTrue(actorRepo.findById(newActor.getActorId()).isPresent());

        actorService.delete(newActor.getActorId());

        assertTrue(actorRepo.findById(newActor.getActorId()).isEmpty());

    }

    @Test
    public void testUpdateActor() {

       Actor newActor = actorRepo
                .save(new Actor(1, "Actor 1", 25, new ArrayList<Movie>()));
       
       ActorBaseRequest actorUpdates = new ActorBaseRequest("Actor 1 updated", 25, new ArrayList<Id>());
       
       ActorView afterUpdateActorView = new ActorView(1, "Actor 1 updated", 25, new ArrayList<MovieView>());
       
       ActorView savedActorAfterUpdates = actorService.update(newActor, actorUpdates);
       
       assertEquals(afterUpdateActorView, savedActorAfterUpdates);

    }

}
