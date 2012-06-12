/**
 * Licensed to jclouds, Inc. (jclouds) under one or more
 * contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  jclouds licenses this file
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
package org.jclouds.cloudwatch.domain;

import org.jclouds.javax.annotation.Nullable;

/**
 * @see <a href="http://docs.amazonwebservices.com/AmazonCloudWatch/latest/APIReference/API_StatisticSet.html" />
 *
 * @author Jeremy Whitlock
 */
public class StatisticValues {

   private final double maximum;
   private final double minimum;
   private final double sampleCount;
   private final double sum;

   public StatisticValues(double maximum, double minimum, double sampleCount, double sum) {
      this.maximum = maximum;
      this.minimum = minimum;
      this.sampleCount = sampleCount;
      this.sum = sum;
   }

   /**
    * return the maximum value of the sample set
    */
   @Nullable
   public double getMaximum() {
      return maximum;
   }

   /**
    * return the minimum value of the sample set
    */
   @Nullable
   public double getMinimum() {
      return minimum;
   }

   /**
    * return the number of samples used for the statistic set
    */
   @Nullable
   public double getSampleCount() {
      return sampleCount;
   }

   /**
    * return the sum of values for the sample set
    */
   @Nullable
   public double getSum() {
      return sum;
   }

   /**
    * Returns a new builder. The generated builder is equivalent to the builder
    * created by the {@link Builder} constructor.
    */
   public static Builder builder() {
      return new Builder();
   }

   public static class Builder {

      private double maximum;
      private double minimum;
      private double sampleCount;
      private double sum;

      /**
       * Creates a new builder. The returned builder is equivalent to the builder
       * generated by {@link org.jclouds.cloudwatch.domain.StatisticValues#builder}.
       */
      public Builder() {}

      /**
       * The maximum value of the sample set.
       *
       * @param maximum the maximum value of the sample set
       *
       * @return this {@code Builder} object
       */
      public Builder maximum(double maximum) {
         this.maximum = maximum;
         return this;
      }

      /**
       * The minimum value of the sample set.
       *
       * @param minimum the minimum value of the sample set
       *
       * @return this {@code Builder} object
       */
      public Builder minimum(double minimum) {
         this.minimum = minimum;
         return this;
      }

      /**
       * The the number of samples used for the statistic set.
       *
       * @param sampleCount the number of samples used for the statistic set
       *
       * @return this {@code Builder} object
       */
      public Builder sampleCount(double sampleCount) {
         this.sampleCount = sampleCount;
         return this;
      }

      /**
       * The sum of values for the sample set.
       *
       * @param sum the sum of values for the sample set
       *
       * @return this {@code Builder} object
       */
      public Builder sum(double sum) {
         this.sum = sum;
         return this;
      }

      /**
       * Returns a newly-created {@code StatisticSet} based on the contents of the {@code Builder}.
       */
      public StatisticValues build() {
         return new StatisticValues(maximum, minimum, sampleCount, sum);
      }

   }

}
