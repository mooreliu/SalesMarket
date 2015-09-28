package com.mooreliu.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Created by mooreliu on 2015/9/1.
 */
public class GzipUtil {
	public static String compress(String str) throws IOException {
		if (str == null || str.length() == 0) return str; /*        System.out.println("String length : " + str.length());*/
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		GZIPOutputStream gzip = new GZIPOutputStream(out);
		gzip.write(str.getBytes());
		gzip.close();
		String outStr = out.toString("ISO-8859-1");
//        System.out.println("Output String lenght : " + outStr.length());
		return outStr;
	}

	public static String decompress(String str) throws IOException {
		if (str == null || str.length() == 0) {
			return str;
		}
//        System.out.println("Input String length : " + str.length());
		GZIPInputStream gis = new GZIPInputStream(new ByteArrayInputStream(str.getBytes("ISO-8859-1")));
		BufferedReader bf = new BufferedReader(new InputStreamReader(gis, "UTF-8"));
		String outStr = "";
		String line;
		while ((line = bf.readLine()) != null) {
			outStr += line;
		}
//        System.out.println("Output String lenght : " + outStr.length());
		return outStr;
	}
}
