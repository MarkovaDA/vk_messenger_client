package su.vistar.client.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import su.vistar.client.dto.CompanyDTO;
import su.vistar.client.dto.CriteriaDTO;
import su.vistar.client.mapper.DBMapper;
import su.vistar.client.model.AdresatCriteria;
import su.vistar.client.model.Company;
import su.vistar.client.model.Message;
import su.vistar.client.model.User;

@Service
public class DBCriteriaService {
    @Autowired
    DBMapper dbMapper;
    
    @Autowired
    AuthService authService;
    
    private void saveCriteria(String criteria, int offset, int userId){
        dbMapper.saveCriteria(criteria,offset,userId);
    }
 
    public void saveCriteria(AdresatCriteria criteria){
        User currentUser = authService.getCurrentUser();  
        //сохранение текста критерия
        saveCriteria(criteria.toString(), 0, currentUser.getId());
        int criteriaId = dbMapper.lastInsertedId();
        int messageId;
        //было создано новое сообщение
        if (criteria.getMessage_id() != null){
            dbMapper.saveMessageCriteria(criteriaId, criteria.getMessage_id());
        }
        //сообщение выбрано среди существующих
        else if (criteria.getMessage() != null){
            dbMapper.saveMessage(criteria.getMessage());
            messageId = dbMapper.lastInsertedId();
            dbMapper.saveMessageCriteria(criteriaId, messageId);
        }
    }
    public int addCompany(Company company){
       return dbMapper.addCompany(company);
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
    
    public List<CompanyDTO> getCompanies(String vkUid){
        return dbMapper.getCompanies(vkUid);
    }

    public CompanyDTO getCompanyInfo(Long companyCode){     
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
    public Integer getCountMessagesByCompanyId(int companyId, String vkUid){
        return dbMapper.getCountMessagesByCompanyId(vkUid, companyId);
    }
}
