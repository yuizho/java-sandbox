package io.github.yuizho.junit5.extention;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(SampleExtention.class)
public class ExtentionJavaTest {
    @Test
    @Preparation(testData = {
            @Table(name = "hoge", rows = {
                    @Row(values = {
                            @Column(key = "id", value = "1"),
                            @Column(key = "name", value = "hoge")
                    })
            })
    })
    public void test() throws Exception {
        Thread.sleep(50);
    }
}