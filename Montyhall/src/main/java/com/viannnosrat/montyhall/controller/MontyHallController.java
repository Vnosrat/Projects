package com.viannnosrat.montyhall.controller;

import com.viannnosrat.montyhall.model.GameResult;
import com.viannnosrat.montyhall.service.MontyHallService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class MontyHallController {

    @Autowired
    MontyHallService montyHallService;

    private static final Logger logger = LoggerFactory.getLogger(MontyHallService.class);

    @PostMapping("/play")
    public ResponseEntity<GameResult> playGame(@RequestParam(value = "iterations", defaultValue = "1000") Integer iterations,
                                               @RequestParam(value = "strategy", defaultValue = "SWITCH") String strategy) {

        String gameStrategy = strategy;
        try{
            // make string uppercase if nessisary
            if(!isStringUpperCase(strategy)){
                gameStrategy = strategy.toUpperCase();
            }

            GameResult.StrategyEnum.valueOf(gameStrategy);

        } catch(IllegalArgumentException e){

            logger.error("invalid strategy parameter in request");
            return new ResponseEntity( "Invalid Strategy, please choose from KEEP or SWITCH", HttpStatus.BAD_REQUEST);
        }
        GameResult result = montyHallService.returnResultFromIterations(gameStrategy,iterations);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    private static boolean isStringUpperCase(String str){

        //convert String to char array
        char[] charArray = str.toCharArray();

        for(int i=0; i < charArray.length; i++){

            //if the character is a letter
            if( Character.isLetter(charArray[i]) ){

                //if any character is not in upper case, return false
                if( !Character.isUpperCase( charArray[i] ))
                    return false;
            }
        }

        return true;
    }

}

