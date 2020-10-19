import java.util.*;

public class Stock {


    private Map<String, String> users = new HashMap<>();
    private List<String> keysValues = new ArrayList<>();

    public Stock() {
        this.users.put("adam", "pass");
        this.users.put("mikolaj", "haslo");
        setKeysValues();
    }

    public Map<String, String> getUsers() {
        return users;
    }

    public void setUsers(Map<String, String> users) {
        this.users = users;
    }

    public ArrayList<String> setKeysValues(){
        for (String key: users.keySet()) {
            keysValues.add("\"" + key + "=" + users.get(key) + "\"" );
        }
        return (ArrayList<String>) keysValues;
    }

    public List<String> getKeysValues() {
        return keysValues;
    }
}
