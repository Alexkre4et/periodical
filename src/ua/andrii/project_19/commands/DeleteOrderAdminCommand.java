package ua.andrii.project_19.commands;

import org.apache.log4j.Logger;
import ua.andrii.project_19.entity.Order;
import ua.andrii.project_19.exception.WrongOrderDataException;
import ua.andrii.project_19.service.AdminService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class DeleteOrderAdminCommand extends Command {
    private static final Logger LOGGER = Logger.getLogger(AdminService.class);
    private final AdminService adminService;

    public DeleteOrderAdminCommand(AdminService adminService) {
        this.adminService = adminService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("DeleteOrderAdminCommand()");
        HttpSession session = request.getSession(true);

        List ordersList = (List) session.getAttribute("orderslist");
        //List ordersList=(List) request.getAttribute("orderslist");

        String del = request.getParameter("delindex");
        int index = (new Integer(del)).intValue();
        Order order = (Order) ordersList.get(index);

        try {
            boolean result = adminService.deleteOrder(order);
            if (result) {

                request.setAttribute("message", "Deleting is successful");
                ordersList.remove(index);
                session.setAttribute("orderslist", ordersList);
                return "/admin_orders.jsp";

            } else {
                request.setAttribute("error", "An internal error occurred while trying to delete an order");
            }
        } catch (WrongOrderDataException e) {
            request.setAttribute("error", e.getMessage());
        }

        session.setAttribute("orderslist", ordersList);
        //request.setAttribute("orderslist", ordersList);

        return "/admin_orders.jsp";
    }
}
