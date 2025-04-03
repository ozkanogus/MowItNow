package com.samatree.interview.moveitnow.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MowCommand {
    private Mower mower;
    private String commands;
    private int moverNumber;
    private String message;

    public MowCommand(Mower mower, String commands) {
        this.mower = mower;
        this.commands = commands;
    }
}


