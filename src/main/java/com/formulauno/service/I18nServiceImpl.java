package com.formulauno.service;


import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class I18nServiceImpl implements I18nService {
    private final MessageSource messageSource;
    private final CurrentLocaleService languageService;

    @Override
    public String getMessage(String code) {
        return getMessage(code, null);
    }

    @Override
    public String getMessage(String code, Map<String, Object> params) {
        var locale = languageService.get();
        return messageSource.getMessage(code, new Object[0], locale);
    }
}