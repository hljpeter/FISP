/**
 * 
 */
package com.synesoft.fisp.app.common.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 文件名规则校验验证器
 * @author yyw
 *
 */
public class FileNameRuleValidation implements ConstraintValidator<FileNameRule, String>{

	
	/* (non-Javadoc)
	 * @see javax.validation.ConstraintValidator#initialize(java.lang.annotation.Annotation)
	 */
	@Override
	public void initialize(FileNameRule arg) {

	}

	/* (non-Javadoc)
	 * @see javax.validation.ConstraintValidator#isValid(java.lang.Object, javax.validation.ConstraintValidatorContext)
	 */
	@Override
	public boolean isValid(String arg0, ConstraintValidatorContext arg1) {
		return FileNameRuleValidator.checkName(arg0);
	}

}
