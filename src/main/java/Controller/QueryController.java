package Controller;

import Model.User;
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
@RequestMapping("/query")
@RestController
public class QueryController {
    @RequestMapping("/user")
    public Map<String, Object> searchUser(@RequestParam(value = "id", defaultValue = "") String _id,
                                       @RequestParam(value = "name", defaultValue = "") String name) throws Exception {
        Map<String, Object> retObj = new LinkedHashMap<>();
        User user = null;
        if (!(_id.equalsIgnoreCase("") ^ name.equalsIgnoreCase(""))) {
            retObj.put("acknowledged", false);
            retObj.put("reason", "Query param protocol broken");
            return retObj;
        }

        if (!_id.equalsIgnoreCase("")) {
            int id;
            try {
                id = Integer.parseInt(_id);
            } catch (Exception e) {
                retObj.put("acknowledged", false);
                retObj.put("reason", "unable to parse id");
                return retObj;
            }

            user = UserRepository.getInstance().searchUserById(id);

            if (user == null) {
                retObj.put("acknowledged", false);
                retObj.put("reason", "user not found with user id=" + _id);
                return retObj;
            }

            retObj.put("user", user);
            return retObj;
        } else if (!name.equalsIgnoreCase("")) {
            retObj.put("users", UserRepository.getInstance().searchUserByName(name));
            return retObj;
        } else {
            retObj.put("acknowledged", false);
            retObj.put("reason", "Query param protocol broken");
            return retObj;
        }
    }

    /*
     * if name found, it searches by name
     * accepts only blocked as status, if not found returns count for total
     */
    @RequestMapping("/group")
    public Map<String, Object> searchUserGroup(@RequestParam(value = "name", defaultValue = "") String name,
                                               @RequestParam(value = "status", defaultValue = "total") String status,
                                               @RequestParam(value = "criteria", defaultValue = "max") String _criteria) throws Exception {
        Map<String, Object> retObj = new LinkedHashMap<>();

        if(name.equalsIgnoreCase("")) {

            boolean criteria;
            if (_criteria.equalsIgnoreCase("max")) {
                criteria = true;
            } else if (_criteria.equalsIgnoreCase("min")) {
                criteria = false;
            } else {
                retObj.put("acknowledged", false);
                retObj.put("reason", "unknown criteria");
                return retObj;
            }

            retObj.put("Usergroups", UserGroupRepository.getInstance().searchUserGroupByCountCriteria(status, criteria));
        }
        else {
            retObj.put("usergroup", UserGroupRepository.getInstance().searchUserGroupByName(name));
        }
        return retObj;
    }
}
