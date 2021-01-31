package com.alexkampos.springcourse.restcontroller;

import com.alexkampos.springcourse.dto.baserequest.LanguageBaseRequest;
import com.alexkampos.springcourse.dto.view.LanguageView;
import com.alexkampos.springcourse.model.Language;
import com.alexkampos.springcourse.service.LanguageService;
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
@RequestMapping("/language")
@AllArgsConstructor
@CrossOrigin
public class LanguageRestController {

    private final LanguageService service;

    @GetMapping("/{id}")
    @ResponseBody
    public LanguageView getLanguage(@PathVariable Long id) {
        return service.getLanguage(id);
    }

    @GetMapping
    @ResponseBody
    public Page<LanguageView> getAllLanguages(@PageableDefault(sort = "languageId", direction = Sort.Direction.ASC) Pageable pageable) {
        return service.findAllLanguages(pageable);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public LanguageView create(@RequestBody @Valid LanguageBaseRequest req) {
        return service.create(req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLanguage(@PathVariable Long id) {
        service.delete(id);
    }

    @PutMapping("/{id}")
    public LanguageView updateLanguage(@PathVariable(name = "id") Long id,
            @RequestBody @Valid LanguageBaseRequest req) {
        Language language = service.findLanguageOrThrow(id);
        return service.update(language, req);
    }
}
