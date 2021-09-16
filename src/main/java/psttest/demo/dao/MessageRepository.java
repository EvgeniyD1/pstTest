package psttest.demo.dao;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import psttest.demo.domain.Message;

import java.util.List;

public interface MessageRepository extends CrudRepository<Message, Long> {

    @EntityGraph(attributePaths = { "user" })
    List<Message> findAll();
}
