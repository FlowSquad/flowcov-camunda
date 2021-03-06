/*
 * Copyright 2020 FlowSquad GmbH
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
 *  limitations under the License.
 */

package io.flowcov.camunda.model;

import org.camunda.bpm.engine.repository.DecisionDefinition;
import org.camunda.bpm.engine.repository.ProcessDefinition;

import java.util.List;
import java.util.Set;

/**
 * A coverage that may have multiple deployed process definitions.
 */
public interface AggregatedCoverage {

    /**
     * Retrieves covered flow nodes for the given process definition key.
     *
     * @param processDefinitionKey
     * @return
     */
    List<CoveredFlowNode> getCoveredFlowNodes(String processDefinitionKey);

    /**
     * Retrieves covered sequence flow IDs for the given process definition key.
     *
     * @param processDefinitionKey
     * @return
     */
    List<CoveredSequenceFlow> getCoveredSequenceFlows(String processDefinitionKey);


    /**
     * Retrieces covered dmn rules for the given decision key
     *
     * @param decisionKey
     * @return
     */
    Set<CoveredDmnRule> getCoveredDecisionRules(String decisionKey);


    /**
     * Retrieves the process definitions of the coverage.
     *
     * @return
     */
    Set<ProcessDefinition> getProcessDefinitions();


    /**
     * Retrieves the decision definitions of the coverage.
     *
     * @return
     */
    Set<DecisionDefinition> getDecisionDefinitions();

}
