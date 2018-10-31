package servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CacheServlet extends HttpServlet {

    private static final String SIZE_VARIABLE = "size";
    private static final String HIT_VARIABLE = "hit";
    private static final String MISS_VARIABLE = "miss";
    private static final String CACHE_PAGE_TEMPLATE = "cache.html";

    private CacheInfo info;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String page = getPage(info);
        resp.getWriter().println(page);
        resp.setContentType("text/html;charset=utf-8");
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    static private String getPage(CacheInfo info) throws IOException {
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put(SIZE_VARIABLE, info.getSize());
        pageVariables.put(HIT_VARIABLE, info.getHits());
        pageVariables.put(MISS_VARIABLE, info.getMiss());
        return TemplateProcessor.getInstance().getPage(CACHE_PAGE_TEMPLATE, pageVariables);
    }
}
