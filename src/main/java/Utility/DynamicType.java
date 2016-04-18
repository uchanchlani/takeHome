package Utility;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by utkarshc on 16/4/16.
 */
public class DynamicType <X, Y> implements ParameterizedType {
    private final Class<?> container;
    private final Class<?> wrapped;

    public DynamicType(Class<X> container, Class<Y> wrapped) {
        this.container = container;
        this.wrapped = wrapped;
    }

    public Type[] getActualTypeArguments() {
        return new Type[]{wrapped};
    }

    public Type getRawType() {
        return container;
    }

    public Type getOwnerType() {
        return null;
    }

}
