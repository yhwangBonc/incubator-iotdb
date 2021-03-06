/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.iotdb.db.sync.receiver.load;

import java.io.File;
import java.io.IOException;

/**
 * This interface is used to log progress in the process of loading deleted files and new tsfiles.
 * If the loading tasks are completed normally and there are no exceptions, the log records will be
 * deleted; otherwise, the status can be restored according to the log at the start of each task. It
 * ensures the correctness of synchronization module when system crashed or network abnormality
 * occurred.
 */
public interface ILoadLogger {

  String LOAD_DELETED_FILE_NAME_START = "load deleted files start";
  String LOAD_TSFILE_START = "load tsfile start";

  /**
   * Start to load deleted files.
   */
  void startLoadDeletedFiles() throws IOException;

  /**
   * After a deleted file is loaded, record it in load log.
   *
   * @param file deleted file to be loaded
   */
  void finishLoadDeletedFile(File file) throws IOException;

  /**
   * Start to load tsfiles
   */
  void startLoadTsFiles() throws IOException;

  /**
   * After a new tsfile is loaded, record it in load log.
   *
   * @param file new tsfile to be loaded
   */
  void finishLoadTsfile(File file) throws IOException;

  void close() throws IOException;

}
