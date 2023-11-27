package com.example.legacydb.service;

import org.apache.poi.ss.usermodel.Sheet;

public interface InsertService {
    void postgresInsertMember();
    void insertMember(Sheet sheet);

}
