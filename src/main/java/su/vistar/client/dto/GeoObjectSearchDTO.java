package su.vistar.client.dto;


public class GeoObjectSearchDTO {
    private Integer country_id;
    private String q;

    public GeoObjectSearchDTO() {
    }

    public GeoObjectSearchDTO(Integer country_id, String q) {
        this.country_id = country_id;
        this.q = q;
    }
    
    public Integer getCountry_id() {
        return country_id;
    }

    public void setCountry_id(Integer country_id) {
        this.country_id = country_id;
    }

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }
    
}
