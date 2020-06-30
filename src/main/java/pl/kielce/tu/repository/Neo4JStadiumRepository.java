package pl.kielce.tu.repository;

import org.apache.commons.lang3.StringUtils;
import org.neo4j.ogm.session.Session;
import pl.kielce.tu.model.Stadium;

import java.util.*;

/**
 * @author ciepluchs
 */
public class Neo4JStadiumRepository implements StadiumRepository {

    private static final int DEPTH_LIST = 0;
    private static final int DEPTH_ENTITY = 1;
    private Session session;

    public Neo4JStadiumRepository(Session session) {
        this.session = session;
    }

    public List<Stadium> findAll() {
        return new ArrayList<>(session.loadAll(Stadium.class, DEPTH_LIST));
    }

    public Stadium find(Long id) {
        return session.load(Stadium.class, id, DEPTH_ENTITY);
    }

    @Override
    public List<Stadium> find(Stadium stadium) {
        StringBuilder sb = new StringBuilder("MATCH (n:Stadium) WHERE ");
        Map<String, Object> queryParams = getQueryParams(stadium);
        String separator = "";
        for (String attributeName : queryParams.keySet()) {
            sb.append(separator)
                    .append("n.")
                    .append(attributeName)
                    .append(" = $")
                    .append(attributeName);
            separator = " AND ";
        }
        sb.append(" RETURN n");
        Iterable<Map<String, Object>> queryResults = session.query(sb.toString(), queryParams).queryResults();
        List<Stadium> foundStadiums = new ArrayList<>();
        queryResults.forEach(result -> {
            Collection<Object> values = result.values();
            values.forEach(value -> foundStadiums.add((Stadium) value));
        });
        return foundStadiums;
    }

    public void delete(Long id) {
        session.delete(session.load(Stadium.class, id));
    }

    public Stadium createOrUpdate(Stadium entity) {
        session.save(entity, DEPTH_ENTITY);
        return find(entity.getId());
    }

    private Map<String, Object> getQueryParams(Stadium stadium) {
        Map<String, Object> params = new HashMap<>();
        String ownerClub = stadium.getOwnerClub();
        if (StringUtils.isNotEmpty(ownerClub)) {
            params.put("ownerClub", ownerClub);
        }
        String city = stadium.getCity();
        if (StringUtils.isNotEmpty(city)) {
            params.put("city", city);
        }
        Integer capacity = stadium.getCapacity();
        if (capacity != null && capacity > 0) {
            params.put("capacity", capacity);
        }
        Boolean isCovered = stadium.isGrandstandCovered();
        if (isCovered != null) {
            params.put("isGrandstandCovered", isCovered);
        }
        return params;
    }

}
