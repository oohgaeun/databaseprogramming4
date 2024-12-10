package com.dbp.tpj.repository;

public interface RentalRepositoryCustom {
    void executeRentalProcedure(Long postId, String signal);
    void executeRentalStatistics(String itemId, String lenderId, String borrowerId, String signal);
    void executeDecreaseCreditOverdue();
}
