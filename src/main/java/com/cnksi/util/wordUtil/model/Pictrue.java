package com.cnksi.util.wordUtil.model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;

public class Pictrue {

	private File file;
	
	private int width;
	private int height;
	
	//<Relationship Id="rId8" Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/image" Target="media/image4.png"/>
	private String id;
	private String target;
	
	//<pkg:part pkg:name="/word/media/image4.png" pkg:contentType="image/png" pkg:compression="store">
	//	<pkg:binaryData></pkg:binaryData>
	//</pkg:part>
	private String name;
	private String contentType;
	private String binaryData; // 图片的二进制数据（base64编码存储）

	/**
	 * @param file
	 *            图片文件
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public Pictrue(File file) throws IOException {
		this(file,ImageIO.read(file));
	}

	private Pictrue(File file, BufferedImage image) throws IOException {
		this(file,image.getWidth(),image.getHeight());
	}

	/**
	 * @param file
	 *            图片文件
	 * @param width
	 *            宽度
	 * @param height
	 *            高度
	 * @throws IOException 
	 * @throws Exception 
	 */
	public Pictrue(File file, int width, int height) throws IOException {
		this.file = file;
		this.width = width;
		this.height = height;
		

		String type = getPictureType(file);
		String UUIDName = UUID.randomUUID().toString();
		
		this.id = "rId"+UUID.randomUUID();
		this.target = "media/"+UUIDName+"."+type;
		
		this.name = "/word/"+this.target;
		this.contentType = "image/"+type;
		this.binaryData = convertFileToBase64(file);
	}

	private String getPictureType(File file) {
		if(file.getName().endsWith("jpg")||file.getName().endsWith("jpeg")){
			return "jpeg";
		}
		return "png";
	}

	/**
	 * 将文件转为base64编码
	 * @param file  文件的路径
	 * @return
	 * @throws IOException 
	 * @throws Exception
	 */
	public static String convertFileToBase64(File file) throws IOException {
		FileInputStream inputFile = new FileInputStream(file);
		byte[] buffer = new byte[(int) file.length()];
		inputFile.read(buffer);
		inputFile.close();

		return new String(Base64.encodeBase64(buffer));

	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getBinaryData() {
		return binaryData;
	}

	public void setBinaryData(String binaryData) {
		this.binaryData = binaryData;
	}
	
	
}
