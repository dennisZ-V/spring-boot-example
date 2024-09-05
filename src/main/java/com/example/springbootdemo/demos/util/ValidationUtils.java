package com.example.springbootdemo.demos.util;

import com.example.springbootdemo.demos.annotation.NotEmpty;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangdi
 * @date 2024/9/5 11:43
 */
public class ValidationUtils {

    public static void validate(Object object) throws IllegalAccessException {
        Class<?> clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();
        List<String> emptyFields = new ArrayList<>();
        for (Field field : fields) {
            if (field.isAnnotationPresent(NotEmpty.class)) {
                field.setAccessible(true);
                Object value = field.get(object);
                if (value == null || (value instanceof String && ((String) value).trim().isEmpty())) {
                    // 提供具体字段名和错误信息
                    emptyFields.add(field.getName());
                }
            }
        }

        if (!CollectionUtils.isEmpty(emptyFields)) {
            String fieldsList = String.join(", ", emptyFields);
            String errorMessage = String.format("Fields [%s] %s", fieldsList, "cannot be empty");
            throw new IllegalArgumentException(errorMessage);
        }
    }
}