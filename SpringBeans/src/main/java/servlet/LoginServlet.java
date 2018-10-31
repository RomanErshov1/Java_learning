package servlet;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LoginServlet extends HttpServlet {

    private static final String LOGIN_VARIABLE = "login";
    private static final String PASSWORD_VARIABLE = "password";
    private static final String LOGIN_PAGE_TEMPLATE = "login.html";
    private static final String LOGIN_PARAMETER_NAME = "login";
    private static final String PASSWORD_PARAMETER_NAME = "password";

    private String login = "";
    private String password = "";

    private static String getPage(String login, String password) throws IOException {
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put(LOGIN_VARIABLE, login == null ? "" : login);
        pageVariables.put(PASSWORD_VARIABLE, password == null ? "" : password);
        return TemplateProcessor.getInstance().getPage(LOGIN_PAGE_TEMPLATE, pageVariables);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestLogin = req.getParameter(LOGIN_PARAMETER_NAME);
        String requestPassword = req.getParameter(PASSWORD_PARAMETER_NAME);

        if (requestLogin != null){
            login = requestLogin;
            req.getSession().setAttribute(LOGIN_PARAMETER_NAME, requestLogin);
            req.getServletContext().setAttribute(LOGIN_PARAMETER_NAME, requestLogin);
            resp.addCookie(new Cookie("hibernate_login", requestLogin));
        }

        if (requestPassword != null){
            password = requestPassword;
            req.getSession().setAttribute(PASSWORD_PARAMETER_NAME, requestPassword);
            req.getServletContext().setAttribute(PASSWORD_PARAMETER_NAME, requestPassword);
            resp.addCookie(new Cookie("hibernate_pass", requestPassword));
        }

        String page = getPage(login, password);
        resp.getWriter().println(page);
        resp.setContentType("text/html;charset=utf-8");
        resp.setStatus(HttpServletResponse.SC_OK);

        if (login.equals("admin") && password.equals("admin")) resp.sendRedirect("/tml/cache");
    }
}
