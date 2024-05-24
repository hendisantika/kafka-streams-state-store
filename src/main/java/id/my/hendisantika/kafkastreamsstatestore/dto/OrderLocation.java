package id.my.hendisantika.kafkastreamsstatestore.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Created by IntelliJ IDEA.
 * Project : kafka-streams-state-store
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 5/24/24
 * Time: 09:16
 * To change this template use File | Settings | File Templates.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderLocation {
    private String orderNumber;
    private BigDecimal latitude;
    private BigDecimal longitude;

}
