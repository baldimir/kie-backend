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
package org.kie.kogito.serverless.workflow.suppliers;

import java.util.function.Supplier;

import org.jbpm.compiler.canonical.descriptors.ExpressionUtils;
import org.jbpm.process.instance.impl.ExpressionReturnValueEvaluator;

import com.github.javaparser.ast.expr.Expression;

public class ExpressionReturnValueEvaluatorSupplier extends ExpressionReturnValueEvaluator implements
        Supplier<Expression> {

    private final Expression expr;

    public ExpressionReturnValueEvaluatorSupplier(String lang, String expression, String rootName, Class<?> returnType) {
        super(lang, expression, rootName);
        expr = ExpressionUtils.getObjectCreationExpr(ExpressionReturnValueEvaluator.class, lang, expression, rootName, returnType);
    }

    @Override
    public Expression get() {
        return expr;
    }
}
