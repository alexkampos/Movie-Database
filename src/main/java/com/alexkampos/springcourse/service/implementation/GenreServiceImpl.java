package com.alexkampos.springcourse.service.implementation;

import com.alexkampos.springcourse.converters.GenreToGenreViewConverter;
import com.alexkampos.springcourse.dto.baserequest.GenreBaseRequest;
import com.alexkampos.springcourse.dto.view.GenreView;
import com.alexkampos.springcourse.error.EntityNotFoundException;
import com.alexkampos.springcourse.model.Genre;
import com.alexkampos.springcourse.repository.GenreRepo;
import com.alexkampos.springcourse.service.GenreService;
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
public class GenreServiceImpl implements GenreService {

    private final GenreRepo genreRepo;
    private final GenreToGenreViewConverter genreToGenreViewConverter;
    private final MessageUtil messageUtil;
    private final MovieService movieService;
    private final EntityManager em;

    @Override
    public Genre findGenreOrThrow(Long genreId) {
        return genreRepo.findById(genreId)
                .orElseThrow(() -> new EntityNotFoundException(messageUtil.getMessage("genre.NotFound", genreId)));
    }

    @Override
    public GenreView getGenre(Long genreId) {
        Genre genre = findGenreOrThrow(genreId);
        return genreToGenreViewConverter.convert(genre);
    }

    @Override
    public GenreView create(GenreBaseRequest req) {
        Genre genreSave = genreRepo.saveAndFlush(prepare(new Genre(), req));
        if (!req.getMovies().isEmpty()) {
            movieService.addGenresToMovies(genreSave, req.getMovies());
        }
        em.refresh(genreSave);
        return genreToGenreViewConverter.convert(genreSave);
    }

    @Override
    public Page<GenreView> findAllGenres(Pageable pageable) {
        Page<Genre> genres = genreRepo.findAll(pageable);
        List<GenreView> genreViews = new ArrayList<>();
        genres.forEach(genre -> {
            GenreView genreView = genreToGenreViewConverter.convert(genre);
            genreViews.add(genreView);
        });
        return new PageImpl<>(genreViews, pageable, genres.getTotalElements());
    }

    @Override
    public void delete(Long genreId) {
        try {
            genreRepo.deleteById(genreId);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException(messageUtil.getMessage("genre.NotFound", genreId));
        }
    }

    @Override
    public GenreView update(Genre genre, GenreBaseRequest req) {
        Genre newGenre = genreRepo.saveAndFlush(prepare(genre, req));
        if (!req.getMovies().isEmpty()) {
            movieService.addGenresToMovies(newGenre, req.getMovies());
        }
        em.refresh(newGenre);
        return genreToGenreViewConverter.convert(newGenre);
    }

    private Genre prepare(Genre genre, GenreBaseRequest req) {
        genre.setName(req.getName());
        return genre;
    }

}
