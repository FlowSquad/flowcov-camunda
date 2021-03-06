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

package io.flowcov.camunda.listeners;

import io.flowcov.camunda.junit.FlowCovTestRunState;
import io.flowcov.camunda.model.CoveredFlowNode;
import io.flowcov.camunda.model.CoveredSequenceFlow;
import lombok.val;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.camunda.bpm.engine.impl.bpmn.parser.AbstractBpmnParseListener;
import org.camunda.bpm.engine.impl.pvm.process.ActivityImpl;
import org.camunda.bpm.engine.impl.pvm.process.ScopeImpl;
import org.camunda.bpm.engine.impl.pvm.process.TransitionImpl;
import org.camunda.bpm.engine.impl.util.xml.Element;

import java.util.Arrays;
import java.util.List;

import static org.camunda.bpm.engine.delegate.ExecutionListener.*;

/**
 * Parse listener for adding coverage information to the test state.
 */
public class ElementCoverageParseListener extends AbstractBpmnParseListener {

    /**
     * The state of the currently running coverage test.
     */
    private FlowCovTestRunState coverageTestRunState;

    private static final List<String> EXECUTION_EVENTS = Arrays.asList(
            EVENTNAME_START,
            EVENTNAME_END);

    private final ExecutionListener executionListener;

    public ElementCoverageParseListener() {
        this.executionListener = this::execute;
    }

    private void execute(final DelegateExecution execution) {

        if (EVENTNAME_START.equals(execution.getEventName())) {
            val coveredActivity = this.createCoveredFlowNode(execution);
            coverageTestRunState.addCoveredElement(coveredActivity);
        }

        if (EVENTNAME_END.equals(execution.getEventName())) {
            val coveredActivity = this.createCoveredFlowNode(execution);
            coverageTestRunState.endCoveredElement(coveredActivity);
        }

        if (EVENTNAME_TAKE.equals(execution.getEventName())) {
            val coveredSequenceFlow = new CoveredSequenceFlow(
                    this.getProcessKey(execution),
                    execution.getCurrentTransitionId());
            coverageTestRunState.addCoveredElement(coveredSequenceFlow);
        }
    }

    private CoveredFlowNode createCoveredFlowNode(final DelegateExecution execution) {
        return new CoveredFlowNode(
                this.getProcessKey(execution),
                execution.getBpmnModelElementInstance().getId(),
                execution.getActivityInstanceId(),
                execution.getBpmnModelElementInstance().getElementType().getTypeName());
    }

    private String getProcessKey(final DelegateExecution delegateExecution) {
        return delegateExecution.getProcessEngineServices().getRepositoryService().getProcessDefinition(delegateExecution.getProcessDefinitionId()).getKey();
    }

    @Override
    public void parseUserTask(final Element userTaskElement, final ScopeImpl scope, final ActivityImpl activity) {
        this.addExecutionListener(activity);
    }

    @Override
    public void parseBoundaryErrorEventDefinition(final Element errorEventDefinition, final boolean interrupting, final ActivityImpl activity, final ActivityImpl nestedErrorEventActivity) {
        // this.addExecutionListener(activity);
    }

    @Override
    public void parseBoundaryEvent(final Element boundaryEventElement, final ScopeImpl scopeElement, final ActivityImpl activity) {
        this.addExecutionListener(activity);
    }

    @Override
    public void parseBoundaryMessageEventDefinition(final Element element, final boolean interrupting, final ActivityImpl activity) {
        //this.addExecutionListener(activity);
    }

    @Override
    public void parseBoundarySignalEventDefinition(final Element signalEventDefinition, final boolean interrupting, final ActivityImpl activity) {
        //this.addExecutionListener(activity);
    }

    @Override
    public void parseBoundaryTimerEventDefinition(final Element timerEventDefinition, final boolean interrupting, final ActivityImpl activity) {
        //this.addExecutionListener(activity);
    }

    @Override
    public void parseBusinessRuleTask(final Element businessRuleTaskElement, final ScopeImpl scope, final ActivityImpl activity) {
        this.addExecutionListener(activity);
    }

    @Override
    public void parseCallActivity(final Element callActivityElement, final ScopeImpl scope, final ActivityImpl activity) {
        this.addExecutionListener(activity);
    }

    @Override
    public void parseCompensateEventDefinition(final Element compensateEventDefinition, final ActivityImpl activity) {
        this.addExecutionListener(activity);
    }

