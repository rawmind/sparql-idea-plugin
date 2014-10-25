package com.mn.plug.idea.sparql4idea.ui;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.FileEditorManagerAdapter;
import com.intellij.openapi.fileEditor.FileEditorManagerListener;
import com.intellij.openapi.fileEditor.impl.text.TextEditorProvider;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.openapi.ui.SimpleToolWindowPanel;
import com.intellij.openapi.ui.popup.Balloon;
import com.intellij.openapi.ui.popup.BalloonBuilder;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.ui.components.JBCheckBox;
import com.intellij.ui.components.JBPanel;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.table.JBTable;
import com.mn.plug.idea.sparql4idea.client.DbLink;
import com.mn.plug.idea.sparql4idea.client.Result;
import com.mn.plug.idea.sparql4idea.client.SparqlClient;
import com.mn.plug.idea.sparql4idea.vfs.SparqlConsole;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;

/**
 * @author Andrey Kovrov
 */
public class MainPanel {

  private static final String BLANK = "";
  private final SimpleToolWindowPanel mainPanel;
  private final JButton executeButton;
  private final JButton cancelButton;
  private final JButton addRepo;
  private final JButton openConsole;
  private final JButton delRepo;
  private final JButton editRepo;
  private final JBPanel optionPanel;
  protected final DefaultComboBoxModel<DbLink> dBlinkModel;
  private final ComboBox repositories;
  private final JBCheckBox updateRequest;
  private final Project project;
  private final JLabel messageLabel;
  private final JBTable resultTable;
  private Document document;
  private final SparqlConsole console = new SparqlConsole();
  public volatile String text = "";
  private final JProgressBar progressBar;
  private final JBPopupFactory myPopupFactory;
  private SwingWorker<Result, Result> swingWorker;
  private static final Logger LOG =
          Logger.getInstance("#com.mn.plug.idea.sparql4idea.ui.MainPanel");

