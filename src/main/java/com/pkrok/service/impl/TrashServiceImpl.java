package com.pkrok.service.impl;

import com.pkrok.domain.PartsDTO;
import com.pkrok.domain.TrashDTO;
import com.pkrok.domain.TypeDTO;
import com.pkrok.entity.PartEntity;
import com.pkrok.entity.TrashEntity;
import com.pkrok.entity.TypeEntity;
import com.pkrok.exceptions.ResourceNotFoundException;
import com.pkrok.repository.TrashRepository;
import com.pkrok.repository.TypeRepository;
import com.pkrok.service.TrashService;
import com.pkrok.utils.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TrashServiceImpl implements TrashService {
    private TrashRepository trashRepository;
    private ObjectMapperUtils modelMapper;

    @Autowired
    public TrashServiceImpl(TrashRepository trashRepository, ObjectMapperUtils modelMapper) {
        this.trashRepository = trashRepository;
        this.modelMapper = modelMapper;
    }

    @PersistenceContext
    private EntityManager entityManager;
    @Transactional
    @Override
    public void insertWithQuery(String tuser, String dayMakeOrder, String adress) {
        entityManager.createNativeQuery("update trash set adress = :adress where user=:tuser")
                .setParameter("adress", adress)
                .setParameter("tuser", tuser)
                .executeUpdate();
        entityManager.createNativeQuery("update trash set day_make_order = :dayMake where user=:tuser")
                .setParameter("dayMake", dayMakeOrder)
                .setParameter("tuser", tuser)
                .executeUpdate();
        entityManager.createNativeQuery("insert into orderr (name,price,qty,summ,user,day_recieve_order, day_make_order, adress, weight) select name, price, qty, summ, user, day_recieve_order, day_make_order, adress, weight from trash where user = :tuser")
                .setParameter("tuser", tuser)
                .executeUpdate();
        entityManager.createNativeQuery("delete from trash where user=:tuser")
                .setParameter("tuser",tuser)
                .executeUpdate();
    }

    @Override
    public void addTrash(TrashDTO trashDTO) {
        TrashEntity trash = modelMapper.map(trashDTO, TrashEntity.class);
        trashRepository.save(trash);
    }

    @Override
    public void deleteTrashById(Long id) {
        TrashEntity trashEntity = trashRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Could not delete type with id[" + id + "]not found"));
        trashRepository.deleteById(id);
    }

    @Override
    public List<TrashDTO> findAllOrderById() {
        List<TrashEntity> trashEntities = trashRepository.findAllByOrderById();
        return modelMapper.mapAll(trashEntities, TrashDTO.class);
    }

    @Override
    public List<TrashDTO> findByUserEquals(String user) {
        List<TrashEntity> trashEntities = trashRepository.findByUserEquals(user);
        return modelMapper.mapAll(trashEntities, TrashDTO.class);
    }

}
