package com.alexkampos.springcourse.dto.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ActorView {

    private long actorId;

    private String fullName;

    private int age;

    @JsonIgnoreProperties({"actors", "genres", "languages"})
    private List<MovieView> movies = new ArrayList<>();

}
