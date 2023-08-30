package com.rainmaker.rainmaker;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@ActiveProfiles("test")
@SpringBootTest
@TestPropertySource(locations = "classpath:/application.yml")
class RainmakerApplicationTests {

    @Test
    void contextLoads() {
    }
}
