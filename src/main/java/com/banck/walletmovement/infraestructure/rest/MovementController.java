package com.banck.walletmovement.infraestructure.rest;

import com.banck.walletmovement.domain.Movement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import com.banck.walletmovement.aplication.MovementOperations;
import com.banck.walletmovement.utils.Concept;
import com.banck.walletmovement.utils.Modality;
import com.banck.walletmovement.utils.ProductType;
import java.time.LocalDate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author jonavcar
 */
@RestController
@RequestMapping("/wallet/mov")
@RequiredArgsConstructor
public class MovementController {

    DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    DateTimeFormatter formatTime = DateTimeFormatter.ofPattern("HH:mm:ss");
    LocalDateTime dateTime = LocalDateTime.now(ZoneId.of("America/Bogota"));
    private final MovementOperations operations;

    @GetMapping
    public Flux<Movement> listAll() {
        return operations.list();
    }

    @GetMapping("/{id}")
    public Mono<Movement> get(@PathVariable("id") String id) {
        return operations.get(id);
    }

    @GetMapping("/customer/{id}/list")
    public Flux<Movement> listByCustomer(@PathVariable("id") String id) {
        return operations.listByCustomer(id);
    }

    @GetMapping("/{product}/list")
    public Flux<Movement> listByProduct(@PathVariable("product") String product) {
        return operations.listByProduct(product);
    }

    @PostMapping
    public Mono<ResponseEntity> create(@RequestBody Movement rqMovement) {
        rqMovement.setMovement(getRandomNumberString());

        rqMovement.setModality(Modality.BANCA_MOVIL.value);
        rqMovement.setConcept(Concept.ENVIO_MOVIL.value);
        rqMovement.setProductType(ProductType.MONEDERO_MOVIL.value);

        rqMovement.setDate(dateTime.format(formatDate));
        rqMovement.setHour(dateTime.format(formatTime));
        rqMovement.setState(true);
        return operations.create(rqMovement).flatMap(w -> {
            return Mono.just(new ResponseEntity(w, HttpStatus.OK));
        });
    }

    @PutMapping("/{id}")
    public Mono<Movement> update(@PathVariable("id") String id, @RequestBody Movement movement) {
        return operations.update(id, movement);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") String id) {
        operations.delete(id);
    }

    public static String getRandomNumberString() {
        Random rnd = new Random();
        int number = rnd.nextInt(999999999);
        return String.format("%09d", number);
    }

    public boolean isDateRange(String strDateI, String strDateF, String strDateC) {
        LocalDate dateI = LocalDate.parse(strDateI, formatDate);
        LocalDate dateF = LocalDate.parse(strDateF, formatDate);
        LocalDate dateC = LocalDate.parse(strDateC, formatDate);
        return ((dateC.isAfter(dateI) || dateC.isEqual(dateI)) && (dateC.isBefore(dateF) || dateC.isEqual(dateF)));
    }

}
