package id.my.hendisantika.kafkastreamsstatestore.controller;

import id.my.hendisantika.kafkastreamsstatestore.dto.OrderLocation;
import id.my.hendisantika.kafkastreamsstatestore.service.OrderLocationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by IntelliJ IDEA.
 * Project : kafka-streams-state-store
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 5/24/24
 * Time: 09:23
 * To change this template use File | Settings | File Templates.
 */
@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class OrderLocationController {

    private final OrderLocationService orderLocationService;

    /**
     * {@code GET  /the order location} : order location for of an order.
     *
     * @param orderNumber order number
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the order location in body.
     */
    @GetMapping("/order-location/{orderNumber}")
    public ResponseEntity<OrderLocation> getOrderLocation(@PathVariable String orderNumber) {
        log.info("REST request to get location of Order");
        OrderLocation orderLocation = orderLocationService.getOrderLocation(orderNumber);
        return ResponseEntity.ok().body(orderLocation);
    }
}
