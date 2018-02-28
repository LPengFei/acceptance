package com.cnksi.util;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;

import com.cnksi.util.wordUtil.FreemarkerUtil;
import com.cnksi.util.wordUtil.XMLUtil;
import com.cnksi.util.wordUtil.model.CRString;
import com.cnksi.util.wordUtil.model.Pictrue;

import freemarker.template.TemplateException;

/**
 * 1、将word转为xml
 * 2、用freemarker来处理标记
 * 3、将文件输出为.doc的后缀。PS：这样就可以直接用word打开
 */
public class WordUtil {

	/**
	 * 根据指定的参数值、模板，生成 word 文档。模板只支持xml格式Word文档。
	 * @param param 参数，用Freemarker来处理参数，value为Pictrue时表示替换图片
	 * @param templatePath 模板
	 * @return
	 * @throws DocumentException 
	 * @throws TemplateException 
	 * @throws IOException
	 */
	public static File generateWord(Map<String, Object> param,String templatePath,String outFilePath) throws DocumentException, TemplateException, IOException {
		File templateFile = new File(templatePath);
		File outFile = new File(outFilePath);
		
		//处理参数中的回车
		handleIterableObject(param.values(),new IParameterHandle() {

			@Override
			public void handle(Object param) {
				if (param instanceof CRString) {
					
					CRString crString = (CRString) param;
					String s = crString.getString();
					
					if(s!=null){
						crString.setString(s.replaceAll("\n", "<w:br/>"));
					}
				}
			}
		});
		
		
		//用Freemarker来处理模板
		FreemarkerUtil.proccess(param, templateFile, outFilePath);
		

		//处理图片
		Document doc = XMLUtil.read(outFile);
		final Element root =doc.getRootElement();
		
		handleIterableObject(param.values(),new IParameterHandle() {
			@Override
			public void handle(Object para) {
				if(para instanceof Pictrue){
					Pictrue pictrue = (Pictrue) para;
					
					//处理Relationship，示例：<Relationship Id="rId5" Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/image" Target="media/image1.png"/>
					String xpath = "//pkg:part[@pkg:name='/word/_rels/document.xml.rels']/pkg:xmlData/*[name()='Relationships']";
					Element relationships = (Element) root.selectSingleNode(xpath);
					
					Element relationship = relationships.addElement("Relationship");
					relationship.addAttribute("Id", pictrue.getId());
					relationship.addAttribute("Type", "http://schemas.openxmlformats.org/officeDocument/2006/relationships/image");
					relationship.addAttribute("Target", pictrue.getTarget());
	
					//处理part，示例：<pkg:part pkg:name="/word/media/image1.png" pkg:contentType="image/png" pkg:compression="store"><pkg:binaryData></pkg:binaryData></pkg:part>
					Element part = root.addElement("pkg:part");
					part.addAttribute("pkg:name", pictrue.getName());
					part.addAttribute("pkg:contentType", pictrue.getContentType());
					part.addAttribute("pkg:compression", "store");
					Element binaryData = part.addElement("pkg:binaryData");
					binaryData.setText(pictrue.getBinaryData());
				}
			}
		});
		
		//写入到文件
		XMLUtil.write(doc,outFile);
		
		return outFile;
	}

	@SuppressWarnings("unchecked")
	private static void handleIterableObject(Iterable<Object> iterable, IParameterHandle handle) {
		Iterator<Object> iterator = iterable.iterator();
		while(iterator.hasNext()){
			Object o = iterator.next();
			
			if(o instanceof Map){
				
				Map<?, Object> map = (Map<?, Object>) o;
				handleIterableObject(map.values(), handle);
			}else if(o instanceof Iterable){
				
				Iterable<Object> i = (Iterable<Object>) o;
				handleIterableObject(i, handle);
			}else{

				handle.handle(o);
			}
		}
	}
	
	/**
	 * 处理参数
	 */
	interface IParameterHandle {
		public void handle(Object param);

	}
}
