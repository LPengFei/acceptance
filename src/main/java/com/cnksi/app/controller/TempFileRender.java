package com.cnksi.app.controller;

import com.jfinal.render.FileRender;
import com.jfinal.render.RenderFactory;

import java.io.File;
import java.io.UnsupportedEncodingException;

public class TempFileRender extends FileRender {
  private String fileName;
  private File tempfile;
  public TempFileRender(String fileName, String fullName) {
      super(fileName);
      this.fileName = fullName;
  }

  public TempFileRender(File file) {
      super(file);
      this.tempfile = file;
  }

  @Override
  public void render() {
      try {
          super.render();
      } finally {

          if(null != fileName) {
              tempfile = new File(fileName);
          }

          if(null != tempfile) {
              tempfile.delete();
              tempfile.deleteOnExit();
          }
      }
  }

  public String encodeFileName(String fileName) {
      try {
           return new String(fileName.getBytes("GBK"), "ISO8859-1");
      } catch (UnsupportedEncodingException e) {
          return fileName;
      }
  }
}
