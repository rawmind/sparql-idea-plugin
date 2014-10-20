package com.mn.plug.idea.sparql4idea.ui;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBPanel;
import com.mn.plug.idea.sparql4idea.core.DbLink;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.net.URI;

/**
 * @author Andrey Kovrov
 */
public class OptionDialog extends DialogWrapper {

  private final JBPanel panel;
  private final JTextField name;
  private final JTextField uri;
  private final MainPanel parent;
  private final ActionType actionType;

  protected OptionDialog(@Nullable Project project, final MainPanel parent, ActionType actionType) {
    super(project);
    this.parent = parent;
    this.actionType = actionType;
    setTitle(actionType.getTitle());
    super.setSize(500, 100);
    panel = new JBPanel(new GridBagLayout());
    name = new JTextField();
    uri = new JTextField();
    if (actionType == ActionType.MODIFY || actionType == ActionType.DELETE) {
      DbLink selectedItem = (DbLink) parent.dblinkModel.getSelectedItem();
      name.setText(selectedItem.name);
      uri.setText(selectedItem.uri.toString());
    }
    name.setEnabled(actionType.modifyAccepted);
    uri.setEnabled(actionType.modifyAccepted);
    GridBagConstraints c = new GridBagConstraints();
    c.fill = GridBagConstraints.BOTH;
    c.anchor = GridBagConstraints.WEST;
    c.gridwidth = 1;
    c.gridheight = 1;
    c.weightx = 0.3;
    c.gridx = 0;
    c.gridy = 0;
    JBLabel nameLabel = new JBLabel("Name:");
    panel.add(nameLabel, c);
    c.gridy = 0;
    c.gridx = 1;
    panel.add(name, c);
    c.gridy = 1;
    c.gridx = 0;
    JBLabel uriLabel = new JBLabel("URI:");
    panel.add(uriLabel, c);
    c.gridy = 1;
    c.gridx = 1;
    panel.add(uri, c);
    init();
  }

  @Nullable
  @Override
  protected JComponent createCenterPanel() {
    return panel;
  }

  @Override
  protected void doOKAction() {
    DbLink anObject = new DbLink(URI.create(uri.getText()), name.getText());
    switch (actionType) {
      case ADD:
        parent.dblinkModel.addElement(anObject);
        parent.dblinkModel.setSelectedItem(anObject);
        break;
      case MODIFY:
        parent.dblinkModel.removeElement(parent.dblinkModel.getSelectedItem());
        parent.dblinkModel.addElement(anObject);
        parent.dblinkModel.setSelectedItem(anObject);
        break;
      case DELETE:
        parent.dblinkModel.removeElement(parent.dblinkModel.getSelectedItem());
        break;
    }
    super.doOKAction();
  }

  public enum ActionType {
    MODIFY("Edit host", true), DELETE("Remove host", false), ADD("Add new host", true);

    String title;
    boolean modifyAccepted;

    ActionType(String title, boolean modifyAccepted) {
      this.title = title;
      this.modifyAccepted = modifyAccepted;
    }

    public String getTitle() {
      return title;
    }
  }

}
