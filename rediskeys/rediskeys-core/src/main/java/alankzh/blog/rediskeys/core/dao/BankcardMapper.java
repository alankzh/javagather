package alankzh.blog.rediskeys.core.dao;

import alankzh.blog.rediskeys.core.BankcardEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BankcardMapper extends BaseMapper<BankcardEntity> {


    @Select("select * from t_bankcard where customer_id=#{customerId}")
    List<BankcardEntity> findByCustomerId(@Param("customerId") String customerId);

    @Select("select * from t_bankcard where index_card_id=#{indexCardId}")
    BankcardEntity findByIndexCardId(@Param("indexCardId") String indexCardId);

    @Update("update t_bankcard " +
            "   set information=#{information}," +
            "       update_time=CURRENT_TIMESTAMP" +
            " where index_card_id=#{indexCardId}")
    int updateInformationByIndexCardId(@Param("information") String information, @Param("indexCardId") String indexCardId);

    @Update("update t_bankcard " +
            "   set information=#{information}," +
            "       update_time=CURRENT_TIMESTAMP" +
            " where customer_id=#{customerId}")
    int updateInformationByCustomerId(@Param("information") String information, @Param("customerId") String customerId);
}
