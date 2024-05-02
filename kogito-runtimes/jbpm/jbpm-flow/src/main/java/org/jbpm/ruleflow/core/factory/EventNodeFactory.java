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
package org.jbpm.ruleflow.core.factory;

import org.jbpm.ruleflow.core.RuleFlowNodeContainerFactory;
import org.jbpm.workflow.core.NodeContainer;
import org.jbpm.workflow.core.node.EventNode;
import org.kie.api.definition.process.WorkflowElementIdentifier;

public class EventNodeFactory<T extends RuleFlowNodeContainerFactory<T, ?>> extends AbstractEventNodeFactory<EventNodeFactory<T>, T> {

    public static final String METHOD_EVENT_TYPE = "eventType";
    public static final String METHOD_SCOPE = "scope";
    public static final String METHOD_VARIABLE_NAME = "variableName";
    public static final String METHOD_INPUT_VARIABLE_NAME = "inputVariableName";

    public EventNodeFactory(T nodeContainerFactory, NodeContainer nodeContainer, WorkflowElementIdentifier id) {
        super(nodeContainerFactory, nodeContainer, new EventNode(), id);
    }

    @Override
    protected EventNode getEventNode() {
        return (EventNode) getNode();
    }

}