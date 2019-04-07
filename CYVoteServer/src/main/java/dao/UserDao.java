package dao;


import dao.domain.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserDao {

    String TABLE_NAME = "user";
    String INSERT_FIELDS = "username, password";
    String SELECT_FIELDS = "id, " + INSERT_FIELDS;

    @Select("SELECT `user`.`id`,\n" +
            "        `user`.`username`,\n" +
            "        `user`.`password`\n" +
            "        FROM `user`")
    List<User> queryUser();
    @Select("SELECT `user`.`id`,\n" +
            "        `user`.`username`,\n" +
            "        `user`.`password`\n" +
            "        FROM `user`\n" +
            "        WHERE `user`.`id` = #{id}")
    User queryUserById(@Param("id") int id);
    @Insert("    INSERT INTO `user`\n" +
            "        (`username`,\n" +
            "        `password`)\n" +
            "        VALUES\n" +
            "        (#{user.username},\n" +
            "        #{user.password});")
    int insertUser(@Param("user") User user);
    @Update(" UPDATE `user` set username=#{user.username}, password=#{user.password} WHERE `id` = #{user.id} ")
    int updateUser(@Param("user") User user);
    @Delete(" DELETE FROM `user`\n" +
            "        WHERE id = #{id}")
    int deleteUser(@Param("id") int id);
    @Select("SELECT `user`.`id`,\n" +
            "        `user`.`username`,\n" +
            "        `user`.`password`\n" +
            "        FROM `user`\n" +
            "        WHERE `user`.`username` = #{user.username}\n" +
            "        AND `user`.`password` = #{user.password};")
    User login(@Param("user") User user);

}
