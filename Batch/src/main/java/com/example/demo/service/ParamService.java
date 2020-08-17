package com.example.demo.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.http.HttpRequest;
import org.apache.http.client.HttpClient;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


import com.example.demo.Model.Paramgroup;
import com.example.demo.Model.Paraminfo;

@Service
public class ParamService {
	private Logger log = LoggerFactory.getLogger(getClass());
	private BufferedReader br;
	
	public void readFile(){
		
		List<Paramgroup> paramgroups = new ArrayList<>();
		List<Paraminfo> paraminfos = new ArrayList<>();
		
		ObjectMapper mapper = new ObjectMapper();

		
		try {
			String filePath = "E:\\test\\PARAM";
			File file = new File(filePath);
			br = new BufferedReader(new FileReader(file));

			
			String st;
			int line = 0;
			while ((st = br.readLine()) != null) {
//				log.info("read line {} : {} ", line, st);
				if(!StringUtils.isEmpty(st)) {
					
					String[] strLine = st.split(Pattern.quote("|"));

					Paraminfo paraminfo = new Paraminfo();
					Paramgroup paramgroup = new Paramgroup();
					
					String paramGroupCode = strLine[0];
					String paramInfoCode = strLine[1];
					String value1 = strLine[2];
					String value2 = strLine[3];

					if(line > 0) {
						
						if(paramgroups.isEmpty()) {
							
							paraminfo.setParamInfoCode(paramInfoCode);
							paraminfo.setParamInfoValue(value1);
							paraminfo.setParamInfoValue2(value2);
							paraminfo.setParamGroupCode(paramGroupCode);
							
							paraminfos.add(paraminfo);
							
							paramgroup.setParamGroupCode(paramGroupCode);
							paramgroup.setInfoList(paraminfos);
							
							paramgroups.add(paramgroup);
						} else {
							boolean b = false;
							for(Paramgroup group : paramgroups) {

								if(group.getParamGroupCode().equals(paramGroupCode)) {
									b = true;
									paraminfo.setParamInfoCode(paramInfoCode);
									paraminfo.setParamInfoValue(value1);
									paraminfo.setParamInfoValue2(value2);
									paraminfo.setParamGroupCode(paramGroupCode);
									
									group.getInfoList().add(paraminfo);		
									break;
								}	
							}
							if(!b) {
								paraminfos = new ArrayList<>(); //WS_CONFIG
								
								paraminfo.setParamInfoCode(paramInfoCode);
								paraminfo.setParamInfoValue(value1);
								paraminfo.setParamInfoValue2(value2);
								paraminfo.setParamGroupCode(paramGroupCode);
								paraminfos.add(paraminfo);
						
								paramgroup.setParamGroupCode(paramGroupCode);
								paramgroup.setInfoList(paraminfos);
								paramgroups.add(paramgroup);
	
							}
						}
						

					}
				}
				line ++;	
			}

			String jsonInString = mapper.writeValueAsString(paramgroups);			
			System.out.println(jsonInString);

			for(Paramgroup group : paramgroups) {
				log.info("group--> " + group.getParamGroupCode());
			}	

			URL url = new URL ("http://localhost:8081/api/saveparamgroup");
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/json; utf-8");
			con.setRequestProperty("Accept", "application/json");
			con.setDoOutput(true);
			try(OutputStream os = con.getOutputStream()) {
			    byte[] input = jsonInString.getBytes("utf-8");
			    os.write(input, 0, input.length);
			}
//			InputStream in = con.getInputStream();
			
			try(BufferedReader br = new BufferedReader(
				  new InputStreamReader(con.getInputStream(), "utf-8"))) {
				    StringBuilder response = new StringBuilder();
				    String responseLine = null;
				    while ((responseLine = br.readLine()) != null) {
				        response.append(responseLine.trim());
				    }
				    System.out.println(response.toString());
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}		
//		return paramgroups;		
	}
	
	public void postGroup() {
		
	}
}
