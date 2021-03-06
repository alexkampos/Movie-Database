package com.alexkampos.springcourse.repository;

import com.alexkampos.springcourse.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepo extends JpaRepository<Genre, Long> {

}
