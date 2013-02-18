/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.solr.hadoop;

import java.util.Collections;
import java.util.Iterator;

import org.apache.hadoop.io.Text;
import org.apache.solr.common.SolrInputDocument;

/**
 * UpdateConflictResolver implementation that rejects multiple documents with
 * the same key with an exception.
 */
public final class ForbiddedUpdateConflictResolver implements UpdateConflictResolver {

  @Override
  public Iterator<SolrInputDocument> orderUpdates(Text uniqueKey, Iterator<SolrInputDocument> collidingUpdates) {    
    SolrInputDocument firstUpdate = null;
    while (collidingUpdates.hasNext()) {
      if (firstUpdate == null) {
        firstUpdate = collidingUpdates.next();
        assert firstUpdate != null;
      } else {
        throw new IllegalArgumentException("Update conflict! Documents with the same unique key are forbidden: "
            + uniqueKey);
      }
    }
    assert firstUpdate != null;
    return Collections.singletonList(firstUpdate).iterator();
  }

}
