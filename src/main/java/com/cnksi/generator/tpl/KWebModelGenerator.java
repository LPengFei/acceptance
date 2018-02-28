package com.cnksi.generator.tpl;

import java.io.IOException;

import com.jfinal.plugin.activerecord.generator.ModelGenerator;
import com.jfinal.plugin.activerecord.generator.TableMeta;

public class KWebModelGenerator extends ModelGenerator {

	protected String _daoTemplate = "%n\tpublic static final %s me = new %s();%n";

	protected String _getClsTemplate = "%n%n\t@SuppressWarnings(\"rawtypes\") %n \t@Override %n \t  protected Class getCls() {%n\t\treturn this.getClass();%n\t}%n";

	public KWebModelGenerator(String modelPackageName, String baseModelPackageName, String modelOutputDir) {
		super(modelPackageName, baseModelPackageName, modelOutputDir);
	}

	@Override
	protected void genImport(TableMeta tableMeta, StringBuilder ret) {
		// ret.append(String.format(importTemplate, baseModelPackageName, tableMeta.baseModelName));
		ret.append(String.format("import %s;%n", "com.cnksi.kcore.jfinal.model.BaseModel"));
	}

	@Override
	protected void genClassDefine(TableMeta tableMeta, StringBuilder ret) {
		ret.append(String.format(classDefineTemplate, tableMeta.modelName, "BaseModel", tableMeta.modelName));
	}

	protected void genGetClsDefine(TableMeta tableMeta, StringBuilder ret) {
		ret.append(String.format(_getClsTemplate));
	}

	@Override
	protected void genDao(TableMeta tableMeta, StringBuilder ret) {
		if (generateDaoInModel)
			ret.append(String.format(_daoTemplate, tableMeta.modelName, tableMeta.modelName));
		else
			ret.append(String.format("\t%n"));
	}

	@Override
	protected void genModelContent(TableMeta tableMeta) {
		StringBuilder ret = new StringBuilder();
		genPackage(ret);
		genImport(tableMeta, ret);
		genClassDefine(tableMeta, ret);
		genDao(tableMeta, ret);
		genGetClsDefine(tableMeta, ret);
		ret.append(String.format("}%n"));
		tableMeta.modelContent = ret.toString();
	}

	@Override
	protected void wirtToFile(TableMeta tableMeta) throws IOException {
		super.wirtToFile(tableMeta);
	}

}
