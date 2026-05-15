package com.app.ecom.service;

import com.app.ecom.dto.OrderResponse;
import com.app.ecom.model.CartItem;
import com.app.ecom.model.User;
import com.app.ecom.repository.OrderRepository;
import com.app.ecom.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CartService cartService;

    private final OrderRepository orderRepository;

    private final UserRepository userRepository;

    public Optional<OrderResponse> createOrder(String userId) {
        // Validate for cart Items
        List<CartItem> cartItems = cartService.getCart(userId);
        if(cartItems.isEmpty()){
            return Optional.empty();
        }

        // Validate for user
        Optional<User> userOptional = userRepository.findById(Long.valueOf(userId));
        if(userOptional.isEmpty()){
            return Optional.empty();
        }
        User user = userOptional.get();

        // Calculate total price
        BigDecimal totatlPrice = cartItems.stream()
                .map(CartItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Create Order
        // Clear the cart
    }
}
