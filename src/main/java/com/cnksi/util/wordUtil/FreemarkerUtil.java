package com.cnksi.util.wordUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.Version;

public class FreemarkerUtil {
	private final static String encoding = "UTF-8";

	/**
	 * 用freemark处理
	 * @param paras 参数
	 * @param templateFile 模板文件
	 * @param out 输出流
	 * @throws TemplateException
	 * @throws IOException
	 */
	public static void proccess(Object paras, File templateFile, Writer out) throws TemplateException, IOException {
		Configuration cfg = new Configuration();

		cfg.setDirectoryForTemplateLoading(templateFile.getParentFile());
		cfg.setObjectWrapper(new DefaultObjectWrapper());
		cfg.setDefaultEncoding(encoding);
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);
		cfg.setIncompatibleImprovements(new Version(2, 3, 20));

		Template template = cfg.getTemplate(templateFile.getName());
		
		template.process(paras, out);
	}

	/**
	 * 用freemark处理
	 * @param paras 参数
	 * @param templateFile 模板文件
	 * @param outFilePath 输出文件的路径
	 * @throws TemplateException
	 * @throws IOException
	 */
	public static void proccess(Object paras, File templateFile, String outFilePath)throws TemplateException, IOException {
		FileOutputStream fos = new FileOutputStream(outFilePath);
		OutputStreamWriter osw = new OutputStreamWriter(fos,encoding);
		try {
			
			proccess(paras, templateFile, osw);
		} finally {
			osw.close();
			fos.close();
		}
	}

	/**
	 * 用freemark处理
	 * @param paras 参数
	 * @param templateFile 模板文件
	 * @return 处理后的结果
	 * @throws TemplateException
	 * @throws IOException
	 */
	public static String proccess(Object paras, File templateFile) throws TemplateException, IOException {
		StringWriter out = new StringWriter();
		proccess(paras, templateFile, out);
		return out.getBuffer().toString();
	}

}
