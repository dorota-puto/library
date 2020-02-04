package libraryManager.repository.jdbc;

import libraryManager.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcAuthorRepository implements AuthorRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public int count() {
        return jdbcTemplate
                .queryForObject("select count(*) from Book", Integer.class);
    }


}
