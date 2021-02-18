package libraryManager.repository.jdbc;

import libraryManager.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JdbcBookRepository implements libraryManager.repository.BookRepository {


    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public boolean save(Book book) {
        return jdbcTemplate.update(
                "insert into Book (isbn,title, page_count, author_ID, publisher_ID, language_ID) values(?,?,?,?,?,?)",
                book.getIsbn(),
                book.getTitle(),
                book.getPageCount(),
                book.getAuthorID(),
                book.getPublisherID(),
                book.getLanguageID()) > 0;
    }

    @Override
    public Optional<Book> findByIsbn(Long isbn) {
        try {
            return jdbcTemplate.queryForObject(
                    "select * from Book where isbn = ?",
                    new Object[]{isbn},
                    (rs, rowNum) ->
                            Optional.of(new Book(
                                    rs.getLong("isbn"),
                                    rs.getString("title"),
                                    rs.getInt("page_count"),
                                    rs.getLong("author_ID"),
                                    rs.getLong("publisher_ID"),
                                    rs.getLong("language_ID")
                            ))
            );
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Book> findAll() {
        return jdbcTemplate.query(
                "select * from Book",
                (rs, rowNum) ->
                        new Book(
                                rs.getLong("isbn"),
                                rs.getString("title"),
                                rs.getInt("page_count"),
                                rs.getLong("author_ID"),
                                rs.getLong("publisher_ID"),
                                rs.getLong("language_ID")
                        ));
    }

    @Override
    public int deleteByIsbn(Long isbn) {
        return jdbcTemplate.update(
                "delete from Book where isbn= ?",
                isbn);
    }
}
