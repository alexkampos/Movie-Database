package com.alexkampos.springcourse.restcontroller;

import com.alexkampos.springcourse.dto.baserequest.GenreBaseRequest;
import com.alexkampos.springcourse.dto.view.GenreView;
import com.alexkampos.springcourse.model.Genre;
import com.alexkampos.springcourse.service.GenreService;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
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
@RequestMapping("/genre")
@AllArgsConstructor
@CrossOrigin
public class GenreRestController {

    private final GenreService service;

    @GetMapping("/{id}")
    @ResponseBody
    public GenreView getGenre(@PathVariable Long id) {
        return service.getGenre(id);
    }

    @GetMapping
    @ResponseBody
    public Page<GenreView> getAllGenres(@PageableDefault(sort = "genreId", direction = Sort.Direction.ASC) Pageable pageable) {
        return service.findAllGenres(pageable);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public GenreView create(@RequestBody @Valid GenreBaseRequest req) {
        return service.create(req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGenre(@PathVariable Long id) {
        service.delete(id);
    }

    @PutMapping("/{id}")
    public GenreView updateGenre(@PathVariable(name = "id") Long id,
            @RequestBody @Valid GenreBaseRequest req) {
        Genre actor = service.findGenreOrThrow(id);
        return service.update(actor, req);
    }
}
