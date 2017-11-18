package com.mobilcuzdan.spring.services;

import com.mobilcuzdan.spring.db.UsersDao;
import com.mobilcuzdan.spring.mvc.models.ActionResult;
import com.mobilcuzdan.spring.mvc.models.LoginSignupResponse;
import com.mobilcuzdan.spring.mvc.models.Transaction;
import com.mobilcuzdan.spring.mvc.models.User;
import com.mobilcuzdan.spring.spring.ActiveUserId;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

/**
 * Created by fikricanca on 18.12.2016.
 */

@Component
public class UsersService {
    @Autowired
    UsersDao usersDao;

    public LoginSignupResponse loginWithEmailAndPassword(String email, String passwordClear) {
        User userByEmail = usersDao.getUserByEmail(email);
        LoginSignupResponse loginSignupResponse;
        if (userByEmail != null) {
            boolean passwordMatch = BCrypt.checkpw(passwordClear, userByEmail.getPassword());
            String token = UUID.randomUUID().toString();
            if (passwordMatch) {
                updateUserToken(userByEmail.getId(), token);
                loginSignupResponse = new LoginSignupResponse(new ActionResult(true, "Login başarılı."), userByEmail, token);
                return loginSignupResponse;
            } else {
                loginSignupResponse = new LoginSignupResponse(new ActionResult(false, "Şifrenizi yanlış girdiniz. Lütfen kontrol edip tekrar deneyiniz."));
                return loginSignupResponse;
            }
        } else {
            loginSignupResponse = new LoginSignupResponse(new ActionResult(false, "E-mail adresinizi ya da şifrenizi yanlış girdiniz. Lütfen kontrol edip tekrar deneyiniz."));
            return loginSignupResponse;
        }
    }

