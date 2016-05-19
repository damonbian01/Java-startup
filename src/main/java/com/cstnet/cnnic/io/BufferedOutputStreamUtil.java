package com.cstnet.cnnic.io;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 
 * 	@author Tao Bian 16/05/19
 * 
 * 	BufferedOutputStream extends FilterOutputStream
	BufferedOutputStream(OutputStream out) 
	Creates a new buffered output stream to write data to the specified underlying output stream. 
	BufferedOutputStream(OutputStream out, int size) 
	Creates a new buffered output stream to write data to the specified underlying output stream with the specified buffer size.
	void flush() 
	Flushes this buffered output stream. 
	void write(byte[] b, int off, int len) 
	Writes len bytes from the specified byte array starting at offset off to this buffered output stream. 
	void write(int b) 
	Writes the specified byte to this buffered output stream. 
 */
public class BufferedOutputStreamUtil {

	public static void main(String[] args) {
		File file = new File("C:/Users/Damon/Desktop/fileoutput.txt");
		FileOutputStream fout = null;
		BufferedOutputStream bout= null;
		try {
			fout = new FileOutputStream(file);
		} catch (FileNotFoundException e) {
			System.out.println(file.getAbsolutePath() + " is not exist");
		}
		bout = new BufferedOutputStream(fout);
		try {
			OutputStreamUtil.generateASCII_1(fout);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (bout != null)
				try {
					bout.close();
				} catch (IOException e) {

				}
		}
	}

}
