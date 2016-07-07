package com.mirzoevnik.openspacechat.servlets;

import com.mirzoevnik.openspacechat.dao.DaoFactory;
import com.mirzoevnik.openspacechat.dao.PersistException;
import com.mirzoevnik.openspacechat.entities.User;
import com.mirzoevnik.openspacechat.mysql.MySqlDaoFactory;
import com.mirzoevnik.openspacechat.mysql.MySqlUserDao;
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
@WebServlet("/sign_in")
public class SignInServlet extends HttpServlet {

    private String servletName;
    private ServletContext servletContext;
    private ServletConfig servletConfig;

    private DaoFactory factory;
    private MySqlUserDao dao;

    public SignInServlet() {
        try {
            factory = new MySqlDaoFactory();
            dao = (MySqlUserDao) factory.getDao(factory.getContext(), User.class);
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

        request.getRequestDispatcher("jsp/sign_in.jsp").forward(request, response);
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

        // get data from sign in
        String userLogin = request.getParameter("login");
        String userPassword = request.getParameter("password");

        try {
            // searching user in DB
            User user = dao.getByLoginAndPassword(userLogin, userPassword);

            if (user != null) {
                // save data about user in session
                HttpSession session = request.getSession();
                session.setAttribute("user", user);

                // move to main page
                response.sendRedirect("/");
                return;
            }
        }
        catch (PersistException e) {
            e.printStackTrace();
        }

        request.getRequestDispatcher("jsp/sign_in.jsp").forward(request, response);
    }
}