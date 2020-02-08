package libraryManager.repository.jdbc;

import libraryManager.entity.BookReadEntity;
import libraryManager.entity.BookWriteEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JdbcBookRepository implements libraryManager.repository.BookRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public int count() {
        return jdbcTemplate
                .queryForObject("select count(*) from Book", Integer.class);
    }


    @Override
    public boolean save(BookWriteEntity bookWriteEntity) {
        return jdbcTemplate.update(
                "insert into Book (isbn, title, page_count, author_ID, publisher_ID, language_ID) values(?,?,?,?,?,?)",
                bookWriteEntity.getIsbn(),
                bookWriteEntity.getTitle(),
                bookWriteEntity.getPageCount(),
                bookWriteEntity.getAuthorID(),
                bookWriteEntity.getPublisherID(),
                bookWriteEntity.getLanguageID())>0;
    }



    @Override
    public List<BookReadEntity> findAll() {
        return jdbcTemplate.query(
                "select * from Book " +
                        "inner join Author on Book.author_ID= Author.author_ID " +
                        "inner join Publisher on Book.publisher_ID=Publisher.publisher_ID " +
                        "inner join Language on Book.language_ID=Language.language_ID",
                (rs, rowNum) ->
                        new BookReadEntity(
                                rs.getLong("isbn"),
                                rs.getString("title"),
                                rs.getInt("page_count"),
                                rs.getString("Author.name"),
                                rs.getString("Author.last_name"),
                                rs.getString("Publisher.name"),
                                rs.getString("Language.name")
                        ));
    }

  @Override
    public Optional<BookReadEntity> findByIsbn(Long isbn) {
        return jdbcTemplate.queryForObject(
                "select * from Book where isbn= ? " +
                        "inner join Author on Book.author_ID= Author.author_ID " +
                        "inner join Publisher on Book.publisher_ID=Publisher.publisher_ID " +
                        "inner join Language on Book.language_ID=Language.language_ID",
                new Object[]{isbn},
                (rs, rowNum) ->
                        Optional.of(new BookReadEntity(
                                rs.getLong("isbn"),
                                rs.getString("title"),
                                rs.getInt("page_count"),
                                rs.getString("Author.name"),
                                rs.getString("Author.last_name"),
                                rs.getString("Publisher.name"),
                                rs.getString("Language.name")
                        ))
        );
    }

    @Override
    public int deleteByIsbn(Long isbn) {
        return jdbcTemplate.update(
                "delete Book where isbn = ?",
                isbn);
    }
}
