package psttest.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import psttest.demo.domain.Goods;
import psttest.demo.domain.User;

import java.util.Optional;

public interface GoodsRepository extends CrudRepository<Goods, Long>,
        JpaRepository<Goods, Long> {

    Optional<Goods> findByGoodName(String name);
}
