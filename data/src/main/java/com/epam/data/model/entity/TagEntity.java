package com.epam.data.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class TagEntity extends BaseEntity<Long> {
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof TagEntity))
            return false;

        TagEntity other = (TagEntity) o;

        return getId() != null &&
                getId().equals(other.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }


}
