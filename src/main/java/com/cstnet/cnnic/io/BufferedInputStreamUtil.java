package com.cstnet.cnnic.io;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * 
 * 	@author Tao Bian 16/05/19
 * 
 *	BufferedInputStream extends FilterInputStream
	BufferedInputStream(InputStream in) 
	Creates a BufferedInputStream and saves its argument, the input stream in, for later use. 
	BufferedInputStream(InputStream in, int size) 
	Creates a BufferedInputStream with the specified buffer size, and saves its argument, the input stream in, for later use. 
	int available() 
	Returns an estimate of the number of bytes that can be read (or skipped over) from this input stream without blocking by the next invocation of a method for this input stream. 
	void close() 
	Closes this input stream and releases any system resources associated with the stream. 
	void mark(int readlimit) 
	See the general contract of the mark method of InputStream. 
	boolean markSupported() 
	Tests if this input stream supports the mark and reset methods. 
	int read() 
	See the general contract of the read method of InputStream. 
	int read(byte[] b, int off, int len) 
	Reads bytes from this byte-input stream into the specified byte array, starting at the given offset. 
	void reset() 
	See the general contract of the reset method of InputStream. 
	long skip(long n) 
	See the general contract of the skip method of InputStream. 
 */
public class BufferedInputStreamUtil {

	public static void main(String[] args) {
		File file = new File("C:/Users/Damon/Desktop/fileoutput.txt");
		FileInputStream fin = null;
		BufferedInputStream bin = null;
		try {
			fin = new FileInputStream(file);
			bin = new BufferedInputStream(fin);
			InputStreamUtil.readM2(bin);
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
			if (bin != null)
				try {
					bin.close();
				} catch (IOException e) {

				}
		}

	}

}
