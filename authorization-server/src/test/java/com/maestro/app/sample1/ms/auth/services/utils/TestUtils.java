package com.maestro.app.sample1.ms.auth.services.utils;

import com.maestro.app.utils.auth.AuthJwtOrganization;
import com.maestro.app.utils.auth.AuthUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.jdbc.core.JdbcTemplate;

public class TestUtils {
    public static String SQL_ROLE_CREATE = "insert into user_roles(id_user, id_role, oper_date) values (?, ?, ?)";
    public static String SQL_USER_CREATE = "insert into users(id, username, full_name, email, enabled, id_org, id_org_main, psw) values (?, ?, ?, ?, ?, ?, ?, ?)";
    public static String SQL_USER_DELETE = "delete from users where id = ?";

    public static String PRIME_IDORG = "c7ff810990e14ff4a739382945722385";

    public static AuthUser queryAuthUser(JdbcTemplate jdbcTemplate, String id) {
        TUser user = jdbcTemplate.queryForObject("SELECT * FROM users WHERE ID = ?", new Object[]{ id }, (rs, rowNum) ->
                new TUser(
                        rs.getString("id"),
                        rs.getString("username"),
                        rs.getString("id_org"),
                        rs.getString("psw")
                ));
        TRole rls = jdbcTemplate.queryForObject("SELECT min(ID_ROLE) as ID_ROLE FROM user_roles WHERE ID_USER = ?", new Object[]{ id }, (rs, rowNum) ->
                new TRole(rs.getLong("id_role")));
        TOrg org = jdbcTemplate.queryForObject("SELECT * FROM orgs WHERE ID = ?", new Object[]{ user.getIdOrg() }, (rs, rowNum) ->
                new TOrg(
                        rs.getString("name"),
                        rs.getInt("org_level")
                ));

        System.out.println(user);

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
        private String psw;

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("\n TstUSer [");
            sb.append("\n\t IdUser: " + iduser);
            sb.append("\n\t UserName: " + userName);
            sb.append("\n\t IdOrg: " + idOrg);
            sb.append("\n\t Psw: " + psw);
            sb.append("\n ]");

            return sb.toString();
        }
    }

    @Data
    @AllArgsConstructor
    public static class TOrg {
        private String orgName;
        private int orgLevel;
    }

    @Data
    @AllArgsConstructor
    public static class TRole {
        private long idrole;
    }
}
