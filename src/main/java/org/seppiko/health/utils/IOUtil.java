/*
 * Copyright 2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.seppiko.health.utils;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Leonard Woo
 */
@Slf4j
public class IOUtil {

  public static BufferedReader load(String filename) {
    try {
      InputStream is = IOUtil.class.getClassLoader().getResourceAsStream(filename);
      if(is == null) {
        is = new FileInputStream(filename);
      }
      return loadStream(is);
    } catch (IOException | NullPointerException ex) {
      return null;
    }
  }

  /**
   * Get file stream
   *
   * @param filepath File path
   * @return File input stream
   * @throws FileNotFoundException File not found
   */
  public static InputStream getStream(String filepath) throws FileNotFoundException {
    return new FileInputStream(filepath);
  }

  /**
   * Get file stream
   *
   * @param file File object
   * @return File input stream
   * @throws FileNotFoundException File not found
   */
  public static InputStream getStream(File file) throws FileNotFoundException {
    return new FileInputStream(file);
  }

  /**
   * Load string to reader
   *
   * @param str String
   * @return Reader
   */
  public static BufferedReader loadString(String str) {
    return new BufferedReader(new StringReader(str));
  }

  /**
   * Load inputstream to reader
   *
   * @param in Inputstream
   * @return Reader
   */
  public static BufferedReader loadStream(InputStream in) {
    return new BufferedReader(new InputStreamReader(in));
  }

  /**
   * Load inputstram to reader with charset
   *
   * @param in      Inputstream
   * @param charset Charest see{@link StandardCharsets}
   * @return Reader
   */
  public static BufferedReader loadStream(InputStream in, Charset charset) {
    return new BufferedReader(new InputStreamReader(in, charset));
  }

  public static ArrayList<String> readerToStringList(BufferedReader reader) throws IOException {
    ArrayList<String> textList = new ArrayList<>();
    while (reader.ready()) {
      textList.add( reader.readLine() );
    }
    return textList;
  }

  public static String readerToString(BufferedReader reader) throws IOException {
    StringBuffer sb = new StringBuffer();
    while (reader.ready()) {
      sb.append( reader.readLine() );
    }
    return sb.toString();
  }

  public static Writer writeFile(String pathname) throws IOException {
    return new FileWriter(pathname);
  }

  public static Writer writeFile(File file) throws IOException {
    if (!file.exists()) {
      file.createNewFile();
    }
    return new FileWriter(file);
  }

  /**
   * Output to file
   *
   * @param b    bytes
   * @param file File
   * @throws IOException I/O exception
   */
  public static void toFile(byte[] b, File file) throws IOException {
    if (!file.exists()) {
      file.createNewFile();
    }
    OutputStream os = new BufferedOutputStream(new FileOutputStream(file));
    os.write(b);
    os.close();
  }

  /**
   * Copy inputstream to outputstream
   *
   * @param is InputStream
   * @param os OutputStream
   * @return size
   * @throws IOException I/O exception
   */
  public static long copy(InputStream is, OutputStream os) throws IOException {
    long size = is.transferTo(os);
    os.flush();
    return size;
  }

}
