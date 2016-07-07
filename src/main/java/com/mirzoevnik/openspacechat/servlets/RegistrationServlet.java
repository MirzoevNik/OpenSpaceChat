package com.mirzoevnik.openspacechat.servlets;

import com.mirzoevnik.openspacechat.dao.DaoFactory;
import com.mirzoevnik.openspacechat.dao.GenericDao;
import com.mirzoevnik.openspacechat.dao.PersistException;
import com.mirzoevnik.openspacechat.entities.User;
import com.mirzoevnik.openspacechat.mysql.MySqlDaoFactory;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author MirzoevNik
 */
@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet {

    private String servletName;
    private ServletConfig servletConfig;
    private ServletContext servletContext;

    private DaoFactory factory;
    private GenericDao dao;

    public RegistrationServlet() {
        try {
            factory = new MySqlDaoFactory();
            dao = factory.getDao(factory.getContext(), User.class);
        }
        catch (PersistException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param servletConfig configuration of servlet
     * @throws ServletException
     */
    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        this.servletConfig = servletConfig;
        this.servletName = this.servletConfig.getServletName();
        this.servletContext = this.servletConfig.getServletContext();
        this.servletContext.log("Servlet was started!");
    }

    /**
     * @param request request from client
     * @param response response to client
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        try {
            // clear data about user
            session.setAttribute("user", null);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        request.getRequestDispatcher("jsp/registration.jsp").forward(request, response);
    }

    /**
     * @param request request from client
     * @param response response to client
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");

        // get data from registration
        String userLogin = request.getParameter("login");
        String userPassword = request.getParameter("password");
        String userName = request.getParameter("name");
        String userSurname = request.getParameter("surname");
        String userCountry = request.getParameter("country");

        // creating new user
        User user = new User();
        if (userLogin != "")
            user.setLogin(userLogin);
        if (userPassword != "")
            user.setPassword(userPassword);
        if (userName != "")
            user.setName(userName);
        if (userSurname != "")
            user.setSurname(userSurname);
        if (userCountry != "")
            user.setCountry(userCountry);

        try {
            // save user in DB
            dao.persist(user);

            // save data about user in session
            HttpSession session = request.getSession();
            session.setAttribute("user", user);

            // move to main page
            response.sendRedirect("/");
            return;
        }
        catch (PersistException e) {
            e.printStackTrace();
        }

        request.getRequestDispatcher("jsp/registration.jsp").forward(request, response);
    }
}