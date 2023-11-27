package com.example.legacydb.controller;

import com.example.legacydb.service.InsertService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/insert")
@RequiredArgsConstructor
public class InsertController {
    private final InsertService insertService;

    @PostMapping("/database")
    public ResponseEntity<?> PostDatabaseData() {
        System.out.println("DBtoDB");
        try {
            insertService.postgresInsertMember();

            return new ResponseEntity<> ("DBtoDB", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();

            return new ResponseEntity<>("failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/file")
    public ResponseEntity<?> FileData(@RequestPart(name = "file", required = false) MultipartFile file) throws IOException {
        try {
            Workbook workbook = new XSSFWorkbook(file.getInputStream());
            Sheet sheet = workbook.getSheetAt(0);
            insertService.insertMember(sheet);

            return new ResponseEntity<>("fileToDB", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();

            return new ResponseEntity<>("failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/ldap")
    public ResponseEntity<?> LdapData() {
        try {
            System.out.println("ldapToDB");

            return new ResponseEntity<>("ldapToDB", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();

            return new ResponseEntity<>("failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
