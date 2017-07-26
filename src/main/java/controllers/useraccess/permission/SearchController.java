package controllers.useraccess.permission;

import model.user.User;

public abstract class SearchController implements ReadWritePermissionInterface {

    @Override
    @MyPermission(value = {PermissionAction.READ_WRITE})
    public abstract void showButtons(User user);
}
