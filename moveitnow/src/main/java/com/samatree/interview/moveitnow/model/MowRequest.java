package com.samatree.interview.moveitnow.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class MowRequest {
    private LawnField lawnField;
    private List <MowCommand> moveItCommandList;
}
