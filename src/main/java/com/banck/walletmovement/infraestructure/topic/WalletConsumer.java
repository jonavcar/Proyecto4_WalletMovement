/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.banck.walletmovement.infraestructure.topic;

import com.banck.walletmovement.domain.Wallet;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 *
 * @author jnacarra
 */
@Component
public class WalletConsumer {

    @KafkaListener(topics = "topic-wallet", containerFactory = "walletKafkaListenerContainerFactory")
    public void consumerWalletCreate(Wallet w) {
        System.out.println(w.toString());
    }
}
