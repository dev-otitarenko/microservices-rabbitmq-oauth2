package com.maestro.app.sample1.ms.events.utils;

import com.maestro.app.utils.auth.AuthJwtOrganization;
import com.maestro.app.utils.auth.AuthUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.jdbc.core.JdbcTemplate;

public class TestUtils {
    public static String SQL_ROLE_CREATE = "insert into tbl_user_roles(id_user, id_role, oper_date) values (?, ?, ?)";
    public static String SQL_USER_CREATE = "insert into tnl_users(id, username, full_name, email, enabled, id_org, id_org_main) values (?, ?, ?, ?, ?, ?, ?)";
    public static String SQL_USER_DELETE = "delete from tbl_users where id = ?";

    public static String PRIME_IDORG = "c7ff810990e14ff4a739382945722385";

    public static AuthUser queryAuthUser(JdbcTemplate jdbcTemplate, String id) {
        TUser user = jdbcTemplate.queryForObject("SELECT * FROM tbl_users WHERE ID = ?", new Object[]{ id }, (rs, rowNum) ->
                new TUser(
                        rs.getString("id"),
                        rs.getString("username"),
                        rs.getString("id_org")
                ));
       TRole rls = jdbcTemplate.queryForObject("SELECT min(ID_ROLE) as ID_ROLE FROM tbl_user_roles WHERE ID_USER = ?", new Object[]{ id }, (rs, rowNum) ->
                new TRole(rs.getLong("id_role")));
        TOrg org = jdbcTemplate.queryForObject("SELECT * FROM tbl_orgs WHERE ID = ?", new Object[]{ user.getIdOrg() }, (rs, rowNum) ->
                new TOrg(
                        rs.getString("name")
                ));
        AuthUser ret = new AuthUser();
        ret.setId(user.getIduser());
        ret.setUsername(user.getUserName());
        ret.setTypeAdmin((int)rls.getIdrole());
        AuthJwtOrganization retOrg = new AuthJwtOrganization();
        retOrg.setId(user.getIdOrg());
        retOrg.setName(org.getOrgName());
        ret.setOrganization(retOrg);

        return ret;
    }

    @Data
    @AllArgsConstructor
    public static class TUser {
        private String iduser;
        private String userName;
        private String idOrg;
    }

    @Data
    @AllArgsConstructor
    public static class TOrg {
        private String orgName;
    }

    @Data
    @AllArgsConstructor
    public static class TRole {
        private long idrole;
    }
}
