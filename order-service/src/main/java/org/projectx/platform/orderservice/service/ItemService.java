package org.projectx.platform.orderservice.service;

import org.projectx.platform.orderservice.entity.ItemEntity;
import org.projectx.platform.orderservice.repository.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Optional<ItemEntity> getItem(Long id) {
        return itemRepository.findById(id);
    }
}
