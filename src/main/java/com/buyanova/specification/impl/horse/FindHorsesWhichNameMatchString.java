package com.buyanova.specification.impl.horse;

import com.buyanova.specification.SqlSpecification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FindHorsesWhichNameMatchString implements SqlSpecification {

    private String pattern;

    public FindHorsesWhichNameMatchString(String pattern) {
        this.pattern = pattern;
    }

    private static final String SQL_QUERY =
            "SELECT \n" +
                    "    horses.horse_id,\n" +
                    "    horse_name, \n" +
                    "    horse_breed,\n" +
                    "    horse_age,\n" +
                    "    is_performing,\n" +
                    "    races_won_number,\n" +
                    "    races_lost_number\n" +
                    "FROM\n" +
                    "    horses\n" +
                    "WHERE\n" +
                    "    horse_name LIKE ?";

    @Override
    public PreparedStatement toSqlStatement(Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SQL_QUERY);
        statement.setString(1, pattern + "%");
        return statement;
    }
}
