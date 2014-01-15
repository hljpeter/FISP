/**
 * 
 */
package com.synesoft.fisp.app.common.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * 非空检查，类型为String时
 * 不能为null，不能只有空格
 * @author yyw
 *
 */
@Documented  
@Retention(RetentionPolicy.RUNTIME)   
@Target({ElementType.ANNOTATION_TYPE, ElementType.FIELD, ElementType.METHOD})   
@Constraint(validatedBy = NotEmptyExValidation.class)  
public @interface NotEmptyEx {
	String message() default "{Not Empty}";
	Class<?>[] groups() default{};
	Class<? extends Payload>[] payload() default{};

}
