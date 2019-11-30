package io.github.yuizho.csv;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.bean.BeanField;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.exceptions.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * A {@link com.opencsv.bean.MappingStrategy} implementation which allows to construct immutable beans containing
 * final fields.
 * <p>
 * It tries to find a constructor with order and types of arguments same as the CSV lines and construct the bean using
 * this constructor. If not found it tries to use the default constructor.
 * <p>
 * https://stackoverflow.com/questions/53667832/using-opencsv-how-can-we-map-a-record-to-a-class-that-uses-a-builder-and-not-se
 *
 * @param <T> Type of the bean to be returned
 */
public class ImmutableColumnPositionMappingStrategy<T> extends ColumnPositionMappingStrategy<T> {

    /**
     * Constructor
     *
     * @param type Type of the bean which will be returned
     */
    public ImmutableColumnPositionMappingStrategy(Class<T> type) {
        setType(type);
    }

    @Override
    public T populateNewBean(String... line)
            throws CsvBeanIntrospectionException,
            CsvRequiredFieldEmptyException,
            CsvDataTypeMismatchException,
            CsvConstraintViolationException,
            CsvValidationException {
        verifyLineLength(line.length);

        try {
            return newInstanceWithParameter(line);
        } catch (NoSuchMethodException |
                IllegalAccessException |
                InvocationTargetException |
                InstantiationException e) {
            return super.populateNewBean(line);
        }
    }

    private T newInstanceWithParameter(String... line)
            throws NoSuchMethodException,
            IllegalAccessException,
            InvocationTargetException,
            InstantiationException {
        Constructor<? extends T> constructor = findSuitableConstructor(line.length);
        Object[] arguments = prepareArguments(line);
        return constructor.newInstance(arguments);
    }

    private Constructor<? extends T> findSuitableConstructor(int columns) throws NoSuchMethodException {
        List<Class<?>> types = new ArrayList<>();
        for (int i = 0; i < columns; i++) {
            BeanField<T, Integer> field = findField(i);
            Class<?> type = field.getField().getType();
            types.add(type);
        }
        return type.getDeclaredConstructor(types.toArray(new Class<?>[0]));
    }

    private Object[] prepareArguments(String... line) {
        List<Object> arguments = new ArrayList<>();
        int len = line.length;
        for (int i = 0; i < len; i++) {
            arguments.add(prepareArgument(i, line[i], findField(i)));
        }
        return arguments.toArray();
    }

    private Object prepareArgument(int index, String value, BeanField<T, Integer> beanField) {
        Field field = beanField.getField();

        // empty value for primitive type would be converted to null which would throw an NPE
        // TODO: numberなら0, booleanならfalse入れたほうが良い気もするが。。。
        if ("".equals(value) && field.getType().isPrimitive()) {
            throw new IllegalArgumentException(
                    String.format("Null value for primitive field '%s'", headerIndex.getByPosition(index))
            );
        }

        try {
            // reflectively access the convert method, as it's protected in AbstractBeanField class
            Method convertMethod = AbstractBeanField.class.getDeclaredMethod("convert", String.class);
            convertMethod.setAccessible(true);
            // ここでBeanFieldの実装(BeanFieldSingleValueなど)のconvertメソッドが実行される
            return convertMethod.invoke(beanField, value);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new IllegalStateException(
                    String.format("Unable to convert bean field '%s'", headerIndex.getByPosition(index)), e
            );
        }
    }
}