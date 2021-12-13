package com.banck.walletmovement.aplication.impl;

import com.banck.walletmovement.domain.Movement;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import com.banck.walletmovement.aplication.MovementOperations;
import com.banck.walletmovement.aplication.WalletOperations;
import com.banck.walletmovement.aplication.model.MovementRepository;
import com.banck.walletmovement.utils.Concept;
import com.banck.walletmovement.utils.Modality;
import com.banck.walletmovement.utils.ProductType;
import com.banck.walletmovement.utils.Status;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * com.banck.walletmovement
 *
 * @author jonavcar
 */
@Service
@RequiredArgsConstructor
public class MovementOperationsImpl implements MovementOperations {

    Logger logger = LoggerFactory.getLogger(WalletOperationsImpl.class);

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    DateTimeFormatter formatTime = DateTimeFormatter.ofPattern("HH:mm:ss");
    DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    LocalDateTime dateTime = LocalDateTime.now(ZoneId.of("America/Bogota"));

    public ResponseService responseService;
    private final MovementRepository movementRepository;
    private final WalletOperations walletOperations;

    @Override
    public Flux<Movement> list() {
        return movementRepository.list();
    }

    @Override
    public Mono<Movement> get(String movement) {
        return movementRepository.get(movement);
    }

    @Override
    public Mono<ResponseService> create(Movement movement) {
        return validateDataWalletToCreate(movement).flatMap(RS -> {
            responseService = RS;
            if (responseService.getStatus() == Status.OK) {
                return walletOperations.get(movement.getProduct()).flatMap(walletR -> {
                    return insertMovement(movement);
                }).switchIfEmpty(errorNotExistWallet(movement));
            } else {
                return Mono.just(responseService);
            }
        });

    }

    @Override
    public Mono<Movement> update(String movement, Movement c) {
        return movementRepository.update(movement, c);
    }

    @Override
    public void delete(String movement) {
        movementRepository.delete(movement);
    }

    @Override
    public Flux<Movement> listByCustomer(String customer) {
        return movementRepository.listByCustomer(customer);
    }

    @Override
    public Flux<Movement> listByCustomerAndProduct(String customer, String product) {
        return movementRepository.listByCustomerAndAccount(customer, product);
    }

    @Override
    public Flux<Movement> listByProduct(String product) {

        return movementRepository.listByProduct(product);
    }

    @Override
    public Mono<Double> mainAccountBalance(String debitCard) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Mono<ResponseService> insertMovement(Movement movement) {
        responseService = new ResponseService();
        movement.setMovement(getRandomNumberString());

        movement.setModality(Modality.BANCA_MOVIL.value);
        movement.setConcept(Concept.ENVIO_MOVIL.value);
        movement.setProductType(ProductType.MONEDERO_MOVIL.value);

        movement.setDate(dateTime.format(formatDate));
        movement.setHour(dateTime.format(formatTime));
        movement.setState(true);
        return movementRepository.create(movement).flatMap(w -> {
            responseService.setStatus(Status.OK);
            responseService.setData(w);
            return Mono.just(responseService);
        });
    }

    public Mono<ResponseService> validateDataWalletToCreate(Movement movement) {
        responseService = new ResponseService();
        responseService.setStatus(Status.ERROR);
        return Mono.just(movement).flatMap(fm -> {
            if (!Optional.ofNullable(movement.getAmount()).isPresent() || movement.getAmount() == 0) {
                responseService.setMessage("Debe el monto y no debe ser 0");
                return Mono.just(responseService);
            }
            if (!Optional.ofNullable(movement.getConcept()).isPresent()) {
                responseService.setMessage("Debe ingresar un concepto");
                return Mono.just(responseService);
            }
            if (!Optional.ofNullable(movement.getProduct()).isPresent() || movement.getProduct().length() < 9) {
                responseService.setMessage("Debe ingresar el numero de telefono es el numero de monedero");
                return Mono.just(responseService);
            }
            responseService.setStatus(Status.OK);
            responseService.setData(fm);
            return Mono.just(responseService);
        });
    }

    public Mono<ResponseService> errorNotExistWallet(Movement movement) {
        responseService = new ResponseService();
        responseService.setStatus(Status.ERROR);
        responseService.setMessage("No existe el wallet " + movement.getProduct());
        responseService.setData(movement);
        return Mono.just(responseService);
    }
    
    public String getRandomNumberString() {
        Random rnd = new Random();
        int number = rnd.nextInt(999999999);
        return String.format("%09d", number);
    }

}
