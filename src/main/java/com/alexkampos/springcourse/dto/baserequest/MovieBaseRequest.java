package com.alexkampos.springcourse.dto.baserequest;

import com.alexkampos.springcourse.base.BaseRequest;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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
public class MovieBaseRequest extends BaseRequest {

    @NotNull
    private String title;

    @NotNull
    private int durationMinutes;

    @NotEmpty
    private List<@Valid Id> actors = new ArrayList<>();

    @NotEmpty
    private List<@Valid Id> genres = new ArrayList<>();

    @NotEmpty
    private List<@Valid Id> languages = new ArrayList<>();

}
