package devpost;

import java.util.HashMap;

public class UserSet extends HashMap<String, User> {
    static UserSet userset = null;

    public UserSet() {
        super();
    }

    public static UserSet getUserSet() {
        if (userset == null) userset = new UserSet();
        return userset;
    }
}
