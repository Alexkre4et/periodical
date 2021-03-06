package ua.andrii.project_19.servlets;

import org.apache.log4j.Logger;
import ua.andrii.project_19.commands.Command;
import ua.andrii.project_19.factory.CommandFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ResourceBundle;


public class ShoppingServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(ShoppingServlet.class);
    private ResourceBundle servletProperties = ResourceBundle.getBundle("resource.servlet_config");

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOGGER.debug("POST request: " + request.getPathInfo());
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        handleRequest(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOGGER.debug("GET request: " + request.getPathInfo());
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        handleRequest(request, response);
    }

    /**
     * Handles request that came from the client
     * Gets response string and passes it to RequestDispatcher
     *
     * @param request  HttpServletRequest object
     * @param response HttpServletResponse object
     */
    private void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        String result;

        String action = request.getParameter("action");
        LOGGER.debug("Action: " + action);
        request.setAttribute("action", action);
        Command command = CommandFactory.getCommand(action);

        if (command == null) {
            // error404 page not found
            result = "/error404.jsp";
        }

        LOGGER.debug("Command: " + command);
        result = command.execute(request, response);
        ServletContext sc = getServletContext();
        RequestDispatcher rd = sc.getRequestDispatcher("/WEB-INF/jsp" + result);
        rd.forward(request, response);
    }
}