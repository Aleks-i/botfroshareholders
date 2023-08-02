package valera_bot.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import valera_bot.model.Anekdote;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class AnekdoteRepository {

    private static final BeanPropertyRowMapper<Anekdote> ROW_MAPPER = BeanPropertyRowMapper.newInstance(Anekdote.class);
    @Autowired
    private final JdbcTemplate jdbcTemplate;

    public Anekdote getAnek(int id) {
        log.info("get anekdote id {}", id);
        List<Anekdote> anekdote = jdbcTemplate.query("SELECT * FROM anekdotes WHERE id=?", ROW_MAPPER, id);
        return DataAccessUtils.singleResult(anekdote);
    }
}
