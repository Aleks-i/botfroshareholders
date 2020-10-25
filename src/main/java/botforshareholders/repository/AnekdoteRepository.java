package botforshareholders.repository;

import botforshareholders.model.Anekdote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AnekdoteRepository {
    private static final Logger log = LoggerFactory.getLogger(AnekdoteRepository.class);

    private static final BeanPropertyRowMapper<Anekdote> ROW_MAPPER = BeanPropertyRowMapper.newInstance(Anekdote.class);
    private final JdbcTemplate jdbcTemplate;

    public AnekdoteRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Anekdote getAnek(int id) {
        log.info("get anekdote id {}", id);
        List<Anekdote> anekdote = jdbcTemplate.query("SELECT * FROM anekdotes WHERE id=?", ROW_MAPPER, id);
     return DataAccessUtils.singleResult(anekdote);
    }
}
