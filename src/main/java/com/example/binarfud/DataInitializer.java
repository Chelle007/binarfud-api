//package com.example.binarfud;
//
//import com.example.binarfud.model.entity.*;
//import com.example.binarfud.model.entity.account.User;
//import com.example.binarfud.repository.MenuItemRepository;
//import com.example.binarfud.repository.RestaurantRepository;
//import com.example.binarfud.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//@Component
//public class DataInitializer {
//    @Autowired private UserRepository userRepository;
//    @Autowired private MenuItemRepository menuItemRepository;
//    @Autowired private RestaurantRepository restaurantRepository;
//
//    public static final User customer = User.builder()
//            .username("Customer")
//            .email("Customer@gmail.com")
//            .password("12345678")
//            .role(User.Role.CUSTOMER)
//            .build();
//
//    public static final User seller = User.builder()
//            .username("Seller")
//            .email("Seller@gmail.com")
//            .password("12345678")
//            .role(User.Role.SELLER)
//            .build();
//
//    public static final Restaurant restaurant = Restaurant.builder()
//            .name("BinarResto")
//            .location("Singapore")
//            .open(true)
//            .owner(seller)
//            .build();
//
//    public void initData() {
//        initializeUsers();
//        initializeRestaurants();
//        initializeMenu();
//    }
//
//    public void initializeRestaurants() {
//        restaurantRepository.save(restaurant);
//    }
//
//    private void initializeUsers() {
//        userRepository.save(customer);
//        userRepository.save(seller);
//    }
//
//    public void initializeMenu() {
//        menuItemRepository.save(MenuItem.builder()
//                .name("Nasi Goreng")
//                .type(MenuItem.Type.FOOD)
//                .priceS(13000)
//                .priceM(15000)
//                .priceL(17000)
//                .restaurant(restaurant)
//                .build());
//        menuItemRepository.save(MenuItem.builder()
//                .name("Mie Goreng")
//                .type(MenuItem.Type.FOOD)
//                .priceS(11000)
//                .priceM(13000)
//                .priceL(15000)
//                .restaurant(restaurant)
//                .build());
//        menuItemRepository.save(MenuItem.builder()
//                .name("Nasi + Ayam")
//                .type(MenuItem.Type.FOOD)
//                .priceS(16000)
//                .priceM(18000)
//                .priceL(20000)
//                .restaurant(restaurant)
//                .build());
//        menuItemRepository.save(MenuItem.builder()
//                .name("Es Teh Manis")
//                .type(MenuItem.Type.BEVERAGE)
//                .priceM(3000)
//                .restaurant(restaurant)
//                .build());
//        menuItemRepository.save(MenuItem.builder()
//                .name("Es Jeruk")
//                .type(MenuItem.Type.BEVERAGE)
//                .priceM(5000)
//                .restaurant(restaurant)
//                .build());
//    }
//}
