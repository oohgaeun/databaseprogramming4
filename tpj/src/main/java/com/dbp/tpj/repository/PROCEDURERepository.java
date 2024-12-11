package com.dbp.tpj.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import org.springframework.stereotype.Repository;

@Repository
public class PROCEDURERepository implements RentalRepositoryCustom {

        @PersistenceContext
        private EntityManager entityManager;

        @Override
        public void executeRentalProcedure(Long postId, String signal) {
            System.out.println("Post ID: " + postId);
            System.out.println("Signal: " + signal);
            StoredProcedureQuery query = entityManager.createStoredProcedureQuery("Rental_Return_Date");
            query.registerStoredProcedureParameter("p_post_id", Long.class, jakarta.persistence.ParameterMode.IN);
            query.registerStoredProcedureParameter("p_signal", String.class, jakarta.persistence.ParameterMode.IN);

            query.setParameter("p_post_id", postId);
            query.setParameter("p_signal", signal);

            query.execute();
        }
        @Override
        public void executeRentalStatistics(String itemId, String lenderId, String borrowerId, String signal) {
            StoredProcedureQuery query = entityManager.createStoredProcedureQuery("Rental_Statistics");
            query.registerStoredProcedureParameter("p_item_id", String.class, jakarta.persistence.ParameterMode.IN);
            query.registerStoredProcedureParameter("p_lender_id", String.class, jakarta.persistence.ParameterMode.IN);
            query.registerStoredProcedureParameter("p_borrower_id", String.class, jakarta.persistence.ParameterMode.IN);
            query.registerStoredProcedureParameter("p_signal", String.class, jakarta.persistence.ParameterMode.IN);

            query.setParameter("p_item_id", itemId);
            query.setParameter("p_lender_id", lenderId);
            query.setParameter("p_borrower_id", borrowerId);
            query.setParameter("p_signal", signal);

            query.execute();
        }

        @Override
        public void executeDecreaseCreditOverdue() {
            StoredProcedureQuery query = entityManager.createStoredProcedureQuery("Decrease_Credit_Overdue");
            query.execute();
        }
}
