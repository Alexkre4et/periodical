package ua.andrii.project_19.commands;

import org.apache.log4j.Logger;
import ua.andrii.project_19.entity.User;
import ua.andrii.project_19.service.AdminService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserInfoCommand extends Command {

    private static final Logger LOGGER = Logger.getLogger(AdminService.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("UserInfoCommand()");
        User user = (User) request.getSession().getAttribute("user");

        return "/admin_page.jsp";
    }
}
