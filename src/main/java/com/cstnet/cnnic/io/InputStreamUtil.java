package com.cstnet.cnnic.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * 	@author Tao Bian 16/05/19
 * 
 * 	FileInputStream read from txt
 * 
 * 	InputStream()
   	int available() 
	Returns an estimate of the number of bytes that can be read (or skipped over) from this input stream without blocking by the next invocation of a method for this input stream. 
	void close() 
	Closes this input stream and releases any system resources associated with the stream. 
	void mark(int readlimit) 
	Marks the current position in this input stream. 
	boolean markSupported() 
	Tests if this input stream supports the mark and reset methods. 
	abstract int read() 
	Reads the next byte of data from the input stream. 
	int read(byte[] b) 
	Reads some number of bytes from the input stream and stores them into the buffer array b. 
	int read(byte[] b, int off, int len) 
	Reads up to len bytes of data from the input stream into an array of bytes. 
	void reset() 
	Repositions this stream to the position at the time the mark method was last called on this input stream. 
	long skip(long n) 
	Skips over and discards n bytes of data from this input stream. 

   
 */
public class InputStreamUtil {

	public static void readM1(InputStream fin) throws IOException {
		byte[] input = new byte[1024];
		for(int i = 0; i < input.length; i++) {
			int b = fin.read();			//read only one byte
			if (b == -1) break;
			input[i] = (byte) b;
		}
	}
	
	public static void readM2(InputStream fin) throws IOException {
		int bytesToRead = 1024;
		int len = 0;
		
		while(true) {
			byte[] input = new byte[bytesToRead];

			if((len = fin.read(input, 0, bytesToRead)) == -1) {
				break;
			}
			System.out.print(new String(input).toString());
			if (len == -1)
				break;
		}
	}
	
	public static void readM3(InputStream fin) throws IOException {
			int bytesAvaiable = fin.available();
			byte[] input = new byte[bytesAvaiable];
			int bytesRead = fin.read(input, 0, bytesAvaiable);
			//deal with input
	}
	
	public static void main(String[] args) {
		File file = new File("C:/Users/Damon/Desktop/fileoutput.txt");
		FileInputStream fin = null;
		try {
			fin = new FileInputStream(file);
			readM2(fin);
		} catch (FileNotFoundException e) {
			System.out.println(file.getAbsolutePath() + " is not existed");
		} catch (IOException e) {
			System.out.println(" errors occured");
		} finally {
			if (fin != null)
				try {
					fin.close();
				} catch (IOException e) {

				}
		}
	}

}
