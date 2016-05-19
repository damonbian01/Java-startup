package com.cstnet.cnnic.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 
 * @author Tao Bian 16/05/19
 * 
 * FileOutputStream write to txt
 * 
 * OutputStream() 
   void close() 
   Closes this output stream and releases any system resources associated with this stream. 
   void flush() 
   Flushes this output stream and forces any buffered output bytes to be written out. 
   void write(byte[] b) 
   Writes b.length bytes from the specified byte array to this output stream. 
   void write(byte[] b, int off, int len) 
   Writes len bytes from the specified byte array starting at offset off to this output stream. 
   abstract void write(int b) 
   Writes the specified byte to this output stream. 
 */


public class OutputStreamUtil {

	
	public static void generateASCII_1(OutputStream out) throws IOException {
		int firstPrintableCharacter = 33;
		int numberOfPrintableCharacters = 94;
		int numberPerLine = 72;
		
		int lines = 30;
		int start = firstPrintableCharacter;
		while(lines-- > 0) {
			byte[] buffer = new byte[numberPerLine+2];
			for(int i = start; i < start + numberPerLine; i++) {
				buffer[i-start] = (byte) ((i - firstPrintableCharacter) % numberOfPrintableCharacters + firstPrintableCharacter);
			}
			out.write(String.valueOf(30-lines).getBytes());
			buffer[72] = (byte) '\r';
			buffer[73] = (byte) '\n';
			out.write(buffer);
			out.flush();
			start = (start + 1 - firstPrintableCharacter) % numberOfPrintableCharacters + firstPrintableCharacter;
		}
	}
	
	public static void main(String[] args) {
		File file = new File("C:/Users/Damon/Desktop/fileoutput.txt");
		FileOutputStream fop = null;
		if (!file.exists()) {
			System.out.println("file does not exist and will create it");
			try {
				file.createNewFile();
			} catch (IOException e) {
				System.out.println("create file failed");
				System.exit(1);
			}
		}
		
		try {
			fop = new FileOutputStream(file);
			generateASCII_1(fop);
			fop.close();
		} catch (FileNotFoundException e) {
			System.out.println(file.getAbsolutePath() + " not found");
			System.exit(1);
		} catch (IOException e) {
			System.out.println("IO error");
		} finally {
			if (fop != null)
				try {
					fop.close();
				} catch (IOException e1) {

				}
		}
		
	}
}
