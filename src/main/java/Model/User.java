package Model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by utkarshc on 16/4/16.
 */
public class User {
    private static int TOTAL_USER_COUNT;
    private static DateFormat dateFormat;

    static {
        TOTAL_USER_COUNT = 0;
        dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    }

    public static int getTotalUserCount() {
        return TOTAL_USER_COUNT;
    }

    public static void setTotalUserCount(int totalUserCount) {
        TOTAL_USER_COUNT = totalUserCount;
    }

    private enum STATUS {
        CREATED, BLOCKED, REGISTERED, DELETED, UNKNOWN
    }

    private int id;
    private String firstName;
    private String lastName;
    private STATUS status;
    private Date lastModifiedDate;
    private int userGroupId;

    public User(String firstName, String lastName, int userGroupId) {
        this.id = ++TOTAL_USER_COUNT;
        this.firstName = firstName;
        this.lastName = lastName;
        this.status = STATUS.CREATED;
        this.lastModifiedDate = new Date();
        this.userGroupId = userGroupId;
    }

    public void changeStatus(String s) {
        this.lastModifiedDate = new Date();
        switch (s.toLowerCase()) {
            case "created":
                this.status = STATUS.CREATED;
                break;
            case "blocked":
                this.status = STATUS.BLOCKED;
                break;
            case "registered":
                this.status = STATUS.REGISTERED;
                break;
            case "deleted":
                this.status = STATUS.DELETED;
                break;
            default:
                this.status = STATUS.UNKNOWN;
                break;
        }
    }

    public void changeName(String firstName, String lastName) {
        this.lastModifiedDate = new Date();
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void changeName(String firstName) {
        this.changeName(firstName, this.lastName);
    }

    public void changeUserGroup(int userGroupId) {
        this.lastModifiedDate = new Date();
        this.userGroupId = userGroupId;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getName() {
        return this.firstName + " " + this.lastName;
    }

    public String getStatus() {
        switch (status) {
            case CREATED:
                return "CREATED";
            case REGISTERED:
                return "REGISTERED";
            case BLOCKED:
                return "BLOCKED";
            case DELETED:
                return "DELETED";
            case UNKNOWN:
            default:
                return "UNKNOWN";
        }
    }

    public String getLastModifiedDate() {
        return dateFormat.format(lastModifiedDate);
    }

    public int getUserGroupId() {
        return userGroupId;
    }

    public int compareName(User u) {
        return compareNameStatic(this, u);
    }

    public static int compareNameStatic(User u1, User u2) {
        return u1.getName().toLowerCase().compareTo(u2.getName().toLowerCase());
    }
}
