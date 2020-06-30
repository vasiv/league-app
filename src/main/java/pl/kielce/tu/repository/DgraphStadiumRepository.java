package pl.kielce.tu.repository;

import com.google.gson.Gson;
import com.google.protobuf.ByteString;
import io.dgraph.DgraphClient;
import io.dgraph.DgraphProto;
import io.dgraph.Transaction;
import pl.kielce.tu.model.Stadium;

import java.util.*;

/**
 * @author ciepluchs
 */
public class DgraphStadiumRepository implements StadiumRepository {

    private DgraphClient dgraphClient;

    public DgraphStadiumRepository(DgraphClient dgraphClient) {
        this.dgraphClient = dgraphClient;
    }

    @Override
    public List<Stadium> findAll() {
        Gson gson = new Gson();
        String query = "query all($a: string){\n" + "all(func: eq(id, $idPlaceHolder)) {\n" + " name\n" + " }\n" + "}";
        Map vars = Collections.singletonMap("$idPlaceHolder", "%");
        DgraphProto.Response res = dgraphClient.newTransaction().queryWithVars(query, vars);
        Stadium ppl = gson.fromJson(res.getJson().toStringUtf8(), Stadium.class);
        return new ArrayList<>();
    }

    @Override
    public Stadium find(Long id) {
        Gson gson = new Gson();
        String query = "query all($a: string){\n" + "all(func: eq(city, \"kielce\")) {\n" + " name\n" + " }\n" + "}";
//        Map vars = Collections.singletonMap("$idPlaceHolder", id.toString());
//        DgraphProto.Response res = dgraphClient.newTransaction().queryWithVars(query, vars);
        DgraphProto.Response res = dgraphClient.newTransaction().query(query);
        return gson.fromJson(res.getJson().toStringUtf8(), Stadium.class);
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public Stadium createOrUpdate(Stadium entity) {
        Gson gson = new Gson();
        entity.setId(UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE);
        Transaction txn = dgraphClient.newTransaction();
        try {
            String json = gson.toJson(entity);
            DgraphProto.Mutation mutation =
                    DgraphProto.Mutation.newBuilder().setSetJson(ByteString.copyFromUtf8(json)).build();
            txn.mutate(mutation);
            txn.commit();
        } finally {
            txn.discard();
        }
        return find(entity.getId());
    }

    @Override
    public List<Stadium> find(Stadium stadium) {
        return new ArrayList<>();
    }
}
