package com.sd.thrillio.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;



public class IOUtil {

	public static void read(List<String> result, String file_path) {
		try (BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file_path), "UTF-8"));) {
			String row;
			while ((row = in.readLine()) != null) {
				result.add(row);
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static String read(InputStream inputStream) {
		StringBuilder sb = new StringBuilder();
		try (BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));) {
			String read;
			while ((read = in.readLine()) != null) {
				sb.append(read + "\n");
			}
			return sb.toString();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static void write(String content, long id) {
		try (BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream("C:\\Users\\souvik dey\\eclipse-workspace\\ThrillioWeb\\web_pages\\"
						+ String.valueOf(id) + ".html"),
				"UTF-8"));) {
			out.write(content);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
