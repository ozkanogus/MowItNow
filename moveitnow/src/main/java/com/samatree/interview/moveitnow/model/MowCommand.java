package com.samatree.interview.moveitnow.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MowCommand {
    @Valid
    @NotNull
    private Mower mower;
    @NotBlank
    private String commands;
    private int moverNumber;
    private String message;

    public MowCommand(Mower mower, String commands) {
        this.mower = mower;
        this.commands = commands;
    }
}

