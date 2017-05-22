package su.vistar.client.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import su.vistar.client.mapper.DBMapper;
import su.vistar.client.model.AdresatCriteria;
import su.vistar.client.model.Message;
import su.vistar.client.model.User;

@Service
public class DBCriteriaService {
    @Autowired
    DBMapper dbMapper;
    
    @Autowired
    AuthService authService;
    
    private void saveCriteria(String criteria, int offset, int userId){
        dbMapper.saveCriteria(criteria,0, userId);
    }
 
    public void saveCriteria(AdresatCriteria criteria){
        User currentUser = authService.getCurrentUser();  
        saveCriteria(criteria.toString(), 0, currentUser.getId());
        int criteriaId = dbMapper.lastInsertedId();
        int messageId;
        if (criteria.getMessage_id() != null){
            dbMapper.saveMessageCriteria(criteriaId, criteria.getMessage_id());
        }
        else if (criteria.getMessage() != null){
            dbMapper.saveMessage(criteria.getMessage());
            messageId = dbMapper.lastInsertedId();
            dbMapper.saveMessageCriteria(criteriaId, messageId);
        }
    }
    
    public List<Message> getMessages(){
        return dbMapper.getAllMessage();
    }
    
    public Message getMessageById(Integer mesId){
        return dbMapper.getMessageById(mesId);
    }
    
}
