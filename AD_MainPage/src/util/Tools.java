package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Tools {

	public static boolean isNull(String str) {
		if (str.equals("") || str.equals(null) || str == "" || str == null) {
			return true;
		}
		return false;
	}

	public static int getCount() {
		final String PATH = "C://visit.txt";
		File f = new File(PATH);
		String str = "";
		int county = 0;
		int i = 0;
		
		FileReader fr = null;
		BufferedReader br = null;
		
		try { 
			fr = new FileReader(f); //파일을 읽어들인다.
			br = new BufferedReader(fr);
			
			while ((i = br.read()) != -1) { str += (char)i; } // i는 char 문자의 고유 번호이다. 이것을 String에 하나씩 추가한다. i가 -1 이 되면 파일에 내용이 더이상 없다는 의미이다.
			
			county = Integer.parseInt(str);
						
		} catch (IOException ex) {
			System.out.println("log : try-catch..\n"+ex);
			county = -1;
			
		} finally {
			try { if (fr != null) fr.close(); } catch (Exception ex) { }
			try { if (br != null) br.close(); } catch (Exception ex) { }
			return county;
			
		}
		
	}
	
	public static int addCount() {
		final String PATH = "C://visit.txt";
		File f = new File(PATH);
		String str = "";
		int county = 0;
		int i = 0;
		
		FileReader fr = null;
		FileWriter fw = null;
		
		BufferedWriter bw = null;
		BufferedReader br = null;
		
		try { 
			fr = new FileReader(f); //파일을 읽어들인다.
			br = new BufferedReader(fr);
			
			while ((i = br.read()) != -1) { str += (char)i; } // i는 char 문자의 고유 번호이다. 이것을 String에 하나씩 추가한다. i가 -1 이 되면 파일에 내용이 더이상 없다는 의미이다.
			
			county = Integer.parseInt(str);
			
			county++; 
			str = ""+county;
			
			fw = new FileWriter(f);
			bw = new BufferedWriter(fw);
			bw.write(str);
			bw.close();
			
		} catch (IOException ex) {
			System.out.println("log : try-catch..\n"+ex);
			county = -1;
			
		} finally {
			try { if (fr != null) fr.close(); } catch (Exception ex) { }
			try { if (fw != null) fw.close(); } catch (Exception ex) { }
			try { if (br != null) br.close(); } catch (Exception ex) { }
			try { if (bw != null) bw.close(); } catch (Exception ex) { }
			return county;
			
		}
	}
	
}
