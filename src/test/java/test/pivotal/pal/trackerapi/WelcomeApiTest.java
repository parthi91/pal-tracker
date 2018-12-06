package test.pivotal.pal.trackerapi;


import com.mysql.cj.jdbc.MysqlDataSource;
import io.pivotal.pal.tracker.PalTrackerApplication;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.Test;

import java.util.TimeZone;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PalTrackerApplication.class, webEnvironment = RANDOM_PORT)
public class WelcomeApiTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void exampleTest() {
        String body = this.restTemplate.getForObject("/", String.class);
        assertThat(body).isEqualTo("Hello from test");
    }

    @Before
    public void setUp() throws Exception {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUrl(System.getenv("SPRING_DATASOURCE_URL"));

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.execute("TRUNCATE time_entries");

        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }
}
