package com.edu.csuf.app.utility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.json.JSONArray;
import org.springframework.stereotype.Service;

@Service
public class Utilities {
	public Date generateTodaysDate(){
		java.util.Date utilDate = new java.util.Date();
		java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
		System.out.println(sqlDate);
		return sqlDate;
	}

	public String currentDateTime(){
		java.util.Date dt = new java.util.Date();
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String currentTime = sdf.format(dt);
		return currentTime;
	}

	public JSONArray executeCppCode(String command){
		List<String> cmdList = new ArrayList<>();
		JSONArray outputList = new JSONArray();
		try{

			//ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", "cd \"E:\\codeRunner\\assignments\" && g++ -o nishant a1.cpp && nishant");
			cmdList.add("cmd.exe");
			cmdList.add("/c");
			cmdList.add(command);
			ProcessBuilder builder = new ProcessBuilder(cmdList);
			builder.redirectErrorStream(true);
			Process p = builder.start();
			// exit value to check gcc program worked or not with 0 and non zero
			System.out.println(builder.command().toString());
			BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line;
			while (true) {
				line = r.readLine();
				if (line == null) { break; }
				outputList.put(line);
			}
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(outputList.toString());

		return outputList;
	}

	public int getCurrentYear(){
		int year = Calendar.getInstance().get(Calendar.YEAR);
		return year;
	}

	public boolean createFolder(String folderPath){
		File files = new File(folderPath);
		boolean flag=false;
		if (!files.exists()) {
			if (files.mkdirs()) {
				System.out.println("Multiple directories are created!");
				flag=true;
			} else {
				System.out.println("Failed to create multiple directories!");
			}
		}
		return flag;
	}

	public String createPath(int year, int sem, long uId, int cId){
		StringBuilder sb = new StringBuilder();
		sb.append("E:\\codeRunner\\assignments");
		sb.append("\\");
		sb.append(String.valueOf(year));
		sb.append("\\");
		sb.append(String.valueOf(sem));
		sb.append("\\");
		sb.append(String.valueOf(uId));
		sb.append("\\");
		sb.append(String.valueOf(cId));

		return sb.toString();
	}

	public boolean fileExistsThenDelete(String filePath){
		File file = new File(filePath);
		boolean flag = false;
		if(file.exists()){
			flag = file.delete();
		}
		return flag;
	}

	public String createAssignmentFile(String str, String filePath){
		String path="";
		try 
		{
			File file = new File(filePath);
			FileWriter writer = new FileWriter(file);
			StringBuffer sb = new StringBuffer();
			String b[] = str.split(" ");
			for(int i=0;i<b.length;i++){
				int charCode = Integer.parseInt(b[i], 2);
				if(charCode!=10){
					String data = new Character((char)charCode).toString();
					sb.append(data);
				}else{
					sb.append(new Character((char)charCode).toString());
					System.out.println(sb.toString());
					writer.write(sb.toString());
					sb = new StringBuffer();
				}
			}
			writer.close();
			path = file.getAbsolutePath();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		return path;
	}

	public String readAssignmentFile(String path){
		BufferedReader br = null;
		FileReader fr = null;
		StringBuilder sb = new StringBuilder();
		try {
			fr = new FileReader(path);
			br = new BufferedReader(fr);
			String sCurrentLine;
			StringBuilder binaryData = null;
			while ((sCurrentLine = br.readLine()) != null) {
				byte[] bytes = sCurrentLine.getBytes();
				binaryData = new StringBuilder();
				for (byte b : bytes)
				{
					int val = b;
					for (int i = 0; i < 8; i++)
					{
						binaryData.append((val & 128) == 0 ? 0 : 1);
						val <<= 1;
					}
					binaryData.append(' ');
				}
				binaryData.append(Integer.toBinaryString(10));
				binaryData.append(' ');
				sb.append(binaryData.toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
				if (fr != null)
					fr.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		System.out.println("File reading -->"+sb.toString());
		return sb.toString().trim();
	}

	public String getEnd(String date) {
		if (date == null) {
			return null;
		}
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date dt;
		Calendar c = Calendar.getInstance();
		try {
			dt = formatter.parse(date);
			c.setTime(dt);
			c.set(Calendar.HOUR_OF_DAY, 23);
			c.set(Calendar.MINUTE, 59);
			c.set(Calendar.SECOND, 59);
			c.set(Calendar.MILLISECOND, 999);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return formatter.format(c.getTime());
	}

}
