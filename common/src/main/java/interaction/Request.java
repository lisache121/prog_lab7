package interaction;

import data.Dragon;


import java.io.Serializable;
import java.util.Map;

public interface Request extends Serializable {
    public String getArguments();
    public Dragon getDragon();
    public String getCommandName();
    public Map.Entry<String, String> getUser();
}
