package com.rt.market.event;

import com.rt.market.dto.OrderDto;
import com.rt.market.dto.OrderItemDto;
import com.rt.market.model.ProductEntity;
import com.rt.market.service.MailService;
import com.rt.market.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MailEventListener {

    private final MailService mailService;
    private final ProductService productService;

    @Async
    @EventListener
    public void handleOrderCreatedEvent(OrderCreatedEvent event) {
        OrderDto orderDto = event.getOrderDto();
        BigDecimal totalPrice = event.getTotalPrice();
        String clientBody = generateClientEmailBody(orderDto, totalPrice);
        String adminBody = generateAdminEmailBody(orderDto, totalPrice);

        mailService.sendEmailToClient(orderDto.getClientEmail(), "Ваш заказ", clientBody);
        mailService.sendEmailToAdmin("Новый заказ", adminBody);
    }


    private String generateClientEmailBody(OrderDto order, BigDecimal totalPrice) {
        String itemList = order.getItems().stream()
                .map(this::formatOrderItem)
                .collect(Collectors.joining());

        return String.format(
                "<h2>Спасибо за ваш заказ, %s!</h2>" +
                        "<p><strong>Ваш заказ:</strong></p>" +
                        "<ul>%s</ul>" +
                        "<p><strong>Итоговая стоимость:</strong> %s руб.</p>" +
                        "<p>Мы свяжемся с вами для подтверждения доставки.</p>",
                order.getClientName(),
                itemList,
                formatPrice(totalPrice)
        );
    }

    private String generateAdminEmailBody(OrderDto order, BigDecimal totalPrice) {
        String itemList = order.getItems().stream()
                .map(this::formatOrderItem)
                .collect(Collectors.joining());

        return String.format(
                "<h2>Поступил новый заказ</h2>" +
                        "<p><strong>Клиент:</strong> %s (%s)</p>" +
                        "<p><strong>Адрес доставки:</strong> %s</p>" +
                        "<p><strong>Заказанные товары:</strong></p>" +
                        "<ul>%s</ul>" +
                        "<p><strong>Итоговая стоимость:</strong> %s руб.</p>",
                order.getClientName(),
                order.getClientEmail(),
                order.getDeliveryAddress(),
                itemList,
                formatPrice(totalPrice)
        );
    }

    private String formatOrderItem(OrderItemDto item) {
        ProductEntity product = productService.findById(item.getProductId());
        return String.format(
                "<li>%s — %d шт. по %s руб.</li>",
                product.getName(),
                item.getQuantity(),
                formatPrice(BigDecimal.valueOf(product.getPrice()))
        );
    }

    private String formatPrice(BigDecimal price) {
        DecimalFormat df = new DecimalFormat("#,##0.00");
        return df.format(price);
    }
}
