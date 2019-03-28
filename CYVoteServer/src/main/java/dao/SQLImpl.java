package dao;

import dao.domain.ChannelInfor;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SQLImpl {
    @Select("Select * from t_channelinfor")
    List<ChannelInfor> getChannelInfor();

    @Insert("Insert into t_channelinfor values(#{channel}, #{infor})")
    int setChannelInfor(@Param("channel") String channel, @Param("infor") String infor);
}
