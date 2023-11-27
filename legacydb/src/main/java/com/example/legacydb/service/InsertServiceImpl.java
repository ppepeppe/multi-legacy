package com.example.legacydb.service;

import com.example.legacydb.dao.mariadb.MemberDao;
import com.example.legacydb.dao.legacydb.LegacyMemberDao;
import com.example.legacydb.model.Member;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class InsertServiceImpl implements InsertService{
    private final LegacyMemberDao legacyMemberDao;
    private final MemberDao memberDao;
    @Override
    public void postgresInsertMember() {
        List<Member> memberList = legacyMemberDao.getMemberList();
        System.out.println(memberList);
        String[] columnList = {"name", "number", "grade", "auth"};

        for (Member member : memberList) {
            Map<String, Object> insertDBData = new HashMap<>();

            for (String column : columnList) {
                try {
                    // Member 클래스에서 해당 이름의 필드(Field) 찾기
                    Field field = member.getClass().getDeclaredField(column);
                    // 필드에 접근하기 위해 private 필드의 접근을 허용
                    field.setAccessible(true);
                    // 필드의 값을 가져오기
                    Object value = field.get(member);

                    if (value == null) {
                        insertDBData.put(column, "0");
                    } else {
                        insertDBData.put(column, value);
                    }
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    System.out.println(column + " 속성을 찾거나 가져올 수 없습니다.");
                }
            }

            memberDao.insertMember((String) insertDBData.get("name"), (int) Double.parseDouble(insertDBData.get("number").toString()), (String) insertDBData.get("grade"), (int) Double.parseDouble(insertDBData.get("auth").toString()));
        }
    }


    @Override
    public void insertMember(Sheet sheet) {
        int rowCount = sheet.getPhysicalNumberOfRows();
        List<String> fileColumns = new ArrayList<>();
        int memberNum = -1;

        Map<String, Map<String, String>> insertMemberData = new HashMap<>();

        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            memberNum++;
            int cellCount = row.getPhysicalNumberOfCells();
            Map<String, String> memberData = new HashMap<>();
            for (int cellIndex = 0; cellIndex < cellCount; cellIndex++) {
                Cell cell = row.getCell(cellIndex);
                if (rowIndex == 0) {
                    fileColumns.add(cell.toString());
                } else {
                    memberData.put(fileColumns.get(cellIndex), cell.toString());
                }
            }
            if (rowIndex != 0) {
                insertMemberData.put("member" + memberNum, memberData);
            }

        }
        String[] columnList = {"name", "number", "grade", "auth"};

        for (Map.Entry<String, Map<String, String>> entry : insertMemberData.entrySet()) {
            Map<String, Object> insertDBData = new HashMap<>();
            Map<String, String> data = entry.getValue();
            System.out.println(data);

            for (String column : columnList) {
                if (!data.get(column).equals("")) {
                    insertDBData.put(column, data.get(column));
                }
            }

            memberDao.insertMember((String) insertDBData.get("name"), (int) Double.parseDouble(insertDBData.get("number").toString()), (String) insertDBData.get("grade"), (int) Double.parseDouble(insertDBData.get("auth").toString()));
        }
    }
}
