package com.dbp.tpj.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.dbp.tpj.domain.RentalWait;

@Repository
public interface RentalWaitRepository extends JpaRepository<RentalWait, Integer> {
}