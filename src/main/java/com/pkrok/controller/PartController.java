package com.pkrok.controller;

import com.pkrok.domain.ErrorDTO;
import com.pkrok.domain.PartsDTO;
import com.pkrok.service.FileStorageService;
import com.pkrok.service.PartService;
import com.pkrok.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("parts")
public class PartController {

    private FileStorageService fileStorageService;
    private PartService partService;

    @Autowired
    public PartController(PartService partService, FileStorageService fileStorageService) {
        this.partService = partService;
        this.fileStorageService = fileStorageService;
    }

    @PostMapping
    public ResponseEntity<?> addPart(@Valid @RequestBody PartsDTO part, BindingResult br) {
        if (br.hasErrors()) {
            System.out.println("Validation error");
            String errMsg = br.getFieldErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .findFirst().get().toString();
            ErrorDTO errorDTO = new ErrorDTO(errMsg);
            return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
        }
        part.setLastChanges(LocalDateTime.now());
        partService.addPart(part);
        return new ResponseEntity<Void>(HttpStatus.CREATED); //201
    }

    @PostMapping("quantity/{id}/{plus}/{minus}/{userChange}")
    public ResponseEntity<Void> editQuantityById(@PathVariable("id") Long id, @PathVariable("plus") int plus, @PathVariable("minus") int minus, @PathVariable("userChange") String userChange) {
        partService.setQuantityById(id, plus, minus, userChange);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<PartsDTO>> getParts() {
        return ResponseEntity.ok(partService.findAllParts());
    }


    @GetMapping("{firmName}/{typeName}")
    public ResponseEntity<List<PartsDTO>> getPartsByFirmAndType(@PathVariable("firmName") String firmName, @PathVariable("typeName") String typeName) {
        return ResponseEntity.ok(partService.findByFirmAndType(firmName, typeName));
    }

    @GetMapping({"nameContains"})
    public ResponseEntity<List<PartsDTO>> getPartsContainsName(@PathVariable("nameContains") String name) {
        return ResponseEntity.ok(partService.findByNameContains(name));
    }


    @GetMapping("Name")
    public ResponseEntity<List<PartsDTO>> getPartsOrderByName() {
        return ResponseEntity.ok(partService.findAllPartsOrderByName());
    }

    @GetMapping("Quantity")
    public ResponseEntity<List<PartsDTO>> getPartsOrderByQuantity() {
        return ResponseEntity.ok(partService.findAllPartsOrderByQuantity());
    }

    @GetMapping("{firmName}/{typeName}/Name")
    public ResponseEntity<List<PartsDTO>> getPartsByFirmAndTypeOrdNameLike(@PathVariable("firmName") String firmName, @PathVariable("typeName") String typeName, @RequestParam(required = false, defaultValue = "") String search) {
        String s = "%" + search + "%";
        return ResponseEntity.ok(partService.findByFirmAndTypeOrdNameLike(firmName, typeName, s));
    }

    @GetMapping("{firmName}/{typeName}/Quantity")
    public ResponseEntity<List<PartsDTO>> getPartsByFirmAndTypeOrdQuantityLike(@PathVariable("firmName") String firmName, @PathVariable("typeName") String typeName, @RequestParam(required = false, defaultValue = "") String search) {
        String s = "%" + search + "%";
        return ResponseEntity.ok(partService.findByFirmAndTypeOrdQuantityLike(firmName, typeName, s));
    }

    @GetMapping("{partId}")
    public ResponseEntity<PartsDTO> getPartById(@PathVariable("partId") Long id) {
        return ResponseEntity.ok(partService.findPartById(id));
    }


    @DeleteMapping("{partId}")
    public ResponseEntity<?> deletePartById(@PathVariable("partId") Long id) {
        partService.deletePartById(id);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @PostMapping("edit")
    public ResponseEntity<?> editPart(@Valid @ModelAttribute PartsDTO part, BindingResult br) {
        if (br.hasErrors()) {
            System.out.println("Validation error");
            String errMsg = br.getFieldErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .findFirst().get();
            ErrorDTO errorDTO = new ErrorDTO(errMsg);
            return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
        }
        System.out.println(part);
        partService.setPartById(part);
        if (FileUtil.isNotEmpty(part.getFile())) {
            fileStorageService.storeFile(part.getFile());
            partService.addImageToProduct(part.getFile().getOriginalFilename(), part.getId());
        }
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @GetMapping("image")
    public ResponseEntity<?> getFile(
            @RequestParam("fileName") String fileName,
            HttpServletRequest request) {
        Resource resource;
        if (fileName.equals("null")) {
            resource = fileStorageService.loadFile("empty.png");
        } else {
            resource = fileStorageService.loadFile(fileName);
        }
        String contentType = null;

        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline: filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

}
