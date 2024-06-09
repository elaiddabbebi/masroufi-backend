package com.masroufi.api.repository.impl;

import com.masroufi.api.dto.StatisticsItem;
import com.masroufi.api.dto.response.GenericObject;
import com.masroufi.api.dto.response.MonthAmount;
import com.masroufi.api.enums.CashFlowType;
import com.masroufi.api.enums.Month;
import com.masroufi.api.repository.StatisticsRepository;
import com.masroufi.api.shared.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class StatisticsRepositoryImpl implements StatisticsRepository {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private RowMapper<StatisticsItem> statisticsItemRowMapper;

    @Autowired
    private RowMapper<GenericObject> genericObjectRowMapper;

    @Autowired
    private RowMapper<Integer> integerRowMapper;

    @Autowired
    private RowMapper<MonthAmount> monthAmountRowMapper;

    @Override
    public List<GenericObject> getCategoriesByCustomerAndCashFlowType(Long customerId, CashFlowType cashFlowType) {
        String sqlQuery =
                "Select DISTINCT (cashFlowCategory.name) as key, cashFlowCategory.uuid as value " +
                "From cash_flow cashFlow " +
                "Left join customer_cash_flow_registry registry ON cashFlow.id = registry.cash_flow_id " +
                "Left join account ON account.id = registry.customer_id " +
                "Left join cash_flow_category cashFlowCategory ON cashFlowCategory.id = cashFlow.category_id " +
                "Where account.id = :customerId " +
                "And registry.type = :cashFlowType " +
                "Order by cashFlowCategory.name";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("customerId", customerId);
        params.addValue("cashFlowType", cashFlowType.name());

        return this.namedParameterJdbcTemplate.query(sqlQuery, params, genericObjectRowMapper);
    }

    @Override
    public List<Integer> getYearsListByCustomer(Long customerId) {
        String sqlQuery =
                "Select distinct (extract(year from cashFlow.created_at))" +
                "From aggregated_customer_cash_flow cashFlow " +
                "Where cashFlow.customer_id = :customerId";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("customerId", customerId);

        return this.namedParameterJdbcTemplate.query(sqlQuery, params, integerRowMapper);
    }

    @Override
    public List<StatisticsItem> getCashFlowByCategoryAndTypeAndCustomerAndDateBetween(
            Long customerId,
            CashFlowType cashFlowType,
            String categoryUuid,
            Date startDate,
            Date endDate,
            int limit
    ) {
        String sqlQuery =
                "Select cashFlow.name as label, SUM(registry.amount) as amount " +
                "From cash_flow cashFlow " +
                "Left join customer_cash_flow_registry registry ON cashFlow.id = registry.cash_flow_id " +
                "Left join account ON account.id = registry.customer_id " +
                "Left join cash_flow_category cashFlowCategory ON cashFlowCategory.id = cashFlow.category_id " +
                "Where account.id = :customerId " +
                "And registry.type = :cashFlowType " +
                "And cashFlowCategory.uuid = :categoryUuid " +
                "And (registry.date >= :startDate And registry.date <= :endDate) " +
                "And amount > 0 " +
                "Group by cashFlow.name " +
                "Order by amount DESC " +
                "Limit :limit";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("customerId", customerId);
        params.addValue("cashFlowType", cashFlowType.name());
        params.addValue("categoryUuid", categoryUuid);
        params.addValue("startDate", startDate);
        params.addValue("endDate", endDate);
        params.addValue("limit", limit);

        return this.namedParameterJdbcTemplate.query(sqlQuery, params, statisticsItemRowMapper);
    }

    @Override
    public List<StatisticsItem> getCashFlowByCustomerAndTypeAndDateBetween(
            Long customerId,
            CashFlowType cashFlowType,
            Date startDate,
            Date endDate,
            int limit
    ) {
        String sqlQuery =
                "Select cashFlowCategory.name as label, SUM(registry.amount) as amount " +
                "From cash_flow cashFlow " +
                "Left join customer_cash_flow_registry registry ON cashFlow.id = registry.cash_flow_id " +
                "Left join Account account ON account.id = registry.customer_id " +
                "Left join cash_flow_category cashFlowCategory ON cashFlowCategory.id = cashFlow.category_id " +
                "Where account.id = :customerId " +
                "And registry.type = :cashFlowType " +
                "And (registry.date >= :startDate And registry.date <= :endDate) " +
                "And amount > 0 " +
                "Group by cashFlowCategory.name " +
                "Order by amount DESC " +
                "Limit :limit";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("customerId", customerId);
        params.addValue("cashFlowType", cashFlowType.name());
        params.addValue("startDate", startDate);
        params.addValue("endDate", endDate);
        params.addValue("limit", limit);

        return this.namedParameterJdbcTemplate.query(sqlQuery, params, statisticsItemRowMapper);
    }

    @Override
    public List<MonthAmount> getExpensesPerMonthByCustomerAndYear(Long customerId, int year) {
        String sqlQuery =
                "Select EXTRACT(month from agg.date) as month, SUM(agg.expense_amount) as amount " +
                "From aggregated_customer_cash_flow  agg " +
                "WHERE agg.customer_id = :customerId " +
                "and EXTRACT(year from agg.date) = :year " +
                "group by month " +
                "order by month";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("customerId", customerId);
        params.addValue("year", year);

        return this.mergeResultInsideAllMonths(this.namedParameterJdbcTemplate.query(sqlQuery, params, monthAmountRowMapper));

    }

    @Override
    public List<MonthAmount> getRevenuesPerMonthByCustomerAndYear(Long customerId, int year) {
        String sqlQuery =
                "Select EXTRACT(month from agg.date) as month, SUM(agg.gain_amount) as amount " +
                "From aggregated_customer_cash_flow  agg " +
                "WHERE agg.customer_id = :customerId " +
                "and EXTRACT(year from agg.date) = :year " +
                "group by month " +
                "order by month";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("customerId", customerId);
        params.addValue("year", year);

        return this.mergeResultInsideAllMonths(this.namedParameterJdbcTemplate.query(sqlQuery, params, monthAmountRowMapper));
    }

    private List<MonthAmount> mergeResultInsideAllMonths(List<MonthAmount> result) {
        List<Month> months = DateUtils.getMonthsOfYear();
        List<MonthAmount> returnValue = new ArrayList<>();
        for (Month month: months) {
            MonthAmount monthAmount = MonthAmount.builder()
                    .month(month)
                    .amount(0D)
                    .build();
            returnValue.add(monthAmount);
        }
        if (result != null && !result.isEmpty()) {
            for (MonthAmount monthAmount: result) {
                for (MonthAmount mAmount: returnValue) {
                    if (mAmount.getMonth().equals(monthAmount.getMonth())) {
                        mAmount.setAmount(monthAmount.getAmount());
                        break;
                    }
                }
            }
        }
        return returnValue;
    }
}
