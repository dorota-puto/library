package libraryManager.repository.jdbc;

import libraryManager.entity.Language;
import libraryManager.repository.LanguageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JdbcLanguageRepository implements LanguageRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public boolean save(Language language) {
        return jdbcTemplate.update(
                "insert into `Language` ( name) values(?)",
                language.getName()) > 0;
    }

    @Override
    public Optional<Language> findById(Long id) {
        try {
            return jdbcTemplate.queryForObject(
                    "select * from `Language` where language_ID = ?",
                    new Object[]{id},
                    (rs, rowNum) ->
                            Optional.of(new Language(
                                    rs.getLong("language_ID"),
                                    rs.getString("name")
                            ))
            );
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Language> findAll() {
        return jdbcTemplate.query(
                "select * from Language",
                (rs, rowNum) ->
                        new Language(
                                rs.getLong("language_ID"),
                                rs.getString("name")
                        ));
    }

    @Override
    public int deleteById(Long id) {
        return jdbcTemplate.update(
                "delete from `Language` where language_ID = ?",
                id);
    }
}
