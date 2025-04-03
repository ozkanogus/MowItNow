package com.samatree.interview.moveitnow.service;

import com.samatree.interview.moveitnow.model.LawnField;
import com.samatree.interview.moveitnow.model.MowCommand;
import com.samatree.interview.moveitnow.model.Mower;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MowerServiceTest {

    private MowerService mowerService;

    @BeforeEach
    void setUp() {
        mowerService = new MowerService();
    }

    @Test
    void testSingleMowerMovement() {
        LawnField lawnField = new LawnField(5, 5);
        Mower mower = new Mower(1, 2, 'N');
        MowCommand command = new MowCommand(mower, "LFLFLFLFF");

        List<Mower> result = mowerService.processCommands(lawnField, List.of(command));

        assertEquals(1, result.size());
        Mower finalMower = result.get(0);
        assertEquals(1, finalMower.getPositionX());
        assertEquals(3, finalMower.getPositionY());
        assertEquals('N', finalMower.getDirection());
    }

    @Test
    void testMultipleMowersWithoutCollision() {
        LawnField lawnField = new LawnField(5, 5);
        MowCommand command1 = new MowCommand(new Mower(1, 2, 'N'), "LFLFLFLFF");
        MowCommand command2 = new MowCommand(new Mower(3, 3, 'E'), "FFRFFRFRRF");

        List<Mower> result = mowerService.processCommands(lawnField, List.of(command1, command2));

        assertEquals(2, result.size());

        Mower mower1 = result.get(0);
        Mower mower2 = result.get(1);

        assertEquals(1, mower1.getPositionX());
        assertEquals(3, mower1.getPositionY());
        assertEquals('N', mower1.getDirection());

        assertEquals(5, mower2.getPositionX());
        assertEquals(1, mower2.getPositionY());
        assertEquals('E', mower2.getDirection());
    }

    @Test
    void testParsedRequestAndProcessCommands() {
        String input = """
                5 5
                1 2 N
                LFLFLFLFF
                
                3 3 E
                
                FFRFFRFRRF
                """;
        String expectedOutput = """
            1 3 N
            5 1 E            """;

        String result = mowerService.parsedRequestAndProcessCommands(input);

        assertEquals(expectedOutput, result);
    }


    @Test
    void testMowerCannotMoveIntoOccupiedSpace() {
        LawnField lawnField = new LawnField(5, 5);
        MowCommand command1 = new MowCommand(new Mower(1, 2, 'N'), "F");
        MowCommand command2 = new MowCommand(new Mower(1, 3, 'N'), "L");

        List<MowCommand> commandList = new ArrayList<>();
        commandList.add(command1);
        commandList.add(command2);
        List<Mower> finalPositions = mowerService.processCommands(lawnField, commandList);

        assertEquals(1, finalPositions.get(0).getPositionX());
        assertEquals(2, finalPositions.get(0).getPositionY());

        assertEquals(1, finalPositions.get(1).getPositionX());
        assertEquals(3, finalPositions.get(1).getPositionY());
    }

    @Test
    void testMowerCannotMoveOutOfBounds() {
        LawnField lawnField = new LawnField(5, 5);
        MowCommand command = new MowCommand(new Mower(0, 0, 'S'), "FFFFF");

        List<Mower> result = mowerService.processCommands(lawnField, List.of(command));

        Mower mower = result.get(0);
        assertEquals(0, mower.getPositionX());
        assertEquals(0, mower.getPositionY());
        assertEquals('S', mower.getDirection());
    }

    @Test
    void testMowerTurningLeft() {
        Mower mower = new Mower(2, 2, 'N');
        Mower result = mowerService.turnLeft(mower);
        assertEquals('W', result.getDirection());
    }

    @Test
    void testMowerTurningRight() {
        Mower mower = new Mower(2, 2, 'N');
        Mower result = mowerService.turnRight(mower);
        assertEquals('E', result.getDirection());
    }
}
