package com.samatree.interview.moveitnow.model;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Mower {
    @Min(0)
    private int positionX;
    @Min(0)
    private int positionY;
    private char direction;

}
