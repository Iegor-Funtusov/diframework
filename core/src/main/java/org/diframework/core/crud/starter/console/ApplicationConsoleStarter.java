package org.diframework.core.crud.starter.console;

import org.diframework.core.storage.BeanStorage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public class ApplicationConsoleStarter {

    private final Map<Integer, ConsoleControllerHelper> map = new HashMap<>();

    public ApplicationConsoleStarter() {
        List<Object> controllers = BeanStorage.getInstance().getBeanMap().values()
                .stream().filter(obj -> obj.getClass().getName().endsWith("ControllerImpl"))
                .collect(Collectors.toList());
        for (int i = 0; i < controllers.size(); i++) {
            Integer pos = i + 1;
            map.put(pos, getAllMethodsByController(controllers.get(i)));
        }
    }

    public void start() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("select your option");
        String position;
        try {
            runNavigation();
            while ((position = reader.readLine()) != null) {
                navigate(position, reader);
                position = reader.readLine();
                if (position.equals("0")) {
                    System.exit(0);
                }
                navigate(position, reader);
            }
        } catch (IOException e) {
            System.out.println("problem: = " + e.getMessage());
        }
    }

    private void runNavigation() {
        this.map.forEach((k, v) -> {
            System.out.println();
            System.out.println("if you want " + v.getController().getClass().getSimpleName() + ", please enter " + k);
            System.out.println();
        });
    }

    private void runControllerNavigation(Map<Integer, Method> methods) {
        methods.forEach((k, v) -> System.out.println("if you want " + v.getName() + ", please enter " + k));
    }

    private void navigate(String position, BufferedReader reader) {
        Integer i = Integer.parseInt(position);
        ConsoleControllerHelper controllerHelper = this.map.get(i);
        runControllerNavigation(controllerHelper.getMethods());
        try {
            Integer pos = Integer.parseInt(reader.readLine());
            Method method = controllerHelper.getMethods().get(pos);
            Object o = method.invoke(controllerHelper.getController(), reader);
            System.out.println("o = " + o);
        } catch (IOException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        runNavigation();
    }

    private ConsoleControllerHelper getAllMethodsByController(Object controller) {
        Map<Integer, Method> map = new HashMap<>();
        Method[] methods = controller.getClass().getDeclaredMethods();
        for (int i = 0; i < methods.length; i++) {
            int methodPosition = i + 1;
            map.put(methodPosition, methods[i]);
        }
        return new ConsoleControllerHelper(map, controller);
    }
}
