package com.epam.data.dao.impl.query;

import com.epam.lib.constants.SortType;

public class GiftCertificateQuery {

    private GiftCertificateQuery() {}

    public static final String SELECT_BY_ID = "" +
            "SELECT * FROM tb_gift_certificates gc " +
            "LEFT JOIN gift_certificate_has_tag gcht ON gc.id = gcht.gift_certificate_id OR gcht.gift_certificate_id IS NULL " +
            "LEFT JOIN tb_tags t ON gcht.tag_id = t.id " +
            "WHERE gc.id IN (:id)";

    public static final String INSERT = "" +
            "INSERT INTO tb_gift_certificates(name, price, duration, description, create_date) " +
            "VALUES (:name, :price, :duration, :description, :createDate);";

    public static final String INSERT_JT = "" +
            "INSERT INTO gift_certificate_has_tag " +
            "VALUES (:certificateId, (SELECT id FROM tb_tags WHERE name IN (:name)));";

    public static final String DELETE_BY_ID_JT = "DELETE FROM gift_certificate_has_tag WHERE gift_certificate_id IN (:certificateId)";

    public static final String DELETE_BY_ID = "DELETE FROM tb_gift_certificates WHERE id IN (:id)";

    public static final String SELECT_BY_NAME = "" +
            "SELECT * FROM tb_gift_certificates gc " +
            "LEFT JOIN gift_certificate_has_tag gcht on gc.id = gcht.gift_certificate_id " +
            "LEFT JOIN tb_tags t on gcht.tag_id = t.id " +
            "WHERE gc.name IN (:name)";

    public static String order = SortType.NONE.getValue();
    public static final String SELECT_BY_NAME_ORD = "" +
            "SELECT * FROM tb_gift_certificates gc " +
            "JOIN gift_certificate_has_tag gcht on gc.id = gcht.gift_certificate_id " +
            "JOIN tb_tags t on gcht.tag_id = t.id " +
            "WHERE t.name IN (:tagName) " +
            "ORDER BY (gc.name, t.name) " + order + " " +
            "LIMIT :limit " +
            "OFFSET :offset;";

    public static final String SELECT_EXISTS = "SELECT EXISTS(SELECT * FROM tb_gift_certificates WHERE id IN (:id));";

    public static final String UPDATE_BY_ID = "" +
            "UPDATE tb_gift_certificates " +
            "SET name = (CASE WHEN name <> (:name) THEN (:name) ELSE name END), " +
            "description = (CASE WHEN description <> (:description) THEN (:description) ELSE description END), " +
            "duration = (CASE WHEN duration <> (:duration) THEN (:duration) ELSE duration END), " +
            "price = (CASE WHEN price <> (:price) THEN (:price) ELSE price END), " +
            "last_update_date = (:updateDate) " +
            "WHERE id IN (:id) ";

    public static final String UNTAG_CERTIFICATE = "" +
            "DELETE FROM gift_certificate_has_tag " +
            "WHERE tag_id IN (SELECT id FROM tb_tags t WHERE t.name IN (:tagName)) " +
            "AND gift_certificate_id IN (:certificateId)";

    public static final String INSERT_LAST_UPDATE_DATE = "" +
            "UPDATE tb_gift_certificates " +
            "SET last_update_date = (:date) " +
            "WHERE id IN (:id)";
}
