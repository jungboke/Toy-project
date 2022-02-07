package me.jungboke.baekshop.service.item;

import lombok.RequiredArgsConstructor;
import me.jungboke.baekshop.domain.item.Item;
import me.jungboke.baekshop.exception.NoItemException;
import me.jungboke.baekshop.repository.ItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {

    private final ItemRepository itemRepository;

    //물품저장
    @Transactional
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    //전체물품조회
    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    //단일물품조회
    public Item findOne(Long itemId) {
        Optional<Item> findItem = itemRepository.findById(itemId);
        if (!findItem.isPresent()) {
            throw new NoItemException("물품이 존재하지 않습니다.");
        }
        return findItem.get();
    }

    @Transactional
    public void updateItem(Long itemid, String name, int price, int stockQuantity) {
        Optional<Item> findItem = itemRepository.findById(itemid);
        findItem.get().setName(name);
        findItem.get().setPrice(price);
        findItem.get().setStockQuantity(stockQuantity);
    }
}
