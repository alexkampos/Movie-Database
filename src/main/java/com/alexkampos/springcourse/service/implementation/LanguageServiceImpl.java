package com.alexkampos.springcourse.service.implementation;

import com.alexkampos.springcourse.converters.LanguageToLanguageViewConverter;
import com.alexkampos.springcourse.dto.baserequest.LanguageBaseRequest;
import com.alexkampos.springcourse.dto.view.LanguageView;
import com.alexkampos.springcourse.error.EntityNotFoundException;
import com.alexkampos.springcourse.model.Language;
import com.alexkampos.springcourse.repository.LanguageRepo;
import com.alexkampos.springcourse.service.LanguageService;
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
public class LanguageServiceImpl implements LanguageService {

    private final LanguageRepo languageRepo;
    private final LanguageToLanguageViewConverter languageToLanguageViewConverter;
    private final MessageUtil messageUtil;
    private final MovieService movieService;
    private final EntityManager em;

    @Override
    public Language findLanguageOrThrow(Long languageId) {
        return languageRepo.findById(languageId)
                .orElseThrow(() -> new EntityNotFoundException(messageUtil.getMessage("language.NotFound", languageId)));
    }

    @Override
    public LanguageView getLanguage(Long languageId) {
        Language language = findLanguageOrThrow(languageId);
        return languageToLanguageViewConverter.convert(language);
    }

    @Override
    public LanguageView create(LanguageBaseRequest req) {
        Language languageSave = languageRepo.saveAndFlush(prepare(new Language(), req));
        if (!req.getMovies().isEmpty()) {
            movieService.addLanguagesToMovies(languageSave, req.getMovies());
        }
        em.refresh(languageSave);
        return languageToLanguageViewConverter.convert(languageSave);
    }

    @Override
    public Page<LanguageView> findAllLanguages(Pageable pageable) {
        Page<Language> languages = languageRepo.findAll(pageable);
        List<LanguageView> languageViews = new ArrayList<>();
        languages.forEach(language -> {
            LanguageView languageView = languageToLanguageViewConverter.convert(language);
            languageViews.add(languageView);
        });
        return new PageImpl<>(languageViews, pageable, languages.getTotalElements());
    }

    @Override
    public void delete(Long languageId) {
        try {
            languageRepo.deleteById(languageId);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException(messageUtil.getMessage("language.NotFound", languageId));
        }
    }

    @Override
    public LanguageView update(Language language, LanguageBaseRequest req) {
        Language newLanguage = languageRepo.saveAndFlush(prepare(language, req));
        if (!req.getMovies().isEmpty()) {
            movieService.addLanguagesToMovies(newLanguage, req.getMovies());
        }
        em.refresh(newLanguage);
        return languageToLanguageViewConverter.convert(newLanguage);
    }

    private Language prepare(Language language, LanguageBaseRequest req) {
        language.setName(req.getName());
        return language;
    }
}
