package com.product.information.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.product.information.entity.OutboxProductEventEntity;


@Repository
public interface OutboxRepository extends JpaRepository<OutboxProductEventEntity, Long>{

}
