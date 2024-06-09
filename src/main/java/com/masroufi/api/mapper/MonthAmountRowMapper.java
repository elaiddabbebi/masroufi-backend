package com.masroufi.api.mapper;

import com.masroufi.api.dto.response.MonthAmount;
import com.masroufi.api.enums.Month;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class MonthAmountRowMapper implements RowMapper<MonthAmount> {
    @Override
    public MonthAmount mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return MonthAmount
                .builder()
                .month(Month.of(resultSet.getInt("month") - 1))
                .amount(resultSet.getDouble("amount"))
                .build();
    }
}
