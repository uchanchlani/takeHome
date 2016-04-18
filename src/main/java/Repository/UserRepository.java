package Repository;

import Model.User;
import Utility.DiskIO;
import Utility.MyCustomSortedCollection;

import java.util.List;
import java.util.Map;

/**
 * Created by utkarshc on 16/4/16.
 */
public class UserRepository {
    private Map<Integer, User> userRepo;
    private MyCustomSortedCollection<User> sortByName;
    private static UserRepository userRepository;

    static {
        userRepository = null;
    }

    private UserRepository() throws NoSuchMethodException {
        userRepo = DiskIO.getUsersFromFile();

        sortByName = new MyCustomSortedCollection<User>(userRepo, User.class.getMethod("compareName", User.class));
        sortByName.reinitializeList(DiskIO.getSortByNameUserIdListFromFile());
    }

    public static UserRepository getInstance() throws NoSuchMethodException {
        if(userRepository == null) {
            userRepository = new UserRepository();
        }
        return userRepository;
    }

    public Object addUser(User user) throws NoSuchMethodException {
        if(userRepo.getOrDefault(user.getId(), null) != null) return "User id already present";
        if(UserGroupRepository.getInstance().searchUserGroupById(user.getUserGroupId()) == null) return "user group doesn't exists";
        UserGroupRepository.getInstance().addUser(user);
        userRepo.put(user.getId(), user);
        return sortByName.addElem(user.getId());
    }

    public List<User> searchUserByName(String name) throws Exception {
        String[] names = name.split(" ");
        if(names.length > 2 || names.length == 0) throw new Exception("name cannot be parsed");
        return sortByName.getAllElem(new User(names[0], names.length > 1 ? names[1] : "", -1));
    }

    public User searchUserById(int id) {
        return userRepo.getOrDefault(id, null);
    }

    public User changeUserStatus(int id, String status) throws NoSuchMethodException {
        User user = this.userRepo.getOrDefault(id, null);
        if(user == null) return user;
        UserGroupRepository.getInstance().deleteUser(user);
        user.changeStatus(status);
        this.userRepo.replace(id, user);
        UserGroupRepository.getInstance().addUser(user);
        return user;
    }

    public User removeUserById(int id) throws NoSuchMethodException {
        User user = userRepo.getOrDefault(id, null);
        if(user == null) return null;
        userRepo.remove(user.getId());
        UserGroupRepository.getInstance().deleteUser(user);
        return user;
    }

    public void printSomething() {
        System.out.println("something in ag");
    }
}
