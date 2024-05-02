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
package org.kie.kogito.event;

import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;
import org.kie.kogito.event.cloudevents.CloudEventExtensionConstants;

import static org.assertj.core.api.Assertions.assertThat;

class CloudEventExtensionConstantsTest {

    @Test
    void verifyKeysNamingConvention() {
        final Pattern nameValidation = Pattern.compile("^[a-z0-9]{1,20}$");
        assertThat(CloudEventExtensionConstants.ADDONS).matches(nameValidation);
        assertThat(CloudEventExtensionConstants.PMML_FULL_RESULT).matches(nameValidation);
        assertThat(CloudEventExtensionConstants.PMML_MODEL_NAME).matches(nameValidation);
        assertThat(CloudEventExtensionConstants.PROCESS_ID).matches(nameValidation);
        assertThat(CloudEventExtensionConstants.PROCESS_TYPE).matches(nameValidation);
        assertThat(CloudEventExtensionConstants.PROCESS_PARENT_PROCESS_INSTANCE_ID).matches(nameValidation);
        assertThat(CloudEventExtensionConstants.PROCESS_INSTANCE_ID).matches(nameValidation);
        assertThat(CloudEventExtensionConstants.PROCESS_INSTANCE_VERSION).matches(nameValidation);
        assertThat(CloudEventExtensionConstants.PROCESS_INSTANCE_STATE).matches(nameValidation);
        assertThat(CloudEventExtensionConstants.PROCESS_REFERENCE_ID).matches(nameValidation);
        assertThat(CloudEventExtensionConstants.PROCESS_ROOT_PROCESS_ID).matches(nameValidation);
        assertThat(CloudEventExtensionConstants.PROCESS_ROOT_PROCESS_INSTANCE_ID).matches(nameValidation);
        assertThat(CloudEventExtensionConstants.PROCESS_START_FROM_NODE).matches(nameValidation);
        assertThat(CloudEventExtensionConstants.PROCESS_USER_TASK_INSTANCE_ID).matches(nameValidation);
        assertThat(CloudEventExtensionConstants.PROCESS_USER_TASK_INSTANCE_STATE).matches(nameValidation);
        assertThat(CloudEventExtensionConstants.RULE_UNIT_ID).matches(nameValidation);
        assertThat(CloudEventExtensionConstants.RULE_UNIT_QUERY).matches(nameValidation);
    }

}