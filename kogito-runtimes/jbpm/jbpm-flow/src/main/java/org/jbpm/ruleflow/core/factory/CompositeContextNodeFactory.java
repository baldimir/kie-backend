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
import org.jbpm.workflow.core.node.CompositeContextNode;
import org.kie.api.definition.process.WorkflowElementIdentifier;

public class CompositeContextNodeFactory<T extends RuleFlowNodeContainerFactory<T, ?>> extends AbstractCompositeNodeFactory<CompositeContextNodeFactory<T>, T> {

    public static final String METHOD_VARIABLE = "variable";
    public static final String METHOD_LINK_INCOMING_CONNECTIONS = "linkIncomingConnections";
    public static final String METHOD_LINK_OUTGOING_CONNECTIONS = "linkOutgoingConnections";
    public static final String METHOD_AUTO_COMPLETE = "autoComplete";

    public CompositeContextNodeFactory(T nodeContainerFactory, NodeContainer nodeContainer, WorkflowElementIdentifier id) {
        super(nodeContainerFactory, nodeContainer, new CompositeContextNode(), id);
    }

    @Override
    protected CompositeContextNode getCompositeNode() {
        return (CompositeContextNode) node;
    }
}
