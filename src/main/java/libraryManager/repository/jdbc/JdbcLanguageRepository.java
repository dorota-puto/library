package libraryManager.repository.jdbc;

import libraryManager.entity.AccountEntity;
import libraryManager.entity.LanguageEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class JdbcLanguageRepository implements LanguageRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public boolean save(LanguageEntity languageEntity) {
        return jdbcTemplate.update(
                "insert into `Language` ( name) values(?)",
                languageEntity.getName()) > 0;
    }

    @Override
    public Optional<LanguageEntity> findById(Long id) {
        return jdbcTemplate.queryForObject(
                "select * from `Language` where language_ID = ?",
                new Object[]{id},
                (rs, rowNum) ->
                        Optional.of(new LanguageEntity(
                                rs.getLong("language_ID"),
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
