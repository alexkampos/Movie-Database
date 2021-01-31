package com.alexkampos.springcourse.repository;

import com.alexkampos.springcourse.model.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LanguageRepo extends JpaRepository<Language, Long> {

}
