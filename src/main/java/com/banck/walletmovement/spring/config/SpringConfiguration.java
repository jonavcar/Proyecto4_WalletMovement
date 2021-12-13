package com.banck.walletmovement.spring.config;

import com.banck.walletmovement.infraestructure.repository.MovementCrudRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.banck.walletmovement.aplication.model.MovementRepository;
import com.banck.walletmovement.aplication.model.WalletRepository;
import com.banck.walletmovement.infraestructure.repository.WalletCrudRepository;

/**
 *
 * @author jonavcar
 */
@Configuration
public class SpringConfiguration {

    @Bean
    public MovementRepository movementRepository() {
        return new MovementCrudRepository();
    }
    
    @Bean
    public WalletRepository walletRepository() {
        return new WalletCrudRepository();
    }
}
