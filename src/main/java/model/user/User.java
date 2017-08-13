package model.user;


import service.useraccess.permission.PermissionAction;

public class User {
    private String userName;
    private String password;
    private PermissionAction permission;

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", permission=" + permission +
                '}';
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public PermissionAction getPermission() {
        return permission;
    }

    public void setPermission(PermissionAction permission) {
        this.permission = permission;
    }
}
