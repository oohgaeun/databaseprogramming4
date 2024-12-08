package com.dbp.tpj.service;

import com.dbp.tpj.domain.Student;
import com.dbp.tpj.domain.Item;
import com.dbp.tpj.repository.ItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    //@Transactional이 붙으면, 데이터베이스의 일관성을 유지하기 위해 모든 작업이 트랜잭션으로 처리

    // 특정 사용자가 등록한 물품 조회
    @Transactional(readOnly = true)
    public List<Item> getItemsByUserId(String userId) {
        return itemRepository.findByUserId(userId);
    }

    //특정 사용자가 등록한 물품 중복회피 조회
    public List<Map<String, Object>> getGroupedItemsByUserId(String userId) {
        List<Object[]> groupedItems = itemRepository.findGroupedItemsByUserId(userId);
        // Stream API를 활용
        return groupedItems.stream()
                .map(row -> Map.of(
                        "itemName", row[0],
                        "category", row[1],
                        "count", row[2]
                ))
                .collect(Collectors.toList());
    }

    // 특정 사용자의 물품 수량 계산
    public Map<String, Long> getItemCounts(String userId) {
        // 쿼리 결과: Object[] 형태의 itemName과 count 값
        List<Object[]> itemCounts = itemRepository.findItemCountsByUserId(userId);

        itemCounts.forEach(row -> {
            System.out.println("Item Name: " + row[0] + ", Count: " + row[1]);
        });

        // itemName을 키로, count를 값으로 Map으로 변환
        return itemCounts.stream()
                .collect(Collectors.toMap(
                        obj -> (String) obj[0],  // itemName
                        obj -> (Long) obj[1]    // count
                ));
    }

    // 물품 등록
    @Transactional
    public void registerItem(Item item, int quantity) {
        // 입력된 수량만큼 물품 저장
        for (int i = 0; i < quantity; i++) {
            Item newItem = new Item();
            newItem.setItemName(item.getItemName());
            newItem.setCategory(item.getCategory());
            newItem.setRentalState("대여가능"); // 대여 상태 설정
            newItem.setStudent(item.getStudent());
            itemRepository.save(newItem); // 저장
        }
    }

    // 물품 삭제
    @Transactional
    public void deleteItem(Long itemId) {
        itemRepository.deleteById(itemId);
    }

    @Transactional
    public List<String> updateAndDeleteItems(String userId, List<Map<String, Object>> updates, List<String> deletions) {
        List<String> errors = new ArrayList<>();
        System.out.println("Updates received: " + updates);
        System.out.println("Deletions received: " + deletions);

        // 삭제 처리
        for (String itemName : deletions) {
            if (itemName == null) {
                errors.add("유효하지 않은 삭제 데이터: itemName=null");
                continue;
            }
            List<Item> items = itemRepository.findByItemNameAndUserId(itemName, userId);
            if (items.isEmpty()) {
                errors.add("Item with name " + itemName + " not found.");
                continue;
            }
            boolean hasRented = items.stream().anyMatch(item -> "대여중".equals(item.getRentalState()));
            if (hasRented) {
                errors.add(itemName + "은(는) 대여중이므로 삭제할 수 없습니다.");
            } else {
                itemRepository.deleteAll(items);
            }
        }

        // 수정 처리
        for (Map<String, Object> update : updates) {
            String itemName = (String) update.get("itemName");
            Integer newQuantity = (Integer) update.get("quantity");
            if (itemName == null || newQuantity == null) {
                errors.add("유효하지 않은 수정 데이터: itemName=" + itemName + ", quantity=" + newQuantity);
                continue;
            }
            List<Item> items = itemRepository.findByItemNameAndUserId(itemName, userId);

            int currentCount = items.size();
            long rentedCount = items.stream().filter(item -> "대여중".equals(item.getRentalState())).count();

            if (newQuantity < rentedCount) {
                if (rentedCount > 0) {
                    errors.add(itemName + "은(는) 대여중인 항목(" + rentedCount + "개)이 있어 수정할 수 없습니다.");
                }
            } else if (newQuantity > currentCount) {
                if (items == null || items.isEmpty()) {
                    throw new IllegalArgumentException(itemName + "에 대한 기존 데이터가 없습니다.");
                }
                // 추가 로직
                for (int i = 0; i < newQuantity - currentCount; i++) {
                    Item newItem = new Item();
                    newItem.setItemName(itemName);
                    newItem.setCategory(items.get(0).getCategory());
                    newItem.setRentalState("대여가능");
                    newItem.setStudent(items.get(0).getStudent());
                    itemRepository.save(newItem);
                }
            } else if (newQuantity < currentCount) {
                // 감소 로직
                int toRemove = currentCount - newQuantity;
                for (Item item : items) {
                    if (toRemove == 0) break;
                    if ("대여가능".equals(item.getRentalState())) {
                        itemRepository.delete(item);
                        toRemove--;
                    }
                }
                if (toRemove > 0) {
                    errors.add(itemName + "은(는) 대여중인 항목(" + rentedCount + "개)이 있어 수정할 수 없습니다.");
                }
            }
        }

        return errors;
    }


    // 특정 물품 조회
    @Transactional(readOnly = true)
    public Optional<Item> getItemById(Long itemId) {
        return itemRepository.findById(itemId);
    }

    @Transactional
    public boolean updateItemQuantity(String userId, String itemName, String category, int newQuantity) {
        // 물품 조회: 사용자 ID, 물품명, 카테고리 기준으로 조회
        List<Item> items = itemRepository.findByUserIdAndItemNameAndCategory(userId, itemName, category);

        // 대여중인 상태의 아이템 수 확인
        long rentedItemsCount = items.stream()
                .filter(item -> item.getRentalState().equals("대여중"))
                .count();

        // 전체 아이템 수 확인
        int totalItems = items.size();
        int availableItems = totalItems - (int) rentedItemsCount;

        // 감소: newQuantity < totalItems
        if (newQuantity < totalItems) {
            int itemsToRemove = totalItems - newQuantity;

            // 대여가능 상태 아이템만 삭제
            for (Item item : items) {
                if (itemsToRemove == 0) break;
                if (item.getRentalState().equals("대여가능")) {
                    itemRepository.delete(item);
                    itemsToRemove--;
                }
            }

            // 감소 가능한 수량보다 줄일 수 없으면 false 반환
            if (itemsToRemove > 0) {
                return false;
            }
        }
        // 증가: newQuantity > totalItems
        else if (newQuantity > totalItems) {
            int itemsToAdd = newQuantity - totalItems;

            for (int i = 0; i < itemsToAdd; i++) {
                Item newItem = new Item();
                newItem.setItemName(itemName);
                newItem.setCategory(category);
                newItem.setRentalState("대여가능");
                newItem.setStudent(new Student(userId)); // 등록자 설정
                itemRepository.save(newItem);
            }
        }

        return true;
    }
}