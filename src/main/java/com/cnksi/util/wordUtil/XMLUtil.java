package com.cnksi.util.wordUtil;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;


public class XMLUtil {
	private final static String encoding = "UTF-8";

	@SuppressWarnings("unused")
	public static void main(String[] args) throws DocumentException {
		URL resource = XMLUtil.class.getResource("/Doc1");

	}

	public static String stripNonValidXMLCharacters(String in) {
		StringBuffer out = new StringBuffer(); // Used to hold the output.
		char current; // Used to reference the current character.

		if (in == null || ("".equals(in))) return ""; // vacancy test.
		for (int i = 0; i < in.length(); i++) {
			current = in.charAt(i); // NOTE: No IndexOutOfBoundsException caught here; it should not happen.
			if ((current == 0x9) ||
					(current == 0xA) ||
					(current == 0xD) ||
					((current >= 0x20) && (current <= 0xD7FF)) ||
					((current >= 0xE000) && (current <= 0xFFFD)) ||
					((current >= 0x10000) && (current <= 0x10FFFF)))
				out.append(current);
		}
		return out.toString();
	}

	/**
	 * 读取文件为Document
	 * @param file
	 * @return
	 * @throws DocumentException
	 * @throws IOException 
	 */
	public static Document read(File file) throws DocumentException, IOException {
		System.out.println(file.getAbsolutePath());
		Document document = null;

		String xml10pattern = "[^\u0009\r\n\u0020-\uD7FF\uE000-\uFFFD\ud800\udc00-\udbff\udfff]";
		String illegal = new String(Files.readAllBytes(Paths.get(file.getPath())), StandardCharsets.UTF_8);
		String legal = illegal.replaceAll(xml10pattern, " ");
		
		InputStream stream = new ByteArrayInputStream(legal.getBytes(StandardCharsets.UTF_8));
		InputStreamReader isr = new InputStreamReader(stream, encoding);
		try {

			SAXReader reader = new SAXReader();
			document = reader.read(isr);
		} finally {
			isr.close();
			stream.close();
		}
		return document;
	}

	/**
	 * 将Document写入到文件
	 * @param doc 待写的内容
	 * @param file 输出的问津
	 * @throws IOException
	 */
	public static void write(Document doc, File file) throws IOException {

		FileOutputStream fos = new FileOutputStream(file);
		OutputStreamWriter osw = new OutputStreamWriter(fos,encoding);
		XMLWriter writer = new XMLWriter(osw);
		try {
			
			writer.write(doc);
		} finally {
			writer.close();
			osw.close();
			fos.close();
		}
	}

}
