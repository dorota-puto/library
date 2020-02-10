package libraryManager.repository.jdbc;

import libraryManager.entity.Publisher;
import libraryManager.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class JdbcPublisherRepository implements PublisherRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public boolean save(Publisher publisher) {
        return jdbcTemplate.update(
                "insert into Publisher ( name) values(?)",
                publisher.getName()) > 0;
    }

    @Override
    public Optional<Publisher> findById(Long id) {
        return jdbcTemplate.queryForObject(
                "select * from Publisher where publisher_ID = ?",
                new Object[]{id},
                (rs, rowNum) ->
                        Optional.of(new Publisher(
                                rs.getLong("publisher_ID"),
                                rs.getString("name")
                        ))
        );
    }

    @Override
    public int deleteById(Long id) {
        return jdbcTemplate.update(
                "delete `Language` where language_ID = ?",
                id);
    }

}
