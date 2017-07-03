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
import su.vistar.client.model.SenderStatisticsReport;

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
    //where criteria_id = 
  
    @Select("SELECT * from vk_messenger_v2.company_statistics where sender_vk_id=#{sender_vk_id}")
    @Results({
        @Result(property = "receiverVkId", column = "receiver_vk_id"),
        @Result(property = "errorMsg", column = "status"),
        @Result(property = "humanDate", column = "device_date"),
        @Result(property = "senderVkId", column = "sender_vk_id")
    })
    List<UserStatisticsDTO> getCriteriaStatisticsBySender(@Param("sender_vk_id")Long senderVkId);
    
    @Select("SELECT sender_vk_id, count(*) as count "
            + "FROM vk_messenger_v2.company_statistics group by sender_vk_id")
    @Results({
        @Result(property = "count", column = "count"),
        @Result(property = "senderVkId", column = "sender_vk_id"),
    })
    List<SenderStatisticsReport> getStatisticsBySendors();//добавить ограниение на принадлежности выбранному критерию
    //where criteria_id =
    
}
