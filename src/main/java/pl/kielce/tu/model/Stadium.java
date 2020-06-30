package pl.kielce.tu.model;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

/**
 * @author ciepluchs
 */
@NodeEntity
public class Stadium {

    @Id
    @GeneratedValue
    Long id;

    private String ownerClub;
    private String city;
    private Integer capacity;
    private Boolean isGrandstandCovered;

    public Stadium() {
    }

    public Stadium(String ownerClub, String city, Integer capacity, boolean isGrandstandCovered) {
        this.ownerClub = ownerClub;
        this.city = city;
        this.capacity = capacity;
        this.isGrandstandCovered = isGrandstandCovered;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOwnerClub() {
        return ownerClub;
    }

    public void setOwnerClub(String ownerClub) {
        this.ownerClub = ownerClub;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Boolean isGrandstandCovered() {
        return isGrandstandCovered;
    }

    public void setGrandstandCovered(Boolean grandstandCovered) {
        isGrandstandCovered = grandstandCovered;
    }
}
