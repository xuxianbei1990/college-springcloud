package college.springcloud.elastic.autobuild;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EsMark {
	BuildPolicy policy();
	String field() default "[unassigned]";
	String[] fields() default "[unassigned]";
}
