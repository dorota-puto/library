package libraryManager.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcFullBookRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;


}
