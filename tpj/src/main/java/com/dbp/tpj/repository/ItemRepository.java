package com.dbp.tpj.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.dbp.tpj.domain.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, String> {
}