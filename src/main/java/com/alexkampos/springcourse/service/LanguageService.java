package com.alexkampos.springcourse.service;

import com.alexkampos.springcourse.dto.baserequest.LanguageBaseRequest;
import com.alexkampos.springcourse.dto.view.LanguageView;
import com.alexkampos.springcourse.model.Language;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LanguageService {

    public Language findLanguageOrThrow(Long languageId);

    public LanguageView getLanguage(Long languageId);

    public LanguageView create(LanguageBaseRequest req);

    public Page<LanguageView> findAllLanguages(Pageable pageable);

    public void delete(Long languageId);

    public LanguageView update(Language language, LanguageBaseRequest req);

}
