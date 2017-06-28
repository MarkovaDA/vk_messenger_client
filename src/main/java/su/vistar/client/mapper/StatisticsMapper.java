package su.vistar.client.mapper;

import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.transaction.annotation.Transactional;
import su.vistar.client.dto.UserStatisticsDTO;

@Transactional("mainManager")
public interface StatisticsMapper {    
    @Insert("INSERT INTO vk_messenger_v2.company_statistics (criteria_id,sender_vk_id,receiver_vk_id,status, device_date) "
            + "values (#{criteria_id}, #{sender_vk_id},#{receiver_vk_id}, #{status}, #{device_date})")
    Integer insertStatisticsInfo(
            @Param("criteria_id")Integer criterionId, 
            @Param("sender_vk_id")Long vkSenderId,
            @Param("receiver_vk_id")Long vkReceiverId,
            @Param("status")String status, 
            @Param("device_date")Date deviceDate);
    
    //сделать ограничение по критерию
    @Select("SELECT * from vk_messenger_v2.company_statistics")
    @Results({
        @Result(property = "criterionId", column = "criteria_id"),
        @Result(property = "receiverVkId", column = "receiver_vk_id"),
        @Result(property = "senderVkId", column = "sender_vk_id"),
        @Result(property = "errorMsg", column = "status"),
        @Result(property = "humanDate", column = "device_date")
    })
    List<UserStatisticsDTO> getCriteriaStatistics();
    
}
