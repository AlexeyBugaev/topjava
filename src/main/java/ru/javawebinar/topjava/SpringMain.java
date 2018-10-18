package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.time.LocalDateTime;
import java.util.Arrays;

public class SpringMain {
    public static void main(String[] args) {
        // java 7 Automatic resource management
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            adminUserController.create(new User(null, "userName", "email@mail.ru", "password", Role.ROLE_ADMIN));
            adminUserController.create(new User(null, "Boshirov", "Boshirov@GRU.ru", "password", Role.ROLE_USER));
            adminUserController.create(new User(null, "Petrov", "Petrov@GRU.ru", "password", Role.ROLE_USER));
            System.out.println(adminUserController.getAll());
            MealRestController mealRestController = (MealRestController) appCtx.getBean(MealRestController.class);
            System.out.println(mealRestController.getAll());
            mealRestController.create(new Meal(LocalDateTime.now(),"Breakfast",800));
            System.out.println(mealRestController.getAll());
            System.out.println(mealRestController.get(7));
        }
    }
}
