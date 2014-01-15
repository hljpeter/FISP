/**
 * 
 */
package com.synesoft.fisp.app.common.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 非空检查验证器
 * @author yyw
 *
 */
public class NotEmptyExValidation implements ConstraintValidator<NotEmptyEx, Object>{

	/* (non-Javadoc)
	 * @see javax.validation.ConstraintValidator#initialize(java.lang.annotation.Annotation)
	 */
	@Override
	public void initialize(NotEmptyEx arg) {
//		this.value = arg.value();
	}

	/* (non-Javadoc)
	 * @see javax.validation.ConstraintValidator#isValid(java.lang.Object, javax.validation.ConstraintValidatorContext)
	 */
	@Override
	public boolean isValid(Object arg0, ConstraintValidatorContext arg1) {
		String value = String.valueOf(arg0).trim();
		
		if (null == value || ("").equals(value)) {
			return false;
		}
		
		return true;
	}


}
