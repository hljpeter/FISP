/*
 * Copyright (c) 2013 Shanghai NTT DATA Synergy Corporation
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions.
 */
package com.synesoft.fisp.app.common.model;

import java.io.Serializable;
import java.util.List;

import com.synesoft.fisp.domain.model.MainParam;

/**
 * @file 	LoginForm.java
 * @author 	Jon
 * @date 	2013-3-31
 * @description TODO
 * @tag 	1.0.0
 * 
 */
public class MainForm implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private List<MainParam> generalList;

	/**
	 * @return the generalList
	 */
	public List<MainParam> getGeneralList() {
		return generalList;
	}

	/**
	 * @param generalList the generalList to set
	 */
	public void setGeneralList(List<MainParam> generalList) {
		this.generalList = generalList;
	}
	
	
	
}
