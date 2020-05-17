import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class MyServlet extends HttpServlet {
    private static final String SERVLET_TAG = "MyServlet: ";

    public MyServlet() {
        super();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        String reqCode = request.getParameter("want");
        System.out.println(SERVLET_TAG + reqCode);

        if (reqCode == null) {
            //@todo
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Неверный запрос");
        }
        else if (reqCode.equals("towns")) {
            String country = request.getParameter("country");

            if(country == null)
//                request.getSession().setAttribute("err", true);
                request.getSession().setAttribute("message", "error");

            DatabaseManager dm = new DatabaseManager();

            // test
//            StringBuilder sb = new StringBuilder();
//            sb.append(dm.databaseCompareAccount("1", "1@mail.ru", "qwerty1"));  // true
//            sb.append(dm.databaseCompareAccount("2", "1@mail.ru", "qwerty1"));  // false
//            sb.append(dm.databaseGetCountries());  // Russia|China
//            sb.append(dm.databaseGetPlaces("Qwerty"));  // ""
//            sb.append(dm.databaseGetPlaces("moscow"));  // "Kremlin and blah-blah-blah"
//            sb.append(dm.databaseGetPlaces("Saint-Petersburg"));  // ""
//            sb.append(dm.databaseGetPlaces("Moscow"));  // "Kremlin and blah-blah-blah"
//            sb.append(dm.databaseGetTownsByCountry("China"));  // "Bejing"
//            sb.append(dm.databaseGetTownsByCountry("USA"));  // ""
//            sb.append(dm.databaseGetTownsByCountry("Russia"));  // "Moscow|SpB"
//            request.getSession().setAttribute("want", sb.toString());
            // /test

            request.getSession().setAttribute("message", dm.databaseGetTownsByCountry(country));
            response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/DisplayPersonalInfo.jsp"));
        }
        else if (reqCode.equals("auth")) {
            String login = request.getParameter("login");
            String email = request.getParameter("email");
            String password = request.getParameter("password");

            if(login == null || email == null || password == null)
//                request.getSession().setAttribute("err", true);
                request.getSession().setAttribute("message", "error");

            DatabaseManager dm = new DatabaseManager();

            request.getSession().setAttribute("message", dm.databaseCompareAccount(login, email, password));
            response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/DisplayPersonalInfo.jsp"));
        }
        else if (reqCode.equals("countries")) {
            DatabaseManager dm = new DatabaseManager();

            request.getSession().setAttribute("message", dm.databaseGetCountries());
            response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/DisplayPersonalInfo.jsp"));
        }
        else if (reqCode.equals("places")) {
            String town = request.getParameter("town");

            if(town == null)
//                request.getSession().setAttribute("err", true);
                request.getSession().setAttribute("message", "error");

            DatabaseManager dm = new DatabaseManager();

            request.getSession().setAttribute("message", dm.databaseGetPlaces(town));
            response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/DisplayPersonalInfo.jsp"));
        }
/*
        for (int i = 0; i < usersToHandle.length; i++) {
            if (userName.equals(usersToHandle[i])) {
                request.getSession(true).setAttribute("user", userName);
                long requestCounter =
                        request.getSession().getAttribute("requestNum") == null ?
                                -1 : (long) request.getSession().getAttribute("requestNum");

                request.getSession().setAttribute("requestNum", requestCounter+1);
                request.getSession().setAttribute("lastDate", new Date(request.getSession().getLastAccessedTime()));

                Cookie cookieUser = new Cookie("user", URLEncoder.encode(userName, "UTF-8"));
                cookieUser.setMaxAge(100);
                response.addCookie(cookieUser);

                Cookie cookieColor = new Cookie("color", URLEncoder.encode(colorName, "UTF-8"));
                cookieColor.setMaxAge(100);
                response.addCookie(cookieColor);

                response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/DisplayPersonalInfo.jsp"));
                return;
            }
        }

        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Пользоваель с именем " + userName + " не найден.");
 */
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}