    @Override
    public void parseEndEvent(final Element endEventElement, final ScopeImpl scope, final ActivityImpl activity) {
        this.addExecutionListener(activity);
    }

    @Override
    public void parseEventBasedGateway(final Element eventBasedGwElement, final ScopeImpl scope, final ActivityImpl activity) {
        this.addExecutionListener(activity);
    }

    @Override
    public void parseExclusiveGateway(final Element exclusiveGwElement, final ScopeImpl scope, final ActivityImpl activity) {
        this.addExecutionListener(activity);
    }

    @Override
    public void parseInclusiveGateway(final Element inclusiveGwElement, final ScopeImpl scope, final ActivityImpl activity) {
        this.addExecutionListener(activity);
    }

    @Override
    public void parseIntermediateCatchEvent(final Element intermediateEventElement, final ScopeImpl scope, final ActivityImpl activity) {
        this.addExecutionListener(activity);
    }

    @Override
    public void parseIntermediateMessageCatchEventDefinition(final Element messageEventDefinition, final ActivityImpl activity) {
        // this.addExecutionListener(activity);
    }

    @Override
    public void parseIntermediateSignalCatchEventDefinition(final Element signalEventDefinition, final ActivityImpl activity) {
        // this.addExecutionListener(activity);
    }

    @Override
    public void parseIntermediateThrowEvent(final Element intermediateEventElement, final ScopeImpl scope, final ActivityImpl activity) {
        this.addExecutionListener(activity);
    }

    @Override
    public void parseIntermediateTimerEventDefinition(final Element timerEventDefinition, final ActivityImpl activity) {
        // this.addExecutionListener(activity);
    }

    @Override
    public void parseManualTask(final Element manualTaskElement, final ScopeImpl scope, final ActivityImpl activity) {
        this.addExecutionListener(activity);
    }

    @Override
    public void parseMultiInstanceLoopCharacteristics(final Element activityElement, final Element multiInstanceLoopCharacteristicsElement, final ActivityImpl activity) {
        // DO NOT IMPLEMENT
        // we do not notify on entering a multi-instance activity, this will be done for every single execution inside that loop.
    }

    @Override
    public void parseParallelGateway(final Element parallelGwElement, final ScopeImpl scope, final ActivityImpl activity) {
        this.addExecutionListener(activity);
    }

//    @Override
//    public void parseProcess(final Element processElement, final ProcessDefinitionEntity processDefinition) {
//        for (final String event : EXECUTION_EVENTS) {
//            processDefinition.addListener(event, executionListener);
//        }
//    }

    @Override
    public void parseReceiveTask(final Element receiveTaskElement, final ScopeImpl scope, final ActivityImpl activity) {
        this.addExecutionListener(activity);
    }

    @Override
    public void parseScriptTask(final Element scriptTaskElement, final ScopeImpl scope, final ActivityImpl activity) {
        this.addExecutionListener(activity);
    }

    @Override
    public void parseSendTask(final Element sendTaskElement, final ScopeImpl scope, final ActivityImpl activity) {
        this.addExecutionListener(activity);
    }

    @Override
    public void parseSequenceFlow(final Element sequenceFlowElement, final ScopeImpl scopeElement, final TransitionImpl transition) {
        this.addExecutionListener(transition);
    }

    @Override
    public void parseServiceTask(final Element serviceTaskElement, final ScopeImpl scope, final ActivityImpl activity) {
        this.addExecutionListener(activity);
    }

    @Override
    public void parseStartEvent(final Element startEventElement, final ScopeImpl scope, final ActivityImpl activity) {
        this.addExecutionListener(activity);
    }

    @Override
    public void parseSubProcess(final Element subProcessElement, final ScopeImpl scope, final ActivityImpl activity) {
        this.addExecutionListener(activity);
    }

    @Override
    public void parseTask(final Element taskElement, final ScopeImpl scope, final ActivityImpl activity) {
        this.addExecutionListener(activity);
    }

    @Override
    public void parseTransaction(final Element transactionElement, final ScopeImpl scope, final ActivityImpl activity) {
        this.addExecutionListener(activity);
    }

    private void addExecutionListener(final ActivityImpl activity) {
        for (final String event : EXECUTION_EVENTS) {
            activity.addListener(event, executionListener);
        }
    }

    private void addExecutionListener(final TransitionImpl transition) {
        transition.addListener(EVENTNAME_TAKE, executionListener);
    }

    public void setCoverageTestRunState(final FlowCovTestRunState coverageTestRunState) {
        this.coverageTestRunState = coverageTestRunState;
    }

}
