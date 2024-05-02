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
package org.drools.ancompiler;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.drools.core.reteoo.AlphaNode;
import org.drools.core.reteoo.BetaNode;
import org.drools.core.reteoo.CompositeObjectSinkAdapter;
import org.drools.core.reteoo.CompositeObjectSinkAdapter.FieldIndex;
import org.drools.core.reteoo.CompositePartitionAwareObjectSinkAdapter;
import org.drools.core.reteoo.LeftInputAdapterNode;
import org.drools.base.reteoo.NodeTypeEnums;
import org.drools.core.reteoo.ObjectSink;
import org.drools.core.reteoo.ObjectSinkNode;
import org.drools.core.reteoo.ObjectSinkPropagator;
import org.drools.core.reteoo.ObjectTypeNode;
import org.drools.core.reteoo.SingleObjectSinkAdapter;
import org.drools.core.reteoo.WindowNode;
import org.drools.base.rule.IndexableConstraint;
import org.drools.base.rule.constraint.AlphaNodeFieldConstraint;
import org.drools.core.util.index.AlphaRangeIndex;

/**
 * This class is used for reading an {@link ObjectTypeNode} using callbacks.
 * <p/>
 * The user defines a number of callback methods in a {@link NetworkHandler} that will be called when events occur
 * during parsing. The events include :
 * <li>ObjectTypeNode</li>
 * <li>Non-hashed and hashed AlphaNodes</li>
 * <li>BetaNodes</li>
 * <li>LeftInputAdapterNodes</li>
 * <p/>
 * Events are fired when each of these network features are encountered, and again when the end of them is encountered.
 * OTN parsing is unidirectional; previously parsed data cannot be re-read without starting the parsing operation again.
 */
public class ObjectTypeNodeParser {

    /**
     * OTN we are parsing/traversing
     */
    private final ObjectTypeNode objectTypeNode;
    private LinkedHashSet<IndexableConstraint> indexableConstraints = new LinkedHashSet<>();

    // When we have more than one indexable constraints we disable traversing of HashedAlphaNode
    private boolean traverseHashedAlphaNodes = true;

    public void setTraverseHashedAlphaNodes(boolean traverseHashedAlphaNodes) {
        this.traverseHashedAlphaNodes = traverseHashedAlphaNodes;
    }

    /**
     * Creates a new parser for the specified ObjectTypeNode
     *
     * @param objectTypeNode otn to parse
     */
    public ObjectTypeNodeParser(ObjectTypeNode objectTypeNode) {
        this.objectTypeNode = objectTypeNode;
    }

    /**
     * Parse the {@link #objectTypeNode}.
     * <p/>
     * <p>The application can use this method to instruct the OTN parser to begin parsing an {@link ObjectTypeNode}.</p>
     * <p/>
     * Once a parse is complete, an application may reuse the same Parser object, possibly with a different
     * {@link NetworkHandler}.</p>
     *
     * @param handler handler that will receieve the events generated by this parser
     * @see NetworkHandler
     */
    public void accept(NetworkHandler handler) {
        ObjectSinkPropagator propagator = objectTypeNode.getObjectSinkPropagator();

        handler.startObjectTypeNode(objectTypeNode);
        traversePropagator(propagator, handler);
        handler.endObjectTypeNode(objectTypeNode);
    }

    private void traversePropagator(ObjectSinkPropagator propagator, NetworkHandler handler) {
        if (propagator instanceof SingleObjectSinkAdapter) {
            // we know there is only a single child sink for this propagator
            ObjectSink sink = propagator.getSinks()[0];

            traverseSink(sink, handler);
        } else if (propagator instanceof CompositeObjectSinkAdapter) {
            CompositeObjectSinkAdapter composite = (CompositeObjectSinkAdapter) propagator;

            if(traverseHashedAlphaNodes) {
                traverseSinkList(composite.getRangeIndexableSinks(), handler);
                traverseSinkList(composite.getHashableSinks(), handler);
                traverseSinkList(composite.getOthers(), handler);
                traverseRangeIndexedAlphaNodes(composite.getRangeIndexMap(), handler);
                traverseHashedAlphaNodes(composite.getHashedSinkMap(), handler);
            } else {
                traverseSinkList(composite.getSinks(), handler);
            }
        } else if (propagator instanceof CompositePartitionAwareObjectSinkAdapter) {
            CompositePartitionAwareObjectSinkAdapter composite = (CompositePartitionAwareObjectSinkAdapter) propagator;
            traverseSinkList(composite.getSinks(), handler);
        }
    }
    private void traverseSinkList(List<? extends ObjectSinkNode> sinks, NetworkHandler handler) {
        if (sinks != null) {
            for (ObjectSinkNode sink : sinks) {
                traverseSink(sink, handler);
            }
        }
    }

    private void traverseSinkList(ObjectSink[] sinks, NetworkHandler handler) {
        if (sinks != null) {
            for (ObjectSink sink : sinks) {
                traverseSink(sink, handler);
            }
        }
    }

