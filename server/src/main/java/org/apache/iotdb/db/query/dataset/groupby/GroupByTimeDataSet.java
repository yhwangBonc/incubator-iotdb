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

package org.apache.iotdb.db.query.dataset.groupby;

import org.apache.iotdb.db.exception.StorageEngineException;
import org.apache.iotdb.db.exception.query.QueryProcessException;
import org.apache.iotdb.db.qp.physical.crud.GroupByTimePlan;
import org.apache.iotdb.db.query.aggregation.AggregateResult;
import org.apache.iotdb.db.query.context.QueryContext;
import org.apache.iotdb.db.query.factory.AggregateResultFactory;
import org.apache.iotdb.db.query.filter.TsFileFilter;
import org.apache.iotdb.db.utils.FilePathUtils;
import org.apache.iotdb.tsfile.file.metadata.enums.TSDataType;
import org.apache.iotdb.tsfile.read.common.Field;
import org.apache.iotdb.tsfile.read.common.Path;
import org.apache.iotdb.tsfile.read.common.RowRecord;
import org.apache.iotdb.tsfile.read.filter.basic.Filter;
import org.apache.iotdb.tsfile.read.query.dataset.QueryDataSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;

public class GroupByTimeDataSet extends QueryDataSet {

  private static final Logger logger = LoggerFactory
    .getLogger(GroupByTimeDataSet.class);

  private List<RowRecord> records = new ArrayList<>();
  private int index = 0;

  protected long queryId;
  private GroupByTimePlan groupByTimePlan;
  private QueryContext context;

  public GroupByTimeDataSet(QueryContext context, GroupByTimePlan plan, GroupByEngineDataSet dataSet)
    throws QueryProcessException, StorageEngineException, IOException {
    this.queryId = context.getQueryId();
    this.paths = plan.getDeduplicatedPaths();
    this.dataTypes = plan.getDeduplicatedDataTypes();
    this.groupByTimePlan = plan;
    this.context = context;

    if (logger.isDebugEnabled()) {
      logger.debug("paths " + this.paths + " level:" + plan.getLevel());
    }

    Map<Integer, String> pathIndex = new HashMap<>();
    Map<String, Long> finalPaths = FilePathUtils.getPathByLevel(plan.getPaths(), plan.getLevel(), pathIndex);

    // get all records from GroupByDataSet, then we merge every record
    if (logger.isDebugEnabled()) {
      logger.debug("only group by level, paths:" + groupByTimePlan.getPaths());
    }
    while (dataSet != null && dataSet.hasNextWithoutConstraint()) {
      RowRecord curRecord = FilePathUtils.mergeRecordByPath(dataSet.nextWithoutConstraint(), finalPaths, pathIndex);
      if (curRecord != null) {
        records.add(curRecord);
      }
    }

    this.dataTypes = new ArrayList<>();
    this.paths = new ArrayList<>();
    for (int i = 0; i < finalPaths.size(); i++) {
      this.dataTypes.add(TSDataType.INT64);
    }
  }

  @Override
  protected boolean hasNextWithoutConstraint() throws IOException {
    return index < records.size();
  }

  @Override
  protected RowRecord nextWithoutConstraint() {
    return records.get(index++);
  }
}
