package com.formulauno.domain;

import lombok.*;

import java.util.Collection;

@Data
@AllArgsConstructor
@EqualsAndHashCode
@Builder(toBuilder = true)
public class Engine {
    private long id;
    private String manufacturer;
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    Collection<Engine> engines;
}
