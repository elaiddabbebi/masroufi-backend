package com.masroufi.api.mapper;

import com.masroufi.api.dto.response.GenericObject;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class GenericObjectMapper implements RowMapper<GenericObject> {
    @Override
    public GenericObject mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return GenericObject
                .builder()
                .key(resultSet.getString("key"))
                .value(resultSet.getObject("value"))
                .build();
    }
}
