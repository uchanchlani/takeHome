package Controller;

import Utility.DiskIO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * Created by utkarshc on 16/4/16.
 */
@RequestMapping("/admin")
@RestController
public class AdminController {

    /*
     * Is not graceful, but it pretty much works
     */
    @RequestMapping("/shutdown")
    public void shutDown() throws IOException {
        DiskIO.writeSortByNameUserIdListToFile();
        DiskIO.writeUsersToFile();
        DiskIO.writeUserGroupsToFile();
        DiskIO.writeSortByTotalCountListToFile();
        DiskIO.writeSortByBlockedCountListToFile();
        DiskIO.writeSortByNameUserGroupIdListToFile();
        DiskIO.writeAllStatics();
        System.exit(0);
    }
}
