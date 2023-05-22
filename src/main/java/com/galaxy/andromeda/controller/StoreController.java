package com.galaxy.andromeda.controller;

import com.galaxy.andromeda.service.StoreService;
import com.galaxy.andromeda.vo.store.StoreVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/stores")
@Validated
public class StoreController {

    @Autowired
    StoreService storeService;

    @GetMapping(value = "/{id}", produces = "application/json")
    public StoreVO getStore(@PathVariable @NotNull Long id) {

        log.info("StoreController::getStore::Start");

        StoreVO storeVO = storeService.getStore(id);

        log.info("StoreController::getStore::End");

        return storeVO;
    }

    @GetMapping(produces = "application/json")
    public List<StoreVO> getAllStores() {

        log.info("StoreController::getAllStores::Start");

        List<StoreVO> stores = storeService.getAllStores();

        log.info("StoreController::getAllStores::End");

        return stores;
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public StoreVO addStore(@RequestBody @Valid StoreVO storeVO) {

        log.info("StoreController::getStore::Start");

        StoreVO resultVO = storeService.saveStore(storeVO);

        log.info("StoreController::getStore::End");

        return resultVO;
    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    public void deleteStore(@PathVariable @NotNull Long id) {

        log.info("StoreController::deleteStore::Start");

        storeService.deleteStore(id);

        log.info("StoreController::deleteStore::End");
    }

    @PatchMapping(value = "/{id}", produces = "application/json")
    public StoreVO editStore(@PathVariable @NotNull Long id, @NotNull @RequestBody StoreVO storeVO) {

        log.info("StoreController::editStore::Start");

        StoreVO res = storeService.editStore(id, storeVO);

        log.info("StoreController::editStore::End");

        return res;
    }

}
