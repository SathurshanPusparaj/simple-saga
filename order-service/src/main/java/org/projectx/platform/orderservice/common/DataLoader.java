package org.projectx.platform.orderservice.common;

import org.projectx.platform.orderservice.entity.ItemEntity;
import org.projectx.platform.orderservice.entity.UserEntity;
import org.projectx.platform.orderservice.repository.ItemRepository;
import org.projectx.platform.orderservice.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;

public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    public DataLoader(UserRepository userRepository, ItemRepository itemRepository) {
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        ItemEntity itemEntity1 = new ItemEntity();
        itemEntity1.setName("Apple");
        itemEntity1.setPrice(125.12);
        itemEntity1.setQuantity(100);

        ItemEntity itemEntity2 = new ItemEntity();
        itemEntity2.setName("Mango");
        itemEntity2.setPrice(82.20);
        itemEntity2.setQuantity(10);

        ItemEntity itemEntity3 = new ItemEntity();
        itemEntity3.setName("Orange");
        itemEntity3.setPrice(45.00);
        itemEntity3.setQuantity(8);

        UserEntity userEntity1 = new UserEntity("Kalid", "kalid@gmail.com");
        UserEntity userEntity2 = new UserEntity("John", "john@gmail.com");

        userRepository.save(userEntity1);
        userRepository.save(userEntity2);

        itemRepository.save(itemEntity1);
        itemRepository.save(itemEntity2);
        itemRepository.save(itemEntity3);
    }
}
