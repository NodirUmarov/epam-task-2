package com.epam.esm.dao.config;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DatabaseInitialization {

    @NonNull JdbcTemplate jdbcTemplate;

    public void createTable(Class target) {
        Field[] allFields = target.getDeclaredFields();
        List<Field> fields = new ArrayList<>(Arrays.asList(allFields));
        String name = "tb_" + target.getSimpleName().toLowerCase().replace("entity", "");
        StringBuilder query = new StringBuilder("CREATE TABLE IF NOT EXISTS " + name + "(");
        for (Field field : fields) {
            if (Boolean.class.equals(field.getType())) {
                query.append(generateBoolean(generateColumnInt(field.getName())));
            } else if (String.class.equals(field.getType())) {
                query.append(generateColumnString(generateColumnInt(field.getName())));
            } else if (Long.class.equals(field.getType())) {
                query.append(generateLong(generateColumnInt(field.getName())));
            } else if (Integer.class.equals(field.getType())) {
                query.append(generateColumnInt(field.getName()));
            }
            if (field.getName().equals("ID")) {
                query.append(" PRIMARY KEY ");
            }
            query.append(",");
        }
        query.deleteCharAt(query.length() - 1);
        query.append(");");
        jdbcTemplate.execute(query.toString());
    }

    private String generateColumnInt(String name) {
        return name + " INTEGER NOT NULL ";
    }

    private String generateColumnString(String name) {
        return name + " VARCHAR(255) NOT NULL ";
    }

    private String generateBoolean(String name) {
        return name + " BOOLEAN NOT NULL ";
    }

    private String generateLong(String name) {
        return name + " BIGINT NOT NULL ";
    }

}
