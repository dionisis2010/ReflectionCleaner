package ru.homework4;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;

public class ReflectionCleaner {

    /**
     * если переданный объект является мапой то удаляются все элементы соответсвующие ключам указанным в fieldsToCleanup и
     * выводятся в консоль все элементы соответсвующие ключам указанным в fieldsToOutput.
     * если полученный объект не являетс я мапой, то удаляются все его поля указанные в fieldsToCleanup и
     * выводятся занчения полей указанных в fieldsToOutput.
     * метод работает только с публичными полями объекта полученного параметром и публичными полями его родителей
     *
     * @param object          объект с полями которого работает метод
     * @param fieldsToCleanup названия полей (ключи для мапы) предназначенные для удаления
     * @param fieldsToOutput  азвания полей (ключи для мапы) предназначенные для печати
     * @throws IllegalAccessException бросается если в полученнной мапе нет указзых ключей
     */
    public void cleanup(Object object, Set<String> fieldsToCleanup, Set<String> fieldsToOutput) throws IllegalAccessException {
        Field[] fields = object.getClass().getFields();

        if (isMap(object)) {
            final Map map = (Map) object;
            removeEntries(map, fieldsToCleanup);
            printEntries(map, fieldsToOutput);
        } else {
            cleanFields(object, fieldsToCleanup, fields);
            printFields(object, fieldsToOutput, fields);
        }
    }

    private void printEntries(Map map, Set<String> fieldsToOutput) {
        for (String str : fieldsToOutput) {
            if (map.containsKey(str)) {
                System.out.println(str + " = " + map.get(str));
            } else {
                throw new IllegalArgumentException("в мапе нет ключа: " + str);
            }
        }
    }

    private void removeEntries(Map map, Set<String> fieldsToCleanup) {
        for (String str : fieldsToCleanup) {
            if (map.containsKey(str)) {
                map.remove(str);
            } else {
                throw new IllegalArgumentException("в мапе нет ключа: " + str);
            }
        }
    }

    private void printFields(Object object, Set<String> fieldsToOutput, Field[] fields) throws IllegalAccessException {
        for (Field field : fields) {
            if (fieldsToOutput.contains(field.getName())) {
                printField(object, field);
            }
        }
    }

    private void printField(Object object, Field field) throws IllegalAccessException {
        if (field.getClass().isPrimitive()) {
            switch (field.getType().toString()) {
                case "byte":
                    System.out.println(field.getByte(object));
                    break;
                case "short":
                    System.out.println(field.getShort(object));
                    break;
                case "int":
                    System.out.println(field.getInt(object));
                    break;
                case "long":
                    System.out.println(field.getLong(object));
                    break;
                case "float":
                    System.out.println(field.getFloat(object));
                    break;
                case "double":
                    System.out.println(field.getDouble(object));
                    break;
                case "boolean":
                    System.out.println(field.getBoolean(object));
                    break;
                case "char":
                    System.out.println(field.getChar(object));
                    break;
                default:
                    throw new EverythingIsBadException("это не должно выполняться никогда");
            }
        } else {
            System.out.println(field);
        }
    }

    private void cleanFields(Object object, Set<String> fieldsToCleanup, Field[] fields) throws IllegalAccessException {
        for (Field field : fields) {
            if (fieldsToCleanup.contains(field.getName())) {
                cleanField(object, field);
            }
        }
    }

    private void cleanField(Object object, Field field) throws IllegalAccessException {
        if (field.getType().isPrimitive()) {
            switch (field.getType().toString()) {
                case "byte":
                    field.setByte(object, (byte) 0);
                    break;
                case "short":
                    field.setShort(object, (short) 0);
                    break;
                case "int":
                    field.setInt(object, (int) 0);
                    break;
                case "long":
                    field.setLong(object, (long) 0);
                    break;
                case "float":
                    field.setFloat(object, (float) 0);
                    break;
                case "double":
                    field.setDouble(object, (double) 0);
                    break;
                case "boolean":
                    field.setBoolean(object, false);
                    break;
                case "char":
                    field.setChar(object, '\u0000');
                    break;
                default:
                    throw new EverythingIsBadException("это не должно выполняться никогда");
            }
        } else {
            if (isBoolean(field)) {
                field.set(object, false);
            } else if (isNumber(field)) {
                field.set(object, 0);
            } else if (isCharacter(field)) {
                field.set(object, '\u0000');
            } else {
                field.set(object, null);
            }
        }
    }

    private boolean isMap(Object object) {
        Class[] intrefaces = object.getClass().getInterfaces();
        for (Class inter : intrefaces) {
            if (inter.getName().equals("java.util.Map")) {
                return true;
            }
        }
        return false;
    }

    private boolean isNumber(Field field) {
        return field.getType().equals(Integer.class) ||
                field.getType().equals(Short.class) ||
                field.getType().equals(Byte.class) ||
                field.getType().equals(Long.class) ||
                field.getType().equals(Double.class) ||
                field.getType().equals(Float.class);
    }

    private boolean isBoolean(Field field) {
        return field.getType().equals(Boolean.class);
    }

    private boolean isCharacter(Field field) {
        return field.getType().equals(Character.class);
    }
}

