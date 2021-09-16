package psttest.demo.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import psttest.demo.dao.GoodsRepository;
import psttest.demo.domain.Goods;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/goods")
public class GoodsController {

    private final GoodsRepository goodsRepository;

    public GoodsController(GoodsRepository goodsRepository) {
        this.goodsRepository = goodsRepository;
    }

    @ApiOperation(value = "Find all goods")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful finding goods"),
            @ApiResponse(code = 500, message = "Server error, something wrong")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "Page number",
                    example = "0", defaultValue = "0", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "Items per page",
                    example = "3", defaultValue = "3", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "Field to sort",
                    example = "id", defaultValue = "id", dataType = "string", paramType = "query")
    })
    @GetMapping
    public ResponseEntity<Page<Goods>> findAll(@ApiIgnore Pageable pageable) {
        Page<Goods> users = goodsRepository.findAll(pageable);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @ApiOperation(value = "Find goods by name")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful loading"),
            @ApiResponse(code = 500, message = "Server error, something wrong")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "Search query - goodName", example = "coca",
                    required = true, dataType = "string", paramType = "query")
    })
    @GetMapping("/searchByGoodsName")
    public ResponseEntity<Goods> findByName(@RequestParam("name") String query) {
        Goods goods = goodsRepository.findByGoodName(query).orElseThrow();
        return new ResponseEntity<>(goods, HttpStatus.OK);
    }

    @ApiOperation(value = "Update goods name")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful update"),
            @ApiResponse(code = 500, message = "Server error, something wrong")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "Goods database id", example = "2" ,required = true,
                    dataType = "long", paramType = "path")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Goods> updateGoods(@PathVariable("id") Long goodsId,
                                           @RequestBody GoodsRequest request) {
        Goods goodsFromDb = goodsRepository.findById(goodsId).orElseThrow();
        goodsFromDb.setGoodName(request.getGoodName());
        return new ResponseEntity<>(goodsRepository.save(goodsFromDb), HttpStatus.OK);
    }

    @ApiOperation(value = "Delete goods")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful delete good"),
            @ApiResponse(code = 500, message = "Server error, something wrong")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "Good database id", example = "3", required = true,
                    dataType = "long", paramType = "path")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteGood(@PathVariable("id") Long goodId) {
        Goods goodsFromDb = goodsRepository.findById(goodId).orElseThrow();
        goodsRepository.delete(goodsFromDb);
        String delete = "Goods with ID = " + goodId + " deleted";
        return new ResponseEntity<>(delete, HttpStatus.OK);
    }

    @ApiOperation(value = "Create Good")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Successful creation good"),
            @ApiResponse(code = 500, message = "Server error, something wrong")
    })
    @PostMapping("/createGoods")
    public ResponseEntity<Goods> create(@RequestBody GoodsRequest request) {
        Goods goods = new Goods();
        goods.setGoodName(request.getGoodName());
        return new ResponseEntity<>(goodsRepository.save(goods), HttpStatus.CREATED);
    }
}
