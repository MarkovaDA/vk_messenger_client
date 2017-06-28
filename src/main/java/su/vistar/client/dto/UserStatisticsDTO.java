
package su.vistar.client.dto;

import java.util.Date;


public class UserStatisticsDTO {
    private Integer criterionId;
    private Long receiverVkId; //vk_id получателя
    private String errorMsg;
    private Long deviceDate;
    
    //создать отдельную модель объекта
    private Date humanDate;
    private Long senderVkId;

    public Long getSenderVkId() {
        return senderVkId;
    }

    public void setSenderVkId(Long senderVkId) {
        this.senderVkId = senderVkId;
    }
    
    public UserStatisticsDTO() {
    }
    
    public UserStatisticsDTO(
            Integer criterionId, 
            Long  receiverVkId,
            String status, 
            Long deviceDate) {
        this.criterionId = criterionId;
        this.errorMsg = status;
        this.deviceDate = deviceDate;
        this.receiverVkId = receiverVkId;
    }

    public Date getHumanDate() {
        return humanDate;
    }

    public void setHumanDate(Date humanDate) {
        this.humanDate = humanDate;
    }

    public Long getReceiverVkId() {
        return receiverVkId;
    }

    public void setReceiverVkId(Long receiverVkId) {
        this.receiverVkId = receiverVkId;
    }

    public Long getDeviceDate() {
        return deviceDate;
    }

    public void setDeviceDate(Long deviceDate) {
        this.deviceDate = deviceDate;
    }
    
    public Integer getCriterionId() {
        return criterionId;
    }

    public void setCriterionId(Integer criterionId) {
        this.criterionId = criterionId;
    }
    
    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
