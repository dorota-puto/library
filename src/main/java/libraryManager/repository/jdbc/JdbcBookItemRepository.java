package libraryManager.repository.jdbc;

import libraryManager.entity.BookItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JdbcBookItemRepository implements libraryManager.repository.BookItemRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public boolean save(BookItem bookItem) {
        return jdbcTemplate.update(
                "insert into Book_item ( `rfid_tag`, isbn) values(?,?)",
                bookItem.getRfidTag(),
                bookItem.getIsbn()) > 0;
    }

    @Override
    public Optional<BookItem> findByRfidTag(String rfigTag) {
        try {
            return jdbcTemplate.queryForObject(
                    "select * from Book_item where rfid_tag= ?",
                    new Object[]{rfigTag},
                    (rs, rowNum) ->
                            Optional.of(new BookItem(
                                    rs.getString("rfid_tag"),
                                    rs.getLong("isbn")
                            ))
            );
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<BookItem> findAll() {
        return jdbcTemplate.query(
                "select * from Book_item",
                (rs, rowNum) ->
                        new BookItem(
                                rs.getString("rfid_tag"),
                                rs.getLong("isbn")
                        ));
    }

    @Override
    public int deleteByRfidTag(String rfidTag) {
        return jdbcTemplate.update(
                "delete from Book_item where  rfid_tag = ?",
                rfidTag);
    }


}
