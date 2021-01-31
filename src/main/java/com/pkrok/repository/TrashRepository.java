package com.pkrok.repository;

import com.pkrok.entity.TrashEntity;
import com.pkrok.entity.TypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public interface TrashRepository extends JpaRepository<TrashEntity, Long> {





    List<TrashEntity> findAllByOrderById();

    List<TrashEntity> findByUserEquals(String user);
}
