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

package org.optaplanner.constraint.streams.bavet.uni;

import java.util.function.Function;

import org.optaplanner.constraint.streams.bavet.common.TupleLifecycle;
import org.optaplanner.constraint.streams.bavet.quad.QuadTuple;
import org.optaplanner.constraint.streams.bavet.quad.QuadTupleImpl;
import org.optaplanner.core.config.solver.EnvironmentMode;
import org.optaplanner.core.impl.util.Quadruple;

final class Group4Mapping0CollectorUniNode<OldA, A, B, C, D>
        extends
        AbstractGroupUniNode<OldA, QuadTuple<A, B, C, D>, QuadTupleImpl<A, B, C, D>, Quadruple<A, B, C, D>, Void, Void> {

    private final int outputStoreSize;

    public Group4Mapping0CollectorUniNode(Function<OldA, A> groupKeyMappingA, Function<OldA, B> groupKeyMappingB,
            Function<OldA, C> groupKeyMappingC, Function<OldA, D> groupKeyMappingD, int groupStoreIndex,
            TupleLifecycle<QuadTuple<A, B, C, D>> nextNodesTupleLifecycle, int outputStoreSize,
            EnvironmentMode environmentMode) {
        super(groupStoreIndex,
                tuple -> createGroupKey(groupKeyMappingA, groupKeyMappingB, groupKeyMappingC, groupKeyMappingD, tuple),
                nextNodesTupleLifecycle, environmentMode);
        this.outputStoreSize = outputStoreSize;
    }

    private static <A, B, C, D, OldA> Quadruple<A, B, C, D> createGroupKey(Function<OldA, A> groupKeyMappingA,
            Function<OldA, B> groupKeyMappingB, Function<OldA, C> groupKeyMappingC, Function<OldA, D> groupKeyMappingD,
            UniTuple<OldA> tuple) {
        OldA oldA = tuple.getFactA();
        A a = groupKeyMappingA.apply(oldA);
        B b = groupKeyMappingB.apply(oldA);
        C c = groupKeyMappingC.apply(oldA);
        D d = groupKeyMappingD.apply(oldA);
        return Quadruple.of(a, b, c, d);
    }

    @Override
    protected QuadTupleImpl<A, B, C, D> createOutTuple(Quadruple<A, B, C, D> groupKey) {
        return new QuadTupleImpl<>(groupKey.getA(), groupKey.getB(), groupKey.getC(), groupKey.getD(), outputStoreSize);
    }

    @Override
    protected void updateOutTupleToResult(QuadTupleImpl<A, B, C, D> outTuple, Void unused) {
        throw new IllegalStateException("Impossible state: collector is null.");
    }

}