  public MainPanel(final Project project) {
    this.project = project;
    resultTable = new JBTable();
    messageLabel = new JLabel();
    mainPanel = new SimpleToolWindowPanel(false);
    executeButton = new JButton("Run");
    executeButton.setEnabled(false);
    executeButton.setIcon(AllIcons.Actions.Execute);
    updateRequest = new JBCheckBox("Update", false);
    openConsole = new JButton("Console");
    addRepo = new JButton();
    addRepo.setIcon(AllIcons.General.Add);
    delRepo = new JButton();
    delRepo.setIcon(AllIcons.General.Remove);
    dBlinkModel = new DefaultComboBoxModel<DbLink>();
    repositories = new ComboBox(dBlinkModel);
    optionPanel = new JBPanel();
    editRepo = new JButton();
    editRepo.setIcon(AllIcons.General.Settings);
    resultTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    resultTable.setColumnSelectionAllowed(true);
    resultTable.setRowSelectionAllowed(true);
    progressBar = new JProgressBar();
    progressBar.setMinimumSize(new Dimension(10, 30));
    myPopupFactory = JBPopupFactory.getInstance();
    cancelButton = new JButton();
    cancelButton.setIcon(AllIcons.Actions.Suspend);
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
        if (dBlinkModel.getSize() == 0) {
          createBallon("You must add db connection first.", addRepo);
          return;
        }
        OptionDialog optionDialog = new OptionDialog(project, p, OptionDialog.ActionType.MODIFY);
        optionDialog.show();
      }
    });
    delRepo.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (dBlinkModel.getSize() == 0) {
          return;
        }
        OptionDialog optionDialog = new OptionDialog(project, p, OptionDialog.ActionType.DELETE);
        optionDialog.show();
      }
    });
    executeButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (dBlinkModel.getSize() == 0) {
          createBallon("You must add at least one db connection.", addRepo);
          return;
        }
        messageLabel.setText(BLANK);
        final boolean selected = updateRequest.isSelected();
        final String queryText = document.getText();
        progressBar.setIndeterminate(true);
        swingWorker = new SwingWorker<Result, Result>() {
          @Override
          protected Result doInBackground() throws Exception {
            return executeQuery(selected, queryText);
          }

          @Override
          protected void done() {
            try {
              Result result = get();
              TableModel tableDataModel = result.getTableDataModel();
              resultTable.setRowSorter(new TableRowSorter<TableModel>(tableDataModel));
              resultTable.setShowColumns(true);
              resultTable.setModel(tableDataModel);
              messageLabel.setText(result.hasError() ? result.getErrorMessage() : result.getMessage());
            } catch (InterruptedException ex) {
              LOG.error("Data receive was interrupted!", ex);
            } catch (ExecutionException ex) {
              LOG.error("Exception occurred during data receive!", ex);
            } catch (CancellationException ex) {
              //it's ok
              LOG.warn("Execution was canceled.");
            }
            finally {
              progressBar.setIndeterminate(false);
            }
          }
        };
        swingWorker.execute();
      }
    });
    openConsole.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        createConsole();
        executeButton.setEnabled(true);
      }
    });
    cancelButton.addActionListener(new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if(swingWorker != null && !swingWorker.isCancelled() )
        swingWorker.cancel(true);
      }
    });
  }

  public JPanel getMainPanel() {
    return mainPanel;
  }

  public String getText() {
    if (document != null) {
      text = document.getText();
    }
    return StringUtils.defaultString(text);
  }

  private void createBallon(String text, JComponent inCenterOfThis) {
    JPanel content = new JPanel(new GridBagLayout());
    BalloonBuilder builder = myPopupFactory.createBalloonBuilder(content);
    builder.setDialogMode(true);
    builder.setAnimationCycle(200);
    builder.setTitle(text);
    Balloon balloon = builder.createBalloon();
    balloon.showInCenterOf(inCenterOfThis);
  }

  private Result executeQuery(boolean isUpdate, String text) {
    DbLink selectedItem = (DbLink) dBlinkModel.getSelectedItem();
    SparqlClient client = new SparqlClient(selectedItem.uri.toString());
    return isUpdate ? client.writeQuery(text) : client.readQuery(text);
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
    c.weighty = 0.1;
    mainPanel.add(messageLabel, c);
  }

  private void optionPanelLayout() {
    optionPanel.setLayout(new GridBagLayout());
    GridBagConstraints c = new GridBagConstraints();
    c.fill = GridBagConstraints.NONE;
    c.anchor = GridBagConstraints.WEST;
    c.gridx = GridBagConstraints.RELATIVE;
    optionPanel.add(repositories, c);
    optionPanel.add(addRepo, c);
    optionPanel.add(delRepo, c);
    optionPanel.add(editRepo, c);
    c.anchor = GridBagConstraints.EAST;
    optionPanel.add(openConsole, c);
    optionPanel.add(updateRequest, c);
    optionPanel.add(executeButton, c);
    optionPanel.add(cancelButton, c);
    optionPanel.add(progressBar, c);
  }

  private void createConsole() {
    FileEditorManager fileEditorManager = FileEditorManager.getInstance(project);
    if (!fileEditorManager.isFileOpen(console)) {
      project.getMessageBus().connect(project).subscribe(FileEditorManagerListener.FILE_EDITOR_MANAGER, new FileEditorManagerAdapter() {
        @Override
        public void fileClosed(@NotNull FileEditorManager source, @NotNull VirtualFile file) {
          if (file.equals(console)) {
            executeButton.setEnabled(false);
          }
        }
      });
      FileEditor[] fileEditors = fileEditorManager.openFile(console, true);
      Document[] documents = TextEditorProvider.getDocuments(fileEditors[0]);
      if (ArrayUtils.isEmpty(documents)) {
        throw new IllegalStateException("No documents found!");
      }
      assert documents != null;
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
