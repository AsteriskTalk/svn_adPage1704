package util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;

import com.oreilly.servlet.MultipartRequest;

public class FileManager {
	
	public static boolean createFolder(String filePath) {
		File dir = new File(filePath);
		if (!dir.isDirectory()) { return dir.mkdirs(); }
		return true;
	}
	
	
	public static String getEXT(String fileName) {
		String[] tmp = fileName.split("\\.");
		int cnt = tmp.length-1;
		return tmp[cnt];
	}
	
	public static boolean copyFile(File inFile, File outFile) {
		FileInputStream fis = null;
		FileOutputStream fos = null;
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		
		try {
			fis = new FileInputStream(inFile);
			fos = new FileOutputStream(outFile);
			
			bis = new BufferedInputStream(fis);
			bos = new BufferedOutputStream(fos);
			
			int i = 0;
			while (( i=bis.read() )!= -1) { bos.write(i); }
			
//			return isSameFile(inFile, outFile);
			return true;
			
		} catch(Exception ex) {
			System.out.println("log : try-catch.."+ ASTKLogManager.getMethodName_withClassName(1) +"\n"+ ex);
			return false;
			
		} finally {
			try { if (bos != null) bos.close(); } catch (Exception ex) { }
			try { if (bis != null) bis.close(); } catch (Exception ex) { }
			try { if (fos != null) fos.close(); } catch (Exception ex) { }
			try { if (fis != null) fis.close(); } catch (Exception ex) { }
		}
	}
	
	public static boolean moveFile(String oldFilePath, String oldFileName,
			String newFilePath, String newFileName) throws Exception {
		File newPath = new File(newFilePath);
		if (!newPath.isDirectory()) { newPath.mkdirs(); }
		
		HashMap<String, Object> resultSet = new HashMap<String, Object>();
		boolean result = false;
		final File OLD_FILE = new File(oldFilePath + "/" + oldFileName);
		final File NEW_FILE = new File(newFilePath + "/"+ newFileName);
		
		result = copyFile(OLD_FILE, NEW_FILE);
		if (result) { resultSet.put("newFile", NEW_FILE);  }
		OLD_FILE.delete();
		
		return result;
	}
	
	
//	
//	public static HashMap<String, Object> moveFile(MultipartRequest multi, String oldPath, String newPath, String newFileName) {
//		FileInputStream fis = null;
//		FileOutputStream fos = null;
//		BufferedInputStream bis = null;
//		BufferedOutputStream bos = null;
//		
//		HashMap<String, Object> resultSet = new HashMap<String, Object>();
//		ArrayList<String> fileNames = new ArrayList<String>();
//		ArrayList<String> failedNames = new ArrayList<String>();
//		Enumeration tmpEnu = multi.getFileNames();
//		
//		int count = 0;
//		int failed = 0;
//		boolean hasFile = false;
//		try {
//			if (tmpEnu != null) {
//				while (tmpEnu.hasMoreElements()) {
//					count++;
//					hasFile = true;
//					String paramName = (String)tmpEnu.nextElement();
//					String oldFileName = multi.getFilesystemName(paramName);
//					final String EXT = getExt(oldFileName);
//					String oldFilePath = oldPath +"/"+ oldFileName;
//					String newFilePath = newPath +"/"+ newFileName +"."+EXT;
//
//					File oldFile = new File(oldFilePath);
//					File newFile = new File(newFilePath);
//					
//					if (!copyFile(oldFile, newFile)) {
//						failedNames.add(oldFilePath);
//						failed++;
//						continue; 
//					}
//					fileNames.add(newFilePath);
//				}
//				if (hasFile) { resultSet.put("result", "T"); }
//				else { resultSet.put("result", "N"); }
//			} else {
//				resultSet.put("result", "F");
//			}
//			
//		} catch(Exception ex) {
//			System.out.println("log : try-catch.."+ ASTKLogManager.getMethodName_withClassName(1) +"\n"+ ex);
//			resultSet.put("result", "E");
//			
//		} finally {
//			try { if (bos != null) bos.close(); } catch (Exception ex) { }
//			try { if (bis != null) bis.close(); } catch (Exception ex) { }
//			try { if (fos != null) fos.close(); } catch (Exception ex) { }
//			try { if (fis != null) fis.close(); } catch (Exception ex) { }
//		}
//		resultSet.put("fileNames", fileNames);
//		resultSet.put("failedNames", failedNames);
//		
//		if (fileNames.size() == 0 && failedNames.size() == 0) { resultSet.put("result", "N"); }
//		else if (fileNames.size() == 0 && failedNames.size() != 0) { resultSet.put("result", "F"); }
//		else { resultSet.put("result", "T"); }
//		
//		return resultSet;
//		
//	}
	
	
	public static boolean isSameFile(File file1, File file2) {
		FileInputStream fis1 = null;
		FileInputStream fis2 = null;
		
		BufferedInputStream bis1 = null;
		BufferedInputStream bis2 = null;
		
		int byte1 = 0;
		int byte2 = 0;
		
		try {
			fis1 = new FileInputStream(file1);
			fis2 = new FileInputStream(file2);
			
			bis1 = new BufferedInputStream(fis1);
			bis2 = new BufferedInputStream(fis2);
			
			check :
			while ( true) {
				byte1 = bis1.read();
				byte2 = bis2.read();
				if (byte1 == -1 && byte2 == -1) { break check; }
//				System.out.print(byte1 +" - "+ byte2+ ";");
				//if (byte1 != byte2) { return false; }
			}
			
//			System.out.println("log : " + byte1 +"-"+ byte2);
			if ( (byte1 == -1) && (byte2 == -1) || (byte1 == 0) && (byte2 == 0)) { return true; }
			return false;
			
		} catch (Exception ex) {
			System.out.println("log : try-catch.."+ ASTKLogManager.getMethodName_withClassName() +"\n"+ ex);
			return false;
			
		} finally {
			try { if (bis1 != null) bis1.close(); } catch (Exception ex) { }
			try { if (bis2 != null) bis2.close(); } catch (Exception ex) { }
			try { if (fis1 != null) fis1.close(); } catch (Exception ex) { }
			try { if (fis2 != null) fis2.close(); } catch (Exception ex) { }
			
		}
	}
	
	public static boolean renameFile(String filePath, String oldFileName) {
		String newFileName = TimeManager.getTime_forFileName("yyMMdd_hhmm");
		return renameFile(filePath, oldFileName, newFileName);
	}
	
	public static boolean renameFile(String filePath, String oldFileName, String newFileName) {
		if (ADTools.isNull(oldFileName)) { return false; }
		String[] tmp = oldFileName.split(".");
		final String EXT = tmp[tmp.length-1];
		final String OLD_FILE_PATH= filePath + "/" + oldFileName;
		final String NEW_FILE_PATH = filePath + "/" + newFileName + EXT;
		final File OLD_FILE= new File(OLD_FILE_PATH);
		final File NEW_FILE = new File(NEW_FILE_PATH);
		return OLD_FILE.renameTo(NEW_FILE);
		
	}
	
	
}
