package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.extractor.GiftCertificateResultSetExtractor;
import com.epam.esm.dao.rowmapper.TagRowMapper;
import com.epam.esm.exception.OperationDeniedException;
import com.epam.esm.model.entity.GiftCertificateEntity;
import com.epam.esm.model.entity.TagEntity;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TagDaoImpl implements TagDao {

    @NonNull JdbcTemplate jdbcTemplate;

    @Override
    public Optional<TagEntity> findById(Long id) {
        String sql = "SELECT * FROM tb_tags WHERE id IN (?);";
        return jdbcTemplate
                .query(sql, new TagRowMapper(), id)
                .stream()
                .findFirst();
    }

    @Override
    public TagEntity save(TagEntity tagEntity) {
        String sql = "INSERT INTO tb_tags VALUES (?) RETURNING id";
        jdbcTemplate.query(sql, rs -> {
            tagEntity.setId(rs.getLong(1));
        }, tagEntity.getName());
        return tagEntity;
    }

    @Override
    public void deleteById(Long id) {
        checkBeforeDelete(id);
        String sql = "DELETE FROM tb_tags WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    private void checkBeforeDelete(Long id) {
        String checkUsing = "" +
                "SELECT * FROM tb_gift_cetificates gc " +
                "JOIN gift_cetificate_has_tag gcht on gc.id = gcht.gift_cetificate_id " +
                "JOIN tb_tags tt on gcht.tag_id = tt.id " +
                "WHERE tt.id = ?"; // constant
        List<GiftCertificateEntity> giftCertificates = jdbcTemplate.query(checkUsing, new GiftCertificateResultSetExtractor(), id);
        if (!giftCertificates.isEmpty()) {
            StringBuilder errorMessage = new StringBuilder(
                    "Tag with id " + id +
                            " is using by " + giftCertificates.size() + " certificates. " +
                            "Untag certificates below first: ");

            giftCertificates.forEach(giftCertificate -> {
                errorMessage.append(giftCertificate.getName()).append(", ");
            });
            errorMessage.replace(errorMessage.lastIndexOf(","), errorMessage.length(), "");
            errorMessage.append(".");
            throw new OperationDeniedException(errorMessage.toString());
        }
    }

    @Override
    public Set<TagEntity> saveAll(Set<TagEntity> tags) {
        String sqlSelect = "SELECT id, name FROM tb_tags WHERE name IN (?);";
        String sqlInsert = "INSERT INTO tb_tags(name) VALUES (?) RETURNING id;";

        return tags
                .stream()
                .map(tagEntity -> jdbcTemplate
                        .query(sqlSelect, new TagRowMapper(), tagEntity.getName())
                        .stream()
                        .findFirst()
                        .orElseGet(() -> {
                            jdbcTemplate.query(sqlInsert, rs -> {
                                tagEntity.setId(rs.getLong(1));
                            }, tagEntity.getName());
                            return tagEntity;
                        }))
                .collect(Collectors.toSet());
    }

    @Override
    public Set<TagEntity> findAllSorted(Integer limit, Integer offset) {
        String sql = "SELECT * FROM tb_tags ORDER BY name LIMIT ? OFFSET ?";
        return new HashSet<>(jdbcTemplate
                .query(sql, new TagRowMapper(), limit, offset));
    }
}
