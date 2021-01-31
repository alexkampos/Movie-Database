package com.alexkampos.springcourse.base;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

public class BaseRequest implements Serializable {

    @AllArgsConstructor
    @NoArgsConstructor
    public static class Id implements Serializable {

        @NotNull
        private Long id;
        
        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Id)) return false;
            Id id1 = (Id) o;
            return Objects.equals(id, id1.id);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }
    }

}
