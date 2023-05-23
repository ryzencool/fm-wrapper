package com.faimio.fmwrapper.service;

import com.faimio.api.domain.generate.Tables;
import com.faimio.api.domain.generate.tables.pojos.Member;
import com.faimio.fmwrapper.common.BaseResponse;
import org.jooq.DSLContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AuthService {
    private final DSLContext dslContext;

    public AuthService(DSLContext dslContext) {
        this.dslContext = dslContext;
    }


    @Transactional
    public BaseResponse login(String username, String password) {
        var member = dslContext.select().from(Tables.MEMBER).where(Tables.MEMBER.USERNAME.eq(username)).fetchOneInto(Member.class);

        if (member == null) {
            return BaseResponse.fail("000001", "用户不存在");
        } else {
            if (!member.getPassword().equals(password)) {
                return BaseResponse.fail("000002", "密码错误");
            } else {
                return BaseResponse.success();
            }
        }
    }

    @Transactional
    public BaseResponse<Object> addUser(String username, String password) {
        dslContext.insertInto(Tables.MEMBER, Tables.MEMBER.USERNAME, Tables.MEMBER.PASSWORD)
                .values(username, password).execute();
        return BaseResponse.success();
    }

    public BaseResponse<List<Member>> listMember() {
        var members = dslContext.selectFrom(Tables.MEMBER).fetchInto(Member.class);

        members.forEach(it -> {
            it.setPassword("");
        });

        return BaseResponse.success(members);
    }

    public BaseResponse<Object> deleteMember(Integer memberId) {
        dslContext.deleteFrom(Tables.MEMBER).where(Tables.MEMBER.ID.eq(memberId)).execute();
        return BaseResponse.success();
    }

    public BaseResponse<Object> updateUser(Integer id, String password) {
        dslContext.update(Tables.MEMBER).set(Tables.MEMBER.PASSWORD, password).where(Tables.MEMBER.ID.eq(id)).execute();
        return BaseResponse.success();
    }
}
