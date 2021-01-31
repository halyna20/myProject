package com.pkrok.service;

import com.pkrok.domain.TrashDTO;
import com.pkrok.domain.TypeDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface TrashService {
    void addTrash(TrashDTO trashDTO);

    void deleteTrashById(Long id);

    List<TrashDTO> findAllOrderById();

    List<TrashDTO> findByUserEquals(String user);

    void insertWithQuery(String tuser, String dayMakeOrder, String adress);
}
