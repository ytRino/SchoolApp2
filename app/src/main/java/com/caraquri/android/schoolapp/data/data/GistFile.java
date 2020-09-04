package com.caraquri.android.schoolapp.data.data;

public class GistFile {
  public final String filename;

  public final String language;

  public GistFile(String filename, String language) {
    this.filename = filename;
    this.language = language;
  }

  @Override public String toString() {
    return "GistFile{" + "filename='" + filename + '\'' + ", language='" + language + '\'' + '}';
  }
}
