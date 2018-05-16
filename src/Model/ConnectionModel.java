/**
 * @author Isswarraj Gopee
 */

package Model;

public class ConnectionModel {
    private String connectionName;
    private String connectionType;

    public ConnectionModel(String connectionName, String connectionType) {
        this.connectionName = connectionName;
        this.connectionType = connectionType;
    }

    public String getConnectionName() {
        return connectionName;
    }

    public void setConnectionName(String connectionName) {
        this.connectionName = connectionName;
    }

    public String getConnectionType() {
        return connectionType;
    }

    public void setConnectionType(String connectionType) {
        this.connectionType = connectionType;
    }
}
