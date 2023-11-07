package com.scicrop.iot.jhikvisionlpr;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class BasicHelper {
	public static String JSON_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static String SQLSERVER_DATETIME_FORMAT = "yyyyMMdd HH:mm:ss";
	public static String HIKVISION_PICTIME = "yyyyMMddHHmmss";
	
	private static BasicHelper INSTANCE = null;
	private BasicHelper(){}
	public static BasicHelper getInstance(){
		if(null == INSTANCE) INSTANCE = new BasicHelper();
		return INSTANCE;
	}

	public String dateToString(Date date, String format){
		String strDate = null;
		try {
			SimpleDateFormat formatter = new SimpleDateFormat(format);
			strDate = formatter.format(date);
		}catch (NullPointerException e) {}
		return strDate;

	}

	public void posixKill(String signal, String pid)  {

		try {
			Runtime runtime = Runtime.getRuntime();
			runtime.exec("kill -"+signal+" "+pid);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Date stringToDate(String dateStr, String format) throws Exception{

		DateFormat dateFormat = new SimpleDateFormat(format, Locale.ENGLISH);
		Date date = null;

		date = dateFormat.parse(dateStr);

		return date;
	}

	public long getDiffHoursBetweenDates(Date init, Date end) {
		long diff = end.getTime() - init.getTime();
		diff = TimeUnit.HOURS.convert(diff, TimeUnit.MILLISECONDS);
		return diff;
	}

	public String readTextFileToString(File source) throws Exception {
		StringBuffer output = null;



		if(source != null && source.exists() && source.isFile()) {
			Path path = source.toPath();
			try {
				List<String> lst = Files.readAllLines(path);
				output = new StringBuffer();
				for (int i = 0; i < lst.size(); i++) {
					output.append(lst.get(i)+"\n");
				}
			} catch (IOException e) {
				throw new Exception("invalid file");
			}
		}else throw new Exception("invalid file");



		return output.toString();
	}

	public void writeStrToFile(String str, String fileName) throws IOException{

		File file = new File(fileName);
		writeStrToFile(str, file);
	}

	
	public void writeStrToFile(String str, File file) throws IOException{

		FileWriter fw = null;
		BufferedWriter out = null;
		try {
			fw = new FileWriter(file);
			out = new BufferedWriter(fw);
			out.write(str);  
		}finally{
			if(out != null)

				out.close();

			if(fw != null)

				fw.close();

		}	
	}
	
	
	public String listToString(List<String> lst) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < lst.size(); i++) {
			if(i + 1 == lst.size()) sb.append(lst.get(i));
			else sb.append(lst.get(i)+", ");
		}
		return sb.toString();
	}

	public List<String> stringToListCli(String defaultRtl433Cli) {
		List<String> lst = new ArrayList<String>();
		defaultRtl433Cli = defaultRtl433Cli.replaceAll("  ", " ");
		String[] rtlStrArray = defaultRtl433Cli.split(" ");
		for (String cliPart : rtlStrArray) {
			lst.add(cliPart);
		}
		return lst;
	}

	public String getAbsoluteRunningPath() {
		Path currentRelativePath = Paths.get("");
		return currentRelativePath.toAbsolutePath().toString();
	}
	
	public String getCurrentPid() {
		String ret = null;
		String jvmName = ManagementFactory.getRuntimeMXBean().getName();
	    int index = jvmName.indexOf('@');
	    ret = Long.toString(Long.parseLong(jvmName.substring(0, index)));
	   
	    
	    return ret;
	}
	
	public String removeSideBlankChars(String source) {
		int init = -1;
		int end = -1;
		for (int i = 0; i < source.length(); i++) {
			if((int)source.charAt(i) == 160 || (int)source.charAt(i) == 32 || (int)source.charAt(i) ==65279 ){
				init = i+1;
			}else break;
		}
		if(init > -1) source = source.substring(init);
		for(int i = source.length()-1; i >=0; i--){
			if((int)source.charAt(i) == 160 || (int)source.charAt(i) == 32 || (int)source.charAt(i) ==65279){
				end = i;
			}else break;
		}
		if(end > -1) source = source.substring(0, end);
		return source;
	}

	public String inputStream2String(InputStream inputStream) {
	    String rString = new BufferedReader(
	      new InputStreamReader(inputStream, StandardCharsets.UTF_8))
	        .lines()
	        .collect(Collectors.joining("\n"));
	    return rString;
	}
}
