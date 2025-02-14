package app.core.application;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

public class QueryFilter {

    Map<String, Object> params;

    StringBuilder sql;

    StringBuilder countSql;

    public QueryFilter(Map<String, Object> params, StringBuilder sql, StringBuilder countSql) {
        this.params = params;
        this.sql = sql;
        this.countSql = countSql;
        convertSqlTypes();
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public StringBuilder getSql() {
        return sql;
    }

    public StringBuilder getCountSql() {
        return countSql;
    }

    private void convertSqlTypes() {

        if (params != null) {
            params.forEach((key, value) -> {

                if (value instanceof LocalDate) {
                    params.put(key, Date.valueOf((LocalDate) value));
                }

                if (value instanceof LocalTime) {
                    params.put(key, Time.valueOf((LocalTime) value));
                }
            });
        }
    }

}
