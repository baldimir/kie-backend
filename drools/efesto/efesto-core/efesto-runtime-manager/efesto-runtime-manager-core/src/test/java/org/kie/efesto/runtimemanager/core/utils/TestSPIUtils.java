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
package org.kie.efesto.runtimemanager.core.utils;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.kie.efesto.runtimemanager.core.mocks.MockKieRuntimeServiceA;
import org.kie.efesto.runtimemanager.core.mocks.MockKieRuntimeServiceC;
import org.kie.efesto.runtimemanager.api.model.EfestoRuntimeContext;
import org.kie.efesto.runtimemanager.api.service.KieRuntimeService;
import org.kie.efesto.runtimemanager.core.model.EfestoRuntimeContextUtils;

class TestSPIUtils {

    private static final List<Class<? extends KieRuntimeService>> KIE_RUNTIME_SERVICES =
            Arrays.asList(MockKieRuntimeServiceA.class, MockKieRuntimeServiceC.class);

    private static EfestoRuntimeContext context;

    @BeforeAll
    static void setUp() {
        context = EfestoRuntimeContextUtils.buildWithParentClassLoader(Thread.currentThread().getContextClassLoader());
    }
}