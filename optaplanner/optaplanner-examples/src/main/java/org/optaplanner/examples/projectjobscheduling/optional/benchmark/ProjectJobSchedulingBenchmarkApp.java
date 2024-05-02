/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.optaplanner.examples.projectjobscheduling.optional.benchmark;

import org.optaplanner.examples.common.app.CommonBenchmarkApp;

public class ProjectJobSchedulingBenchmarkApp extends CommonBenchmarkApp {

    public static void main(String[] args) {
        new ProjectJobSchedulingBenchmarkApp().buildAndBenchmark(args);
    }

    public ProjectJobSchedulingBenchmarkApp() {
        super(
                new ArgOption("template",
                        "org/optaplanner/examples/projectjobscheduling/optional/benchmark/projectJobSchedulingBenchmarkConfigTemplate.xml.ftl",
                        true));
    }

}
