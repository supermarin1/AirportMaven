package controllers.useraccess.permission;

import controllers.AirportViewController;
import model.user.User;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

public class ActionProxy implements InvocationHandler {
    private Object obj;

    private ActionProxy(Object obj) {
        this.obj = obj;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = null;

        Method loginMethod = AirportViewController.class.getMethod("showMenu", User.class);
        Method permissionMethod = SearchController.class.getMethod("showButtons", User.class);

        //taking user permission
        for (Object arg : args) {
            Field field = arg.getClass().getDeclaredField("permission");
            field.setAccessible(true);
            PermissionAction userPerm = (PermissionAction) field.get(arg);
            field.setAccessible(false);


            if (method.getName().equals(loginMethod.getName())) {
                //taking method annotation
                MyPermission mainAnn = AirportViewController.class.getDeclaredMethod(method.getName(), User.class)
                        .getAnnotation(MyPermission.class);
                PermissionAction[] mainPerm = mainAnn.value();

                if (Arrays.asList(mainPerm).contains(userPerm)) {
                    result = method.invoke(obj, args);
                } else {
                    System.out.println("You are not allowed");
                }
            }

            if (method.getName().equals(permissionMethod.getName())) {
                //taking method annotation
                MyPermission flightAnn = SearchController.class.getDeclaredMethod(method.getName(), User.class)
                        .getAnnotation(MyPermission.class);
                PermissionAction[] flightPerm = flightAnn.value();

                if (Arrays.asList(flightPerm).contains(userPerm)) {
                    result = method.invoke(obj, args);
                } else {
                    System.out.println("You are not allowed");
                }

            }
        }
        return result;
    }

    public static <T> T newInstance(Object objAction) {
        return (T) Proxy.newProxyInstance(objAction.getClass().getClassLoader(),
                objAction.getClass().getInterfaces(),
                new ActionProxy(objAction));
    }
}