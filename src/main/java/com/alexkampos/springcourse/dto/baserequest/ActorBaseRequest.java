package com.alexkampos.springcourse.dto.baserequest;

import com.alexkampos.springcourse.base.BaseRequest;
import com.alexkampos.springcourse.interfaces.BasicInfo;
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
public class ActorBaseRequest extends BaseRequest {

    @NotNull(groups = BasicInfo.class)
    private String fullName;

    @NotNull(groups = BasicInfo.class)
    private int age;
    
    private List<@Valid Id> movies = new ArrayList<>();
    
    

}
