package psttest.demo.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import psttest.demo.domain.User;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long>,
                                        JpaRepository<User, Long>{

    @EntityGraph(attributePaths = { "roles", "goods", "messages" })
    Optional<User> findByUsername(String username);

    @Modifying
    @Transactional
    @Query(value = "insert into l_user_goods (good_id, user_id) values (:good_id, :user_id)", nativeQuery = true)
    void byeGoods(@Param("user_id") Long userId, @Param("good_id") Long goodId);

    @Modifying
    @Transactional
    @Query(value = "delete from l_user_goods where good_id=:good_id and user_id=:user_id", nativeQuery = true)
    void removeGoods(@Param("user_id") Long userId, @Param("good_id") Long goodId);

    @Modifying
    @Transactional
    @Query(value = "delete from l_user_goods where user_id=:user_id", nativeQuery = true)
    void removeUser(@Param("user_id") Long userId);

    @EntityGraph(attributePaths = { "roles", "goods", "messages" })
    Page<User> findAll(Pageable pageable);

    @EntityGraph(attributePaths = { "roles", "goods", "messages" })
    Optional<User> findById(Long userId);

}
