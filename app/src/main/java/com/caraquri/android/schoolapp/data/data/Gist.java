package com.caraquri.android.schoolapp.data.data;

import java.util.Map;

public class Gist {
  public final String url;

  public final String id;

  public final Map<String, GistFile> files;

  public Gist(String url, String id, Map<String, GistFile> files) {
    this.url = url;
    this.id = id;
    this.files = files;
  }

  @Override public String toString() {
    return "Gist{" + "url='" + url + '\'' + ", id='" + id + '\'' + ", files=" + files + '}';
  }
}
