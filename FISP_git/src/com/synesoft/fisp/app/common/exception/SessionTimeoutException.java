/*
 * Copyright (c) 2013 Shanghai NTT DATA Synergy Corporation
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions.
 */
package com.synesoft.fisp.app.common.exception;


/**
 * @file 	SessionTimeoutException.java
 * @author 	Jon
 * @date 	2013-4-26
 * @description TODO
 * @tag 	1.0.0
 * 
 */
public class SessionTimeoutException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	

	public SessionTimeoutException() {
		super();
		// TODO Auto-generated constructor stub
	}



	public SessionTimeoutException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}



	public SessionTimeoutException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	

	@Override
	public void printStackTrace() {
		// TODO Auto-generated method stub
	}



	@Override
	public synchronized Throwable fillInStackTrace() {
		// TODO Auto-generated method stub
		return null;
	}

	
}
