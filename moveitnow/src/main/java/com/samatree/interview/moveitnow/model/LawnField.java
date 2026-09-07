package com.samatree.interview.moveitnow.model;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LawnField {
    @Min(0)
    private int width;
    @Min(0)
    private int height;
}
