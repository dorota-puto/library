package libraryManager.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcAuthorRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public int count() {
        return jdbcTemplate
                .queryForObject("select count(*) from Book", Integer.class);
    }
}
