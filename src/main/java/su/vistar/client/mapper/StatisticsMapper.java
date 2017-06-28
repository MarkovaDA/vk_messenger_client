package su.vistar.client.mapper;

import java.util.Date;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

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
    
}
