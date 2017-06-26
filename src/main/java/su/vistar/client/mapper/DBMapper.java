package su.vistar.client.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Delete;
import su.vistar.client.model.User;
import su.vistar.client.model.Message;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.transaction.annotation.Transactional;
import su.vistar.client.dto.UserToCompanyDTO;
import su.vistar.client.dto.CriteriaDTO;
import su.vistar.client.dto.VKObjectDTO;
import su.vistar.client.model.Company;

@Transactional("mainManager")
public interface DBMapper {
    
    @Select("SELECT * from vk_messenger_v2.users WHERE login=#{login}")
    User findUserByLogin(@Param("login")String login);
    
    @Select("SELECT * from vk_messenger_v2.company WHERE user_id=#{user_id}")
    List<Company> findCompany(@Param("user_id")Integer userId);
    
    @Insert("INSERT into vk_messenger_v2.company (title,code,user_id) values(#{title},#{code},#{user_id})")
    Integer addCompany(Company company);
    
    @Update("UPDATE vk_messenger_v2.company set code=#{code} where title=#{title} and user_id=#{user_id}")
    Integer updateCompanyCodeByTitle(Company company);
    
    @Select("SELECT type FROM vk_messenger_v2.users left join vk_messenger_v2.user_profile " +
    "ON vk_messenger_v2.user_profile.user_id = vk_messenger_v2.users.id " +
    "LEFT join vk_messenger_v2.profile " +
    "ON vk_messenger_v2.profile.id = vk_messenger_v2.user_profile.profile_id where login=#{login}")
    String roleByLogin(@Param("login")String login);
    
    @Update("UPDATE vk_messenger_v2.users SET access_token=#{access_token},last_date=#{last_date} WHERE login=#{login} and password=#{password}")
    void updateAccessToken(User user);
    
    @Insert("INSERT into vk_messenger_v2.criteria (vk_messenger_v2.criteria.condition, vk_messenger_v2.criteria.offset, company_id, title) values (#{condition},#{offset},#{company_id},#{title})")
    void saveCriteria(@Param("condition")String condition,
            @Param("title")String title,
            @Param("offset")int offset, 
            @Param("company_id")int companyId);
    
    
    @Insert("INSERT into vk_messenger_v2.messages (text) values (#{text})")
    void saveMessage(@Param("text")String message);
    
    @Insert("INSERT into vk_messenger_v2.criteria_message (criteria_id, message_id) values (#{criteria_id}, #{message_id})")
    void saveMessageCriteria(@Param("criteria_id")Integer criteriaId, @Param("message_id")Integer messageId);
    
    @Select("SELECT id, text FROM vk_messenger_v2.messages")
    List<Message> getAllMessage();
    
    @Select("SELECT id,text FROM vk_messenger_v2.messages WHERE id=#{mesId}")
    Message getMessageById(@Param("mesId")Integer mesId);
      
    @Select("SELECT LAST_INSERT_ID()")
    Integer lastInsertedId();
    //сделать отдельные методы для получения последнего id у критерия и у сообщения
    
    @Select("SELECT max(id) from vk_messenger_v2.messages")
    Integer lastMessageId();
    
    @Select("SELECT max(id) from vk_messenger_v2.criteria")
    Integer lastCriteriaId();
    
    @Select("SELECT * from vk_messenger_v2.company where code=#{code}")
    Company getCompanyByCode(@Param("code")Long code);
    
    @Insert("INSERT into vk_messenger_v2.sendors(vk_uid, company_id, message_count) values (#{vk_uid},#{company_id},#{message_count})")
    Integer subscribe(@Param("vk_uid")Long vkUid, @Param("company_id")Integer companyId,  @Param("message_count")Integer message_count);   
 
    @Delete("DELETE from vk_messenger_v2.sendors where vk_uid=#{vk_uid} and company_id=#{company_id}")
    Integer unscribeFromCompany(@Param("vk_uid")Long vkUid, @Param("company_id")int companyId);
    
    @Delete("DELETE from vk_messenger_v2.sendors where vk_uid=#{vk_uid}")
    Integer unscribeFromAll(@Param("vk_uid")Long vkUid);
    
