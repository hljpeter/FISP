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
 * 文件名规则校验
 * @author yyw
 *
 */
@Documented  
@Retention(RetentionPolicy.RUNTIME)   
@Target({ElementType.ANNOTATION_TYPE, ElementType.FIELD})   
@Constraint(validatedBy = FileNameRuleValidation.class)  
public @interface FileNameRule {
	String message() default "{The file name cannot match the rule}";
	Class<?>[] groups() default{};
	Class<? extends Payload>[] payload() default{};

}
