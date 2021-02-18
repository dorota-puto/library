package libraryManager.repository.jdbc;

import libraryManager.entity.Author;
import libraryManager.entity.Language;
import libraryManager.entity.full.FullBookItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public class JdbcFullBookItemRepository implements libraryManager.repository.FullBookItemRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<FullBookItem> findByTitle(String title){
        return jdbcTemplate.query(
                "select * from Book " +
                        "inner join Author on Book.author_ID= Author.author_ID " +
                        "inner join Publisher on Book.publisher_ID=Publisher.publisher_ID " +
                        "inner join Language on Book.language_ID=Language.language_ID " +
                        "inner join Book_item on Book.isbn=Book_item.isbn " +
                        "where Book.title=?",
                new Object[]{title},
                (rs, rowNum) ->
                        new FullBookItem(
                                rs.getLong("isbn"),
                                rs.getString("title"),
                                new Author(rs.getLong("Author.author_ID"),  rs.getString("Author.name"), rs.getString("Author.last_name")),
                                rs.getString("Publisher.name"),
                                rs.getInt("page_count"),
                                new Language( rs.getLong("Language.language_ID"), rs.getString("Language.name")),
                                rs.getString("Book_item.rfid_tag")
                        ));
    }

    @Override
    public List<FullBookItem> findByAuthor(String name, String surname){
        return jdbcTemplate.query(
                "select * from Book " +
                        "inner join Author on Book.author_ID= Author.author_ID " +
                        "inner join Publisher on Book.publisher_ID=Publisher.publisher_ID " +
                        "inner join Language on Book.language_ID=Language.language_ID " +
                        "inner join Book_item on Book.isbn=Book_item.isbn " +
                        "where Author.name=? and Author.last_name=?",
                new Object[]{name,surname},

                (rs, rowNum) ->
                        new FullBookItem(
                                rs.getLong("isbn"),
                                rs.getString("title"),
                                new Author(rs.getLong("Author.author_ID"),  rs.getString("Author.name"), rs.getString("Author.last_name")),
                                rs.getString("Publisher.name"),
                                rs.getInt("page_count"),
                                new Language( rs.getLong("Language.language_ID"), rs.getString("Language.name")),
                                rs.getString("Book_item.rfid_tag")
                        ));
    }

    @Override
    public List<FullBookItem> findByIsbn(Long isbn){
        return jdbcTemplate.query(
                "select * from Book " +
                        "inner join Author on Book.author_ID= Author.author_ID " +
                        "inner join Publisher on Book.publisher_ID=Publisher.publisher_ID " +
                        "inner join Language on Book.language_ID=Language.language_ID " +
                        "inner join Book_item on Book.isbn=Book_item.isbn " +
                        "where Book_item.isbn=?",
                new Object[]{isbn},
                (rs, rowNum) ->
                        new FullBookItem(
                                rs.getLong("isbn"),
                                rs.getString("title"),
                                new Author( rs.getLong("Author.author_ID"), rs.getString("Author.name"), rs.getString("Author.last_name")),
                                rs.getString("Publisher.name"),
                                rs.getInt("page_count"),
                                new Language( rs.getLong("Language.language_ID"), rs.getString("Language.name")),
                                rs.getString("Book_item.rfid_tag")
                        ));
    }

    @Override
    public Optional<FullBookItem> findByRfidTag(String rfidTag){
        try {
            return jdbcTemplate.queryForObject(
                    "select * from Book " +
                            "inner join Author on Book.author_ID= Author.author_ID " +
                            "inner join Publisher on Book.publisher_ID=Publisher.publisher_ID " +
                            "inner join Language on Book.language_ID=Language.language_ID " +
                            "inner join Book_item on Book.isbn=Book_item.isbn " +
                            "where Book_item.rfid_tag=?",
                    new Object[]{rfidTag},
                    (rs, rowNum) ->
                            Optional.of(   new FullBookItem(
                                    rs.getLong("isbn"),
                                    rs.getString("title"),
                                    new Author(rs.getLong("Author.author_ID"), rs.getString("Author.name"), rs.getString("Author.last_name")),
                                    rs.getString("Publisher.name"),
                                    rs.getInt("page_count"),
                                    new Language( rs.getLong("Language.language_ID"), rs.getString("Language.name")),
                                    rs.getString("Book_item.rfid_tag")
                                    )
                            ));
        }catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<FullBookItem> listAll(){
        return jdbcTemplate.query(
                "select * from Book " +
                        "inner join Author on Book.author_ID= Author.author_ID " +
                        "inner join Publisher on Book.publisher_ID=Publisher.publisher_ID " +
                        "inner join Language on Book.language_ID=Language.language_ID " +
                        "inner join Book_item on Book.isbn=Book_item.isbn ",

                (rs, rowNum) ->
                        new FullBookItem(
                                rs.getLong("isbn"),
                                rs.getString("title"),
                                new Author( rs.getLong("Author.author_ID"), rs.getString("Author.name"), rs.getString("Author.last_name")),
                                rs.getString("Publisher.name"),
                                rs.getInt("page_count"),
                                new Language( rs.getLong("Language.language_ID"), rs.getString("Language.name")),
                                rs.getString("Book_item.rfid_tag")
                        ));
    }

    @Override
    public Set<String> getRfidTAgs(){
        return new HashSet<>(jdbcTemplate.query(
                "select rfid_tag from Book_item  ",
                (rs, rowNum) ->
                        rs.getString("rfid_tag")
                ));
    }

}
