package accounts;

import dbService.DBException;
import dbService.DBService;
import dbService.dataSets.UsersDataSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by SERhiO on 20.06.2017.
 */

public class AccountService {
    private final Map<String, UserProfile> loginToProfile;
    private final Map<String, UserProfile> sessionIdToProfile;
    private DBService dbService;

    public AccountService() {
        loginToProfile = new HashMap<>();
        sessionIdToProfile = new HashMap<>();
        dbService = new DBService();
        List<UsersDataSet> dataSet = dbService.getAll();
        for (UsersDataSet usersDataSet : dataSet) {
            loginToProfile.put(usersDataSet.getName(), new UserProfile(usersDataSet.getName(), usersDataSet.getPassword()));
        }
    }

    public void addNewUser(UserProfile userProfile) throws DBException {
        loginToProfile.put(userProfile.getLogin(), userProfile);
        dbService.addUser(userProfile.getLogin(), userProfile.getPass());
    }

    public UserProfile getUserByLogin(String login) {
        return loginToProfile.get(login);
    }

    public UserProfile getUserBySessionId(String sessionId) {
        return sessionIdToProfile.get(sessionId);
    }

    public void addSession(String sessionId, UserProfile userProfile) {
        sessionIdToProfile.put(sessionId, userProfile);
    }

    public void deleteSession(String sessionId) {
        sessionIdToProfile.remove(sessionId);
    }
}