package mc03.model;

import java.sql.Connection;

/**
 * Created by david on 4/8/2016.
 */
public class DBSomething {
    private String transID;
    private Connection conn;

    public DBSomething(String transID, Connection conn) {
        this.transID = transID;
        this.conn = conn;
    }

    public String getTransID() {
        return transID;
    }

    public void setTransID(String transID) {
        this.transID = transID;
    }

    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }
}
