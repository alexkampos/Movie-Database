package com.alexkampos.springcourse.servicetests;

import com.alexkampos.springcourse.base.BaseRequest;
import com.alexkampos.springcourse.dto.baserequest.LanguageBaseRequest;
import com.alexkampos.springcourse.dto.view.LanguageView;
import com.alexkampos.springcourse.dto.view.MovieView;
import com.alexkampos.springcourse.model.Language;
import com.alexkampos.springcourse.model.Movie;
import com.alexkampos.springcourse.repository.LanguageRepo;
import com.alexkampos.springcourse.service.LanguageService;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class LanguageServiceTests {

    @Autowired
    private LanguageRepo languageRepo;

    @Autowired
    private LanguageService languageService;

    @Test
    public void testFindLanguageOrThrow() {

        Language newLanguage = languageRepo
                .save(new Language(1, "Language 1", new ArrayList<Movie>()));

        Language savedLanguage = languageService
                .findLanguageOrThrow(newLanguage.getLanguageId());

        assertEquals(newLanguage, savedLanguage);

    }

    @Test
    public void testGetLanguage() {

        Language newLanguage = languageRepo
                .save(new Language(1, "Language 1", new ArrayList<Movie>()));

        LanguageView finalLanguageView = new LanguageView(1, "Language 1", new ArrayList<MovieView>());

        LanguageView savedLanguageView = languageService
                .getLanguage(newLanguage.getLanguageId());

        assertEquals(finalLanguageView, savedLanguageView);

    }

    @Test
    public void testCreate() {

        LanguageBaseRequest newLanguage = new LanguageBaseRequest("Language 1", new ArrayList<BaseRequest.Id>());

        LanguageView finalLanguageView = new LanguageView(1, "Language 1", new ArrayList<MovieView>());

        LanguageView savedLanguageView = languageService.create(newLanguage);

        assertEquals(finalLanguageView, savedLanguageView);

    }

    @Test
    public void testFindAllLanguages() {

        languageRepo.save(new Language(1, "Language 1", new ArrayList<Movie>()));

        languageRepo.save(new Language(2, "Language 2", new ArrayList<Movie>()));

        Pageable pageable = PageRequest.of(0, 10, Sort.by("LanguageId").ascending());

        List<LanguageView> finalLanguageViewsList = Arrays
                .asList(new LanguageView(1, "Language 1", new ArrayList<MovieView>()), new LanguageView(2, "Language 2", new ArrayList<MovieView>()));

        Page<LanguageView> finalLanguageViewsPage
                = new PageImpl<>(finalLanguageViewsList, pageable, finalLanguageViewsList.size());

        Page<LanguageView> savedLanguageViewsPage = languageService.findAllLanguages(pageable);

        assertEquals(finalLanguageViewsPage, savedLanguageViewsPage);

    }

    @Test
    public void testDeleteLanguage() {

        Language newLanguage = languageRepo
                .save(new Language(1, "Language 1", new ArrayList<Movie>()));

        assertTrue(languageRepo.findById(newLanguage.getLanguageId()).isPresent());

        languageService.delete(newLanguage.getLanguageId());

        assertTrue(languageRepo.findById(newLanguage.getLanguageId()).isEmpty());

    }

    @Test
    public void testUpdateLanguage() {

        Language newLanguage = languageRepo
                .save(new Language(1, "Language 1", new ArrayList<Movie>()));

        LanguageBaseRequest LanguageUpdates = new LanguageBaseRequest("Language 1 updated", new ArrayList<BaseRequest.Id>());

        LanguageView afterUpdateLanguageView = new LanguageView(1, "Language 1 updated", new ArrayList<MovieView>());

        LanguageView savedLanguageAfterUpdates = languageService.update(newLanguage, LanguageUpdates);

        assertEquals(afterUpdateLanguageView, savedLanguageAfterUpdates);

    }

}
