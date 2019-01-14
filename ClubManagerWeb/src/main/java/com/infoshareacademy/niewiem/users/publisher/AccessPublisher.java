package com.infoshareacademy.niewiem.users.publisher;

import com.infoshareacademy.niewiem.users.dao.UserDao;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Stateless
public class AccessPublisher {

    public static final String HAS_ACCESS_PARAM = "hasAccess";
    private static final Integer ADMIN = 1;

    @Inject
    private UserDao userDao;

    public boolean publishAccess(HttpSession session, Map<String, Object> model){

        String sessionEmail = (String) session.getAttribute("userEmail");

        if(userDao.findByEmail(sessionEmail).getRole() == ADMIN){
            model.put(HAS_ACCESS_PARAM, true);
            return true;
        }
        model.put(HAS_ACCESS_PARAM, false);
        return false;
    }

}
