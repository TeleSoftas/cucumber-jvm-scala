package io.cucumber.scala

import io.cucumber.core.backend.Glue

class GlueAdaptor(glue: Glue) {

  /**
   * Load the step definitions and hooks from a ScalaDsl instance into the glue.
   *
   * @param registry       ScalaDsl instance registry
   * @param scenarioScoped true for class instances, false for object singletons
   */
  def loadRegistry(registry: ScalaDslRegistry, scenarioScoped: Boolean): Unit = {
    registry.stepDefinitions.map(ScalaStepDefinition(_, scenarioScoped)).foreach(glue.addStepDefinition)
    registry.beforeHooks.map(ScalaHookDefinition(_, scenarioScoped)).foreach(glue.addBeforeHook)
    registry.afterHooks.map(ScalaHookDefinition(_, scenarioScoped)).foreach(glue.addAfterHook)
    registry.afterStepHooks.map(ScalaHookDefinition(_, scenarioScoped)).foreach(glue.addAfterStepHook)
    registry.beforeStepHooks.map(ScalaHookDefinition(_, scenarioScoped)).foreach(glue.addBeforeStepHook)
    registry.docStringTypes.map(ScalaDocStringTypeDefinition(_, scenarioScoped)).foreach(glue.addDocStringType)
    registry.dataTableTypes.map(ScalaDataTableTypeDefinition(_, scenarioScoped)).foreach(glue.addDataTableType)
    registry.parameterTypes.map(ScalaParameterTypeDefinition(_, scenarioScoped)).foreach(glue.addParameterType)
    registry.defaultParameterTransformers.map(ScalaDefaultParameterTransformerDefinition(_, scenarioScoped)).foreach(glue.addDefaultParameterTransformer)
    registry.defaultDataTableCellTransformers.map(ScalaDefaultDataTableCellTransformerDefinition(_, scenarioScoped)).foreach(glue.addDefaultDataTableCellTransformer)
    registry.defaultDataTableEntryTransformers.map(ScalaDefaultDataTableEntryTransformerDefinition(_, scenarioScoped)).foreach(glue.addDefaultDataTableEntryTransformer)
  }

}