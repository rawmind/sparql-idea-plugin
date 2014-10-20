package com.mn.plug.idea.sparql4idea.ui;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.editor.impl.DocumentImpl;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.openapi.ui.SimpleToolWindowPanel;
import com.intellij.ui.EditorTextField;
import com.intellij.ui.components.JBCheckBox;
import com.intellij.ui.components.JBPanel;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.table.JBTable;
import com.mn.plug.idea.sparql4idea.SparqlFileType;
import com.mn.plug.idea.sparql4idea.core.DbLink;
import com.mn.plug.idea.sparql4idea.core.Result;
import com.mn.plug.idea.sparql4idea.core.SparqlClient;
import com.mn.plug.idea.sparql4idea.vfs.SparqlConsole;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Andrey Kovrov
 */
public class MainPanel {

  private static final String BLANK = "";
  public static final int MAX_RIGHT_GRID_POSITION = 5;
  public static final int MIX_LEFT_GRID_X_POSITION = 0;
  private final SimpleToolWindowPanel mainPanel;
  private final JButton executeButton;
  private final JButton addRepo;
  private final JButton delRepo;
  private final JButton editRepo;
  private final JBPanel optionPanel;
  protected final DefaultComboBoxModel<DbLink> dblinkModel;
  private final ComboBox repos;
  private final JBCheckBox updateRequest;
  private final Project project;
  private final JLabel messageLabel;
  protected final EditorTextField queryEditor;
  private final JBTable resultTable;
  private final Document document = new DocumentImpl("");

  public MainPanel(final Project project) {
    this.project = project;
    resultTable = new JBTable();
    messageLabel = new JLabel();
    mainPanel = new SimpleToolWindowPanel(false);
    executeButton = new JButton("Run");
    executeButton.setIcon(AllIcons.General.Run);
    queryEditor =new EditorTextField(document, project, SparqlFileType.SPARQL_FILE_TYPE);
    queryEditor.setOneLineMode(false);
    updateRequest = new JBCheckBox("Update", false);
    addRepo = new JButton();
    addRepo.setIcon(AllIcons.General.Add);
    delRepo = new JButton();
    delRepo.setIcon(AllIcons.General.Remove);
    dblinkModel = new DefaultComboBoxModel<DbLink>();
    repos = new ComboBox(dblinkModel);
    optionPanel = new JBPanel();
    editRepo = new JButton();
    editRepo.setIcon(AllIcons.General.Settings);
    resultTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    resultTable.setColumnSelectionAllowed(true);
    resultTable.setRowSelectionAllowed(true);
    //
    mainPanelLayout();
  }

  public void registerListeners() {
    final MainPanel p = this;
    addRepo.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        OptionDialog optionDialog = new OptionDialog(project, p, OptionDialog.ActionType.ADD);
        optionDialog.show();
      }
    });
    editRepo.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        OptionDialog optionDialog = new OptionDialog(project, p, OptionDialog.ActionType.MODIFY);
        optionDialog.show();
      }
    });
    delRepo.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        OptionDialog optionDialog = new OptionDialog(project, p, OptionDialog.ActionType.DELETE);
        optionDialog.show();
      }
    });
    executeButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        executeQuery(updateRequest.isSelected());
      }
    });
  }

  // todo: remove from EDT
  private void executeQuery(boolean isUpdate) {
    DbLink selectedItem = (DbLink) dblinkModel.getSelectedItem();
    SparqlClient client = new SparqlClient(selectedItem.uri.toString());
    messageLabel.setText(BLANK);
    if (isUpdate) {
      Result result = client.writeQuery(queryEditor.getText());
      messageLabel.setText(result.hasError() ? result.getErrorMessage() : result.getMessage());
      return;
    }
    Result result = client.readQuery(queryEditor.getText());
    resultTable.setModel(result.getTableDataModel());
    messageLabel.setText(result.hasError() ? result.getErrorMessage() : result.getMessage());
  }

  private void mainPanelLayout() {
    JBScrollPane jbScrollPane = new JBScrollPane(resultTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    GridBagConstraints c = new GridBagConstraints();
    optionPanelLayout();
    mainPanel.setLayout(new GridBagLayout());
    c.fill = GridBagConstraints.NONE;
    c.anchor = GridBagConstraints.WEST;
    c.gridwidth = 1;
    c.gridheight = 1;
    c.gridx = 0;
    c.weightx = 0.3;
    c.gridy = 0;
    mainPanel.add(optionPanel, c);
    c.fill = GridBagConstraints.BOTH;
    c.weightx = 0.3;
    c.weighty = 1;
    c.gridy = 1;
    mainPanel.add(queryEditor, c);
    c.fill = GridBagConstraints.BOTH;
    c.gridx = MAX_RIGHT_GRID_POSITION;
    c.weightx = 1.7;
    c.gridy = 0;
    c.gridwidth = 1;
    c.gridheight = 2;
    mainPanel.add(jbScrollPane, c);
    c.fill = GridBagConstraints.BOTH;
    c.weightx = 0.3;
    c.gridwidth = 1;
    c.gridheight = 1;
    c.gridx = MIX_LEFT_GRID_X_POSITION;
    c.gridy = 5;
    mainPanel.add(messageLabel, c);
  }

  private void optionPanelLayout() {
    optionPanel.setLayout(new GridBagLayout());
    GridBagConstraints c = new GridBagConstraints();
    c.fill = GridBagConstraints.NONE;
    c.anchor = GridBagConstraints.WEST;
    c.gridwidth = 1;
    c.gridheight = 1;
    c.gridx = GridBagConstraints.RELATIVE;
    optionPanel.add(repos, c);
    optionPanel.add(addRepo, c);
    optionPanel.add(delRepo, c);
    optionPanel.add(editRepo, c);
    c.anchor = GridBagConstraints.EAST;
    optionPanel.add(updateRequest, c);
    optionPanel.add(executeButton, c);
  }

  public JPanel getMainPanel() {
    return mainPanel;
  }

  public void release() {
    for (Editor editor : EditorFactory.getInstance().getEditors(document)) {
      EditorFactory.getInstance().releaseEditor(editor);
    }
  }

  private void createConsole(){
    FileEditor[] fileEditors = FileEditorManager.getInstance(project).openFile(new SparqlConsole(), true);
  }

}
