/*
 * Copyright (c) 2013 Shanghai NTT DATA Synergy Corporation
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions.
 */
package com.synesoft.fisp.app.common.utils.urlcfg;

import java.util.ArrayList;
import java.util.List;

/**
 * @file 	UrlCfgs.java
 * @author 	Jon
 * @date 	2013-5-13
 * @description TODO
 * @tag 	1.0.0
 * 
 */
public class UrlCfgs {
	
	private String id = "";
	
	private List<UrlCfg> urlCfgList = new ArrayList<UrlCfg>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<UrlCfg> getUrlCfgList() {
		return urlCfgList;
	}

	public void setUrlCfgList(List<UrlCfg> urlCfgList) {
		this.urlCfgList = urlCfgList;
	}

}
