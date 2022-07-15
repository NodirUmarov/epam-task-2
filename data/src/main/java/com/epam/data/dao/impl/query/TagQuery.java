package com.epam.data.dao.impl.query;

public class TagQuery {

    private TagQuery() {}

    public static final String SELECT_BY_ID = "SELECT * FROM tb_tags WHERE id IN (:id);";

    public static final String SELECT_BY_NAME = "SELECT * FROM tb_tags WHERE name IN (:name);";

    public static final String FIND_ALL_SORTED_PAGED = "SELECT * FROM tb_tags ORDER BY name LIMIT :limit OFFSET :offset";

    public static final String EXISTS_BY_NAME = "SELECT EXISTS(SELECT * FROM tb_tags WHERE name IN (:name))";

    public static final String INSERT = "INSERT INTO tb_tags(name) VALUES (:name);";

    public static final String DELETE_BY_ID = "DELETE FROM tb_tags WHERE id IN (:id);";
}
