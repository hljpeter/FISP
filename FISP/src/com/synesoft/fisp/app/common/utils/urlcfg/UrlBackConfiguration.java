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
 * @file 	UrlBackConfiguration.java
 * @author 	Jon
 * @date 	2013-5-13
 * @description TODO
 * @tag 	1.0.0
 * 
 */
public class UrlBackConfiguration {

	private List<UrlCfg> urlList = new ArrayList<UrlCfg>();
	
	private List<UrlCfgs> urlCfgs = new ArrayList<UrlCfgs>();

	public List<UrlCfg> getUrlList() {
		return urlList;
	}

	public void setUrlList(List<UrlCfg> urlList) {
		this.urlList = urlList;
	}

	public List<UrlCfgs> getUrlCfgs() {
		return urlCfgs;
	}

	public void setUrlCfgs(List<UrlCfgs> urlCfgs) {
		this.urlCfgs = urlCfgs;
	}

	

}
