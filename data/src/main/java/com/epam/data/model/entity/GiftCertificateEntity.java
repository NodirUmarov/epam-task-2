package com.epam.data.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class GiftCertificateEntity extends BaseEntityAudit<Long> {

    private String name;
    private String description;
    private BigDecimal price;
    private LocalDateTime duration;
    private Set<TagEntity> tags;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof GiftCertificateEntity))
            return false;

        GiftCertificateEntity other = (GiftCertificateEntity) o;

        return getId() != null &&
                getId().equals(other.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
