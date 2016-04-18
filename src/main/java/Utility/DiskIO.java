package Utility;

import Model.User;
import Model.UserGroup;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by utkarshc on 16/4/16.
 */
public class DiskIO {
    private static Map<Integer, User> userRepo = null;
    private static Map<Integer, UserGroup> userGroupRepo = null;
    private static List<Integer> sortByNameUserIdList = null;
    private static List<Integer> sortByNameUserGroupIdList = null;
    private static List<Integer> sortByBlockedCountList = null;
    private static List<Integer> sortByTotalCountList = null;

    static {
        try {
            User.setTotalUserCount(Integer.parseInt(readFromFile(PropertiesGetter.getProperty("filepath")+PropertiesGetter.getProperty("file.userMaxCount"))));
        } catch (IOException e) {
            e.printStackTrace();
            User.setTotalUserCount(0);
        }
        try {
            UserGroup.setTotalGroupCount(Integer.parseInt(readFromFile(PropertiesGetter.getProperty("filepath")+PropertiesGetter.getProperty("file.userGroupMaxCount"))));
        } catch (IOException e) {
            e.printStackTrace();
            UserGroup.setTotalGroupCount(0);
        }
    }

    private static String readFromFile(String fileName) throws IOException {
        String data = "";
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String buf;
        while((buf = br.readLine()) != null) data += buf;
        br.close();
        if(data == null)
            throw new IOException("data not found");
        return data;
    }

    private static void writeToFile(String data, String fileName) throws IOException {
        File file = new File(fileName);

        if(!file.exists())
            file.createNewFile();

        BufferedWriter bw = new BufferedWriter(new FileWriter(file.getAbsoluteFile()));
        bw.write(data);
        bw.close();
    }

    private static void loadEverything() {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss.SSS").create();
        if(userRepo == null) {
            try {
                Type type = new TypeToken<HashMap<Integer, User>>(){}.getType();
                userRepo = gson.fromJson(readFromFile(PropertiesGetter.getProperty("filepath")+PropertiesGetter.getProperty("file.users")), type);
            } catch (Exception e) {
                userRepo = new HashMap<>();
            }
        }
        if(userGroupRepo == null) {
            try {
                Type type = new TypeToken<HashMap<Integer, UserGroup>>(){}.getType();
                userGroupRepo = gson.fromJson(readFromFile(PropertiesGetter.getProperty("filepath")+PropertiesGetter.getProperty("file.userGroups")), type);
            } catch (Exception e) {
                userGroupRepo = new HashMap<>();
            }
        }
        if(sortByNameUserIdList == null) {
            try {
                Type type = new TypeToken<ArrayList<Integer>>(){}.getType();
                sortByNameUserIdList = gson.fromJson(readFromFile(PropertiesGetter.getProperty("filepath")+PropertiesGetter.getProperty("file.sortByNameUserId")), type);
            } catch (Exception e) {
                sortByNameUserIdList = new ArrayList<>();
            }
        }
        if(sortByNameUserGroupIdList == null) {
            try {
                Type type = new TypeToken<ArrayList<Integer>>(){}.getType();
                sortByNameUserGroupIdList = gson.fromJson(readFromFile(PropertiesGetter.getProperty("filepath")+PropertiesGetter.getProperty("file.sortByNameUserGroupId")), type);
            } catch (Exception e) {
                sortByNameUserGroupIdList = new ArrayList<>();
            }
        }
        if(sortByBlockedCountList == null) {
            try {
                Type type = new TypeToken<ArrayList<Integer>>(){}.getType();
                sortByBlockedCountList = gson.fromJson(readFromFile(PropertiesGetter.getProperty("filepath")+PropertiesGetter.getProperty("file.sortByBlockedCount")), type);
            } catch (Exception e) {
                sortByBlockedCountList = new ArrayList<>();
            }
        }
        if(sortByTotalCountList == null) {
            try {
                Type type = new TypeToken<ArrayList<Integer>>(){}.getType();
                sortByTotalCountList = gson.fromJson(readFromFile(PropertiesGetter.getProperty("filepath")+PropertiesGetter.getProperty("file.sortByTotalCount")), type);
            } catch (Exception e) {
                sortByTotalCountList = new ArrayList<>();
            }
        }
    }

    public static Map<Integer, User> getUsersFromFile() {
        loadEverything();
        return userRepo;
    }

    public static void writeUsersToFile() throws IOException {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss.SSS").create();
        if(userRepo != null) {
            writeToFile(gson.toJson(userRepo), PropertiesGetter.getProperty("filepath")+PropertiesGetter.getProperty("file.users"));
        }
    }

    public static List<Integer> getSortByNameUserIdListFromFile() {
        loadEverything();
        return sortByNameUserIdList;
    }

    public static void writeSortByNameUserIdListToFile() throws IOException {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss.SSS").create();
        if(sortByNameUserIdList != null) {
            writeToFile(gson.toJson(sortByNameUserIdList), PropertiesGetter.getProperty("filepath")+PropertiesGetter.getProperty("file.sortByNameUserId"));
        }
    }

    public static Map<Integer, UserGroup> getUserGroupsFromFile() {
        loadEverything();
        return userGroupRepo;
    }

    public static void writeUserGroupsToFile() throws IOException {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss.SSS").create();
        if(userGroupRepo != null) {
            writeToFile(gson.toJson(userGroupRepo), PropertiesGetter.getProperty("filepath")+PropertiesGetter.getProperty("file.userGroups"));
        }
    }

    public static List<Integer> getSortByNameUserGroupIdListFromFile() {
        loadEverything();
        return sortByNameUserGroupIdList;
    }

    public static void writeSortByNameUserGroupIdListToFile() throws IOException {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss.SSS").create();
        if(sortByNameUserGroupIdList!= null) {
            writeToFile(gson.toJson(sortByNameUserGroupIdList), PropertiesGetter.getProperty("filepath")+PropertiesGetter.getProperty("file.sortByNameUserGroupId"));
        }
    }

    public static List<Integer> getSortByBlockedCountListFromFile() {
        loadEverything();
        return sortByBlockedCountList;
    }

    public static void writeSortByBlockedCountListToFile() throws IOException {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss.SSS").create();
        if(sortByBlockedCountList!= null) {
            writeToFile(gson.toJson(sortByBlockedCountList), PropertiesGetter.getProperty("filepath")+PropertiesGetter.getProperty("file.sortByBlockedCount"));
        }
    }

    public static List<Integer> getSortByTotalCountListFromFile() {
        loadEverything();
        return sortByTotalCountList;
    }

    public static void writeSortByTotalCountListToFile() throws IOException {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss.SSS").create();
        if(sortByTotalCountList!= null) {
            writeToFile(gson.toJson(sortByTotalCountList), PropertiesGetter.getProperty("filepath")+PropertiesGetter.getProperty("file.sortByTotalCount"));
        }
    }

    public static void writeAllStatics() throws IOException {
        writeToFile(Integer.toString(User.getTotalUserCount()), PropertiesGetter.getProperty("filepath")+PropertiesGetter.getProperty("file.userMaxCount"));
        writeToFile(Integer.toString(UserGroup.getTotalGroupCount()), PropertiesGetter.getProperty("filepath")+PropertiesGetter.getProperty("file.userGroupMaxCount"));
    }
}
