package com.maestro.app.sample1.ms.messages.repositories;

import com.maestro.app.sample1.ms.messages.entities.UserMessages;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Date;

public interface UserMessagesRepository extends JpaRepository<UserMessages, String> {
    @Query(value = "SELECT v FROM UserMessages v WHERE v.idUser = :idUser ORDER BY v.dateRec DESC")
    Page<UserMessages> getListMessages(Pageable pageable, @Param("idUser") String idUser);

    @Query(value = "SELECT count(v) FROM UserMessages v WHERE v.idUser = :idUser")
    long countMessages(@Param("idUser") String idUser);

    @Modifying
    @Query(value = "UPDATE UserMessages v" +
                        " SET v.title = :title, " +
                                "v.message = :message," +
                                "v.state = :state," +
                                "v.dateRec = :dt," +
                                "v.idDownload = :idDownload," +
                                "v.fileName = :fname," +
                                "v.executionTime = :time" +
                        " WHERE v.code = :code")
    int updateMessage(@Param("code") String code,
                       @Param("title") String title,
                       @Param("message") String message,
                       @Param("state") Integer state,
                       @Param("idDownload") String idDownload,
                       @Param("fname") String fname,
                       @Param("time") Double execTime,
                       @Param("dt") Date dt);

    @Modifying
    @Query(value = "DELETE FROM UserMessages v WHERE v.code = :code")
    void deleteMessage(@Param("code") String code);

    @Modifying
    @Query(value = "DELETE FROM UserMessages v WHERE v.idUser = :idUser")
    void deleteAllMessages(@Param("idUser") String idUser);
}
