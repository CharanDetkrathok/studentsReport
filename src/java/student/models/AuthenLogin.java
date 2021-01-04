package student.models;

import java.io.*;
import java.net.*;

import java.util.HashMap;
import java.util.Map;
import java.math.BigDecimal;
import java.util.Base64;
import javax.servlet.http.HttpSession;

import lombok.Data;

@Data
public class AuthenLogin {

    private final static String AUTH_URL = "http://202.41.160.113:1323/login?";

    public AuthenLogin() {

    }

    public String autLogin(String username, String password) {
        try {
            URL url = new URL(AUTH_URL);

            HashMap<String, String> params = new HashMap<String, String>();
            params.put("username", username);
            params.put("password", password);
//            System.out.println("params => " + params);

            StringBuilder postData = new StringBuilder();
            for (Map.Entry<String, String> param : params.entrySet()) {
                if (postData.length() != 0) {
                    postData.append('&');
                }
                postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                postData.append('=');
                postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
            }
//            System.out.println("postData => " + postData);
            byte[] postDataBytes = postData.toString().getBytes("UTF-8");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
            conn.setDoOutput(true);
            conn.getOutputStream().write(postDataBytes);
//            System.out.println("conn => " + conn);

            int responseCode = conn.getResponseCode();
            //out.println("POST Response Code :: " + responseCode);
//            System.out.println("responseCode => " + responseCode);
            
            if (responseCode == HttpURLConnection.HTTP_OK) { //success
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        conn.getInputStream()));
                String inputLine;
                StringBuffer r = new StringBuffer();
                String tmp = null;
                while ((inputLine = in.readLine()) != null) {
                    tmp = inputLine;
                    r.append(inputLine);
                }
                in.close();
//                System.out.println("tmp => " + tmp);
                // print result
                return tmp;

                //return true;
                // return Integer.toString(responseCode);
                //out.println(r.toString());
            } else {
                //return false;
                return "0";
                //out.println("WRONG PASSWORD ");
            }

        } catch (Exception e) {

            return "0";
        }
    }

    public String decodeparam(String param) {
        byte[] decodedstr = Base64.getDecoder().decode(param);
        String str = new String(decodedstr);
        return str;

    }
}
