package psttest.demo.service;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import psttest.demo.controller.requests.GoodsRequest;
import psttest.demo.dao.GoodsRepository;
import psttest.demo.domain.Goods;

import java.util.Optional;

@Service
@CacheConfig(cacheNames = {"goods"})
public class GoodsService {

    private final GoodsRepository goodsRepository;

    public GoodsService(GoodsRepository goodsRepository) {
        this.goodsRepository = goodsRepository;
    }

    @Cacheable(value = "goods")
    public Page<Goods> findAll(Pageable pageable){
        return goodsRepository.findAll(pageable);
    }

    @Cacheable(value = "goods")
    public Optional<Goods> findByGoodName(String name){
        return goodsRepository.findByGoodName(name);
    }

    @Cacheable(value = "goods")
    public Optional<Goods> findById(Long goodId){
        return goodsRepository.findById(goodId);
    }

    @CacheEvict(value = "goods", allEntries = true)
    public Goods save(Goods goods){
        return goodsRepository.save(goods);
    }

    @CacheEvict(value = "goods", allEntries = true)
    public void delete(Goods goods){
        goodsRepository.delete(goods);
    }

    @CacheEvict(value = "goods", allEntries = true)
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public Goods updateGoods(Long goodsId, GoodsRequest request){
        Goods goodsFromDb = findById(goodsId).orElseThrow();
        goodsFromDb.setGoodName(request.getGoodName());
        return save(goodsFromDb);
    }

    @CacheEvict(value = "goods", allEntries = true)
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public String deleteGoods(Long goodId){
        Goods goodsFromDb = findById(goodId).orElseThrow();
        delete(goodsFromDb);
        return "Goods with ID = " + goodId + " deleted";
    }

    @CacheEvict(value = "goods", allEntries = true)
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public Goods create(GoodsRequest request){
        Goods goods = new Goods();
        goods.setGoodName(request.getGoodName());
        return save(goods);
    }
}
