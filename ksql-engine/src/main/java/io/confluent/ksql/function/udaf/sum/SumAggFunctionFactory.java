/**
 * Copyright 2017 Confluent Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 **/

package io.confluent.ksql.function.udaf.sum;

import org.apache.kafka.connect.data.Schema;

import java.util.Arrays;
import java.util.List;

import io.confluent.ksql.function.AggregateFunctionFactory;
import io.confluent.ksql.function.KsqlAggregateFunction;
import io.confluent.ksql.util.KsqlException;

public class SumAggFunctionFactory extends AggregateFunctionFactory {
  private static final String FUNCTION_NAME = "SUM";

  public SumAggFunctionFactory() {
    super(
        FUNCTION_NAME,
        Arrays.asList(
            new DoubleSumKudaf(FUNCTION_NAME, -1), new LongSumKudaf(FUNCTION_NAME, -1),
            new IntegerSumKudaf(FUNCTION_NAME,-1)));
  }

  @Override
  public KsqlAggregateFunction getProperAggregateFunction(final List<Schema> argTypeList) {
    // For now we only support aggregate functions with one arg.
    for (final KsqlAggregateFunction<?, ?> ksqlAggregateFunction : getAggregateFunctionList()) {
      if (ksqlAggregateFunction.hasSameArgTypes(argTypeList)) {
        return ksqlAggregateFunction;
      }
    }
    throw new KsqlException("No SUM aggregate function with " + argTypeList.get(0) + " "
                           + " argument type exists!");
  }

}
