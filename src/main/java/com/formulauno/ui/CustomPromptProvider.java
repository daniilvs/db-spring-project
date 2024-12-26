package com.formulauno.ui;

import lombok.RequiredArgsConstructor;
import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.shell.jline.PromptProvider;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomPromptProvider implements PromptProvider {
    private static final AttributedString
            UPDATING_TEAM_PROMPT = new AttributedString("processing-book> ",
                    AttributedStyle.BOLD.foreground(AttributedStyle.CYAN)),
            UPDATING_ENGINE_PROMPT = new AttributedString("processing-book> ",
                    AttributedStyle.BOLD.foreground(AttributedStyle.YELLOW)),
            MAIN_MENU_PROMPT = new AttributedString("main-menu> ",
                    AttributedStyle.BOLD.foreground(AttributedStyle.MAGENTA));

    private final Commands commands;

    @Override
    public AttributedString getPrompt() {
        return switch (commands.getState()) {
            case PROCESSING_TEAM -> UPDATING_TEAM_PROMPT;
            case PROCESSING_ENGINE -> UPDATING_ENGINE_PROMPT;
            case MAIN_MENU -> MAIN_MENU_PROMPT;
        };
    }
}
