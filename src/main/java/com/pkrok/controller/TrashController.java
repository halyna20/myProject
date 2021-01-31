package com.pkrok.controller;

import com.pkrok.domain.ErrorDTO;
import com.pkrok.domain.PartsDTO;
import com.pkrok.domain.TrashDTO;
import com.pkrok.domain.TypeDTO;
import com.pkrok.service.TrashService;
import com.pkrok.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("trash")
public class TrashController {
    @Autowired
    private TrashService trashService;
    @PostMapping
    public ResponseEntity<?> addTrash(@Valid @RequestBody TrashDTO trash, BindingResult br) {
        if (br.hasErrors()) {
            System.out.println("Validation error");
            String errMsg = br.getFieldErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .findFirst().get().toString();
            ErrorDTO errorDTO = new ErrorDTO(errMsg);
            return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
        }
        trash.setDayRecieveOrder(LocalDateTime.now());
        trashService.addTrash(trash);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<TrashDTO>> getTrash() {
        return ResponseEntity.ok(trashService.findAllOrderById());
    }

    @GetMapping("u")
    public ResponseEntity<List<TrashDTO>> getEquals(@RequestParam String user) {
        return ResponseEntity.ok(trashService.findByUserEquals(user));
    }
    @DeleteMapping("{trashId}")
    public ResponseEntity<?> deleteTrashById(@PathVariable("trashId") Long id) {
        trashService.deleteTrashById(id);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @PostMapping("ins/{user}/{day}/{adress}")
    public ResponseEntity<Void> insert(@PathVariable("user") String user, @PathVariable ("day") String dayMakeOrder, @PathVariable ("adress") String adress) {
        trashService.insertWithQuery(user, dayMakeOrder, adress);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

}
