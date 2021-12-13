package com.banck.walletmovement.infraestructure.repository;

import com.banck.walletmovement.domain.Wallet;
import com.banck.walletmovement.infraestructure.model.dao.WalletDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import com.banck.walletmovement.aplication.model.WalletRepository;

/**
 *
 * @author jonavcar
 */
@Component
public class WalletCrudRepository implements WalletRepository {

    @Autowired
    IWalletCrudRepository crudRepository;

    @Override
    public Mono<Wallet> get(String debitcardaccount) {
        return crudRepository.findById(debitcardaccount).map(this::PersonDaoToPerson);
    }

    @Override
    public Flux<Wallet> list() {
        return crudRepository.findAll().map(this::PersonDaoToPerson);
    }

    @Override
    public Mono<Wallet> create(Wallet debitcardaccount) {
        return crudRepository.save(PersonToPersonDao(debitcardaccount)).map(this::PersonDaoToPerson);
    }

    @Override
    public Mono<Wallet> update(String debitcardaccount, Wallet c) {
        return crudRepository.save(PersonToPersonDao(c)).map(this::PersonDaoToPerson);
    }

    @Override
    public void delete(String debitcardaccount) {
        crudRepository.deleteById(debitcardaccount).subscribe();
    }

    public Wallet PersonDaoToPerson(WalletDao md) {
        Wallet m = new Wallet();
        m.setPerson(md.getPerson());
        m.setDocument(md.getDocument());
        m.setDocumentType(md.getDocumentType());
        m.setTelephone(md.getTelephone());
        m.setImei(md.getImei());
        m.setMail(md.getMail());
        m.setDate(md.getDate());
        m.setState(md.isState());
        return m;
    }

    public WalletDao PersonToPersonDao(Wallet p) {
        WalletDao pd = new WalletDao();
        pd.setPerson(p.getPerson());
        pd.setDocument(p.getDocument());
        pd.setDocumentType(p.getDocumentType());
        pd.setTelephone(p.getTelephone());
        pd.setImei(p.getImei());
        pd.setMail(p.getMail());
        pd.setDate(p.getDate());
        pd.setState(p.isState());
        return pd;
    }

    @Override
    public Flux<Wallet> listByDocument(String document) {
        return crudRepository.findAllByDocument(document).map(this::PersonDaoToPerson);
    }

}
