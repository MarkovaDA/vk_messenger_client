
package su.vistar.client.model;


public class SenderStatisticsReport {
    private Long senderVkId;
    private Integer count;

    public SenderStatisticsReport() {
    }

    public SenderStatisticsReport(Long senderVkId, Integer count) {
        this.senderVkId = senderVkId;
        this.count = count;
    }
    
    public Long getSenderVkId() {
        return senderVkId;
    }

    public void setSenderVkId(Long senderVkId) {
        this.senderVkId = senderVkId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
        
}
