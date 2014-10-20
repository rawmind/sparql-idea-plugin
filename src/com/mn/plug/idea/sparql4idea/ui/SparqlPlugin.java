package com.mn.plug.idea.sparql4idea.ui;

import com.intellij.openapi.Disposable;
import com.intellij.openapi.components.*;
import com.intellij.openapi.editor.actionSystem.EditorActionManager;
import com.intellij.openapi.editor.actionSystem.TypedAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowAnchor;
import com.intellij.openapi.wm.ToolWindowContentUiType;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentManager;
import com.mn.plug.idea.sparql4idea.core.DbLink;
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
    Content toolContent = contentManager.getFactory().createContent(mainWindow.getMainPanel(), SPARQL_TOOL_WINDOW, false);
    contentManager.addContent(toolContent);
    if (state != null && state.inputText != null) {
      mainWindow.queryEditor.setText(state.inputText);
      for (Map.Entry<String, String> link : state.links.entrySet()) {
        mainWindow.dblinkModel.addElement(new DbLink(URI.create(link.getKey()), link.getValue()));
      }
    }
  }

  @Override
  public void projectClosed() {
    ToolWindowManager toolWindowManager = ToolWindowManager.getInstance(project);
    if (toolWindowManager.getToolWindow(SPARQL_TOOL_WINDOW) != null) {
      toolWindowManager.unregisterToolWindow(SPARQL_TOOL_WINDOW);
    }
    mainWindow.release();
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
      state.inputText = mainWindow.queryEditor.getText();
      for (int i = 0; i < mainWindow.dblinkModel.getSize(); i++) {
        DbLink elementAt = mainWindow.dblinkModel.getElementAt(i);
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
}
