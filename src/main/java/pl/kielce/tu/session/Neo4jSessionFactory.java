package pl.kielce.tu.session;

import org.neo4j.ogm.config.Configuration;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;


/**
 * @author ciepluchs
 */
public class Neo4jSessionFactory {

    static Configuration configuration = new Configuration.Builder()
            .uri("bolt://neo4j:liga@192.168.220.197")
            .build();

    static SessionFactory sessionFactory =
            new SessionFactory(configuration, "pl.kielce.tu.model");
    static Neo4jSessionFactory factory = new Neo4jSessionFactory();

    private Neo4jSessionFactory() {
    }

    public static Neo4jSessionFactory getInstance() {
        return factory;
    }

    public Session getNeo4jSession() {
        return sessionFactory.openSession();
    }

    public void close() {
        sessionFactory.close();
    }
}