    @Select("SELECT id from vk_messenger_v2.sendors where vk_uid=#{vk_uid} and company_id=#{company_id}")
    Object tryUnigueSubscribe(@Param("vk_uid")Long vkUid, @Param("company_id")int companyId);

    @Select("SELECT distinct title, code, fio, message_count FROM vk_messenger_v2.company join vk_messenger_v2.sendors ON "
            + "vk_messenger_v2.company.id = vk_messenger_v2.sendors.company_id "
            + "left join vk_messenger_v2.users on vk_messenger_v2.company.user_id = vk_messenger_v2.users.id "
            + "where vk_uid=#{vk_uid}")
    List<UserToCompanyDTO> getCompanies(@Param("vk_uid")String vkUid);
    
    @Select("SELECT company.id, code, title, fio FROM vk_messenger_v2.company join " +
            "vk_messenger_v2.users on users.id=company.user_id where code=#{code}")
    UserToCompanyDTO getCompanyInfo(Long code);
    
    @Select("SELECT * from vk_messenger_v2.criteria where company_id=#{company_id} and considered=0")
    List<CriteriaDTO> getCriteriesByCompanyId(@Param("company_id")Integer companyId);
    
    @Select("SELECT * FROM vk_messenger_v2.messages join vk_messenger_v2.criteria_message on id=message_id "
            + "where criteria_id=#{criteria_id}")
    Message getMessageByCriteriaId(@Param("criteria_id")Integer criteriaId);
    
    @Update("UPDATE vk_messenger_v2.criteria set offset=#{offset} where id=#{criteria_id}")
    void updateOffset(@Param("criteria_id")Integer criteriaId, @Param("offset")Integer offset);
    /*не учла здесь компании, кроме текущей*/
    @Select("SELECT sum(message_count) from vk_messenger_v2.sendors where vk_uid=#{vk_uid} group by vk_uid")
    Integer countOfSubscribesForUser(@Param("vk_uid")Long vkUid);//исправить на массив значений
    
    @Select("SELECT message_count from vk_messenger_v2.sendors where vk_uid=#{vk_uid} and company_id=#{company_id} limit 1")
    Integer countOfSubscribeForUser(@Param("vk_uid")Long vkUid, @Param("company_id")Integer companyId);
    
    @Select("SELECT message_count from vk_messenger_v2.sendors where vk_uid=#{vk_uid} and company_id=#{company_id}")
    Integer getCountMessagesByCompanyId(@Param("vk_uid")Long vkUid, @Param("company_id")Integer companyId);
    
    @Update("update vk_messenger_v2.sendors " +
            "set message_count = #{message_count} where vk_uid=#{vk_uid} " +
            "and company_id in " +
            "(select id from company where code = #{code})")
    Integer updateCompanyCode(@Param("vk_uid")Long vkUid, 
                              @Param("message_count")Integer message_count,
                              @Param("code")Long code);
    
    @Select("SELECT * from vk_messenger_v2.company where code=#{code}")
    Integer tryUniqueCode(@Param("code")Long code);
    
    @Select("SELECT * from vk_messenger_v2.company where title=#{title}")
    Integer tryUniqueTitle(@Param("title")String title);
    
    @Select("SELECT * from vk_messenger_v2.messages where text=#{text} limit 1")
    Message tryUniqueMessage(@Param("text")String text);
    
    @Select("select * from vk_messenger_v2.country")
    public List<VKObjectDTO> getCountries();
    
    @Select("SELECT * FROM vk_messenger_v2.messages join vk_messenger_v2.criteria_message " +
            "on id = message_id join criteria on criteria_message.criteria_id = criteria.id " +
            "where criteria.company_id = #{company_id}")
    public List<Message> getMessagesByCompanyId(@Param("company_id")Integer companyId);
                
    @Update("UPDATE vk_messenger_v2.messages set text=#{title} where id=#{id}")
    public void updateMessage(@Param("id")Integer messageId, @Param("title")String title);
    
    @Update("DELETE from vk_messenger_v2.messages where id=#{id}")
    public void deleteMessage(@Param("id")Integer messageId);
    
    
}
