package com.formulauno.domain;

import lombok.*;

import java.util.Collection;

@Data
@AllArgsConstructor
@Builder(toBuilder = true)
public class Team {
    private long id;
    private String name;
    private String location;
    private int since;
    private int till;
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Collection<Team> teams;
}
