package com.alexkampos.springcourse.service.implementation;

import com.alexkampos.springcourse.converters.ActorToActorViewConverter;
import com.alexkampos.springcourse.dto.baserequest.ActorBaseRequest;
import com.alexkampos.springcourse.dto.view.ActorView;
import com.alexkampos.springcourse.error.EntityNotFoundException;
import com.alexkampos.springcourse.model.Actor;
import com.alexkampos.springcourse.repository.ActorRepo;
import com.alexkampos.springcourse.service.ActorService;
import com.alexkampos.springcourse.service.MovieService;
import com.alexkampos.springcourse.util.MessageUtil;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
@Transactional
public class ActorServiceImpl implements ActorService {

    private final ActorRepo actorRepo;
    private final ActorToActorViewConverter actorToActorViewConverter;
    private final MessageUtil messageUtil;
    private final MovieService movieService;
    private final EntityManager em;

    @Override
    public Actor findActorOrThrow(Long actorId) {
        return actorRepo.findById(actorId)
                .orElseThrow(() -> new EntityNotFoundException(messageUtil.getMessage("actor.NotFound", actorId)));
    }

    @Override
    public ActorView getActor(Long actorId) {
        Actor actor = findActorOrThrow(actorId);
        return actorToActorViewConverter.convert(actor);
    }

    @Override
    public ActorView create(ActorBaseRequest req) {
        Actor newActor = new Actor();
        prepare(newActor, req);
        Actor actorSave = actorRepo.saveAndFlush(newActor);
        if (!req.getMovies().isEmpty()) {
            movieService.addActorsToMovies(actorSave, req.getMovies());
        }
        em.refresh(actorSave);
        return actorToActorViewConverter.convert(actorSave);
    }

    @Override
    public Page<ActorView> findAllActors(Pageable pageable) {
        Page<Actor> actors = actorRepo.findAll(pageable);
        List<ActorView> actorViews = new ArrayList<>();
        actors.forEach(actor -> {
            ActorView actorView = actorToActorViewConverter.convert(actor);
            actorViews.add(actorView);
        });
        return new PageImpl<>(actorViews, pageable, actors.getTotalElements());
    }

    @Override
    public void delete(Long actorId) {
        try {
            actorRepo.deleteById(actorId);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException(messageUtil.getMessage("actor.NotFound", actorId));
        }
    }

    
    @Override
    public ActorView update(Actor actor, ActorBaseRequest req) {
        Actor newActor = actorRepo.saveAndFlush(prepare(actor, req));
        if (!req.getMovies().isEmpty()) {
            movieService.addActorsToMovies(newActor, req.getMovies());
        }
        em.refresh(newActor);
        return actorToActorViewConverter.convert(newActor);
    }

    private Actor prepare(Actor actor, ActorBaseRequest req) {
        actor.setFullName(req.getFullName());
        actor.setAge(req.getAge());
        return actor;
    }
}
