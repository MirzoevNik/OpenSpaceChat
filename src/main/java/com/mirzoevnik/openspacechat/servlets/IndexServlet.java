package com.mirzoevnik.openspacechat.servlets;

import com.mirzoevnik.openspacechat.entities.User;
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
@WebServlet("")
public class IndexServlet extends HttpServlet {

    private String servletName;
    private ServletContext servletContext;
    private ServletConfig servletConfig;

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

        try {
            // take data about user from session
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");

            // save data about user at request (for JSON)
            request.setAttribute("id", user.getId());
            request.setAttribute("login", user.getLogin());
            request.setAttribute("password", user.getPassword());
            request.setAttribute("name", user.getName());
            request.setAttribute("surname", user.getSurname());
            request.setAttribute("country", user.getCountry());

            // set user name in main page
            String userName = user.getName();
            if (user.getSurname() != null) {
                userName = userName.concat(" " + user.getSurname());
            }
            request.setAttribute("user_name", userName);
        }
        catch (Exception e) {
            e.printStackTrace();

            // move to page of sign in
            response.sendRedirect("/sign_in");
            return;
        }

        request.getRequestDispatcher("jsp/index.jsp").forward(request, response);
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

        request.getRequestDispatcher("jsp/index.jsp").forward(request, response);
    }
}