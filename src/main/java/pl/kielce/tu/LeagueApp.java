package pl.kielce.tu;

import io.dgraph.DgraphClient;
import io.dgraph.DgraphGrpc;
import io.dgraph.DgraphGrpc.DgraphStub;
import io.dgraph.DgraphProto.Operation;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.neo4j.ogm.session.Session;
import pl.kielce.tu.repository.DgraphStadiumRepository;
import pl.kielce.tu.repository.Neo4JStadiumRepository;
import pl.kielce.tu.repository.StadiumRepository;
import pl.kielce.tu.session.Neo4jSessionFactory;
import pl.kielce.tu.util.ViewUtil;

/**
 * @author ciepluchs
 */
public class LeagueApp {

    private static final String NEO4J = "neo4J";
    private static final String DGRAPH = "dGraph";


    public static void main(String[] args) {
        String type = NEO4J;
        StadiumRepository stadiumRepository = null;
        Neo4jSessionFactory neoSessionFactory = null;
        if (type.equalsIgnoreCase(NEO4J)) {
            neoSessionFactory = Neo4jSessionFactory.getInstance();
            Session session = neoSessionFactory.getNeo4jSession();
            session.purgeDatabase();
            stadiumRepository = new Neo4JStadiumRepository(session);
        } else if (type.equalsIgnoreCase(DGRAPH)){
            DgraphClient dgraphClient = createDgraphClient();
            stadiumRepository = new DgraphStadiumRepository(dgraphClient);
        } else {
            System.err.println("Incorrect configuration.");
            return;
        }

        ViewUtil.displayMainMenu(stadiumRepository);


        if (type.equalsIgnoreCase(NEO4J)) {
            neoSessionFactory.close();
        } else {
            return;
        }



    }

    private static DgraphClient createDgraphClient() {
        ManagedChannel channel =
                ManagedChannelBuilder.forAddress("192.168.220.197", 9080).usePlaintext().build();
        DgraphStub stub = DgraphGrpc.newStub(channel);
        DgraphClient dgraphClient = new DgraphClient(stub);
        dgraphClient.alter(Operation.newBuilder().setDropAll(true).build());
        String schema = "name: string @index(exact) .";
        Operation operation = Operation.newBuilder().setSchema(schema).build();
        dgraphClient.alter(operation);
        return dgraphClient;
    }

}