    private void traverseHashedAlphaNodes(Map<CompositeObjectSinkAdapter.HashKey, AlphaNode> hashedAlphaNodes, NetworkHandler handler) {
        if (hashedAlphaNodes != null && !hashedAlphaNodes.isEmpty()) {
            AlphaNode firstAlpha = hashedAlphaNodes.values().iterator().next();
            IndexableConstraint hashedFieldReader = getClassFieldReaderForHashedAlpha(firstAlpha);
            indexableConstraints.add(hashedFieldReader);

            // start the hashed alphas
            handler.startHashedAlphaNodes(hashedFieldReader);

            AlphaNode optionalNullAlphaNodeCase = null;
            for (Map.Entry<CompositeObjectSinkAdapter.HashKey, AlphaNode> entry : hashedAlphaNodes.entrySet()) {
                CompositeObjectSinkAdapter.HashKey hashKey = entry.getKey();
                AlphaNode alphaNode = entry.getValue();

                final Object objectValue = hashKey.getObjectValue();
                if (objectValue != null) {
                    handler.startHashedAlphaNode(alphaNode, objectValue);
                    // traverse the propagator for each alpha
                    traversePropagator(alphaNode.getObjectSinkPropagator(), handler);
                    handler.endHashedAlphaNode(alphaNode, hashKey.getObjectValue());
                } else {
                    optionalNullAlphaNodeCase = alphaNode;
                }
            }

            // end of the hashed alphas
            handler.endHashedAlphaNodes(hashedFieldReader);

            if (optionalNullAlphaNodeCase != null) {
                handler.nullCaseAlphaNodeStart(optionalNullAlphaNodeCase);
                traversePropagator(optionalNullAlphaNodeCase.getObjectSinkPropagator(), handler);
                handler.nullCaseAlphaNodeEnd(optionalNullAlphaNodeCase);
            }
        }
    }

    private void traverseRangeIndexedAlphaNodes(Map<CompositeObjectSinkAdapter.FieldIndex, AlphaRangeIndex> rangeIndexMap, NetworkHandler handler) {
        if (rangeIndexMap == null) {
            return;
        }
        Collection<Entry<FieldIndex, AlphaRangeIndex>> entrySet = rangeIndexMap.entrySet();
        for (Entry<FieldIndex, AlphaRangeIndex> entry : entrySet) {
            AlphaRangeIndex alphaRangeIndex = entry.getValue();

            handler.startRangeIndex(alphaRangeIndex);
            Collection<AlphaNode> alphaNodes = alphaRangeIndex.getAllValues();
            for (AlphaNode alphaNode : alphaNodes) {
                handler.startRangeIndexedAlphaNode(alphaNode);
                traversePropagator(alphaNode.getObjectSinkPropagator(), handler);
                handler.endRangeIndexedAlphaNode(alphaNode);
            }
            handler.endRangeIndex(alphaRangeIndex);
        }
    }

    private void traverseSink(ObjectSink sink, NetworkHandler handler) {
        if (sink.getType() == NodeTypeEnums.AlphaNode) {
            AlphaNode alphaNode = (AlphaNode) sink;

            handler.startNonHashedAlphaNode(alphaNode);

            traversePropagator(alphaNode.getObjectSinkPropagator(), handler);

            handler.endNonHashedAlphaNode(alphaNode);
        } else if (NodeTypeEnums.isBetaNode( sink ) ) {
            BetaNode betaNode = (BetaNode) sink;

            handler.startBetaNode(betaNode);
            handler.endBetaNode(betaNode);
        } else if (NodeTypeEnums.isLeftInputAdapterNode(sink)) {
            LeftInputAdapterNode leftInputAdapterNode = (LeftInputAdapterNode) sink;

            handler.startLeftInputAdapterNode(leftInputAdapterNode);
            handler.endWindowNode(leftInputAdapterNode);
        } else if (sink.getType() == NodeTypeEnums.WindowNode) {
            WindowNode windowNode = (WindowNode) sink;

            handler.startWindowNode(windowNode);
            handler.endWindowNode(windowNode);
        }
    }

    /**
     * Returns the ClassFieldReader for the hashed AlphaNode. The AlphaNode's constraint has to be a
     * MvelConstraint. This is the only type of hashed alpha currently supported.
     *
     * @param alphaNode hashed alpha to get reader for
     * @return ClassFieldReader
     */
    private IndexableConstraint getClassFieldReaderForHashedAlpha(final AlphaNode alphaNode) {
        final AlphaNodeFieldConstraint fieldConstraint = alphaNode.getConstraint();

        if (!(fieldConstraint instanceof IndexableConstraint)) {
            throw new IllegalArgumentException("Only support IndexableConstraint hashed AlphaNodes, not " + fieldConstraint.getClass());
        }
        // we need to get the first alpha in the map to get the attribute name that be use for the prefix of the
        // generated variable name
        return (IndexableConstraint) fieldConstraint;
    }

    public Set<IndexableConstraint> getIndexableConstraints() {
        return indexableConstraints;
    }

    public IndexableConstraint getIndexableConstraint() {
        IndexableConstraint[] constraintsArray = indexableConstraints
                .toArray(new IndexableConstraint[0]);

        return indexableConstraints.isEmpty() ? null :
                constraintsArray[constraintsArray.length - 1];
    }
}
