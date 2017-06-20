package su.vistar.client.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import su.vistar.client.dto.UserToCompanyDTO;
import su.vistar.client.dto.CriteriaDTO;
import su.vistar.client.mapper.CriteriaMapper;
import su.vistar.client.mapper.DBMapper;
import su.vistar.client.model.AdresatCriteria;
import su.vistar.client.model.Company;
import su.vistar.client.model.Message;


@Service
public class DBCriteriaService {
    
    @Autowired
    DBMapper dbMapper;
    
    
    @Autowired
    AuthService authService;
    
    private void saveCriteria(String criteria,String title,int offset, int companyId){
        dbMapper.saveCriteria(criteria,title,offset,companyId);
    }
    
    public void saveCriteria(AdresatCriteria criteria, int companyId){
        saveCriteria(criteria.toString(), criteria.getCriteriaName(), 0, companyId); 
        int criteriaId = dbMapper.lastCriteriaId();
        int messageId; 
        Message suggestedMessage = dbMapper.tryUniqueMessage(criteria.getMessage());
        if (suggestedMessage != null)
            messageId = suggestedMessage.getId();//идентификатор существующего сообщения          
        else {
            dbMapper.saveMessage(criteria.getMessage());
            messageId = dbMapper.lastMessageId();//только вставленное сообщение
        }
        dbMapper.saveMessageCriteria(criteriaId, messageId);
    }
    
    public Integer addCompany(Company company){
       return dbMapper.addCompany(company);
    }    
    public Integer updateCompanyCode(Company company){
        return dbMapper.updateCompanyCodeByTitle(company);
    }
    public List<Message> getMessages(){
        return dbMapper.getAllMessage();
    }   
    public Message getMessageById(Integer mesId){
        return dbMapper.getMessageById(mesId);
    }   
    public int subscribe(Long vkUid, int companyId, int countCompany){
        return dbMapper.subscribe(vkUid, companyId, countCompany);
    }    
    public Company getCompanyByCode(Long code){
        return dbMapper.getCompanyByCode(code);
    }    
    public Object tryUnigueSubscribe(Long vkUid, int companyId){
        return dbMapper.tryUnigueSubscribe(vkUid, companyId);
    }    
    public int unscribe(Long vkUid, Integer companyId){
        if (companyId != null){           
            return dbMapper.unscribeFromCompany(vkUid, companyId);
        }
        else return dbMapper.unscribeFromAll(vkUid);
    }    
    public List<UserToCompanyDTO> getCompanies(String vkUid){
        return dbMapper.getCompanies(vkUid);
    }
    public UserToCompanyDTO getCompanyInfo(Long companyCode){     
        return dbMapper.getCompanyInfo(companyCode);
    }
    //извлекаем активные критерии
    public List<CriteriaDTO> getCriteriaByCompanyId(int criteriaId){
        return dbMapper.getCriteriesByCompanyId(criteriaId);
    }
    public Message getMessageByCriteriaId(int criteriaId){
        return dbMapper.getMessageByCriteriaId(criteriaId);
    }
    public void updateOffset(Integer criteriaId, Integer offset){
        dbMapper.updateOffset(criteriaId, offset);
    }
    public Integer countOfSubscribesForUser(Long vkUid){
        return dbMapper.countOfSubscribesForUser(vkUid);
    }   
    public Integer countOfSubscribeForUser(Long vkUid, Integer companyId){
        return dbMapper.countOfSubscribeForUser(vkUid, companyId);
    }
    public Integer getCountMessagesByCompanyId(int companyId, Long vkUid){
        return dbMapper.getCountMessagesByCompanyId(vkUid, companyId);
    }
    //проверка кода на уникальность
    public Integer tryUnigueCompanyCode(Long code){
        return dbMapper.tryUniqueCode(code);
    }
    //проверка названия кампании на уникальность
    public Integer tryUniqueCompanyTitle(String title){
        return dbMapper.tryUniqueTitle(title);
    }
    public Integer updateCompanyCode(Long vkUid, Integer messageCount, Long code){
        return dbMapper.updateCompanyCode(vkUid, messageCount, code);
    }

}
