package org.projectx.platform.orderservice.configuration;

import org.projectx.platform.orderservice.common.DataLoader;
import org.projectx.platform.orderservice.repository.ItemRepository;
import org.projectx.platform.orderservice.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServerConfiguration {

    @Bean
    CommandLineRunner loadData(UserRepository userRepository, ItemRepository itemRepository) {
        return new DataLoader(userRepository, itemRepository);
    }

}
