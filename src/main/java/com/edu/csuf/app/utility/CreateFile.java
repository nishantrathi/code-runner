package com.edu.csuf.app.utility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class CreateFile 
{




	public static void main(String[] args) throws Exception {
		Utilities ut = new Utilities();
		System.out.println(ut.getEnd("2018-04-30"));

		String binary="101111 101111 100000 1101101 1111001 100000 1100110 1101001 1110010 1110011 1110100 100000 1110000 1110010 1101111 1100111 1110010 1100001 1101101 100000 1101001 1101110 100000 1000011 101011 101011 1010 100011 1101001 1101110 1100011 1101100 1110101 1100100 1100101 100000 111100 1101001 1101111 1110011 1110100 1110010 1100101 1100001 1101101 111110 1010 1010 1101001 1101110 1110100 100000 1101101 1100001 1101001 1101110 101000 101001 1010 1111011 1010 1001 1101001 1101110 1110100 100000 1100001 101100 100000 1100010 101100 100000 1110011 1110101 1101101 111011 1010 1001 1100001 111101 110001 110000 111011 1010 1001 1100010 111101 110010 110000 111011 1010 1001 1110011 1110101 1101101 111101 1100001 101010 1100010 111011 1010 1001 1110011 1110100 1100100 111010 111010 1100011 1101111 1110101 1110100 100000 111100 111100 100010 1010011 1110101 1101101 100000 1101111 1100110 100000 1110100 1101000 1100101 100000 1110100 1110111 1101111 100000 1101110 1110101 1101101 1100010 1100101 1110010 100000 1101001 1110011 100000 100010 111100 111100 1110011 1110101 1101101 111011 1010 1111101";
		String bin[] = binary.split(" ");
		//System.out.println(binary);
		try 
		{
			File file = new File("C:\\Users\\nishantrathi\\Desktop\\dummy\\temp.cpp");
			FileWriter writer = new FileWriter(file);
			StringBuffer sb = new StringBuffer();
			for(int i=0;i<bin.length;i++){
				int charCode = Integer.parseInt(bin[i], 2);
System.out.println("-->"+Integer.parseInt("1010",2));
				if(charCode!=10){
					System.out.println();
					String str = new Character((char)charCode).toString();
					sb.append(str);
				}else{
					sb.append(new Character((char)charCode).toString());
					System.out.print(sb.toString());
					writer.write(sb.toString());
					sb = new StringBuffer();
				}
			}
			writer.close();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}


		/*BufferedReader br = null;
		FileReader fr = null;
		StringBuilder sb = new StringBuilder();
		try {
			fr = new FileReader("E:\\codeRunner\\assignments\\2018\\3\\1\\3\\2\\ques_3_ans.cpp");
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
				System.out.println(Integer.toBinaryString(10));
				binaryData.append(Integer.toBinaryString(10));
				binaryData.append(' ');
				sb.append(binaryData.toString());
			}
			System.out.println(sb.toString());
			//System.out.println(binary.equals(sb.toString()));

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

		//CreateFile cf = new CreateFile();
		//String command =  "cd \"E:\\codeRunner\\assignments\\2018\\3\\1\\1\\1\" && g++ -o nishant ques_1_ans.cpp && nishant";
		//cf.executeCppCode(command,"");
*/	}

	/*	public static void main(String[] args) throws Exception {
//		ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", "cd \"E:\\codeRunner\\assignments\" && g++ -o nishant a1.cpp && nishant");
		ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", "cd \"E:\\codeRunner\\assignments\\2018\\3\\1\\1\\1\" && g++ -o nishant ques_1_ans.cpp && nishant");
		builder.redirectErrorStream(true);
		Process p = builder.start();
		System.out.println(builder.command().toString());
		BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
		String line;
		while (true) {
			line = r.readLine();
			if (line == null) { break; }
			System.out.println(line);
		}
	}*/
}
