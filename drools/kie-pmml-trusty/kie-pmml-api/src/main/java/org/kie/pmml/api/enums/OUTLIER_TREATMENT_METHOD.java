/**
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
package org.kie.pmml.api.enums;

import java.util.Arrays;
import java.util.Objects;

import org.kie.pmml.api.exceptions.KieEnumException;

public enum OUTLIER_TREATMENT_METHOD implements Named {

    AS_IS("asIs"),
    AS_MISSING_VALUES("asMissingValues"),
    AS_EXTREME_VALUES("asExtremeValues");

    private String name;

    OUTLIER_TREATMENT_METHOD(String name) {
        this.name = name;
    }

    public static OUTLIER_TREATMENT_METHOD byName(String name) {
        return Arrays.stream(OUTLIER_TREATMENT_METHOD.values()).filter(value -> Objects.equals(name, value.name)).findFirst().orElseThrow(() -> new KieEnumException("Failed to find OUTLIER_TREATMENT_METHOD with name: " + name));
    }

    @Override
    public String getName() {
        return name;
    }
}