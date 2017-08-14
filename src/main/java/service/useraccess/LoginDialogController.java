package service.useraccess;

import database.UserDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.user.User;
import service.useraccess.permission.PermissionAction;
import org.apache.commons.codec.digest.DigestUtils;

public class LoginDialogController {

    @FXML
    private TextField login;
    @FXML
    private PasswordField password;

    public User loginIntoTheSystem() {
        User inputUser = getUserFromDialog();
        User loggedUser = inputUser;

        if ( checkUser(inputUser)) {
            loggedUser = getUserPermission(inputUser);
            System.out.println(loggedUser.toString());
        } else  {
            Alert alert = new Alert(Alert.AlertType.ERROR);

            alert.setTitle("Login Error");
            alert.setHeaderText("Login Failed. Wrong input.");
            alert.setContentText("Invalid login or password. Try again.");
            alert.show();
        }

        return loggedUser;
    }

    private User getUserFromDialog() {
        User loginUser = new User();
        loginUser.setUserName(login.getText());
        loginUser.setPassword(DigestUtils.md5Hex(password.getText()));
        loginUser.setPermission(PermissionAction.REFUSE);

        System.out.println(loginUser.toString());
        return loginUser;
    }

    private boolean checkUser(User user) {
        return UserDAO.queryCheckUser(user);
    }

    private User getUserPermission(User user) {
        return UserDAO.queryGetUserPermission(user);
    }
}
