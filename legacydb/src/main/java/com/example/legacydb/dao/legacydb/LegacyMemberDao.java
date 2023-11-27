package com.example.legacydb.dao.legacydb;

import com.example.legacydb.model.Member;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LegacyMemberDao {
    List<Member> getMemberList();
}
