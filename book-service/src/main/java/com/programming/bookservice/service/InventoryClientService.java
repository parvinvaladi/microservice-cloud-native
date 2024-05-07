package com.programming.bookservice.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "",url = "{com.programming.inventory.service}")
public interface InventoryClientService {

//    @RequestMapping(method = RequestMethod.POST, path = "/api/v1/inventory/get-all")
}
