package com.example;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.Collection;
import java.util.stream.Stream;

@RepositoryRestResource
interface OrderRepository extends PagingAndSortingRepository<OrderItem, Long> {

    @RestResource(path = "by-name")
    Collection<OrderItem> findByCustomerName(@Param("customerName") String customerName);
}

@SpringBootApplication
@EnableAutoConfiguration
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(OrderRepository orderRepository) {
        return args -> {
            Stream.of("47774", "77333", "99333", "27772")
                    .forEach(orderNumber -> orderRepository.save(new OrderItem(orderNumber, "Customer1")));
            orderRepository.findAll().forEach(System.out::println);
        };
    }
}
