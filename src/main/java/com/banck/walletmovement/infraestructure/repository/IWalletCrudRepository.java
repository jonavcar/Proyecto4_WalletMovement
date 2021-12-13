package com.banck.walletmovement.infraestructure.repository;

import com.banck.walletmovement.infraestructure.model.dao.WalletDao;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

/**
 *
 * @author jonavcar
 */
public interface IWalletCrudRepository extends ReactiveCrudRepository<WalletDao, String> {

    Flux<WalletDao> findAllByDocument(String document);
}
