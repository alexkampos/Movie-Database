package com.alexkampos.springcourse.service;

import com.alexkampos.springcourse.dto.baserequest.ActorBaseRequest;
import com.alexkampos.springcourse.dto.view.ActorView;
import com.alexkampos.springcourse.model.Actor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ActorService {

    public Actor findActorOrThrow(Long actorId);

    public ActorView getActor(Long actorId);

    public ActorView create(ActorBaseRequest req);

    public Page<ActorView> findAllActors(Pageable pageable);

    public void delete(Long actorId);

    public ActorView update(Actor actor, ActorBaseRequest req);

}
