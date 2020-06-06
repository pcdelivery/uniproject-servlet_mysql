import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.Base64;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(name = "MyServlet", urlPatterns = "/t")
public class MyServlet extends HttpServlet {
    private final static String TAG = "Servlet: ";
    private final static String PATH_IMAGE_PLACE = "arcantown/places/";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html; charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        String reqCode = request.getParameter("want");
        System.out.println("Servlet processing request code: " + reqCode);

        if (reqCode == null) {
            PrintWriter pt = response.getWriter();
            pt.println("NULL_ERROR");
            pt.flush();

        } else if (reqCode.equals("specs")) {
            // Get specs list from arcantown.countries
            PrintWriter pt = response.getWriter();

            String countries = DatabaseManager.getAllSpecsInSQL();

            System.out.println(TAG + "Specs: " + countries);
            pt.println(countries);
            pt.close();

        } else if (reqCode.equals("countries")) {
            // Get country list from arcantown.countries
            PrintWriter pt = response.getWriter();

            String countries = DatabaseManager.getAllCountriesInSQL();

            System.out.println(TAG + "Countries: " + countries);
            pt.println(countries);
            pt.close();

        } else if (reqCode.equals("towns")) {
            // Get towns list from arcantown.towns using country
            String country = request.getParameter("country");
            PrintWriter pt = response.getWriter();

            String towns =
                    country == null ?
                            "false" : DatabaseManager.getAllTownsInSQL(country);

            System.out.println(TAG + "Towns: " + towns);
            pt.println(towns);
            pt.flush();
            pt.close();

        } else if (reqCode.equals("places")) {
            // Get places list from arcantown.places using town and country
            String town = request.getParameter("town");
            String country = request.getParameter("country");
            PrintWriter pt = response.getWriter();

            String places =
                    (country == null || town == null) ?
                            "false" : DatabaseManager.getAllPlacesInSQL(country, town);

            System.out.println(TAG + "Places: " + places);
            pt.println(places);
            pt.flush();
            pt.close();

        } else if (reqCode.equals("account")) {
            // Get account from arcantown.accounts using email
            String email = request.getParameter("email");
            PrintWriter pt = response.getWriter();

            String account =
                    email == null ?
                            "false" : DatabaseManager.getAccountInfoInSQL(email);

            System.out.println(TAG + "Account: " + account);
            pt.println(account);
            pt.flush();
            pt.close();

        } else if (reqCode.equals("put_acc")) {
            String authType = request.getParameter("auth_type");
            String email = request.getParameter("email");
            String login = request.getParameter("login");
            PrintWriter pt = response.getWriter();

            String result =
                    (authType == null || email == null || login == null) ?
                            "false" : DatabaseManager.createAccountInSQL(authType, email, login);

            System.out.println(TAG + "New account creation: " + result);
            pt.println(result);
            pt.flush();
            pt.close();

        } else if (reqCode.equals("quiz")) {
            // Get quiz from arcantown.places and arcantown.questions using placeid
            // TODO
//            String types = request.getParameter("types");
//            String num = request.getParameter("value");
            String placeID = request.getParameter("placeid");
            PrintWriter pt = response.getWriter();

            String quiz =
                    placeID == null ?
                            "false" : DatabaseManager.getQuizInSQL(placeID);

            System.out.println(TAG + "Quiz: " + quiz);
            pt.println(quiz);
            pt.flush();
            pt.close();

        } else if (reqCode.equals("change")) {
            // Get true/false - status of column updating
            String email = request.getParameter("email");
            String field = request.getParameter("field");
            String value = request.getParameter("value");
            PrintWriter pt = response.getWriter();

            String result =
                    (email == null || field == null || value == null) ?
                            "false" : DatabaseManager.changeAccountProperty(email, field, value);

            System.out.println(TAG + "Account update: " + result);
            pt.println(result);
            pt.flush();
            pt.close();

        } else if (reqCode.equals("update_quiz")) {
            // Get true/false - status of columns updating
            String email = request.getParameter("email");
            String placeID = request.getParameter("placeid");
            String points = request.getParameter("points");
            PrintWriter pt = response.getWriter();

            String result =
                    (email == null || placeID == null || points == null) ?
                            "false" : DatabaseManager.appendCompletedQuiz(email, placeID, points);

            System.out.println(TAG + "Quiz account update: " + result);
            pt.println(result);
            pt.flush();
            pt.close();

        } else if (reqCode.equals("load_pic")) {
            //todo try/catch
            String url = request.getParameter("url");
            PrintWriter pt = response.getWriter();

            BufferedImage image = ImageIO.read(new URL(url));

            File file = new File("arcantown/places/tryimageio");
            boolean res = ImageIO.write(image, "png", file);

            if (res)
                System.out.println(TAG + "Success: Image write");
            else
                System.out.println(TAG + "Not success: Image write");

//            pt.println(String.format("<img src=\"%s\" alt=\"Haha. error\" width=\"255\" height=\"255\">",
//                    "tryimageio"));
//            pt.println("<img src=\"%s\" alt=\"Haha. error\" width=\"255\" height=\"255\">");
//            System.out.println(TAG + "PATH: " + request.getContextPath());
//
//            request.getSession().setAttribute("image", "tryimageio.png");
//            response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/DisplayPersonalInfo.jsp"));
//            pt.println("<img src=\"%s\">");

            FileInputStream fs = new FileInputStream(file);
            String line = "";
            byte[] bytes = new byte[(int)file.length()];
            fs.read(bytes);
            fs.close();
            line = Base64.getEncoder().encodeToString(bytes);

            System.out.println(TAG + "Success: Http: " + line);
            String nLine = "";

            for(int i = 0; i < 2000; i++)
                nLine += "a";

//            pt.println(line);
            pt.println(nLine);
            pt.println(nLine);
            pt.println(nLine);
            pt.println(nLine);
            pt.flush();
            pt.close();

//            BufferedInputStream stream = new BufferedInputStream(new URL(url).openStream());
//            ByteArrayOutputStream out = new ByteArrayOutputStream();
//
//            byte[] buf = new byte[1024];
//            int b;
//
//            while ((b = stream.read(buf)) != -1)
//                out.write(buf, 0, b);
//
//            stream.close();
//            out.close();
//            byte[] bres = out.toByteArray();


//            System.out.println(TAG + "Image added: " + result.toString());
//            pt.print(result.toString());
//            pt.close();
        } else if (reqCode.equals("sendpics")) {
            // TODO
            System.out.println("HERE");
            byte buff[] = new byte[23];
            request.getInputStream().read(buff, 23, 23);


            try {
                DataInputStream stream = new DataInputStream(request.getInputStream());

                System.out.println("HEREHERE: " + buff + stream.read(buff));

                stream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

//            DataInputStream stream = new DataInputStream(request.getInputStream());
//            byte[] buffer = new byte[255];
//            ByteArrayInputStream stream = new ByteArrayInputStream(buffer);
//
//            String res = stream.readAllBytes().toString();
//
//            stream.close();

//            String result = "false";
//
//                    System.out.println("PIC GET: " + "String representation: " + res);
//            File picFile = new File("pic1.txt");

//            if (picFile.createNewFile())
//                request.getSession().setAttribute("img", "true");
//            else
//                request.getSession().setAttribute("img", "false");
//            FileWriter fw = new FileWriter("pic1.txt");
//            fw.write(res);
//            fw.close();

//            request.getSession().setAttribute("img", res);

//            if(town == null)
//                request.getSession().setAttribute("err", true);
//                request.getSession().setAttribute("message", "error");

//            request.getSession().setAttribute("message", dm.databaseGetPlaces(town));
//            response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/DisplayPersonalInfo.jsp"));

        /*
        for (int i = 0; i < usersToHandle.length; i++) {
            if (userName.equals(usersToHandle[i])) {
                request.getSession(true).setAttribute("user", userName);
                long requestCounter =)
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
        System.out.println("\t[goGet Method]");
        processRequest(request, response);
    }

    public void doPost (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("\t[goPost Method]");
        processRequest(request, response);
    }
}