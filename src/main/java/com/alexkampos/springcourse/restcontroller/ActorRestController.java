package com.alexkampos.springcourse.restcontroller;

import com.alexkampos.springcourse.dto.baserequest.ActorBaseRequest;
import com.alexkampos.springcourse.dto.view.ActorView;
import com.alexkampos.springcourse.interfaces.BasicInfo;
import com.alexkampos.springcourse.model.Actor;
import com.alexkampos.springcourse.service.ActorService;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/actor")
@AllArgsConstructor
@CrossOrigin
public class ActorRestController {

    private final ActorService service;

    @GetMapping("/{id}")
    @ResponseBody
    public ActorView getActor(@PathVariable Long id) {
        return service.getActor(id);
    }

    @GetMapping
    @ResponseBody
    public Page<ActorView> getAllActors(@PageableDefault(sort = "actorId", direction = Sort.Direction.ASC) Pageable pageable) {
        return service.findAllActors(pageable);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public ActorView create(@RequestBody @Validated(BasicInfo.class) ActorBaseRequest req) {
        return service.create(req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteActor(@PathVariable Long id) {
        service.delete(id);
    }

    @PutMapping("/{id}")
    public ActorView updateActor(@PathVariable(name = "id") Long id,
            @RequestBody @Valid ActorBaseRequest req) {
        Actor actor = service.findActorOrThrow(id);
        return service.update(actor, req);
    }
}
