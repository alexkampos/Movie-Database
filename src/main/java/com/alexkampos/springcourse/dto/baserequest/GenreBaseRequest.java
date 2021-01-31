package com.alexkampos.springcourse.dto.baserequest;

import com.alexkampos.springcourse.base.BaseRequest;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
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
public class GenreBaseRequest extends BaseRequest {

    @NotNull
    private String name;
    
    private List<@Valid Id> movies = new ArrayList<>();

}
