package Repository;

import Model.User;
import Model.UserGroup;
import Utility.DiskIO;
import Utility.MyCustomSortedCollection;

import java.util.List;
import java.util.Map;

/**
 * Created by utkarshc on 16/4/16.
 */
public class UserGroupRepository {
    private Map<Integer, UserGroup> userGroupRepo;
    private MyCustomSortedCollection<UserGroup> sortByName;
    private MyCustomSortedCollection<UserGroup> sortByTotal;
    private MyCustomSortedCollection<UserGroup> sortByBlocked;
    private static UserGroupRepository userGroupRepository;

    static {
        userGroupRepository = null;
    }

    private UserGroupRepository() throws NoSuchMethodException {
        userGroupRepo = DiskIO.getUserGroupsFromFile();

        sortByName = new MyCustomSortedCollection<UserGroup>(userGroupRepo, UserGroup.class.getMethod("compareName", UserGroup.class));
        sortByName.reinitializeList(DiskIO.getSortByNameUserGroupIdListFromFile());

        sortByBlocked = new MyCustomSortedCollection<UserGroup>(userGroupRepo, UserGroup.class.getMethod("compareBlocked", UserGroup.class));
        sortByBlocked.reinitializeList(DiskIO.getSortByBlockedCountListFromFile());

        sortByTotal = new MyCustomSortedCollection<UserGroup>(userGroupRepo, UserGroup.class.getMethod("compareTotal", UserGroup.class));
        sortByTotal.reinitializeList(DiskIO.getSortByTotalCountListFromFile());
    }

    public static UserGroupRepository getInstance() throws NoSuchMethodException {
        if(userGroupRepository == null) {
            userGroupRepository = new UserGroupRepository();
        }
        return userGroupRepository;
    }

    private void removeUserGroupFromQueues(UserGroup userGroup) {
        sortByName.removeExactElem(userGroup, userGroup.getId());
        sortByBlocked.removeExactElem(userGroup, userGroup.getId());
        sortByTotal.removeExactElem(userGroup, userGroup.getId());
        userGroupRepo.remove(userGroup.getId());
    }

    private void addUserGroupToQueues(UserGroup userGroup) {
        userGroupRepo.put(userGroup.getId(), userGroup);
        sortByName.addElem(userGroup.getId());
        sortByBlocked.addElem(userGroup.getId());
        sortByTotal.addElem(userGroup.getId());
    }

    public UserGroup searchUserGroupById(Integer id) {
        return userGroupRepo.getOrDefault(id, null);
    }

    public UserGroup searchUserGroupByName(String name) {
        return sortByName.getElem(new UserGroup(name));
    }

    public List<UserGroup> searchUserGroupByCountCriteria(String entitity, boolean wantMax) {
        if(entitity.equalsIgnoreCase("blocked")) {
            if(wantMax)
                return sortByBlocked.getAllMaxElem();
            else
                return sortByBlocked.getAllMinElem();
        } else {
            if(wantMax)
                return sortByTotal.getAllMaxElem();
            else
                return sortByTotal.getAllMinElem();
        }
    }

    public void deleteUser(User user) {
        UserGroup userGroup = searchUserGroupById(user.getUserGroupId());
        removeUserGroupFromQueues(userGroup);
        userGroup.removeUser(user);
        addUserGroupToQueues(userGroup);
    }

    public void addUser(User user) {
        UserGroup userGroup = searchUserGroupById(user.getUserGroupId());
        removeUserGroupFromQueues(userGroup);
        userGroup.addUser(user);
        addUserGroupToQueues(userGroup);
    }

    public void addUserGroup(UserGroup userGroup) {
        addUserGroupToQueues(userGroup);
    }

    public void printSomething() {
        System.out.println("something in ag");
    }
}
