package libraryManager.repository.jdbc;

import libraryManager.entity.AuthorEntity;
import libraryManager.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class JdbcAuthorRepository implements AuthorRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public boolean save(AuthorEntity authorEntity) {
        return jdbcTemplate.update(
                "insert into Author ( `name`, last_name) values(?,?)",
                authorEntity.getFirstName(),
                authorEntity.getLastName()) > 0;
    }

    @Override
    public Optional<AuthorEntity> findById(Long id) {
        return jdbcTemplate.queryForObject(
                "select * from Author where author_ID = ?",
                new Object[]{id},
                (rs, rowNum) ->
                        Optional.of(new AuthorEntity(
                                rs.getLong("author_ID"),
                                rs.getString("name"),
                                rs.getString("last_name")
                        ))
        );
    }

    @Override
    public int deleteById(Long id) {
        return jdbcTemplate.update(
                "delete Author where author_ID = ?",
                id);
    }

}
