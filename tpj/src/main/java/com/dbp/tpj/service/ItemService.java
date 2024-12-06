package com.dbp.tpj.service;

import com.dbp.tpj.domain.Student;
import com.dbp.tpj.domain.Item;
import com.dbp.tpj.repository.ItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    // 특정 사용자가 등록한 물품 조회
    @Transactional(readOnly = true)
    public List<Item> getItemsByUserId(String userId) {
        return itemRepository.findByUserId(userId);
    }

    // 물품 등록
    @Transactional
    public void registerItem(Item item, int quantity) {
        // 입력된 수량만큼 물품 저장
        for (int i = 0; i < quantity; i++) {
            Item newItem = new Item();
            newItem.setItemName(item.getItemName());
            newItem.setCategory(item.getCategory());
            newItem.setStudent(item.getStudent());
            itemRepository.save(newItem); // 저장
        }
    }

    // 물품 삭제
    @Transactional
    public void deleteItem(Long itemId) {
        itemRepository.deleteById(itemId);
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