package com.samatree.interview.moveitnow.service;

import com.samatree.interview.moveitnow.model.LawnField;
import com.samatree.interview.moveitnow.model.MowCommand;
import com.samatree.interview.moveitnow.model.MowRequest;
import com.samatree.interview.moveitnow.model.Mower;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MowerService {

    public List<Mower> processCommands(LawnField lawnField, List<MowCommand> mowCommandList) {
        Map<String, Integer> locatedMowerPositions = new HashMap<>();
        List<MowCommand> locateMowCommandList = new ArrayList<>();

        int i = 0;
        for (MowCommand mowCommand : mowCommandList) {
            mowCommand.setMoverNumber(++i);
            placeMowersOnLawnField(mowCommand, lawnField, locatedMowerPositions, locateMowCommandList);
        }

        return locateMowCommandList.stream()
                .map(command -> processSingleMower(command, lawnField, locatedMowerPositions))
                .collect(Collectors.toList());

    }

    private void placeMowersOnLawnField(MowCommand command, LawnField lawnField,
                                        Map<String, Integer> locatedMowerPositions,
                                        List<MowCommand> locateMowCommandList) {
        int x = command.getMower().getPositionX();
        int y = command.getMower().getPositionY();

        if (x >= 0 && x <= lawnField.getWidth() && y >= 0 && y <= lawnField.getHeight()) {
            String positionKey = x + "," + y;
            if (!locatedMowerPositions.containsKey(positionKey)) {
                locatedMowerPositions.put(positionKey, command.getMoverNumber());
                command.setMessage("Mower " + command.getMoverNumber() + " is located on the lawn.");
                locateMowCommandList.add(command);
            } else {
                command.setMessage("Mower " + command.getMoverNumber() + " could not be placed at " + positionKey + " (already occupied).");
            }
        }
    }

    private Mower processSingleMower(MowCommand command, LawnField lawnField, Map<String, Integer> locatedMowerPositions) {
        Mower mower = command.getMower();
        for (char c : command.getCommands().toCharArray()) {
            switch (c) {
                case 'L': mower = turnLeft(mower); break;
                case 'R': mower = turnRight(mower); break;
                case 'F': mower = moveForward(mower,command.getMoverNumber(), lawnField, locatedMowerPositions); break;
            }
        }
        return mower;
    }


    public Mower turnLeft(Mower mower) {
        char newDirection = switch (mower.getDirection()) {
            case 'N' -> 'W';
            case 'W' -> 'S';
            case 'S' -> 'E';
            case 'E' -> 'N';
            default -> mower.getDirection();
        };
        return new Mower(mower.getPositionX(), mower.getPositionY(), newDirection);
    }

    public Mower turnRight(Mower mower) {
        char newDirection = switch (mower.getDirection()) {
            case 'N' -> 'E';
            case 'E' -> 'S';
            case 'S' -> 'W';
            case 'W' -> 'N';
            default -> mower.getDirection();
        };
        return new Mower(mower.getPositionX(), mower.getPositionY(), newDirection);
    }

    private Mower moveForward(Mower mower,int mowerNumber, LawnField lawnField, Map<String,Integer> locatedMowerPositions) {
        int x = mower.getPositionX();
        int y = mower.getPositionY();
        char direction = mower.getDirection();

        switch (direction) {
            case 'N': if (y < lawnField.getHeight()) y++; break;
            case 'E': if (x < lawnField.getWidth()) x++; break;
            case 'S': if (y > 0) y--; break;
            case 'W': if (x > 0) x--; break;
        }

        String newPositionKey = x + "," + y;
        if (!locatedMowerPositions.containsKey(newPositionKey)) {
            locatedMowerPositions.remove(mower.getPositionX() + "," + mower.getPositionY());
            locatedMowerPositions.put(newPositionKey, mowerNumber);

            return new Mower(x, y, direction);
        }
        return mower;
    }

    public String parsedRequestAndProcessCommands(String input) {
        try {
            MowRequest parsedRequest = parseInputString(input);
            List<Mower> mowers =  processCommands(parsedRequest.getLawnField(),parsedRequest.getMoveItCommandList());
            return mowers.stream()
                    .map(mower -> mower.getPositionX() + " " + mower.getPositionY() + " " + mower.getDirection())
                    .collect(Collectors.joining("\n"));
        }catch (IllegalArgumentException e){
            return e.getMessage();
        }
    }

    private MowRequest parseInputString(String input) {
        List<String> lines = Arrays.stream(input.split("\\n"))
                .map(String::trim)
                .filter(line -> !line.isEmpty())
                .collect(Collectors.toList());
        if (lines.size() < 3 || lines.size() % 2 == 0) {
            throw new IllegalArgumentException("Invalid input format");
        }

        String[] lawnDimensions = lines.get(0).split(" ");
        int width = Integer.parseInt(lawnDimensions[0]);
        int height = Integer.parseInt(lawnDimensions[1]);
        LawnField lawnField = new LawnField(width, height);

        List<MowCommand> mowCommands = new ArrayList<>();
        for (int i = 1; i < lines.size(); i += 2) {
            String[] position = lines.get(i).split(" ");
            int x = Integer.parseInt(position[0]);
            int y = Integer.parseInt(position[1]);
            char direction = position[2].charAt(0);
            Mower mower = new Mower(x, y, direction);

            String commands = lines.get(i + 1);

            mowCommands.add(new MowCommand(mower, commands));
        }

        return new MowRequest(lawnField, mowCommands);
    }
}
