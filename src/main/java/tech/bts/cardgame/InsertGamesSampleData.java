package tech.bts.cardgame;

import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import tech.bts.cardgame.repository.DataSourceUtil;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class InsertGamesSampleData {

    // Java JDBC Interfaces:
    // DataSource - provider of connections
    // Connection - class that lets you connect to a database, we need a DataSource to connect


    public static void main(String[] args) throws SQLException {

        DataSource dataSource = DataSourceUtil.getDataSourceInPath();
        Connection connection = dataSource.getConnection();

        //Reads the sql file and executes it
        ScriptUtils.executeSqlScript(connection, new ClassPathResource("sql-scripts/sample-data.sql"));
    }
}