package io.github.yuizho.junit5.extention;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.mariadb.jdbc.MariaDbDataSource;

import java.sql.SQLException;

public class ExtentionJavaTest {

    @RegisterExtension
    final SampleExtention sampleExtention;

    {
        MariaDbDataSource dataSource = new MariaDbDataSource();
        try {
            dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/test?characterEncoding=UTF-8");
            dataSource.setUser("test");
            dataSource.setPassword("password");
        } catch (SQLException e) {
        }
        sampleExtention = new SampleExtention(dataSource);
    }

    @Test
    @Preparation(testData = {
            @Table(name = "division", rows = {
                    @Row(vals = {
                            @Col(name = "id", value = "4", isId = true),
                            @Col(name = "name", value = "hoge")
                    })
            })
    })
    public void test() throws Exception {
        Thread.sleep(50);
    }
}