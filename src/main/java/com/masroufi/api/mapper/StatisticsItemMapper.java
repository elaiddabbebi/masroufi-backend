package com.masroufi.api.mapper;

import com.masroufi.api.dto.StatisticsItem;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class StatisticsItemMapper implements RowMapper<StatisticsItem> {
    @Override
    public StatisticsItem mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return StatisticsItem
                .builder()
                .label(resultSet.getString("label"))
                .amount(resultSet.getDouble("amount"))
                .build();
    }
}
