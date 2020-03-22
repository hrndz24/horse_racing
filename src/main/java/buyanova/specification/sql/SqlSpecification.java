package buyanova.specification.sql;

import buyanova.specification.Specification;

public interface SqlSpecification extends Specification {

    String toSqlQuery();
}
