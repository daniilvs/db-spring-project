//package com.formulauno.service;
//
//import com.formulauno.service.IO;
//import com.formulauno.repository.EngineRepository;
//import com.formulauno.repository.TeamRepository;
//import org.springframework.stereotype.Service;
//
//@Service("Manager")
//public class Manager {
//    private final I18nService i18nService;
//    private final IO ioService;
//    private final CurrentLocaleService localeService;
//    private final EngineRepository engineRepository;
//    private final TeamRepository teamRepository;
//
//
//    public Manager(I18nService i18nService, IO ioService, CurrentLocaleService locleService, EngineRepository engineRepository, TeamRepository teamRepository) {
//        this.i18nService = i18nService;
//        this.ioService = ioService;
//        this.localeService = locleService;
//        this.engineRepository = engineRepository;
//        this.teamRepository = teamRepository;
//
//    }
//
//    public void loop() {
//        selectLanguage();
//        String userInput;
//        do {
//            ioService.print("> ");
//            userInput = ioService.readLine().strip().toLowerCase();
//            try {
//                if (userInput.equals("find-all")) {
//                    findAll();
//                } else if (userInput.contains("find")) {
//                    findByArg(userInput);
//                } else if (userInput.equals("help")) {
//                    help();
//                } else if(userInput.contains("lang")){
//                    checkLang(userInput);
//                } else {
//                    unrecognizedCommand(userInput);
//                }
//            } catch (Exception e) {
//                ioService.print("Error: " + e.getMessage());
//            }
//
//        } while (!"exit".equals(userInput));
//    }
//
//    private void checkLang(String userInput){
//        try{
//            var list = userInput.split(" ");
//            var language = list[1];
//            localeService.set(language);
//            ioService.println(localeService.get());
//        } catch (Exception e){
//            ioService.println("Selected default language");
//        }
//    }
//
//    private void selectLanguage() {
//        try{
//            ioService.print("Select language(ru/en): ");
//            var language = ioService.readLine();
//            localeService.set(language);
//            ioService.println(i18nService.getMessage("welcome"));
//        }catch (Exception e){
//            ioService.println("Selected default language");
//        }
//    }
//
//    private void findAll() {
//        try {
//            var result = repository.getAll();
//            ioService.println(result);
//        } catch (Exception e) {
//            logger.logERROR(e.toString());
//            ioService.println(e.toString());
//        }
//    }
//
//    private void help() {
//        ioService.println(i18nService.getMessage("helpCommand"));
//    }
//
//    private void findByArg(String userInput) {
//        try {
//            var list = userInput.split(" ");
//            var name = list[1];
//            var result = repository.getByArg(name);
//            ioService.println(result.isEmpty() ? i18nService.getMessage("no-info") : result);
//        } catch (Exception e) {
//            logger.logERROR(e.toString());
//            ioService.println(e.toString());
//        }
//
//        private void unrecognizedCommand(String unregMessage) {
//            ioService.println(unregMessage + ": " + i18nService.getMessage("command-not-found"));
//        }
//}
