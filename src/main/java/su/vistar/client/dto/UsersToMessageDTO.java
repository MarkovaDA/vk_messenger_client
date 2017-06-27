
package su.vistar.client.dto;

import java.util.List;


public class UsersToMessageDTO {
    private String message;
    private List<VKUserDTO> users;
    private Integer criterionId;//теперь добавляем учет критериев

    public Integer getCriterionId() {
        return criterionId;
    }

    public void setCriterionId(Integer criterionId) {
        this.criterionId = criterionId;
    }
    
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<VKUserDTO> getUsers() {
        return users;
    }

    public void setUsers(List<VKUserDTO> users) {
        this.users = users;
    }

    public UsersToMessageDTO(String message, List<VKUserDTO> users, Integer criteriaId) {
        this.message = message;
        this.users = users;
        this.criterionId = criteriaId;
    }

    public UsersToMessageDTO(String message, List<VKUserDTO> users) {
        this.message = message;
        this.users = users;
    }

}
