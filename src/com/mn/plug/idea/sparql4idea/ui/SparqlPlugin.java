package com.mn.plug.idea.sparql4idea.ui;

import com.intellij.openapi.Disposable;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.components.StoragePathMacros;
import com.intellij.openapi.editor.actionSystem.EditorActionManager;
import com.intellij.openapi.editor.actionSystem.TypedAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowAnchor;
import com.intellij.openapi.wm.ToolWindowContentUiType;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentManager;
import com.mn.plug.idea.sparql4idea.client.DbLink;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.URI;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@State(name = "SparqlSettings", storages = {@Storage(id = "main", file = StoragePathMacros.WORKSPACE_FILE)})
public class SparqlPlugin implements ProjectComponent, PersistentStateComponent<ConfigurationState> {

  public static final String COMPONENT_NAME = "Sparql Plugin";
  public static final String SPARQL_TOOL_WINDOW = "SPARQL console";
  private final MainPanel mainWindow;
  public static volatile ConfigurationState state;
  public static final Lock lock = new ReentrantLock();
  private Project project;

  public SparqlPlugin(Project project) {
    this.project = project;
    mainWindow = new MainPanel(project);
    mainWindow.registerListeners();
  }

  @NotNull
  @Override
  public String getComponentName() {
    return COMPONENT_NAME;
  }

  @Override
  @SuppressWarnings("unused")
  public void projectOpened() {
    ToolWindowManager toolWindowManager = ToolWindowManager.getInstance(project);
    ToolWindow toolWindow = toolWindowManager.registerToolWindow(SPARQL_TOOL_WINDOW, true, ToolWindowAnchor.BOTTOM, new Disposable() {
      @Override
      public void dispose() {

      }
    }, true);
    TypedAction typedAction = EditorActionManager.getInstance().getTypedAction();
    //typedAction.setupHandler(new SparqlPluginTypedActionHandler(typedAction.getHandler()));
    toolWindow.setDefaultContentUiType(ToolWindowContentUiType.TABBED);
    ContentManager contentManager = toolWindow.getContentManager();
    Content toolContent = contentManager.getFactory().createContent(mainWindow.getMainPanel(), "", false);
    contentManager.addContent(toolContent);
    if (state != null) {
      mainWindow.text = returnIfNotBlank(mainWindow.text, state.inputText);
      for (Map.Entry<String, String> link : state.links.entrySet()) {
        mainWindow.dBlinkModel.addElement(new DbLink(URI.create(link.getKey()), link.getValue()));
      }
    }
  }

  @Override
  public void projectClosed() {
    ToolWindowManager toolWindowManager = ToolWindowManager.getInstance(project);
    if (toolWindowManager.getToolWindow(SPARQL_TOOL_WINDOW) != null) {
      toolWindowManager.unregisterToolWindow(SPARQL_TOOL_WINDOW);
    }
  }

  @Override
  public void initComponent() {

  }

  @Override
  public void disposeComponent() {
  }

  @Nullable
  @Override
  public ConfigurationState getState() {
    lock.lock();
    try {
      SparqlPlugin.state = new ConfigurationState();
      state.inputText = returnIfNotBlank(state.inputText, mainWindow.getText());
      for (int i = 0; i < mainWindow.dBlinkModel.getSize(); i++) {
        DbLink elementAt = mainWindow.dBlinkModel.getElementAt(i);
        state.links.put(elementAt.uri.toString(), elementAt.name);
      }
    } finally {
      lock.unlock();
    }

    return SparqlPlugin.state;
  }

  @Override
  public void loadState(ConfigurationState state) {
    lock.lock();
    try {
      SparqlPlugin.state = state;
    } finally {
      lock.unlock();
    }
  }

  public String returnIfNotBlank(String to, String what) {
    if (StringUtils.isNotBlank(what)) {
      to = what;
    }
    return to;
  }

}
