package Controller;

import Model.User;
import Model.UserGroup;
import Repository.UserGroupRepository;
import Repository.UserRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by utkarshc on 16/4/16.
 */
@RequestMapping("/create")
@RestController
public class CreateController {

    @RequestMapping("/group")
    public Map<String, Object> addGroup(@RequestParam(value="name", defaultValue = "") String name) throws NoSuchMethodException {
        Map<String, Object> retObj = new LinkedHashMap<>();
        UserGroup ug = null;

        if(name.equalsIgnoreCase("")) {
            retObj.put("acknowledged", false);
            retObj.put("reason", "Necessary Params not found");
            return retObj;
        }

        ug = UserGroupRepository.getInstance().searchUserGroupByName(name);
        if(ug != null) {
            retObj.put("acknowledged", false);
            retObj.put("reason", "group with the same name exists");
            return retObj;
        }
        UserGroupRepository.getInstance().addUserGroup(ug = new UserGroup(name));
        retObj.put("acknowledged", true);
        retObj.put("id", ug.getId());
        return retObj;
    }

    @RequestMapping("/user")
    public Map<String, Object> addUser(@RequestParam(value="fname", defaultValue = "") String firstName,
                                       @RequestParam(value="lname", defaultValue = "") String lastName,
                                       @RequestParam(value="group", defaultValue = "") String group) throws NoSuchMethodException {
        Map<String, Object> retObj = new LinkedHashMap<>();
        User user = null;
        if(firstName.equalsIgnoreCase("") || lastName.equalsIgnoreCase("") || group.equalsIgnoreCase("")) {
            retObj.put("acknowledged", false);
            retObj.put("reason", "Necessary Params not found");
            return retObj;
        }

        int groupId;
        try {
            groupId = Integer.parseInt(group);
        } catch (Exception e) {
            UserGroup ug = UserGroupRepository.getInstance().searchUserGroupByName(group);
            if(ug == null) {
                retObj.put("acknowledged", false);
                retObj.put("reason", "user group not found");
                return retObj;
            }
            groupId = ug.getId();
        }

        try {
            UserRepository.getInstance().addUser(user = new User(firstName, lastName, groupId));
        } catch (NoSuchMethodException e) {
            retObj.put("acknowledged", false);
            retObj.put("reason", "user group not found");
            retObj.put("debug Reason", e.getMessage());
            return retObj;
        }
        retObj.put("acknowledged", true);
        retObj.put("id", user.getId());
        return retObj;
    }

    @RequestMapping("/alterUser")
    public Map<String, Object> addUser(@RequestParam(value="id", defaultValue = "") String _id,
                                       @RequestParam(value="status", defaultValue = "") String status) throws NoSuchMethodException {
        Map<String, Object> retObj = new LinkedHashMap<>();
        User user = null;
        if(_id.equalsIgnoreCase("") || status.equalsIgnoreCase("")) {
            retObj.put("acknowledged", false);
            retObj.put("reason", "Necessary Params not found");
            return retObj;
        }

        int id;
        try {
            id = Integer.parseInt(_id);
        } catch (Exception e) {
            retObj.put("acknowledged", false);
            retObj.put("reason", "unable to parse id");
            return retObj;
        }

        user = UserRepository.getInstance().searchUserById(id);

        if(user == null) {
            retObj.put("acknowledged", false);
            retObj.put("reason", "user not found with user id=" + _id);
            return retObj;
        }

        UserRepository.getInstance().changeUserStatus(id, status);
        retObj.put("acknowledged", true);
        retObj.put("id", user.getId());
        retObj.put("new status", user.getStatus());
        return retObj;
    }

    /*
     * For debugging it is
     */
    @RequestMapping("/test")
    public String test() throws NoSuchMethodException {
        UserGroupRepository.getInstance().printSomething();
        UserRepository.getInstance().printSomething();
        return "success";
    }
}
