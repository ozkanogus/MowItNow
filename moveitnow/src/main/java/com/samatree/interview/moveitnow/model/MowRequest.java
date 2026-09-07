package com.samatree.interview.moveitnow.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class MowRequest {
    @Valid
    @NotNull
    private LawnField lawnField;
    @Valid
    @NotEmpty
    private List <MowCommand> moveItCommandList;
}
