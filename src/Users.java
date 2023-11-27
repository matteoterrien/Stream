import java.util.HashMap;

public class Users {
    public HashMap<Integer, User> users = new HashMap<>();
    public class User {
        private int userID;
        private String username;
        private String email;
        private String password;

        public void setUserID(int userID) {
            this.userID = userID;
        }
        public void setUsername(String username) {
            this.username = username;
        }
        public void setEmail(String email) {
            this.email = email;
        }
        public void setPassword(String password) {
            this.password = password;
        }
        public int getUserID() {
            return userID;
        }
        public String getUsername() {
            return username;
        }
        public String getEmail() {
            return email;
        }
        public String getPassword() {
            return password;
        }
    }

    public void setUsers(HashMap<Integer, User> users) {
        this.users = users;
    }
    public void addUser(User S) {
        users.put(S.userID, S);
    }
    public HashMap<Integer, User> getUsers() {
        return users;
    }
    public User getUser(int id) {
        return users.get(id);
    }
}
