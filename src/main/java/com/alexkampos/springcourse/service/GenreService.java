package com.alexkampos.springcourse.service;

import com.alexkampos.springcourse.dto.baserequest.GenreBaseRequest;
import com.alexkampos.springcourse.dto.view.GenreView;
import com.alexkampos.springcourse.model.Genre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GenreService {

    public Genre findGenreOrThrow(Long genreId);

    public GenreView getGenre(Long genreId);

    public GenreView create(GenreBaseRequest req);

    public Page<GenreView> findAllGenres(Pageable pageable);

    public void delete(Long genreId);

    public GenreView update(Genre genre, GenreBaseRequest req);

}
