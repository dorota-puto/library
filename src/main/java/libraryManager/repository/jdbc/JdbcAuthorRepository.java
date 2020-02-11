package libraryManager.repository.jdbc;

import libraryManager.entity.Author;
import libraryManager.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JdbcAuthorRepository implements AuthorRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public boolean save(Author author) {
        return jdbcTemplate.update(
                "insert into Author ( `name`, last_name) values(?,?)",
                author.getFirstName(),
                author.getLastName()) > 0;
    }

    @Override
    public Optional<Author> findById(Long id) {
        try {
            return jdbcTemplate.queryForObject(
                    "select * from Author where author_ID = ?",
                    new Object[]{id},
                    (rs, rowNum) ->
                            Optional.of(new Author(
                                    rs.getLong("author_ID"),
                                    rs.getString("name"),
                                    rs.getString("last_name")
                            ))
            );
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Author> findAll() {
        return jdbcTemplate.query(
                "select * from Author",
                (rs, rowNum) ->
                        new Author(
                                rs.getLong("author_ID"),
                                rs.getString("name"),
                                rs.getString("last_name")
                        ));
    }
    @Override
    public int deleteById(Long id) {
        return jdbcTemplate.update(
                "delete from Author where author_ID = ?",
                id);
    }

}


