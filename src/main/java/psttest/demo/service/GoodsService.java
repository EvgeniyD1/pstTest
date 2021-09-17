package psttest.demo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import psttest.demo.controller.GoodsRequest;
import psttest.demo.dao.GoodsRepository;
import psttest.demo.domain.Goods;

import java.util.Optional;

@Service
public class GoodsService {

    private final GoodsRepository goodsRepository;

    public GoodsService(GoodsRepository goodsRepository) {
        this.goodsRepository = goodsRepository;
    }

    public Page<Goods> findAll(Pageable pageable){
        return goodsRepository.findAll(pageable);
    }

    public Optional<Goods> findByGoodName(String name){
        return goodsRepository.findByGoodName(name);
    }

    public Optional<Goods> findById(Long goodId){
        return goodsRepository.findById(goodId);
    }

    public Goods save(Goods goods){
        return goodsRepository.save(goods);
    }

    public void delete(Goods goods){
        goodsRepository.delete(goods);
    }

    public Goods updateGoods(Long goodsId, GoodsRequest request){
        Goods goodsFromDb = findById(goodsId).orElseThrow();
        goodsFromDb.setGoodName(request.getGoodName());
        return save(goodsFromDb);
    }

    public String deleteGoods(Long goodId){
        Goods goodsFromDb = findById(goodId).orElseThrow();
        delete(goodsFromDb);
        return "Goods with ID = " + goodId + " deleted";
    }

    public Goods create(GoodsRequest request){
        Goods goods = new Goods();
        goods.setGoodName(request.getGoodName());
        return save(goods);
    }
}
