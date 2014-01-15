/*
 * Copyright (c) 2013 Shanghai NTT DATA Synergy Corporation
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions.
 */
package com.synesoft.fisp.app.sm.model;

import java.io.Serializable;

public class PwdChgForm implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String oldPwd ;
	
	private String newPwd ;
	
	private String newPwdAgain ;

	public String getOldPwd() {
		return oldPwd;
	}

	public void setOldPwd(String oldPwd) {
		this.oldPwd = oldPwd;
	}

	public String getNewPwd() {
		return newPwd;
	}

	public void setNewPwd(String newPwd) {
		this.newPwd = newPwd;
	}

	public String getNewPwdAgain() {
		return newPwdAgain;
	}

	public void setNewPwdAgain(String newPwdAgain) {
		this.newPwdAgain = newPwdAgain;
	}

	
}
