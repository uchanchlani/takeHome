package Model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by utkarshc on 16/4/16.
 */
public class UserGroup {
    private static int TOTAL_GROUP_COUNT;
    private static DateFormat dateFormat;

    static {
        TOTAL_GROUP_COUNT = 0;
        dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    }

    public static int getTotalGroupCount() {
        return TOTAL_GROUP_COUNT;
    }

    public static void setTotalGroupCount(int totalGroupCount) {
        TOTAL_GROUP_COUNT = totalGroupCount;
    }

    private int id;
    private String name;
    private Date lastModifiedDate;
    private Set<Integer> blockedUsers;
    private Set<Integer> totalUsers;

    public UserGroup(String name) {
        id = ++TOTAL_GROUP_COUNT;
        this.name = name;
        this.lastModifiedDate = new Date();
        this.blockedUsers = new HashSet<>();
        this.totalUsers = new HashSet<>();
    }

    public void changeName(String name) {
        this.name = name;
        lastModifiedDate = new Date();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public Set<Integer> getBlockedUsers() {
        return blockedUsers;
    }

    public Set<Integer> getTotalUsers() {
        return totalUsers;
    }

    public void addUser(User user) {
        if(user.getStatus().equalsIgnoreCase("blocked")) {
            blockedUsers.add(user.getId());
        }
        totalUsers.add(user.getId());
    }

    public void removeUser(User user) {
        blockedUsers.remove(user.getId());
        totalUsers.remove(user.getId());
    }

    public int compareName(UserGroup u) {
        return compareNameStatic(this, u);
    }

    public int compareBlocked(UserGroup u) {
        return compareBlockedStatic(this, u);
    }

    public int compareTotal(UserGroup u) {
        return compareTotalStatic(this, u);
    }

    public static int compareNameStatic(UserGroup u1, UserGroup u2) {
        return u1.getName().toLowerCase().compareTo(u2.getName().toLowerCase());
    }

    public static int compareBlockedStatic(UserGroup u1, UserGroup u2) {
        return u1.blockedUsers.size() - u2.blockedUsers.size();
    }

    public static int compareTotalStatic(UserGroup u1, UserGroup u2) {
        return u1.totalUsers.size() - u2.totalUsers.size();
    }
}
