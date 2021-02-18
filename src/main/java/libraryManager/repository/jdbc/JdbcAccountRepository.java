package libraryManager.repository.jdbc;

import libraryManager.entity.Account;
import libraryManager.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JdbcAccountRepository implements AccountRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public int count() {
        return jdbcTemplate
                .queryForObject("select count(*) from Account", Integer.class);
    }


    @Override
    public boolean save(Account account) {
        return jdbcTemplate.update(
                "insert into Account (name, active) values(?,?)",
                account.getName(),
                account.getActive()) > 0;
    }


    @Override
    public boolean updateActive(Long accountID, boolean active) {
        return jdbcTemplate.update(
                "update Account set active = ? where account_ID= ?",
                active ? 1 : 0,
                accountID) > 0;
    }


    @Override
    public List<Account> findAll() {
        return jdbcTemplate.query(
                "select * from Account",
                (rs, rowNum) ->
                        new Account(
                                rs.getLong("account_ID"),
                                rs.getString("name"),
                                rs.getBoolean("active")
                        ));
    }

    @Override
    public Optional<Account> findById(Long id) {
        try {
            return jdbcTemplate.queryForObject(
                    "select * from Account where account_ID = ?",
                    new Object[]{id},
                    (rs, rowNum) ->
                            Optional.of(new Account(
                                    rs.getLong("account_ID"),
                                    rs.getString("name"),
                                    rs.getBoolean("active")
                            ))
            );
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public int deleteById(Long id) {
        return jdbcTemplate.update(
                "delete from Account where account_ID = ?",
                id);
    }

}

