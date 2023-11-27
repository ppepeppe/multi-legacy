package com.example.legacydb.dao.mariadb;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberDao {
    void insertMember(String name, int number, String grade, int auth);
}
