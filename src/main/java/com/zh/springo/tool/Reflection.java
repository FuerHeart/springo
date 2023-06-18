package com.zh.springo.tool;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Reflection {

    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchFieldException, ClassNotFoundException {
//        Class<Student> studentClass = Student.class;
        Class<?> studentClass = Class.forName("com.zh.springo.tool.Student");
//        Constructor<Student> constructor = studentClass.getConstructor();
        Constructor<?> constructor = studentClass.getConstructor();
        Student student = (Student) constructor.newInstance();

        Field[] declaredFields = studentClass.getDeclaredFields();

        for (Field declaredField : declaredFields) {
            System.out.println(declaredField.getName());
            Field field = student.getClass().getDeclaredField(declaredField.getName());
            field.setAccessible(true);
            String text = "";
            switch (declaredField.getName()) {
                case "name":
                    text = "曾曾";
                    break;
                case "age":
                    text = "22";
                    break;
                case "address":
                    text = "深圳";
                    break;
            }
            field.set(student, text);
        }

        for (Method declaredMethod : student.getClass().getDeclaredMethods()) {
            System.out.println(declaredMethod);
        }

        Method setName = student.getClass().getDeclaredMethod("setName", String.class);
        setName.setAccessible(true);
        setName.invoke(student, "增增曾曾曾");

        System.out.println(student.toString());
    }
}
