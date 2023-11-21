package com.example.legacydb.controller;

import com.example.legacydb.dao.MemberDao;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/insert")
@RequiredArgsConstructor
public class DatabaseController {
    private final MemberDao memberDao;

    @PostMapping("/database")
    public ResponseEntity<?> Database() {
        try {
            System.out.println(memberDao.getMemberList());

            return new ResponseEntity<> ("success", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();

            return new ResponseEntity<> ("failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}