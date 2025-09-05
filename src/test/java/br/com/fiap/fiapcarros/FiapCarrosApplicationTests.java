package br.com.fiap.fiapcarros;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
class FiapCarrosApplicationTests {

    @Test
    void contextLoads() {
    }

}
