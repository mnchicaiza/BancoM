package com.bankM.transactions.client;


import com.bankM.transactions.vo.ClientToTransactionResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "clientAccount", url = "${transactions.services.clients.url}")
public interface IClientClient {
    @RequestMapping(method = RequestMethod.GET, value = "/client/findBy")
    ResponseEntity<ClientToTransactionResponse> findBy(@RequestParam Integer id, @RequestParam String ci);
}
