package com.mn.plug.idea.sparql4idea.ui;

import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.SimpleToolWindowPanel;
import com.intellij.ui.EditorTextField;
import com.intellij.ui.components.JBCheckBox;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.table.JBTable;
import com.mn.plug.idea.sparql4idea.SparqlFileType;
import com.mn.plug.idea.sparql4idea.core.SparqlClient;
import com.mn.plug.idea.sparql4idea.lang.NameSpaces;
import org.openrdf.query.*;
import org.openrdf.repository.RepositoryConnection;

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
  private final SimpleToolWindowPanel mainPanel;
  private final JButton executeButton;
  private final JTextField addressString;
  private final JBCheckBox checkBox;

  private final JLabel errorLabel;
  protected final EditorTextField queryEditor;
  private final JBTable resultTable;
  private final NameSpaces normalizer = new NameSpaces();

  public MainPanel(Project project) {
    resultTable = new JBTable();
    errorLabel = new JLabel();
    mainPanel = new SimpleToolWindowPanel(false);
    executeButton = new JButton("Run query");
    executeButton.setMaximumSize(new Dimension(20, 20));
    addressString = new JTextField();
    Document document = EditorFactory.getInstance().createDocument(INIT_SELECT);
    queryEditor = new EditorTextField(document, project, SparqlFileType.SPARQL_FILE_TYPE);
    queryEditor.setOneLineMode(false);
    checkBox = new JBCheckBox("Update", false);
    configureLayout();
    ActionListener l = registerListeners();
    executeButton.addActionListener(l);
  }

  private ActionListener registerListeners() {
    return new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          executeQuery(checkBox.isSelected());
        }
      };
  }

  // todo: remove from EDT
  private void executeQuery(boolean isUpdate) {
    try {
      SparqlClient client = new SparqlClient(addressString.getText());
      RepositoryConnection con = client.getConnection();
      addressString.setText(client.url);
      errorLabel.setText(BLANK);
      if(isUpdate){
        Update update = con.prepareUpdate(QueryLanguage.SPARQL, queryEditor.getText());
        try {
          update.execute();
        } catch (UpdateExecutionException e) {
          errorLabel.setText(e.getMessage());
        }
        return;
      }
      TupleQuery tupleQuery =
              con.prepareTupleQuery(QueryLanguage.SPARQL, queryEditor.getText());
      DefaultTableModel dm = new DefaultTableModel();
      TupleQueryResult result = tupleQuery.evaluate();
      for (String binding : result.getBindingNames()) {
        dm.addColumn(binding);
      }
      try {
        while (result.hasNext()) {
          Vector<String> vector = new Vector<String>();
          BindingSet row = result.next();
          for (String binding : result.getBindingNames()) {
            String e = row.getValue(binding).stringValue();
            vector.add(normalizer.normalize(e));
          }
          dm.addRow(vector);
        }
      } finally {
        result.close();
      }
      resultTable.setModel(dm);
      resultTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
    } catch (Exception e1) {
      errorLabel.setText(e1.getMessage());
    }
  }

  private void configureLayout() {
    JBScrollPane jbScrollPane = new JBScrollPane(resultTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    GridBagLayout gridLayout = new GridBagLayout();
    GridBagConstraints c = new GridBagConstraints();
    mainPanel.setLayout(gridLayout);
    c.fill = GridBagConstraints.BOTH;
    c.gridwidth = 1;
    c.gridheight = 1;
    c.gridx = 0;
    c.weightx = 0.3;
    c.weighty = 1;
    c.gridy = 0;
    mainPanel.add(queryEditor, c);
    c = new GridBagConstraints();
    c.fill = GridBagConstraints.BOTH;
    c.gridx = 1;
    c.weightx = 1.7;
    c.gridy = 0;
    c.gridwidth = 1;
    c.gridheight = 2;
    mainPanel.add(jbScrollPane, c);
    c.fill = GridBagConstraints.NONE;
    c.weightx = 0.5;
    c.anchor = GridBagConstraints.CENTER;
    c.gridwidth = 1;
    c.gridheight = 1;
    c.gridx = 0;
    c.gridy = 1;
    mainPanel.add(executeButton, c);
    c.fill = GridBagConstraints.NONE;
    c.anchor = GridBagConstraints.WEST;
    c.weightx = 0.5;
    c.gridwidth = 0;
    c.gridheight = 1;
    c.gridx = 1;
    c.gridy = 3;
    mainPanel.add(checkBox, c);
    c.fill = GridBagConstraints.HORIZONTAL;
    c.weightx = 0.5;
    c.gridwidth = 1;
    c.gridheight = 1;
    c.gridx = 0;
    c.gridy = 3;
    mainPanel.add(addressString, c);
    c.fill = GridBagConstraints.BOTH;
    c.weightx = 1;
    c.gridwidth = 2;
    c.gridheight = 1;
    c.gridx = 0;
    c.gridy = 4;
    mainPanel.add(errorLabel, c);

  }

  public JPanel getMainPanel() {
    return mainPanel;
  }

  public void release() {
    Editor editor = queryEditor.getEditor();
    if(editor != null){
      EditorFactory.getInstance().releaseEditor(editor);
    }
  }

}
