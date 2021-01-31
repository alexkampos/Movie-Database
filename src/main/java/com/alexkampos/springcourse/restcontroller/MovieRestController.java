package com.alexkampos.springcourse.restcontroller;

import com.alexkampos.springcourse.dto.baserequest.MovieBaseRequest;
import com.alexkampos.springcourse.dto.view.MovieView;
import com.alexkampos.springcourse.model.Movie;
import com.alexkampos.springcourse.service.MovieService;
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
@RequestMapping("/movie")
@AllArgsConstructor
@CrossOrigin
public class MovieRestController {

    private final MovieService service;

    @GetMapping("/{id}")
    @ResponseBody
    public MovieView getMovie(@PathVariable Long id) {
        return service.getMovie(id);
    }

    @GetMapping
    @ResponseBody
    public Page<MovieView> getAllMovies(@PageableDefault(sort = "movieId", direction = Sort.Direction.ASC) Pageable pageable) {
        return service.findAllMovies(pageable);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public MovieView create(@RequestBody @Valid MovieBaseRequest req) {
        return service.create(req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMovie(@PathVariable Long id) {
        service.delete(id);
    }

    @PutMapping("/{id}")
    public MovieView updateMovie(@PathVariable(name = "id") Long id,
            @RequestBody @Valid MovieBaseRequest req) {
        Movie movie = service.findMovieOrThrow(id);
        return service.update(movie, req);
    }
}
