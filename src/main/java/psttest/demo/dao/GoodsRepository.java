package psttest.demo.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import psttest.demo.domain.Goods;

import java.util.Optional;

@Repository
public interface GoodsRepository extends CrudRepository<Goods, Long>,
        JpaRepository<Goods, Long> {

    @EntityGraph(attributePaths = {"users"})
    Page<Goods> findAll(Pageable pageable);

    Optional<Goods> findByGoodName(String name);
}
