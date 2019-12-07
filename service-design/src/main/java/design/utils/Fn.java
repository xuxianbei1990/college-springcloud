package design.utils;

import java.io.Serializable;
import java.util.function.Function;

/**
 * User: xuxianbei
 * Date: 2019/11/12
 * Time: 10:43
 * Version:V1.0
 */
public interface Fn<T, R> extends Function<T, R>, Serializable {
}