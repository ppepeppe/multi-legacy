package com.example.legacydb.dao;

import com.example.legacydb.model.Member;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberDao {
    List<Member> getMemberList();
}
