package com.example.appliance.config;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.appliance.models.Account;
import com.example.appliance.models.Brand;
import com.example.appliance.models.Category;
import com.example.appliance.models.Customer;
import com.example.appliance.models.OrderInfo;
import com.example.appliance.models.OrderItem;
import com.example.appliance.models.Payment;
import com.example.appliance.models.Product;
import com.example.appliance.models.Review;
import com.example.appliance.models.Role;
import com.example.appliance.repositories.AccountRepository;
import com.example.appliance.repositories.BrandRepository;
import com.example.appliance.repositories.CategoryRepository;
import com.example.appliance.repositories.CustomerRepository;
import com.example.appliance.repositories.OrderInfoRepository;
import com.example.appliance.repositories.OrderItemRepository;
import com.example.appliance.repositories.PaymentRepository;
import com.example.appliance.repositories.ProductRepository;
import com.example.appliance.repositories.ReviewRepository;
import com.example.appliance.repositories.RoleRepository;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initData(
            RoleRepository roleRepository,
            AccountRepository accountRepository,
            CustomerRepository customerRepository,
            CategoryRepository categoryRepository,
            BrandRepository brandRepository,
            ProductRepository productRepository,
            OrderInfoRepository orderInfoRepository,
            OrderItemRepository orderItemRepository,
            ReviewRepository reviewRepository,
            PaymentRepository paymentRepository,
            BCryptPasswordEncoder passwordEncoder
    ) {
        return args -> {

            if (roleRepository.count() > 0) {
                return;
            }

            Role adminRole = new Role();
            adminRole.setRoleLabel("ROLE_ADMIN");

            Role userRole = new Role();
            userRole.setRoleLabel("ROLE_USER");

            roleRepository.save(adminRole);
            roleRepository.save(userRole);

            Account adminAccount = new Account();
            adminAccount.setUserLogin("admin");
            adminAccount.setUserPassword(passwordEncoder.encode("Admin123!"));
            adminAccount.setEmail("admin@appliance.com");
            adminAccount.setRole(adminRole);

            Account userAccount1 = new Account();
            userAccount1.setUserLogin("ivanov");
            userAccount1.setUserPassword(passwordEncoder.encode("Ivanov123!"));
            userAccount1.setEmail("ivanov@gmail.com");
            userAccount1.setRole(userRole);

            Account userAccount2 = new Account();
            userAccount2.setUserLogin("petrova");
            userAccount2.setUserPassword(passwordEncoder.encode("Petrova123!"));
            userAccount2.setEmail("petrova@gmail.com");
            userAccount2.setRole(userRole);

            accountRepository.save(adminAccount);
            accountRepository.save(userAccount1);
            accountRepository.save(userAccount2);

            Customer customer1 = new Customer();
            customer1.setFirstName("Иван");
            customer1.setLastName("Иванов");
            customer1.setPatronymic("Иванович");
            customer1.setPhoneNumber("+79990000001");
            customer1.setAddress("г. Москва, ул. Ленина, д. 10");
            customer1.setAccount(userAccount1);

            Customer customer2 = new Customer();
            customer2.setFirstName("Анна");
            customer2.setLastName("Петрова");
            customer2.setPatronymic("Сергеевна");
            customer2.setPhoneNumber("+79990000002");
            customer2.setAddress("г. Санкт-Петербург, Невский пр., д. 25");
            customer2.setAccount(userAccount2);

            customerRepository.save(customer1);
            customerRepository.save(customer2);

            Category category1 = new Category();
            category1.setCategoryName("Холодильники");
            category1.setDescription("Бытовые холодильники разных моделей.");

            Category category2 = new Category();
            category2.setCategoryName("Стиральные машины");
            category2.setDescription("Автоматические стиральные машины.");

            Category category3 = new Category();
            category3.setCategoryName("Телевизоры");
            category3.setDescription("Современные телевизоры для дома.");

            Category category4 = new Category();
            category4.setCategoryName("Пылесосы");
            category4.setDescription("Пылесосы для сухой и влажной уборки.");

            categoryRepository.save(category1);
            categoryRepository.save(category2);
            categoryRepository.save(category3);
            categoryRepository.save(category4);

            Brand brand1 = new Brand();
            brand1.setBrandName("Samsung");
            brand1.setCountry("Южная Корея");

            Brand brand2 = new Brand();
            brand2.setBrandName("LG");
            brand2.setCountry("Южная Корея");

            Brand brand3 = new Brand();
            brand3.setBrandName("Bosch");
            brand3.setCountry("Германия");

            Brand brand4 = new Brand();
            brand4.setBrandName("Philips");
            brand4.setCountry("Нидерланды");

            brandRepository.save(brand1);
            brandRepository.save(brand2);
            brandRepository.save(brand3);
            brandRepository.save(brand4);

            Product product1 = new Product();
            product1.setProductName("Холодильник Samsung");
            product1.setModel("RB34C602ESA");
            product1.setDescription("Двухкамерный холодильник с системой No Frost.");
            product1.setPrice(new BigDecimal("64999.99"));
            product1.setStockQuantity(15);
            product1.setWarrantyMonths(24);
            product1.setColor("Серебристый");
            product1.setImageUrl("https://image-us.samsung.com/SamsungUS/home/appliances/refrigerators/rb34c602esa/01-RB34C602ESA-001.jpg");
            product1.setAvailabilityStatus("В наличии");
            product1.setCategory(category1);
            product1.setBrand(brand1);

            Product product2 = new Product();
            product2.setProductName("Стиральная машина LG");
            product2.setModel("F2V5HS0W");
            product2.setDescription("Стиральная машина с функцией пара.");
            product2.setPrice(new BigDecimal("52999.00"));
            product2.setStockQuantity(10);
            product2.setWarrantyMonths(24);
            product2.setColor("Белый");
            product2.setImageUrl("https://www.lg.com/ru/images/washing-machines/md07500457/gallery/F2V5HS0W-D-01.jpg");
            product2.setAvailabilityStatus("В наличии");
            product2.setCategory(category2);
            product2.setBrand(brand2);

            Product product3 = new Product();
            product3.setProductName("Телевизор Bosch Smart");
            product3.setModel("BSH-55UHD");
            product3.setDescription("55-дюймовый Smart TV с поддержкой 4K.");
            product3.setPrice(new BigDecimal("71999.50"));
            product3.setStockQuantity(7);
            product3.setWarrantyMonths(36);
            product3.setColor("Черный");
            product3.setImageUrl("https://media3.bosch-home.com/ProductShots/Unified/2000x1500/BSH-55UHD-01.png");
            product3.setAvailabilityStatus("В наличии");
            product3.setCategory(category3);
            product3.setBrand(brand3);

            Product product4 = new Product();
            product4.setProductName("Пылесос Philips");
            product4.setModel("XD3110");
            product4.setDescription("Компактный пылесос для сухой уборки.");
            product4.setPrice(new BigDecimal("14999.90"));
            product4.setStockQuantity(20);
            product4.setWarrantyMonths(12);
            product4.setColor("Синий");
            product4.setImageUrl("https://www.philips.ru/c-dam/b2c/master/vacuum-cleaners/xd3110/XD3110-main-2000px.png");
            product4.setAvailabilityStatus("В наличии");
            product4.setCategory(category4);
            product4.setBrand(brand4);

            productRepository.save(product1);
            productRepository.save(product2);
            productRepository.save(product3);
            productRepository.save(product4);

            OrderInfo order1 = new OrderInfo();
            order1.setOrderDate("2026-03-25 10:30");
            order1.setOrderStatus("Новый");
            order1.setTotalAmount(new BigDecimal("117998.99"));
            order1.setDeliveryAddress(customer1.getAddress());
            order1.setComment("Доставка в первой половине дня.");
            order1.setCustomer(customer1);

            OrderInfo order2 = new OrderInfo();
            order2.setOrderDate("2026-03-25 12:15");
            order2.setOrderStatus("Оплачен");
            order2.setTotalAmount(new BigDecimal("14999.90"));
            order2.setDeliveryAddress(customer2.getAddress());
            order2.setComment("Позвонить за 30 минут до доставки.");
            order2.setCustomer(customer2);

            orderInfoRepository.save(order1);
            orderInfoRepository.save(order2);

            OrderItem orderItem1 = new OrderItem();
            orderItem1.setQuantity(1);
            orderItem1.setItemPrice(new BigDecimal("64999.99"));
            orderItem1.setOrderInfo(order1);
            orderItem1.setProduct(product1);

            OrderItem orderItem2 = new OrderItem();
            orderItem2.setQuantity(1);
            orderItem2.setItemPrice(new BigDecimal("52999.00"));
            orderItem2.setOrderInfo(order1);
            orderItem2.setProduct(product2);

            OrderItem orderItem3 = new OrderItem();
            orderItem3.setQuantity(1);
            orderItem3.setItemPrice(new BigDecimal("14999.90"));
            orderItem3.setOrderInfo(order2);
            orderItem3.setProduct(product4);

            orderItemRepository.save(orderItem1);
            orderItemRepository.save(orderItem2);
            orderItemRepository.save(orderItem3);

            List<OrderItem> order1Items = new ArrayList<>();
            order1Items.add(orderItem1);
            order1Items.add(orderItem2);
            order1.setOrderItems(order1Items);

            List<OrderItem> order2Items = new ArrayList<>();
            order2Items.add(orderItem3);
            order2.setOrderItems(order2Items);

            orderInfoRepository.save(order1);
            orderInfoRepository.save(order2);

            Review review1 = new Review();
            review1.setRating(5);
            review1.setComment("Отличный холодильник, работает тихо и вместительный.");
            review1.setReviewDate("2026-03-24");
            review1.setCustomer(customer1);
            review1.setProduct(product1);

            Review review2 = new Review();
            review2.setRating(4);
            review2.setComment("Хороший пылесос, удобный и компактный.");
            review2.setReviewDate("2026-03-23");
            review2.setCustomer(customer2);
            review2.setProduct(product4);

            reviewRepository.save(review1);
            reviewRepository.save(review2);

            Payment payment1 = new Payment();
            payment1.setAmount(new BigDecimal("117998.99"));
            payment1.setPaymentMethod("Банковская карта");
            payment1.setPaymentStatus("Ожидает оплаты");
            payment1.setPaymentDate("2026-03-25 10:35");
            payment1.setOrderInfo(order1);

            Payment payment2 = new Payment();
            payment2.setAmount(new BigDecimal("14999.90"));
            payment2.setPaymentMethod("Наличные");
            payment2.setPaymentStatus("Оплачено");
            payment2.setPaymentDate("2026-03-25 12:20");
            payment2.setOrderInfo(order2);

            paymentRepository.save(payment1);
            paymentRepository.save(payment2);

            order1.setPayment(payment1);
            order2.setPayment(payment2);

            orderInfoRepository.save(order1);
            orderInfoRepository.save(order2);
        };
    }
}