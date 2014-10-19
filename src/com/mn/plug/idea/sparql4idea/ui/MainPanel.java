package com.mn.plug.idea.sparql4idea.ui;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorFactory;
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
import com.mn.plug.idea.sparql4idea.lang.NameSpaces;
import org.openrdf.query.BindingSet;
import org.openrdf.query.QueryEvaluationException;
import org.openrdf.query.TupleQueryResult;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

/**
 * @author Andrey Kovrov
 */
public class MainPanel {

  public static final String INIT_SELECT =
          "PREFIX w-m:<http://model.wiley.com/ontologies/EntityStore.owl#>\n" +
                  "PREFIX w-o:<http://entities.wiley.com/objects/>\n" +
                  "SELECT * \n" +
                  "WHERE {\n" +
                  "?o ?s ?p\n" +
                  "}\n" +
                  "limit 100";
  private static final String BLANK = "";
  public static final int MAX_RIGHT_GRID_POSITION = 5;
  public static final int MIX_LEFT_GRID_X_POSITION = 0;
  private final SimpleToolWindowPanel mainPanel;
  private final JButton executeButton;
  private final JButton addRepo;
  private final JButton delRepo;
  private final JButton confRepo;
  private final JBPanel optionPanel;
  protected final DefaultComboBoxModel<DbLink> dblinkModel;
  private final ComboBox repos;
  private final JTextField addressString;
  private final JBCheckBox updateRequest;

  private final JLabel errorLabel;
  protected final EditorTextField queryEditor;
  private final JBTable resultTable;
  private final NameSpaces normalizer = new NameSpaces();

  public MainPanel(final Project project) {
    resultTable = new JBTable();
    errorLabel = new JLabel();
    mainPanel = new SimpleToolWindowPanel(false);
    executeButton = new JButton("Run");
    executeButton.setIcon(AllIcons.General.Run);
    executeButton.setMaximumSize(new Dimension(20, 20));
    addressString = new JTextField();
    Document document = EditorFactory.getInstance().createDocument(INIT_SELECT);
    queryEditor = new EditorTextField(document, project, SparqlFileType.SPARQL_FILE_TYPE);
    queryEditor.setOneLineMode(false);
    updateRequest = new JBCheckBox("Update", false);
    addRepo = new JButton();
    addRepo.setIcon(AllIcons.General.Add);
    delRepo = new JButton();
    delRepo.setIcon(AllIcons.General.Remove);
    dblinkModel = new DefaultComboBoxModel<DbLink>();
    repos = new ComboBox(dblinkModel);
    dblinkModel.addElement(SparqlClient.DEFAULT_REPOSITORIES.get(0));
    optionPanel = new JBPanel();
    confRepo = new JButton();
    confRepo.setIcon(AllIcons.General.Settings);
    //
    configureLayout();
    ActionListener l = registerListeners();
    executeButton.addActionListener(l);
    final MainPanel p  = this;  //TODO: !!!!!
    addRepo.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        //TODO: !!!!!
        OptionDialog optionDialog = new OptionDialog(project,  p);
        optionDialog.show();
      }
    });
  }

  private ActionListener registerListeners() {
    return new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        executeQuery(updateRequest.isSelected());
      }
    };
  }

  // todo: remove from EDT
  private void executeQuery(boolean isUpdate) {
    DbLink selectedItem = (DbLink) dblinkModel.getSelectedItem();
      SparqlClient client = new SparqlClient(selectedItem.uri.toString());
    errorLabel.setText(BLANK);
    if (isUpdate) {
      Result result = client.writeQuery(queryEditor.getText());
      if (result.hasError()) {
        errorLabel.setText(result.getErrorMessage());
      }
    }
    DefaultTableModel dm = new DefaultTableModel();
    Result result = client.readQuery(queryEditor.getText());
    if (result.hasError()) {
      errorLabel.setText(result.getErrorMessage());
      return;
    }
    TupleQueryResult tupleQueryResult = result.getTupleQueryResult();
    try {
      for (String binding : tupleQueryResult.getBindingNames()) {
        dm.addColumn(binding);
      }
      while (tupleQueryResult.hasNext()) {
        Vector<String> vector = new Vector<String>();
        BindingSet row = tupleQueryResult.next();
        for (String binding : tupleQueryResult.getBindingNames()) {
          String e = row.getValue(binding).stringValue();
          vector.add(normalizer.normalize(e));
        }
        dm.addRow(vector);
      }
    } catch (QueryEvaluationException e) {
      errorLabel.setText(e.getMessage());
    } finally {
      try {
        tupleQueryResult.close();
      } catch (QueryEvaluationException e) {
        // skip
      }
    }
    resultTable.setModel(dm);
    resultTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
  }

  private void configureLayout() {
    JBScrollPane jbScrollPane = new JBScrollPane(resultTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    GridBagLayout gridLayout = new GridBagLayout();
    GridBagConstraints c = new GridBagConstraints();
    optionPanelLayout();
    mainPanel.setLayout(gridLayout);
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
    c.weightx = 1;
    c.gridwidth = 1;
    c.gridheight = 1;
    c.gridx = MIX_LEFT_GRID_X_POSITION;
    c.gridy = 5;
    mainPanel.add(errorLabel, c);
  }

  private void optionPanelLayout() {
    GridBagLayout gridLayout = new GridBagLayout();
    optionPanel.setLayout(gridLayout);
    GridBagConstraints c = new GridBagConstraints();
    c.fill = GridBagConstraints.NONE;
    c.anchor = GridBagConstraints.WEST;
    c.gridwidth = 1;
    c.gridheight = 1;
    c.gridx = GridBagConstraints.RELATIVE;
    optionPanel.add(repos, c);
    optionPanel.add(addRepo, c);
    optionPanel.add(delRepo, c);
    optionPanel.add(confRepo, c);
    c.anchor = GridBagConstraints.EAST;
    optionPanel.add(updateRequest, c);
    optionPanel.add(executeButton, c);
  }

  public JPanel getMainPanel() {
    return mainPanel;
  }

  public void release() {
    Editor editor = queryEditor.getEditor();
    if (editor != null) {
      EditorFactory.getInstance().releaseEditor(editor);
    }
  }

}
