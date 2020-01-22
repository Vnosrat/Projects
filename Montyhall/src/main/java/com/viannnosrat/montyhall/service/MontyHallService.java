package com.viannnosrat.montyhall.service;

import com.viannnosrat.montyhall.model.GameResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class MontyHallService {
    private static final Logger logger = LoggerFactory.getLogger(MontyHallService.class);

    Random gen = new Random();
    GameResult gameResult = new GameResult();

    public GameResult returnResultFromIterations(String strategy, int iterations){

        logger.debug("method start : returnResultFromIterations");

        int switchWins = 0;
        int stayWins = 0;

        //loop through the interations
        for (int plays = 0; plays < iterations; plays++) {

            int[] doors = {0, 0, 0};//0 is a goat, 1 is a car
            doors[gen.nextInt(3)] = 1;//put a winner in a random door
            int choice = gen.nextInt(3); //pick a door, any door
            int shown; //the shown door
            do {
                shown = gen.nextInt(3);
                //don't show the winner or the choice
            } while (doors[shown] == 1 || shown == choice);

            stayWins += doors[choice];//if you won by staying, count it

            //the switched (last remaining) door is (3 - choice - shown), because 0+1+2=3
            switchWins += doors[3 - choice - shown];
        }

        // Set the Game Strategy that the player choose
        gameResult.setGameStrategy(GameResult.StrategyEnum.valueOf(strategy));

        // Based on strategy update and return the model class.
        switch (gameResult.getGameStrategy()) {
            case KEEP:
                gameResult = updateResultModel(stayWins, iterations);
                break;
            case SWITCH:
                gameResult = updateResultModel(switchWins, iterations);
                break;
                default:
                throw new IllegalArgumentException();
        }

        logger.debug("method end : returnResultFromIterations");

        return gameResult;
    }


    private GameResult updateResultModel(int wins, int iterations){

        logger.debug("method start : updateResultModel");

        gameResult.setNumberOfIterations(iterations);
        gameResult.setNumberOfWins(wins);
        gameResult.setPercentage(calculateProcent(iterations, wins));

        logger.debug("method end : updateResultModel");

        return gameResult;
    }

    private float calculateProcent(int numberOfIterations, int numberOfwins){

        logger.debug("method start : calculateProcent");

        float percentage =  (numberOfwins * 100.0f) / numberOfIterations;;

        logger.debug("method end : calculateProcent");

        return percentage;


    }

}
