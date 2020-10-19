import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.*;

import java.util.HashMap;
import java.util.Map;

public class Login implements HttpHandler {


        @Override
        public void handle(HttpExchange httpExchange) throws IOException {

            Stock stock = new Stock();
            String response = "";
            String method = httpExchange.getRequestMethod();


            if(method.equals("GET")){

                response = "<html>" + "<head>\n" +
                        "</head>" +
                        "<body>" +
                        "<form id=\"submit\" method=\"POST\">\n" +
                        "  User Name:<br>\n" +
                        "  <input id=\"userLogin\" type=\"text\" name=\"username\" value=\"Login\">\n" +
                        "  <br>\n" +
                        "  Password:<br>\n" +
                        "  <input id=\"password\" type=\"text\" name=\"password\" value=\"Password\">\n" +
                        "  <br><br>\n" +
                        "  <input type=\"submit\" value=\"Log In\">\n" +
                        "</form> " +
                        "</body>" +
                        "<script>\n" +
                        " const user = document.cookie;\n" +
                        " console.log(user);\n" +
                        "for (let i = 0; i < "+ stock.getKeysValues().size() + "; i++) {\n" +
                            "  let cookieUser = user.split(\' \')[i];\n" +
                            "let pairs = " + stock.getKeysValues()+ ";\n"+
                            "pairs.forEach(function(entry) {\n" +
                                "if(cookieUser===entry){\n" +
                                    "document.getElementById(\'userLogin\').value = cookieUser.split(\'=\')[0]; \n" +
                                    "document.getElementById(\'password\').value = cookieUser.split(\'=\')[1]; \n" +
                                    "document.getElementById(\"submit\").submit();\n" +
                                    ";}\n" +
//                                "console.log(entry);\n" +
                            "});" +

                        "}\n" +


                        "function reloadPage() {\n" +
                        " location.reload(); " +
                        "}\n" +
                        "function myFunction() { }" +
                        "</script>\n" +
                        "</html>";
            }

            if(method.equals("POST")){
                InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
                BufferedReader br = new BufferedReader(isr);
                String formData = br.readLine();

                Map<String, String> inputs = parseFormData(formData);
                Map<String, String> usersFromStock = stock.getUsers();

                if(usersFromStock.containsKey(inputs.keySet().toArray()[0])){
                    if(inputs.get(inputs.keySet().toArray()[0]).equals(usersFromStock.get(inputs.keySet().toArray()[0]))){
                        response = "<html>" + "<script>\n" +
                                "function deleteCookie() {\n" +
                                "document.cookie = \"" + inputs.keySet().toArray()[0] + "=;\";" +
                                " window.location.replace(location); }" +
                                "document.cookie = \"" +
                                inputs.keySet().toArray()[0] +
                                "=" + inputs.get(inputs.keySet().toArray()[0]) +
                                "\";\n" +
                                "</script>" +

                                "<body>" +
                                "<h1>Hello " +
                                inputs.keySet().toArray()[0] + " " +
                                "!</h1>" +
                                "<br><br>\n" +
                                "<button type=\"button\" onclick=\"deleteCookie()\">LOG OUT</button>\n" +

                                "</body><html>";

                    }
                    else response = "<script>\n" +
                            "function reloadPage() {\n" +
                            " window.location.replace(location); " +
                            "}\n" +
                            "</script>" +"<h1> Wrong password! </h1> "
                            + "<button type=\"button\" onclick=\"reloadPage()\">Try again</button>\n";



                }
                else response = "<script>\n" +
                        "function reloadPage() {\n" +
                        " window.location.replace(location); " +
                        "}\n" +
                        "</script>" +"<h1> No such user in stock </h1> "
                        + "<button type=\"button\" onclick=\"reloadPage()\">Try again</button>\n";


            }

            httpExchange.sendResponseHeaders(200, response.length());
            OutputStream os = httpExchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }


    private static Map<String, String> parseFormData(String formData) throws UnsupportedEncodingException {
        Map<String, String> map = new HashMap<>();
        String[] pairs = formData.split("&");
        String user = pairs[0].split("=")[1];
        String pass = pairs[1].split("=")[1];
        map.put(user, pass);
        return map;
    }


}

