package com.mn.plug.idea.sparql4idea.ui;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.impl.text.TextEditorProvider;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.openapi.ui.SimpleToolWindowPanel;
import com.intellij.ui.components.JBCheckBox;
import com.intellij.ui.components.JBPanel;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.table.JBTable;
import com.mn.plug.idea.sparql4idea.core.DbLink;
import com.mn.plug.idea.sparql4idea.core.Result;
import com.mn.plug.idea.sparql4idea.core.SparqlClient;
import com.mn.plug.idea.sparql4idea.vfs.SparqlConsole;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Andrey Kovrov
 */
public class MainPanel {

  private static final String BLANK = "";
  private final SimpleToolWindowPanel mainPanel;
  private final JButton executeButton;
  private final JButton addRepo;
  private final JButton openConsole;
  private final JButton delRepo;
  private final JButton editRepo;
  private final JBPanel optionPanel;
  protected final DefaultComboBoxModel<DbLink> dblinkModel;
  private final ComboBox repos;
  private final JBCheckBox updateRequest;
  private final Project project;
  private final JLabel messageLabel;
  private final JBTable resultTable;
  private Document document;
  private final SparqlConsole console = new SparqlConsole();
  public volatile String text = "";

  public MainPanel(final Project project) {
    this.project = project;
    resultTable = new JBTable();
    messageLabel = new JLabel();
    mainPanel = new SimpleToolWindowPanel(false);
    executeButton = new JButton("Run");
    executeButton.setEnabled(false);
    executeButton.setIcon(AllIcons.General.Run);
    updateRequest = new JBCheckBox("Update", false);
    openConsole = new JButton("Console");
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
        executeQuery(updateRequest.isSelected(), document.getText());
      }
    });
    openConsole.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        createConsole();
        executeButton.setEnabled(true);
      }
    });
  }

  // todo: remove from EDT
  private void executeQuery(boolean isUpdate, String text) {
    DbLink selectedItem = (DbLink) dblinkModel.getSelectedItem();
    SparqlClient client = new SparqlClient(selectedItem.uri.toString());
    messageLabel.setText(BLANK);
    if (isUpdate) {
      Result result = client.writeQuery(text);
      messageLabel.setText(result.hasError() ? result.getErrorMessage() : result.getMessage());
      return;
    }
    Result result = client.readQuery(text);
    resultTable.setModel(result.getTableDataModel());
    messageLabel.setText(result.hasError() ? result.getErrorMessage() : result.getMessage());
  }

  private void mainPanelLayout() {
    JBScrollPane jbScrollPane = new JBScrollPane(resultTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    GridBagConstraints c = new GridBagConstraints();
    optionPanelLayout();
    mainPanel.setLayout(new GridBagLayout());
    c.gridy = GridBagConstraints.RELATIVE;
    c.fill = GridBagConstraints.NONE;
    c.anchor = GridBagConstraints.WEST;
    c.gridx = 0;
    c.weightx = 1;
    mainPanel.add(optionPanel, c);
    c.weighty = 1;
    c.fill = GridBagConstraints.BOTH;
    mainPanel.add(jbScrollPane, c);
   // c.gridheight = 1;
    c.weighty = 0.1;
    mainPanel.add(messageLabel, c);
  }

  private void optionPanelLayout() {
    optionPanel.setLayout(new GridBagLayout());
    GridBagConstraints c = new GridBagConstraints();
    c.fill = GridBagConstraints.NONE;
    c.anchor = GridBagConstraints.WEST;
    c.gridx = GridBagConstraints.RELATIVE;
    optionPanel.add(repos, c);
    optionPanel.add(addRepo, c);
    optionPanel.add(delRepo, c);
    optionPanel.add(editRepo, c);
    c.anchor = GridBagConstraints.EAST;
    optionPanel.add(openConsole, c);
    optionPanel.add(updateRequest, c);
    optionPanel.add(executeButton, c);
  }

  public JPanel getMainPanel() {
    return mainPanel;
  }


  public String getText(){
    if(document!= null){
      text = document.getText();
    }
    return StringUtils.defaultString(text);
  }

  private void createConsole() {
    FileEditorManager fileEditorManager = FileEditorManager.getInstance(project);
    if (!fileEditorManager.isFileOpen(console)) {
      FileEditor[] fileEditors = fileEditorManager.openFile(console, true);
      Document[] documents = TextEditorProvider.getDocuments(fileEditors[0]);
      if (ArrayUtils.isEmpty(documents)) {
        throw new IllegalStateException("No documents found!");
      }
      document = documents[0];
      Application application = ApplicationManager.getApplication();
      application.runWriteAction(new Runnable() {
        @Override
        public void run() {
          document.setText(text);
        }
      });
    }
  }
}
