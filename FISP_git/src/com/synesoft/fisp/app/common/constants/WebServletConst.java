/*
 * Copyright (c) 2013 Shanghai NTT DATA Synergy Corporation
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions.
 */
package com.synesoft.fisp.app.common.constants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.synesoft.fisp.app.common.utils.urlcfg.UrlBackConfiguration;
import com.synesoft.fisp.app.common.utils.urlcfg.UrlCfg;

/**
 * @file 	WebServletConst.java
 * @author 	Jon
 * @date 	2013-5-13
 * @description TODO
 * @tag 	1.0.0
 * 
 */
public class WebServletConst {

	public static UrlBackConfiguration ubcfg = new UrlBackConfiguration();
	
	public static Map<String, String> urlMap = new HashMap<String, String>();
	
	public static Map<String, List<UrlCfg>> urlCfgMap = new HashMap<String, List<UrlCfg>>();
	
}