    public ActionResult updateUser(User user) {
        ActionResult actionResult;
        try {
            if (StringUtils.isEmpty(user.getName())) {
                user.setName(null);
            }
            if (StringUtils.isEmpty(user.getSurname())) {
                user.setSurname(null);
            }
            if (StringUtils.isEmpty(user.getEmail())) {
                user.setEmail(null);
            }
            if (StringUtils.isEmpty(user.getPhone())) {
                user.setPhone(null);
            }
            if (StringUtils.isEmpty(user.getFbId())) {
                user.setFbId(null);
            }
            if (StringUtils.isEmpty(user.getFbAccessToken())) {
                user.setFbAccessToken(null);
            }
            if (user.getImage().equals("")) {
                user.setImage(null);
            }

            if (usersDao.updateUser(user) > 0) {
                actionResult = new ActionResult(true, "Güncellendi.");
            } else {
                actionResult = new ActionResult(false, "Güncelleme Başarısız.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            actionResult = new ActionResult(false, "Hata oluştu. Sonra tekrar deneyiniz.");
        }
        return actionResult;
    }

    public User getUserById(int userId) {

        return usersDao.getUserById(userId);
    }

    public void deleteUser(Integer id) {
        usersDao.deleteUser(id);
    }
    public List<User> getAllUsers() {
        return usersDao.getAllUsers();
    }

    public User checkIfEmailRegistered(String email) {

        return usersDao.getUserByEmail(email);

    }

    public LoginSignupResponse createUser(String name, String surname, String email, String tcNo, String phone, String image, String fbId, String fbAccessToken, String passwordClear) {

        String hashedPassword = BCrypt.hashpw(passwordClear, BCrypt.gensalt());

        User user = new User();
        user.setName(name);
        user.setSurname(surname);
        user.setEmail(email);
        user.setTcNo(tcNo);
        user.setPhone(phone);
        user.setImage(image);
        user.setFbId(fbId);
        user.setFbAccessToken(fbAccessToken);
        user.setPassword(hashedPassword);

        User userCheckForMail = usersDao.getUserByEmail(user.getEmail());
        if (userCheckForMail != null) {
            return new LoginSignupResponse(new ActionResult(false, "Kayıtlı mail adresi."));
        }

        String token = UUID.randomUUID().toString();

        Integer newUserId = usersDao.createUser(user, token);

        if (newUserId > 0) {
            user.setId(newUserId);
            return new LoginSignupResponse(new ActionResult(true, "Yeni kayıt oluşturuldu."), user, token);
        }else {
            return new LoginSignupResponse(new ActionResult(false, "Kayıt oluşturma başarısız."));
        }
    }

    public User validateTokenAndGetUser(String token) {

        return usersDao.getUserByToken(token);

    }

    public void updateUserToken(int id, String token) {

        usersDao.updateTokenByUserId(id, token);

    }

    public Double getUserPoints(int userId) {

        return usersDao.getUserPoints(userId);

    }

    public ActionResult sendMoneyPoints(int userId, Double amount, String phone) {

        Double userPoints = usersDao.getUserPoints(userId);
        if (userPoints >= amount) {
            if(usersDao.substractPoints(userId, amount) > 0) {
                if (usersDao.transferPoints(amount, phone) > 0) {
                    return new ActionResult(true, "Puan başarıyla gönderilmiştir");
                }else {
                    usersDao.addPoints(userId, amount);
                    return new ActionResult(false,"Puan gönderme işlemi sırasında bir hata oluştu, lütfen tekrar deneyiniz.");
                }
            }else {
                usersDao.addPoints(userId, amount);
                return new ActionResult(false,"Puan gönderme işlemi sırasında bir hata oluştu, lütfen tekrar deneyiniz.");
            }
        }else {
            return new ActionResult(false, "Yeterli paunınız bulunmamaktadır.");
        }

    }

    public ActionResult registerNotification(Integer userId, String notificationId) {

        if (usersDao.updateNotificationIdForUser(userId, notificationId) > 0) {
            return new ActionResult(true, "Bildirim Numarası güncellendi");
        } else {
            return new ActionResult(false, "Başarısız Güncelleme");
        }

    }

    public static String createNotificationSend(String title, String contents, String notificationIds) {
        try {
            String jsonResponse;

            URL url = new URL("https://onesignal.com/api/v1/notifications");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setUseCaches(false);
            con.setDoOutput(true);
            con.setDoInput(true);

            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            con.setRequestProperty("Authorization", "Basic NTA5OTI2OTItMWVhZi00NzQxLWI5YjEtNTQ0NTE5NzNmNTQy");
            con.setRequestMethod("POST");

            String strJsonBody = "{"
                    + "\"app_id\": \"63ea362a-fbe4-40fa-9ebf-66cd65006f91\","
                    + "\"include_player_ids\": [\"" + notificationIds + "\"],"
                    + "\"headings\": {\"en\": \"" + title + "\"},"
                    + "\"contents\": {\"en\": \"" + contents + "\"},"
                    + "\"delivery_time_of_day\": \"" + new SimpleDateFormat("dd.MM.yyyy HH:mm").format(new Date()) + "\""
                    + "}";

            byte[] sendBytes = strJsonBody.getBytes("UTF-8");
            con.setFixedLengthStreamingMode(sendBytes.length);

            OutputStream outputStream = con.getOutputStream();
            outputStream.write(sendBytes);

            Integer httpResponse = con.getResponseCode();
            if (httpResponse >= HttpURLConnection.HTTP_OK
                    && httpResponse < HttpURLConnection.HTTP_BAD_REQUEST) {
                Scanner scanner = new Scanner(con.getInputStream(), "UTF-8");
                jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                scanner.close();
            } else {
                Scanner scanner = new Scanner(con.getErrorStream(), "UTF-8");
                jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                scanner.close();
            }
            return httpResponse.toString() + "-" + jsonResponse;
        } catch (Throwable t) {
            t.printStackTrace();
            return null;
        }
    }

    public void sendNotificationForPointTransfer(String receiverPhone, Double amount, Integer senderId) {

        String receiverNotificationId = usersDao.getUserNotificationIdByPhone(receiverPhone);
        User senderUser = usersDao.getUserById(senderId);
        String content = senderUser.getName() + " " + senderUser.getSurname() + " size " + amount + " para puan gönderdi. Güle güle harcayın :)";

        createNotificationSend("Puan Transferi", content, receiverNotificationId);

    }
}
