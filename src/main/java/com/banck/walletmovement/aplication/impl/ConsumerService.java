/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.banck.walletmovement.aplication.impl;

import com.banck.walletmovement.aplication.WalletOperations;
import com.banck.walletmovement.domain.Wallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/**
 *
 * @author jnacarra
 */
@Service
public class ConsumerService {

//    @Autowired
//    public WalletOperations operations;
//
//    @KafkaListener(
//            topics = "topic-wallet", containerFactory = "walletKafkaListenerContainerFactory")
//    public void consumerWalletCreate(Wallet Wallet) {
//        System.out.println("El telefonito:" + Wallet.getTelephone());
//        //operations.create(person);
//    }

}
