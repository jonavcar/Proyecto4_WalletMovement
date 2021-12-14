/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.banck.walletmovement.aplication.impl;

import com.banck.walletmovement.aplication.WalletOperations;
import com.banck.walletmovement.domain.Wallet;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 *
 * @author jnacarra
 */
@Service
@RequiredArgsConstructor
public class ConsumerService {

    private final WalletOperations operations;

    @KafkaListener(topics = "topic-wallet", containerFactory = "walletKafkaListenerContainerFactory")
    public void createWallet(Wallet wallet) {
        Mono<ResponseService> rs = operations.create(wallet);
        rs.subscribe(w -> {
            System.out.println(w.getMessage());
        });

    }

}
